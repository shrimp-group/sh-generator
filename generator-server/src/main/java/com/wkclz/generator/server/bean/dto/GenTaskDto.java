package com.wkclz.generator.server.bean.dto;

import com.wkclz.generator.server.bean.entity.GenTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description Create by sh-generator
 * @author shrimp
 * @table GenTask (代码生成-任务) 数据库实例扩展，代码重新生成不覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenTaskDto extends GenTask {


    /**
     * 模板key
     */
    private String tempKey;




    /**
     * entity 转 Dto
     * @param source
     * @return
     */
    public static GenTaskDto copy(GenTask source) {
        GenTaskDto target = new GenTaskDto();
        GenTask.copy(source, target);
        return target;
    }
}

