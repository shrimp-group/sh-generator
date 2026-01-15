package com.wkclz.generator.server.service;


import com.wkclz.core.exception.SystemException;
import com.wkclz.core.exception.ValidationException;
import com.wkclz.dynamicdb.DynamicDataSourceHolder;
import com.wkclz.generator.server.bean.entity.*;
import com.wkclz.generator.server.bean.gen.GenParam;
import com.wkclz.generator.server.bean.gen.GenPkg;
import com.wkclz.generator.server.helper.GenParamHFetchelper;
import com.wkclz.mybatis.bean.ColumnInfo;
import com.wkclz.mybatis.bean.TableInfo;
import com.wkclz.mybatis.mapper.TableInfoMapper;
import com.wkclz.spring.utils.FreeMarkerTemplateUtil;
import com.wkclz.tool.utils.CompressUtil;
import com.wkclz.tool.utils.MapUtil;
import com.wkclz.tool.utils.StringFormat;
import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenService {


    @Autowired
    private GenLogService genLogService;
    @Autowired
    private GenTaskService genTaskService;
    @Autowired
    private TableInfoMapper tableInfoMapper;
    @Autowired
    private GenProjectService genProjectService;
    @Autowired
    private GenTemplateService genTemplateService;
    @Autowired
    private GenDatasourceService genDatasourceService;


    public List<GenParam> getGenData(String projectCode) {
        GenProject project = genProjectService.getProjectByCode(projectCode);
        return getGenData(project);
    }

    public List<GenParam> getGenData(GenProject project) {
        GenDatasource datasource = genDatasourceService.getDatasourceByCode(project.getDbCode());
        List<TableInfo> tables = getTables(project, datasource);
        List<ColumnInfo> columns = getColumns(project, datasource, tables);
        List<GenTask> genTaskList = genTaskService.getTask4CodeGen(project.getProjectCode());
        return GenParamHFetchelper.fetchGenParam(tables, columns, genTaskList);
    }

    public List<GenTask> getGenRule(String projectCode) {
        return genTaskService.getTask4CodeGen(projectCode);
    }

    public void getGenZip(HttpServletResponse resp, String projectCode) {
        String distPath = getDistPath();

        // 日志记录
        GenLog genLog = new GenLog();
        genLog.setProjectCode(projectCode);
        genLog.setStartTime(LocalDateTime.now());
        genLog.setGenPath(distPath);
        genLogService.insert(genLog);

        genCodeData(projectCode, distPath);

        // 日志更新
        genLog = genLogService.selectById(genLog.getId());
        genLog.setEndTime(LocalDateTime.now());
        genLogService.updateById(genLog);

        zipDataAndPush(resp, distPath);
    }

    private void genCodeData(String projectCode, String distPath) {
        GenProject project = genProjectService.getProjectByCode(projectCode);
        List<GenParam> params = getGenData(project);
        if (CollectionUtils.isEmpty(params)) {
            throw ValidationException.of("没有可生成代码的数据");
        }

        List<String> tempCodes = params.get(0).getPkgs().values().stream().map(GenPkg::getTempCode).toList();
        List<GenTemplate> templates = genTemplateService.getTemplates4CodeGen(tempCodes);
        Map<String, GenTemplate> templateMap = templates.stream().collect(Collectors.toMap(GenTemplate::getTempCode, t -> t));

        for (GenPkg pkg : params.get(0).getPkgs().values()) {
            GenTemplate template = templateMap.get(pkg.getTempCode());
            String projectBasePath = pkg.getProjectBasePath();
            // 父路径会逃逸。暂时替换成 parent/, 后续再重新找回路径
            projectBasePath = projectBasePath.replace("../", "parent/");
            String packagePath = pkg.getPkgPath().replaceAll("\\.", "/");

            String fileDistPath = distPath + "/" + projectBasePath + "/" + packagePath + "/";
            fileDistPath = fileDistPath.replace("\\", "/");
            fileDistPath = fileDistPath.replace("//", "/");

            // 目录存在性检查
            File fileDir = new File(fileDistPath);
            if (!fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            // 文件生成
            for (GenParam param : params) {
                File tarFile = new File(fileDistPath + param.getTable().getEntityName() + template.getTempSubfix());

                FileOutputStream fos = null;
                PrintStream ps = null;
                try {
                    String tempContent = template.getTempContent();
                    Map<String, Object> map = MapUtil.obj2Map(param);
                    fos = new FileOutputStream(tarFile);
                    ps = new PrintStream(fos);

                    String code;
                    try {
                        code = FreeMarkerTemplateUtil.parseString(tempContent, map);
                        code = code.replace("\r\n", "\n");
                    } catch (TemplateException | IOException e) {
                        code = StringFormat.of("代码生成异常: \n\n文件: {}\n\n异常内容: \n{}\n\n模板:\n{}", tarFile.getAbsolutePath(), e.getMessage(), tempContent);
                    }
                    ps.println(code);
                    log.info("======> 生成文件 {}", tarFile.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    throw SystemException.of("代码生成异常: {}", e.getMessage());
                } finally {
                    if (ps != null) {
                        ps.flush();
                        ps.close();
                    }
                    if (fos != null) {
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            // who care ?
                        }
                    }
                }
            }
        }
    }


    private static void zipDataAndPush(HttpServletResponse resp, String distPath) {
        String tarZipFilePath = distPath + ".zip";
        try {
            FileOutputStream fos = new FileOutputStream(distPath + ".zip");
            CompressUtil.zip(distPath, fos);
        } catch (FileNotFoundException e) {
            throw SystemException.of("压缩代码异常: {}", e.getMessage());
        }
        byte[] bytes = new byte[1024];

        File file = new File(tarZipFilePath);
        String fileName = file.getName();
        log.info("the zip file is in {}", file.getPath());
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        try (InputStream in = new FileInputStream(file);
             OutputStream fops = resp.getOutputStream()) {
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                fops.write(bytes, 0, len);
            }
            fops.flush();
        } catch (Exception var34) {
            log.error("Exception", var34);
        }

    }



    private List<TableInfo> getTables(GenProject project, GenDatasource datasource) {
        TableInfo info = new TableInfo();
        info.setTableSchema(datasource.getDbSchema());
        info.setTableName(project.getTablePrefix());

        DynamicDataSourceHolder.set(project.getDbCode());
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
        DynamicDataSourceHolder.set(project.getDbCode());
        List<ColumnInfo> columns = tableInfoMapper.getColumns(info);
        return columns.stream().filter(t -> !"deleted".equals(t.getColumnName())).toList();
    }


    private static String getDistPath() {
        String property = System.getProperty("catalina.base");
        SimpleDateFormat sdfYmdhmss = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateStr = sdfYmdhmss.format(new Date());
        String genPath = property + "/gen/" + dateStr;
        File fileDir = new File(genPath);
        if (!fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        return genPath;
    }

}

