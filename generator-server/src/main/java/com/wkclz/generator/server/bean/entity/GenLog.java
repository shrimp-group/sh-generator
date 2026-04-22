package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.FieldDesc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * Description Create by sh-generator
 * @author shrimp
 * @table gen_log (代码生成-日志) 重新生成代码会覆盖
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GenLog extends BaseEntity {

    /**
     * 用户名
     */
    @FieldDesc(value = "用户名")
    private String userCode;

    /**
     * 项目编码
     */
    @FieldDesc(value = "项目编码")
    private String projectCode;

    /**
     * 授权码
     */
    @FieldDesc(value = "授权码")
    private String authCode;

    /**
     * 生成路径
     */
    @FieldDesc(value = "生成路径")
    private String genPath;

    /**
     * 开始时间
     */
    @FieldDesc(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @FieldDesc(value = "结束时间")
    private LocalDateTime endTime;


    public static GenLog copy(GenLog source, GenLog target) {
        if (target == null ) { target = new GenLog();}
        if (source == null) { return target; }
        target.setId(source.getId());
        target.setUserCode(source.getUserCode());
        target.setProjectCode(source.getProjectCode());
        target.setAuthCode(source.getAuthCode());
        target.setGenPath(source.getGenPath());
        target.setStartTime(source.getStartTime());
        target.setEndTime(source.getEndTime());
        target.setSort(source.getSort());
        target.setCreateTime(source.getCreateTime());
        target.setCreateBy(source.getCreateBy());
        target.setUpdateTime(source.getUpdateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setRemark(source.getRemark());
        target.setVersion(source.getVersion());
        return target;
    }

    public static GenLog copyIfNotNull(GenLog source, GenLog target) {
        if (target == null ) { target = new GenLog();}
        if (source == null) { return target; }
        if (source.getId() != null) { target.setId(source.getId()); }
        if (source.getUserCode() != null) { target.setUserCode(source.getUserCode()); }
        if (source.getProjectCode() != null) { target.setProjectCode(source.getProjectCode()); }
        if (source.getAuthCode() != null) { target.setAuthCode(source.getAuthCode()); }
        if (source.getGenPath() != null) { target.setGenPath(source.getGenPath()); }
        if (source.getStartTime() != null) { target.setStartTime(source.getStartTime()); }
        if (source.getEndTime() != null) { target.setEndTime(source.getEndTime()); }
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

