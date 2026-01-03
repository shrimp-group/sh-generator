package com.wkclz.generator.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = {"com.wkclz.generator.server"})
@MapperScan(basePackages = {"com.wkclz.generator.server.mapper"})
public class GeneratorServerConfig {
}


