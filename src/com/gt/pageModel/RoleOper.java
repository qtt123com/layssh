package com.gt.pageModel;

/**
 * 
 * @功能说明：人员角色表
 * @作者： herun
 * @创建日期：2015-10-08
 * @版本号：V1.0
 */
public class RoleOper extends BasePageForLayUI {

	private static final long serialVersionUID = 1L;

	private String uuid;// uuid

	private String operCd;// 人员CD

	private String roleCd;// 角色CD

	public String getOperCd() {
		return operCd;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setOperCd(String operCd) {
		this.operCd = operCd;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
}
