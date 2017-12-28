package com.gt.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TSysRoleInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_ROLE_INF", schema = "")
public class TSysRoleInf implements java.io.Serializable {

	// Fields

	private String roleCd;
	private TSysOperInf TSysOperInf;
	private String remark;
	private String roleNm;
	private String jumpUrl;
	private String urlNm;
	private Set<TSysRoleOper> TSysRoleOpers = new HashSet<TSysRoleOper>(0);
	private Set<TSysRoleFunction> TSysRoleFunctions = new HashSet<TSysRoleFunction>(0);

	// Constructors

	/** default constructor */
	public TSysRoleInf() {
	}

	/** minimal constructor */
	public TSysRoleInf(String roleCd, String roleNm) {
		this.roleCd = roleCd;
		this.roleNm = roleNm;
	}

	/** full constructor */
	public TSysRoleInf(String roleCd, TSysOperInf TSysOperInf, String jumpUrl,String urlNm,String remark, String roleNm, Set<TSysRoleOper> TSysRoleOpers, Set<TSysRoleFunction> TSysRoleFunctions) {
		this.roleCd = roleCd;
		this.TSysOperInf = TSysOperInf;
		this.remark = remark;
		this.roleNm = roleNm;
		this.TSysRoleOpers = TSysRoleOpers;
		this.TSysRoleFunctions = TSysRoleFunctions;
		this.jumpUrl=jumpUrl;
		this.urlNm=urlNm;
	}

	// Property accessors
	@Id
	@Column(name = "ROLE_CD", unique = true, nullable = false, length = 72)
	public String getRoleCd() {
		return this.roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WRITE_OPER_CD")
	public TSysOperInf getTSysOperInf() {
		return this.TSysOperInf;
	}

	public void setTSysOperInf(TSysOperInf TSysOperInf) {
		this.TSysOperInf = TSysOperInf;
	}

	@Column(name = "REMARK", length = 400)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ROLE_NM", nullable = false, length = 60)
	public String getRoleNm() {
		return this.roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}

	@Column(name = "JUMP_URL", nullable = false, length = 200)
	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	
	@Column(name = "URL_NM", nullable = false, length = 50)
	public String getUrlNm() {
		return urlNm;
	}

	public void setUrlNm(String urlNm) {
		this.urlNm = urlNm;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysRoleInf")
	public Set<TSysRoleOper> getTSysRoleOpers() {
		return this.TSysRoleOpers;
	}

	public void setTSysRoleOpers(Set<TSysRoleOper> TSysRoleOpers) {
		this.TSysRoleOpers = TSysRoleOpers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysRoleInf")
	public Set<TSysRoleFunction> getTSysRoleFunctions() {
		return this.TSysRoleFunctions;
	}

	public void setTSysRoleFunctions(Set<TSysRoleFunction> TSysRoleFunctions) {
		this.TSysRoleFunctions = TSysRoleFunctions;
	}

}