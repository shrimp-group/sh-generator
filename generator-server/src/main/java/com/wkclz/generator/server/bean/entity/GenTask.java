package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;




/**
 * Description Create by sh-generator
 * @author shrimp
 * @table gen_task (代码生成-任务) 重新生成代码会覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenTask extends BaseEntity {

    /**
     * 用户名
     */
    @Desc("用户名")
    private String userCode;

    /**
     * 项目编码
     */
    @Desc("项目编码")
    private String projectCode;

    /**
     * 模板编码
     */
    @Desc("模板编码")
    private String tempCode;

    /**
     * 任务名称
     */
    @Desc("任务名称")
    private String taskName;

    /**
     * 是否生成
     */
    @Desc("是否生成")
    private Integer createSwitch;

    /**
     * 是否删除(本地模式有效)
     */
    @Desc("是否删除(本地模式有效)")
    private Integer deleteSwitch;

    /**
     * 任务项目基本路径
     */
    @Desc("任务项目基本路径")
    private String projectBasePath;

    /**
     * 任务包路径
     */
    @Desc("任务包路径")
    private String packagePath;

    /**
     * 任务描述
     */
    @Desc("任务描述")
    private String taskDesc;


    public static GenTask copy(GenTask source, GenTask target) {
        if (target == null ) { target = new GenTask();}
        if (source == null) { return target; }
        target.setId(source.getId());
        target.setUserCode(source.getUserCode());
        target.setProjectCode(source.getProjectCode());
        target.setTempCode(source.getTempCode());
        target.setTaskName(source.getTaskName());
        target.setCreateSwitch(source.getCreateSwitch());
        target.setDeleteSwitch(source.getDeleteSwitch());
        target.setProjectBasePath(source.getProjectBasePath());
        target.setPackagePath(source.getPackagePath());
        target.setTaskDesc(source.getTaskDesc());
        target.setSort(source.getSort());
        target.setCreateTime(source.getCreateTime());
        target.setCreateBy(source.getCreateBy());
        target.setUpdateTime(source.getUpdateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setRemark(source.getRemark());
        target.setVersion(source.getVersion());
        return target;
    }

    public static GenTask copyIfNotNull(GenTask source, GenTask target) {
        if (target == null ) { target = new GenTask();}
        if (source == null) { return target; }
        if (source.getId() != null) { target.setId(source.getId()); }
        if (source.getUserCode() != null) { target.setUserCode(source.getUserCode()); }
        if (source.getProjectCode() != null) { target.setProjectCode(source.getProjectCode()); }
        if (source.getTempCode() != null) { target.setTempCode(source.getTempCode()); }
        if (source.getTaskName() != null) { target.setTaskName(source.getTaskName()); }
        if (source.getCreateSwitch() != null) { target.setCreateSwitch(source.getCreateSwitch()); }
        if (source.getDeleteSwitch() != null) { target.setDeleteSwitch(source.getDeleteSwitch()); }
        if (source.getProjectBasePath() != null) { target.setProjectBasePath(source.getProjectBasePath()); }
        if (source.getPackagePath() != null) { target.setPackagePath(source.getPackagePath()); }
        if (source.getTaskDesc() != null) { target.setTaskDesc(source.getTaskDesc()); }
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

