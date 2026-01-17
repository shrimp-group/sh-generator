package com.wkclz.generator.server.helper;

import com.wkclz.core.base.DbColumnEntity;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.bean.gen.GenColumn;
import com.wkclz.generator.server.bean.gen.GenParam;
import com.wkclz.generator.server.bean.gen.GenPkg;
import com.wkclz.generator.server.bean.gen.GenTable;
import com.wkclz.mybatis.bean.ColumnInfo;
import com.wkclz.mybatis.bean.DataTypeEnum;
import com.wkclz.mybatis.bean.TableInfo;
import com.wkclz.tool.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GenParamHFetchelper {


    private final static List<String> BLOB_FIELDS = List.of("TEXT", "MEDIUMTEXT", "TINYTEXT", "LONGTEXT", "JSON");
    private final static List<String> INSERT_IGNORE_FIELDS = List.of("id", "createTime", "updateTime", "version");
    private final static List<String> UPDATE_IGNORE_FIELDS = List.of("id", "createBy", "createTime", "updateTime", "version");


    public static List<GenParam> fetchGenParam(List<TableInfo> tables, List<ColumnInfo> columns, List<GenTask> tasks) {
        List<GenParam> params = new ArrayList<>();
        if (CollectionUtils.isEmpty(tables)) {
            return params;
        }
        if (CollectionUtils.isEmpty(columns)) {
            return params;
        }
        if (CollectionUtils.isEmpty(tasks)) {
            return params;
        }
        List<String> baseFields = getBaseFields();
        Map<String, List<GenColumn>> columnsMap = getColumns(columns);

        Map<String, GenPkg> pkgs = getPkgMap(tasks);
        for (TableInfo table : tables) {
            GenParam param = new GenParam();
            params.add(param);
            param.setPkgs(pkgs);
            GenTable genTable = getTable(table);
            param.setTable(genTable);
            List<GenColumn> tableColumns = columnsMap.get(genTable.getTableName());

            param.setFullColumns(tableColumns);

            List<GenColumn> bizColumns = tableColumns.stream().filter(t -> !baseFields.contains(t.getFieldName())).toList();
            List<GenColumn> listColumns = tableColumns.stream().filter(t -> !BLOB_FIELDS.contains(t.getDbType())).toList();
            List<GenColumn> insertColumns = tableColumns.stream().filter(t -> !INSERT_IGNORE_FIELDS.contains(t.getFieldName())).toList();
            List<GenColumn> updateColumns = tableColumns.stream().filter(t -> !UPDATE_IGNORE_FIELDS.contains(t.getFieldName())).toList();

            List<GenColumn> queryColumns = tableColumns.stream()
                    .filter(t -> !baseFields.contains(t.getFieldName()))
                    .filter(t -> !BLOB_FIELDS.contains(t.getDbType()))
                    .toList();

            param.setBizColumns(bizColumns);
            param.setListColumns(listColumns);
            param.setQueryColumns(queryColumns);
            param.setInsertColumns(insertColumns);
            param.setUpdateColumns(updateColumns);

            for (GenColumn c : bizColumns) {
                if ("LocalDateTime".equals(c.getJavaType())) {
                    genTable.setDatetimeImport("import java.time.LocalDateTime;");
                    continue;
                }
                if ("BigDecimal".equals(c.getJavaType())) {
                    genTable.setBigdecimalImport("import java.math.BigDecimal;");
                }
            }
        }
        return params;
    }



    private static Map<String, GenPkg> getPkgMap(List<GenTask> tasks) {
        Map<String, GenPkg> pkgs = new HashMap<>();
        for (GenTask task : tasks) {
            GenPkg pkg = new GenPkg();
            pkg.setTempCode(task.getTempCode());
            pkg.setPkgPath(task.getPackagePath());
            pkg.setProjectBasePath(task.getProjectBasePath());
            pkgs.put(task.getTempKey(), pkg);
        }
        return pkgs;
    }


    private static GenTable getTable(TableInfo table) {
        GenTable genTable = new GenTable();
        genTable.setTableName(table.getTableName());
        genTable.setTableComment(table.getTableComment());
        genTable.setVariableName(StringUtil.underlineToCamel(genTable.getTableName()));
        genTable.setEntityName(StringUtil.firstChatToUpperCase(genTable.getVariableName()));
        genTable.setRestGroupName(genTable.getTableName().toUpperCase());
        genTable.setRestPathPrefix("/" + genTable.getTableName().replace("_", "/"));
        return genTable;
    }

    private static Map<String, List<GenColumn>> getColumns(List<ColumnInfo> columns) {
        return columns.stream().map(t -> {
            String columnName = t.getColumnName();
            String fFieldName = StringUtil.underlineToCamel(columnName);
            String firstChatUpperCaseName = StringUtil.firstChatToUpperCase(fFieldName);
            String dbType = t.getDataType().toUpperCase();
            DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(dbType);
            GenColumn column = new GenColumn();
            column.setTableName(t.getTableName());
            column.setColumnName(columnName);
            column.setColumnComment(t.getColumnComment());
            column.setFieldName(fFieldName);
            column.setFieldGetterName("get" + firstChatUpperCaseName);
            column.setFieldSetterName("set" + firstChatUpperCaseName);
            column.setMaxLength(t.getLength());
            column.setDbType(dbType);
            column.setJavaType(dataTypeEnum.getJavaType());
            column.setTsType(dataTypeEnum.getTsType());
            column.setInputType(dataTypeEnum.getInputType());
            return column;
        }).collect(Collectors.groupingBy(GenColumn::getTableName));
    }

    private static List<String> getBaseFields() {
        return Arrays.stream(DbColumnEntity.class.getDeclaredFields()).map(Field::getName).toList();
    }

}
