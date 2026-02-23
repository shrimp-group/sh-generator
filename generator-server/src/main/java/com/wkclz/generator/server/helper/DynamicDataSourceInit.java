package com.wkclz.generator.server.helper;

import cn.hutool.core.util.StrUtil;
import com.wkclz.core.exception.ValidationException;
import com.wkclz.dynamicdb.DynamicDataSourceFactory;
import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.generator.server.bean.enums.DatasourceTypeEnum;
import com.wkclz.generator.server.service.GenDatasourceService;
import com.wkclz.mybatis.bean.DataSourceInfo;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DynamicDataSourceInit implements DynamicDataSourceFactory {

    @Autowired
    private GenDatasourceService genDatasourceService;

    @SneakyThrows
    @Override
    public DataSourceInfo createDataSource(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        GenDatasource ds = genDatasourceService.getDatasourceByCode(key);
        if (ds == null) {
            throw ValidationException.of("找不到数据信息: " + key);
        }

        DatasourceTypeEnum anEnum = EnumUtils.getEnum(DatasourceTypeEnum.class, ds.getDbType());
        if (anEnum == null) {
            throw ValidationException.of("不能识别的数据库类型: " + ds.getDbType());
        }
        if (anEnum != DatasourceTypeEnum.MYSQL && anEnum != DatasourceTypeEnum.MARIADB) {
            throw ValidationException.of("无法识别的数据库类型");
        }

        String url = StrUtil.format("jdbc:mysql://{}:{}/{}?useUnicode=true&characterEncoding=utf8&useSSL=false",
            ds.getDbHost(), ds.getDbPort(), ds.getDbSchema());

        String password = ds.getDbPassword();
        /*
        if (password != null && password.startsWith(BaseConstant.CONFIG_ENCRYPTED_PREFIX)){
            password = password.substring(BaseConstant.CONFIG_ENCRYPTED_PREFIX.length());
            password = SecretUtil.getDecryptPassword(password);
        }
        */

        DataSourceInfo info = new DataSourceInfo();
        info.setUrl(url);
        info.setUsername(ds.getDbUsername());
        info.setPassword(password);
        return info;
    }
}

