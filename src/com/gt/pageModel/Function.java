package com.gt.pageModel;

/**
 * 
 * @功能说明：系统功能
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class Function extends BasePageForLayUI {

	private static final long serialVersionUID = 1L;
	
	private String functionCd;//主键

	private String functionNm;//菜单名称

	private String functionUrl;//请求地址

	private String menuId;//菜单ID


	
	public String getFunctionCd() {
		return  functionCd;
	}

	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

	
	public String getFunctionNm() {
		return  functionNm;
	}

	public void setFunctionNm(String functionNm) {
		this.functionNm = functionNm;
	}

	
	public String getFunctionUrl() {
		return  functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	
	public String getMenuId() {
		return  menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
