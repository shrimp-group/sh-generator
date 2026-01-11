package com.wkclz.generator.server.service;


import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.mybatis.bean.ColumnInfo;
import com.wkclz.mybatis.bean.TableInfo;
import com.wkclz.mybatis.mapper.TableInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class GenService {


    @Autowired
    private GenTaskService genTaskService;
    @Autowired
    private TableInfoMapper tableInfoMapper;
    @Autowired
    private GenProjectService genProjectService;
    @Autowired
    private GenDatasourceService genDatasourceService;

    public Object getGenData(String projectCode) {
        GenProject project = genProjectService.getProjectByCode(projectCode);
        List<GenTask> tasks = genTaskService.getGenTaskList(projectCode);
        GenDatasource datasource = genDatasourceService.getDatasourceByCode(project.getDbCode());

        List<TableInfo> tables = getTables(project, datasource);
        List<ColumnInfo> columns = getColumns(project, datasource, tables);




        return null;
    }


    private List<TableInfo> getTables(GenProject project, GenDatasource datasource) {
        TableInfo info = new TableInfo();
        info.setTableSchema(datasource.getDbSchema());
        info.setTableName(project.getTablePrefix());
        return tableInfoMapper.getTables(info);
    }


    private List<ColumnInfo> getColumns(GenProject project, GenDatasource datasource, List<TableInfo> tables) {
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyList();
        }
        List<String> tableNames = tables.stream().map(TableInfo::getTableName).toList();
        TableInfo info = new TableInfo();
        info.setTableSchema(datasource.getDbSchema());
        info.setTableNames(tableNames);
        return tableInfoMapper.getColumns(info);
    }





}

