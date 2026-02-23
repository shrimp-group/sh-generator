package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;




/**
 * Description Create by sh-generator
 * @author shrimp
 * @table gen_template (代码生成-模板) 重新生成代码会覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenTemplate extends BaseEntity {

    /**
     * 用户名
     */
    @Desc("用户名")
    private String userCode;

    /**
     * 模板编码
     */
    @Desc("模板编码")
    private String tempCode;

    /**
     * 模板Key
     */
    @Desc("模板Key")
    private String tempKey;

    /**
     * 模板名称
     */
    @Desc("模板名称")
    private String tempName;

    /**
     * 生成的文件后缀
     */
    @Desc("生成的文件后缀")
    private String tempSubfix;

    /**
     * 模板描述
     */
    @Desc("模板描述")
    private String tempDesc;

    /**
     * 模板内容
     */
    @Desc("模板内容")
    private String tempContent;


    public static GenTemplate copy(GenTemplate source, GenTemplate target) {
        if (target == null ) { target = new GenTemplate();}
        if (source == null) { return target; }
        target.setId(source.getId());
        target.setUserCode(source.getUserCode());
        target.setTempCode(source.getTempCode());
        target.setTempKey(source.getTempKey());
        target.setTempName(source.getTempName());
        target.setTempSubfix(source.getTempSubfix());
        target.setTempDesc(source.getTempDesc());
        target.setTempContent(source.getTempContent());
        target.setSort(source.getSort());
        target.setCreateTime(source.getCreateTime());
        target.setCreateBy(source.getCreateBy());
        target.setUpdateTime(source.getUpdateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setRemark(source.getRemark());
        target.setVersion(source.getVersion());
        return target;
    }

    public static GenTemplate copyIfNotNull(GenTemplate source, GenTemplate target) {
        if (target == null ) { target = new GenTemplate();}
        if (source == null) { return target; }
        if (source.getId() != null) { target.setId(source.getId()); }
        if (source.getUserCode() != null) { target.setUserCode(source.getUserCode()); }
        if (source.getTempCode() != null) { target.setTempCode(source.getTempCode()); }
        if (source.getTempKey() != null) { target.setTempKey(source.getTempKey()); }
        if (source.getTempName() != null) { target.setTempName(source.getTempName()); }
        if (source.getTempSubfix() != null) { target.setTempSubfix(source.getTempSubfix()); }
        if (source.getTempDesc() != null) { target.setTempDesc(source.getTempDesc()); }
        if (source.getTempContent() != null) { target.setTempContent(source.getTempContent()); }
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

