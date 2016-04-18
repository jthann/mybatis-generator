package com.jd.panda.code.tools;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static Log logger = LogFactory.getLog(Generator.class);

    public static final String UTF_8 = "UTF-8";

    public static String getPKType(List<FieldVO> fieldVOList, String primaryField) {
        for (FieldVO fieldVO : fieldVOList) {
            if (primaryField.equalsIgnoreCase(fieldVO.getFieldName())) {
                return fieldVO.getType();
            }
        }
        return "";
    }

    public static String getOutputPath(String pkgName, String pojoName, String fileSuffix) {
        String output = FactoryUtils.xmlConfiguration.getString("output");
        if (output == null || "".equals(output)) {
            throw new RuntimeException("请配置文件输出路径");
        }
        if (!output.endsWith("\\") || !output.endsWith("/")) {
            output = output + "/";
        }
        String pkgNamePath = "";
        if ((pkgName != null) && (!"".equals(pkgName))) {
            pkgNamePath = pkgName.replace(".", "/");
        }
        if (pkgNamePath.length() > 0) {
            pkgNamePath = pkgNamePath + "/";
        }
        String outputPath = output + pkgNamePath + pojoName
                + fileSuffix;
        return outputPath;
    }

    public static void generateFile(String itemName, String template, String pkgName, String fileSuffix) {
        XMLConfiguration xmlConfig = FactoryUtils.xmlConfiguration;
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("xml", xmlConfig);
        velocityContext.put("util", new ConvertUtil());
        velocityContext.put("pkgName", pkgName);
        List<String> pojoClassList = new ArrayList<String>();
        for (int i = 0; i < xmlConfig.getMaxIndex("classes.class") + 1; i++) {
            String indexName = "classes.class(" + i + ")";
            String pojoName = xmlConfig.getString(indexName + "[@name]");
            String tableName = xmlConfig.getString(indexName + "[@tableName]");
            pojoClassList.add(pojoName);
            List<FieldVO> fieldVOList = SchemaLoader.loadTableSchema(tableName);
            String primaryField = xmlConfig.getString(indexName + "[@primaryField]");
            velocityContext.put("fields", fieldVOList);
            velocityContext.put("primaryField", primaryField);
            velocityContext.put("primaryFieldType", getPKType(fieldVOList, primaryField));
            velocityContext.put("tableName", tableName);
            velocityContext.put("pojoName", pojoName);
            velocityContext.put("pojoNames", pojoClassList);
            VelocityEngine velocityEngine = FactoryUtils.createVelocityEngine();
            try {
                Template templateFile = templateFile = velocityEngine.getTemplate(template, UTF_8);
                StringWriter stringWriter = new StringWriter();
                templateFile.merge(velocityContext, stringWriter);
                String outputPath = getOutputPath(pkgName, pojoName, fileSuffix);
                File file = new File(outputPath);
                FileUtils.writeStringToFile(file, stringWriter.toString(), "UTF-8");
            } catch (Exception e) {
                logger.error("生成文件出现异常!", e);
            }

        }
    }

    public static void bootstrap() {
        XMLConfiguration xmlConfiguration = FactoryUtils.xmlConfiguration;
        List<String> itemNameList = xmlConfiguration.getList("templates.item.name");
        for (int i = 0; i < itemNameList.size(); i++) {
            String prefixName = "templates.item(" + i + ").";
            String itemName = xmlConfiguration.getString(prefixName + "name");
            String template = xmlConfiguration.getString(prefixName + "template");
            String pkgName = xmlConfiguration.getString(prefixName + "package");
            String fileSuffix = xmlConfiguration.getString(prefixName + "fileSuffix");
            System.out.println("正在生成" + itemName + "代码...");
            generateFile(itemName, template, pkgName, fileSuffix);
            System.out.println("成功生成" + itemName + "代码...");
        }
    }


    public static void main(String[] args) {

        bootstrap();

    }
}
