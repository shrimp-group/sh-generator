package com.wkclz.generator.server.bean.dto;

import com.wkclz.generator.server.bean.entity.GenDatasource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description Create by sh-generator
 * @author shrimp
 * @table GenDatasource () 数据库实例扩展，代码重新生成不覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenDatasourceDto extends GenDatasource {




    /**
     * entity 转 Dto
     * @param source
     * @return
     */
    public static GenDatasourceDto copy(GenDatasource source) {
        GenDatasourceDto target = new GenDatasourceDto();
        GenDatasource.copy(source, target);
        return target;
    }
}

