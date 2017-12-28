package com.gt.pageModel;

/**
 * 
 * @功能说明：系统功能
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class SysFunctionInf extends BasePageForLayUI {
	private static final long serialVersionUID = 1L;
	private String functionCd;// 主键
	private String menuCd;// 菜单ID
	private String menuNm;// 菜单名称
	private String functionNm;// 功能名称
	private String functionUrl;// 请求地址

	public String getFunctionCd() {
		return functionCd;
	}

	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

	public String getMenuCd() {
		return menuCd;
	}

	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getFunctionNm() {
		return functionNm;
	}

	public void setFunctionNm(String functionNm) {
		this.functionNm = functionNm;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
}
