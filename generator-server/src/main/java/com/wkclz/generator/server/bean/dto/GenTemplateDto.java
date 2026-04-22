package com.wkclz.generator.server.bean.dto;

import com.wkclz.generator.server.bean.entity.GenTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description Create by sh-generator
 * @author shrimp
 * @table GenTemplate (代码生成-模板) 数据库实例扩展，代码重新生成不覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenTemplateDto extends GenTemplate {




    /**
     * entity 转 Dto
     * @param source
     * @return
     */
    public static GenTemplateDto copy(GenTemplate source) {
        GenTemplateDto target = new GenTemplateDto();
        GenTemplate.copy(source, target);
        return target;
    }
}

