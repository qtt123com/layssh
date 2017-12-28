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
 * TSysFunctionInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_FUNCTION_INF", schema = "")
public class TSysFunctionInf implements java.io.Serializable {

	// Fields

	private String functionCd;
	private TSysMenuInf TSysMenuInf;
	private String functionNm;
	private String functionUrl;
	private Set<TSysRoleFunction> TSysRoleFunctions = new HashSet<TSysRoleFunction>(0);

	// Constructors

	/** default constructor */
	public TSysFunctionInf() {
	}

	/** minimal constructor */
	public TSysFunctionInf(String functionCd, String functionNm) {
		this.functionCd = functionCd;
		this.functionNm = functionNm;
	}

	/** full constructor */
	public TSysFunctionInf(String functionCd, TSysMenuInf TSysMenuInf, String functionNm, String functionUrl, Set<TSysRoleFunction> TSysRoleFunctions) {
		this.functionCd = functionCd;
		this.TSysMenuInf = TSysMenuInf;
		this.functionNm = functionNm;
		this.functionUrl = functionUrl;
		this.TSysRoleFunctions = TSysRoleFunctions;
	}

	// Property accessors
	@Id
	@Column(name = "FUNCTION_CD", unique = true, nullable = false, length = 72)
	public String getFunctionCd() {
		return this.functionCd;
	}

	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	public TSysMenuInf getTSysMenuInf() {
		return this.TSysMenuInf;
	}

	public void setTSysMenuInf(TSysMenuInf TSysMenuInf) {
		this.TSysMenuInf = TSysMenuInf;
	}

	@Column(name = "FUNCTION_NM", nullable = false, length = 100)
	public String getFunctionNm() {
		return this.functionNm;
	}

	public void setFunctionNm(String functionNm) {
		this.functionNm = functionNm;
	}

	@Column(name = "FUNCTION_URL", length = 400)
	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysFunctionInf")
	public Set<TSysRoleFunction> getTSysRoleFunctions() {
		return this.TSysRoleFunctions;
	}

	public void setTSysRoleFunctions(Set<TSysRoleFunction> TSysRoleFunctions) {
		this.TSysRoleFunctions = TSysRoleFunctions;
	}

}