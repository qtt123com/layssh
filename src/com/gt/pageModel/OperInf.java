package com.gt.pageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @功能说明：系统用户
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class OperInf extends BasePageForLayUI {

	private static final long serialVersionUID = 1L;

	private String operCd;// 用户名

	private String insUuid;// 机构UUID

	private String insCd;// 机构编号

	private String insCdNm;// 机构编号-名称

	private String roleCd;// 角色代码

	private String roleCdNm;// 角色代码-名称

	private String operNm;// 用户真实姓名

	private String operSt;// 用户状态

	private String operStNm;// 用户状态-名称

	private String telephone;// 电话号码

	private String email;// EMAIL

	private String operPwd;// 用户密码

	private String oldOperPwd;// 旧用户密码

	private String recCrtTs;// 记录创建时间

	private String recUpdTs;// 记录修改时间

	private String code;// 验证码

	private String job;//岗位
	
	private String jobNm;//岗位-名称
	
	public String getJobNm() {
		return jobNm;
	}

	public void setJobNm(String jobNm) {
		this.jobNm = jobNm;
	}

	private List<RoleInf> roleInfs = new ArrayList<RoleInf>();// 角色信息

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getInsUuid() {
		return insUuid;
	}

	public void setInsUuid(String insUuid) {
		this.insUuid = insUuid;
	}

	public List<RoleInf> getRoleInfs() {
		return roleInfs;
	}

	public void setRoleInfs(List<RoleInf> roleInfs) {
		this.roleInfs = roleInfs;
	}

	public String getOperCd() {
		return operCd;
	}

	public String getOldOperPwd() {
		return oldOperPwd;
	}

	public void setOldOperPwd(String oldOperPwd) {
		this.oldOperPwd = oldOperPwd;
	}

	public String getInsCdNm() {
		return insCdNm;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setInsCdNm(String insCdNm) {
		this.insCdNm = insCdNm;
	}

	public String getRoleCdNm() {
		return roleCdNm;
	}

	public void setRoleCdNm(String roleCdNm) {
		this.roleCdNm = roleCdNm;
	}

	public String getOperStNm() {
		return operStNm;
	}

	public void setOperStNm(String operStNm) {
		this.operStNm = operStNm;
	}

	public void setOperCd(String operCd) {
		this.operCd = operCd;
	}

	public String getInsCd() {
		return insCd;
	}

	public void setInsCd(String insCd) {
		this.insCd = insCd;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getOperNm() {
		return operNm;
	}

	public void setOperNm(String operNm) {
		this.operNm = operNm;
	}

	public String getOperSt() {
		return operSt;
	}

	public void setOperSt(String operSt) {
		this.operSt = operSt;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOperPwd() {
		return operPwd;
	}

	public void setOperPwd(String operPwd) {
		this.operPwd = operPwd;
	}

	public String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

}
