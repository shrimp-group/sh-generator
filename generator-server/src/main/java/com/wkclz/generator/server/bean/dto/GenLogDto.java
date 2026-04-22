package com.wkclz.generator.server.bean.dto;

import com.wkclz.generator.server.bean.entity.GenLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description Create by sh-generator
 * @author shrimp
 * @table GenLog (代码生成-日志) 数据库实例扩展，代码重新生成不覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenLogDto extends GenLog {




    /**
     * entity 转 Dto
     * @param source
     * @return
     */
    public static GenLogDto copy(GenLog source) {
        GenLogDto target = new GenLogDto();
        GenLog.copy(source, target);
        return target;
    }
}

