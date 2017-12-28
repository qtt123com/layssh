package com.gt.utils;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

/**
 * 
 * @功能说明：解析Properties文件
 * @作者： herun
 * @创建日期：2014-3-4
 * @版本号：V1.0
 */
public class ParseProperties {

	private static Logger logger = Logger.getLogger(ParseProperties.class);

	/**
	 * 相对路径
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		Properties p = null;
		try {
			// 生成输入流
			InputStream ins = ParseProperties.class.getResourceAsStream("../../../jdbc.properties");
			// 生成properties对象
			p = new Properties();
			try {
				p.load(ins);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			logger.error("获取系统配置文件jdbc.properties出错：" + e);

		}
		return p;
	}

	public static void main(String[] args) {
		System.out.println(ParseProperties.getProperties().getProperty("port"));
	}

}