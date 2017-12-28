package com.gt.utils;

import java.util.Comparator;

import com.gt.pageModel.MenuInf;
/**
 * 
 * @功能说明：菜单自定义排序
 * @作者： herun
 * @创建日期：2014-3-26
 * @版本号：V1.0
 */
public class Mycomparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		MenuInf mInf1=(MenuInf)o1;
		MenuInf mInf2=(MenuInf)o2;
		if (Integer.parseInt(mInf1.getMenuSort())>Integer.parseInt(mInf2.getMenuSort())) {
			return 1;
		}
		return 0;
	}
}
