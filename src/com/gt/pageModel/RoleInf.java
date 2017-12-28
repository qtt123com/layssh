package com.gt.pageModel;

/**
 * 
 * @功能说明：系统角色
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class RoleInf extends BasePageForLayUI {

	private static final long serialVersionUID = 1L;

	private String roleCd;// 角色代码

	private String roleNm;// 角色名称

	private String remark;// 备注

	private String write_oper_cd;// 创建人

	private String operInsCdNm;// 创建人所属机构名称

	private String jumpUrl;//URL地址

	private String urlNm;//URL名称

	public String getWrite_oper_cd() {
		return write_oper_cd;
	}

	public void setWrite_oper_cd(String write_oper_cd) {
		this.write_oper_cd = write_oper_cd;
	}

	public String getOperInsCdNm() {
		return operInsCdNm;
	}

	public void setOperInsCdNm(String operInsCdNm) {
		this.operInsCdNm = operInsCdNm;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getRoleNm() {
		return roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getUrlNm() {
		return urlNm;
	}

	public void setUrlNm(String urlNm) {
		this.urlNm = urlNm;
	}

}
