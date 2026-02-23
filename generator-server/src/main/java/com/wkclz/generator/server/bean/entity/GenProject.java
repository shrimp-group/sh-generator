package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;




/**
 * Description Create by sh-generator
 * @author shrimp
 * @table gen_project (代码生成-项目) 重新生成代码会覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenProject extends BaseEntity {

    /**
     * 项目编码(唯一标识)
     */
    @Desc("项目编码(唯一标识)")
    private String projectCode;

    /**
     * 用户名
     */
    @Desc("用户名")
    private String userCode;

    /**
     * 数据库编码
     */
    @Desc("数据库编码")
    private String dbCode;

    /**
     * 表前缀
     */
    @Desc("表前缀")
    private String tablePrefix;

    /**
     * 模块名(英文)
     */
    @Desc("模块名(英文)")
    private String moduleName;

    /**
     * 项目名称
     */
    @Desc("项目名称")
    private String projectName;

    /**
     * 项目说明
     */
    @Desc("项目说明")
    private String projectDesc;


    public static GenProject copy(GenProject source, GenProject target) {
        if (target == null ) { target = new GenProject();}
        if (source == null) { return target; }
        target.setId(source.getId());
        target.setProjectCode(source.getProjectCode());
        target.setUserCode(source.getUserCode());
        target.setDbCode(source.getDbCode());
        target.setTablePrefix(source.getTablePrefix());
        target.setModuleName(source.getModuleName());
        target.setProjectName(source.getProjectName());
        target.setProjectDesc(source.getProjectDesc());
        target.setSort(source.getSort());
        target.setCreateTime(source.getCreateTime());
        target.setCreateBy(source.getCreateBy());
        target.setUpdateTime(source.getUpdateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setRemark(source.getRemark());
        target.setVersion(source.getVersion());
        return target;
    }

    public static GenProject copyIfNotNull(GenProject source, GenProject target) {
        if (target == null ) { target = new GenProject();}
        if (source == null) { return target; }
        if (source.getId() != null) { target.setId(source.getId()); }
        if (source.getProjectCode() != null) { target.setProjectCode(source.getProjectCode()); }
        if (source.getUserCode() != null) { target.setUserCode(source.getUserCode()); }
        if (source.getDbCode() != null) { target.setDbCode(source.getDbCode()); }
        if (source.getTablePrefix() != null) { target.setTablePrefix(source.getTablePrefix()); }
        if (source.getModuleName() != null) { target.setModuleName(source.getModuleName()); }
        if (source.getProjectName() != null) { target.setProjectName(source.getProjectName()); }
        if (source.getProjectDesc() != null) { target.setProjectDesc(source.getProjectDesc()); }
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

