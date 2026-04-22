package com.wkclz.generator.server.bean.dto;

import com.wkclz.generator.server.bean.entity.GenProject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description Create by sh-generator
 * @author shrimp
 * @table GenProject (代码生成-项目) 数据库实例扩展，代码重新生成不覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenProjectDto extends GenProject {




    /**
     * entity 转 Dto
     * @param source
     * @return
     */
    public static GenProjectDto copy(GenProject source) {
        GenProjectDto target = new GenProjectDto();
        GenProject.copy(source, target);
        return target;
    }
}

