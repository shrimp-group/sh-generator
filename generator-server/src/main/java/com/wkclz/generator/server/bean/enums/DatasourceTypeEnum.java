package com.wkclz.generator.server.bean.enums;

import com.alibaba.druid.util.JdbcConstants;

import java.util.Arrays;
import java.util.List;

/**
 * @Description  先简单点
 * @Author ZengDongfang
 * @Date 2022/7/25 1:17
 */
public enum DatasourceTypeEnum {

    MYSQL("MYSQL", JdbcConstants.MYSQL_DRIVER_6),

    POSTGRESQL("POSTGRESQL",JdbcConstants.POSTGRESQL_DRIVER),

    MARIADB("MARIADB",JdbcConstants.MARIADB_DRIVER),

    ORACLE("ORACLE",JdbcConstants.ORACLE_DRIVER),

    ORACLE_2("ORACLE_2",JdbcConstants.ORACLE_DRIVER2),
    ;

    private final String name;
    private final String driverClassName;

    private DatasourceTypeEnum(String name, String driverClassName) {
        this.name = name;
        this.driverClassName = driverClassName;
    }

    public final String getName() {
        return name;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public static List<String> getGenDatasourceTypeNameList(){
        return Arrays.stream(DatasourceTypeEnum.values())
            .map(DatasourceTypeEnum::getName)
            .toList();
    }

    public static String getDriverClassNameByName(String name) {
        for (DatasourceTypeEnum type : DatasourceTypeEnum.values()) {
            if (type.getName().equals(name)) {
                return type.getDriverClassName();
            }
        }
        return null;
    }
}
