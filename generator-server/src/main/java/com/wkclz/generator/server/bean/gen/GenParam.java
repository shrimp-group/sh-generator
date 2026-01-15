package com.wkclz.generator.server.bean.gen;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class GenParam implements Serializable {

    // 所有任务
    private Map<String, GenPkg> pkgs;
    // 表结构信息
    private GenTable table;

    // 所有列 (对象返回列)
    private List<GenColumn> fullColumns;
    // 业务列
    private List<GenColumn> bizColumns;

    // 列表返回列
    private List<GenColumn> listColumns;
    // 查询参数列表
    private List<GenColumn> queryColumns;
    // 新增列
    private List<GenColumn> insertColumns;
    // 修改列
    private List<GenColumn> updateColumns;


}
