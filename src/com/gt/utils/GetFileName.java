package com.gt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 生成css文件工具
 * 
 * @功能说明：
 * @作者： herun
 * @创建日期：2015-11-23
 * @版本号：V1.0
 */
public class GetFileName {
	
	public static String[] getFileName(String path) {
		File file = new File(path);
		String[] fileName = file.list();
		return fileName;
	}

	public static void getAllFileName(String path, ArrayList<String> fileName) {
		File file = new File(path);
		File[] files = file.listFiles();
		String[] names = file.list();
		if (names != null)
			fileName.addAll(Arrays.asList(names));
		for (File a : files) {
			if (a.isDirectory()) {
				getAllFileName(a.getAbsolutePath(), fileName);
			}
		}
	}

	public static void main(String[] args) throws IOException, TemplateException {
		String path = "D:/Users/Administrator/Workspaces/MyEclipse Professional_2/sshframe/WebRoot/js/jquery-easyui-1.4.3/themes/pngIcons";
		String[] fileName = getFileName(path);
		for (String name : fileName) {
			System.out.println(name);
		}
		System.out.println("--------------------------------");
		ArrayList<String> listFileName = new ArrayList<String>();
		getAllFileName(path, listFileName);
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();
		for (String name : listFileName) {
			if (name.substring(name.lastIndexOf(".") + 1).equals("png")) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("className", name.replace(".png", ""));
				clList.add(oMap);
			}

		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("cloums", clList);
		creatTemplate(data, "png.ftl", "d:/ico.css");

	}

	private static void creatTemplate(Map<String, Object> data, String ftlName, String fileName) throws IOException, TemplateException {

		Configuration cfg = new Configuration();

		// 设置字符集编码
		cfg.setEncoding(Locale.CHINA, "GBK");

		String PATH = "D:/Users/Administrator/Workspaces/MyEclipse Professional_2/sshframe/WebRoot/tempalte/png/";
		// 加载模板文件
		cfg.setDirectoryForTemplateLoading(new File(PATH));

		// 设置对象包装器
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// 设计异常处理器
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

		// 获取指定模板文件
		Template template = cfg.getTemplate(ftlName);

		// 检查路径是否存在，不存在则创建
		String path = fileName.substring(0, fileName.lastIndexOf("."));
		// 目标文件
		File saveFile = new File(path);
		// 判断存放路径是否存在，存在就删除，不存在就创建
		if (saveFile.exists()) {
			saveFile.delete();
		} else {
			saveFile.getParentFile().mkdirs();
		}

		// 定义输入文件，默认生成在工程根目录，设置文件的编码格式
		Writer out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");

		// 最后开始生成
		template.process(data, out);

		// 关闭资源，否则删除不掉
		out.close();
	}
}
