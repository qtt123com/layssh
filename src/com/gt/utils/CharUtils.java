package com.gt.utils;

/**
 * 字符串处理工具
 * 
 * @author hhr
 * 
 */
public class CharUtils {

	/**
	 * 获取校验码
	 * 
	 * @return
	 */
	public static String getCheckCode(String str) {
		// 自右向左顺序编号，（从序号2开始求出偶数位的数字相加之和*7）+（从
		// 序号3开始求出奇数位的数字相加之和*3），取最后一位的值作为校验码的值

		// 字符串倒序,偶数
		StringBuffer ossb = new StringBuffer();
		for (int i = str.length() - 1; i >= 0; i--) {
			ossb.append(str.charAt(i));
		}
		char[] ossbChar = ossb.toString().toCharArray();

		int ostotal = 0;// 偶数之和
		for (int i = 0; i < ossbChar.length; i++) {
			if (i % 2 == 0) {
				ostotal += Integer.parseInt(ossbChar[i] + "");
			}
		}

		// 字符串倒序,奇数
		StringBuffer jssb = new StringBuffer();
		for (int i = str.length() - 1; i >= 0; i--) {
			jssb.append(str.charAt(i));
		}
		char[] jssbChar = jssb.toString().toCharArray();

		int jstotal = 0;// 奇数之和
		for (int i = 1; i < jssbChar.length; i++) {
			if (i % 2 == 1) {
				jstotal += Integer.parseInt(jssbChar[i] + "");

			}
		}
		String result = ((ostotal * 7 + jstotal * 3) + "");
		result = result.substring(result.length() - 1);// 取最后一位
		return result;
	}
	
}
