package com.gt.pageModel;

/**
 * 
 * @功能说明：角色功能信息
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class RoleFunction extends BasePageForLayUI {

	private static final long serialVersionUID = 1L;
	
	private String uuid;// UUID

	private String roleCd;// 角色代码

	private String functionCd;// 功能代码

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getFunctionCd() {
		return functionCd;
	}

	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

}