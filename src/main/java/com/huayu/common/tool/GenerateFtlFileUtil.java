/**
 *
 */
package com.huayu.common.tool;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * @author DengPeng
 * @DateCreate 2015年11月3日下午8:45:39
 */
public class GenerateFtlFileUtil {

    public static final String ENCODING = "UTF-8";

    public static void crateFile(Map<String, Object> data, String templatePath, String templateName, String outFile) throws IOException, TemplateException {
        Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_23);
        //设置要解析的模板所在的目录，并加载模板文件
        freemarkerCfg.setDirectoryForTemplateLoading(new File(templatePath));
        //设置包装器，并将对象包装为数据模型
//        freemarkerCfg.setObjectWrapper(new SimpleObjectWrapper(Configuration.VERSION_2_3_23));
        //指定模版路径
        Template template = freemarkerCfg.getTemplate(templateName, ENCODING);
        FileOutputStream fos = new FileOutputStream(outFile);
        Writer out = new OutputStreamWriter(fos, ENCODING);
        //合并数据模型与模板
        template.process(data, out);
        out.flush();
        out.close();
    }
}
