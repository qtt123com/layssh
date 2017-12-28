package com.gt.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @功能说明：List<Map>结果集转化为List<JavaBean>工具类,
 * @去掉下划线，忽略大小写进行比较,例如ad_code可赋值给adCode属性
 * @作者： herun
 * @创建日期：2014-4-1
 * @版本号：V1.0
 */
public class MapToBeanUtils2<T> {

	/**
	 * List<Map>数据转换为JavaBean数据
	 * 
	 * @param maps
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public List<T> ListMapToJavaBean(List<Map> maps, Class<T> beanClass) throws Exception {
		// 返回数据集合
		List<T> list = new ArrayList<T>();

		// 得到对象所有字段
		Field fields[] = beanClass.getDeclaredFields();

		// 遍历数据
		for (Map<String, Object> mapdata : maps) {

			boolean v = false;// 是否有字段匹配

			// 创建一个泛型类型实例
			T t = beanClass.newInstance();

			Iterator i = mapdata.entrySet().iterator();

			// 循环遍历获取所有的key
			while (i.hasNext()) {
				Map.Entry e = (Map.Entry) i.next();
				String key = e.getKey().toString().replace("_", "").toLowerCase();// 去掉下划线并且转成小写
				// 遍历所有的字段
				for (Field field : fields) {
					String fieldName = field.getName().replace("_", "").toLowerCase();// 去掉下划线并且转成小写;
					// 去掉下划线，忽略大小写进行比较
					if (fieldName.equals(key)) {
						v = true;
						// 对象方法名称, 拼接set方法
						// String methodname = "set" +
						// PbUtils.fristStrToUpperCase(field.getName());
						String methodname = field.getName().toLowerCase();
						// 赋值给字段
						// Method m = beanClass.getDeclaredMethod(methodname,
						// field.getType());
						Method[] methods = beanClass.getMethods();
						for (Method m : methods) {
							if (m.getName().contains("get")) {
								continue;
							}
							if (m.getName().replace("set", "").toLowerCase().equals(methodname)) {
								// 判断字段类型
								if (field.getType() == Integer.class || field.getType() == int.class) {
									if (e.getValue() != null) {
										m.invoke(t, Integer.parseInt(e.getValue().toString()));
									}
								} else if (field.getType() == Float.class || field.getType() == float.class) {
									if (e.getValue() != null) {
										m.invoke(t, Float.parseFloat(e.getValue().toString()));
									}
								} else if (field.getType() == Long.class || field.getType() == long.class) {
									if (e.getValue() != null) {
										m.invoke(t, Long.parseLong(e.getValue().toString()));
									}
								} else if (field.getType() == Double.class || field.getType() == double.class) {
									if (e.getValue() != null) {
										m.invoke(t, Double.parseDouble(e.getValue().toString()));
									}
								} else if (field.getType() == Short.class || field.getType() == short.class) {
									if (e.getValue() != null) {
										m.invoke(t, Short.parseShort(e.getValue().toString()));
									}
								} else if (field.getType() == String.class) {
									if (e.getValue() != null) {
										m.invoke(t, (Object) e.getValue().toString());
									}
								} else if (field.getType() == Date.class) {
									if (e.getValue() != null) {
										m.invoke(t, e.getValue());
									}
								} else {
									// 字符串、或者byte类型
									if (e.getValue() != null) {
										m.invoke(t, e.getValue().toString());
									}
								}
								
							}
						}
					}
				}
			}
			// 存入返回列表
			if (v) {
				list.add(t);
			}
		}
		// 返回
		return list;
	}

}
