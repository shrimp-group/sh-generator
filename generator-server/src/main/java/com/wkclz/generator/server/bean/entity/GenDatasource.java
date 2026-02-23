package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;




/**
 * Description Create by sh-generator
 * @author shrimp
 * @table gen_datasource (代码生成-数据库) 重新生成代码会覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenDatasource extends BaseEntity {

    /**
     * 用户名
     */
    @Desc("用户名")
    private String userCode;

    /**
     * 数据源编码
     */
    @Desc("数据源编码")
    private String dbCode;

    /**
     * 数据库类型
     */
    @Desc("数据库类型")
    private String dbType;

    /**
     * 主机名
     */
    @Desc("主机名")
    private String dbHost;

    /**
     * 端口
     */
    @Desc("端口")
    private Integer dbPort;

    /**
     * 数据库名称
     */
    @Desc("数据库名称")
    private String dbSchema;

    /**
     * 数据库用户名
     */
    @Desc("数据库用户名")
    private String dbUsername;

    /**
     * 数据库密码
     */
    @Desc("数据库密码")
    private String dbPassword;


    public static GenDatasource copy(GenDatasource source, GenDatasource target) {
        if (target == null ) { target = new GenDatasource();}
        if (source == null) { return target; }
        target.setId(source.getId());
        target.setUserCode(source.getUserCode());
        target.setDbCode(source.getDbCode());
        target.setDbType(source.getDbType());
        target.setDbHost(source.getDbHost());
        target.setDbPort(source.getDbPort());
        target.setDbSchema(source.getDbSchema());
        target.setDbUsername(source.getDbUsername());
        target.setDbPassword(source.getDbPassword());
        target.setSort(source.getSort());
        target.setCreateTime(source.getCreateTime());
        target.setCreateBy(source.getCreateBy());
        target.setUpdateTime(source.getUpdateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setRemark(source.getRemark());
        target.setVersion(source.getVersion());
        return target;
    }

    public static GenDatasource copyIfNotNull(GenDatasource source, GenDatasource target) {
        if (target == null ) { target = new GenDatasource();}
        if (source == null) { return target; }
        if (source.getId() != null) { target.setId(source.getId()); }
        if (source.getUserCode() != null) { target.setUserCode(source.getUserCode()); }
        if (source.getDbCode() != null) { target.setDbCode(source.getDbCode()); }
        if (source.getDbType() != null) { target.setDbType(source.getDbType()); }
        if (source.getDbHost() != null) { target.setDbHost(source.getDbHost()); }
        if (source.getDbPort() != null) { target.setDbPort(source.getDbPort()); }
        if (source.getDbSchema() != null) { target.setDbSchema(source.getDbSchema()); }
        if (source.getDbUsername() != null) { target.setDbUsername(source.getDbUsername()); }
        if (source.getDbPassword() != null) { target.setDbPassword(source.getDbPassword()); }
        if (source.getSort() != null) { target.setSort(source.getSort()); }
        if (source.getCreateTime() != null) { target.setCreateTime(source.getCreateTime()); }
        if (source.getCreateBy() != null) { target.setCreateBy(source.getCreateBy()); }
        if (source.getUpdateTime() != null) { target.setUpdateTime(source.getUpdateTime()); }
        if (source.getUpdateBy() != null) { target.setUpdateBy(source.getUpdateBy()); }
        if (source.getRemark() != null) { target.setRemark(source.getRemark()); }
        if (source.getVersion() != null) { target.setVersion(source.getVersion()); }
        return target;
    }

}

