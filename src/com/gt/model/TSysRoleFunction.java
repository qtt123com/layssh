package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TSysRoleFunction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_ROLE_FUNCTION", schema = "")
public class TSysRoleFunction implements java.io.Serializable {

	// Fields

	private String uuid;
	private TSysFunctionInf TSysFunctionInf;
	private TSysRoleInf TSysRoleInf;

	// Constructors

	/** default constructor */
	public TSysRoleFunction() {
	}

	/** full constructor */
	public TSysRoleFunction(String uuid, TSysFunctionInf TSysFunctionInf, TSysRoleInf TSysRoleInf) {
		this.uuid = uuid;
		this.TSysFunctionInf = TSysFunctionInf;
		this.TSysRoleInf = TSysRoleInf;
	}

	// Property accessors
	@Id
	@Column(name = "UUID", unique = true, nullable = false, length = 72)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNCTION_CD", nullable = false)
	public TSysFunctionInf getTSysFunctionInf() {
		return this.TSysFunctionInf;
	}

	public void setTSysFunctionInf(TSysFunctionInf TSysFunctionInf) {
		this.TSysFunctionInf = TSysFunctionInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_CD", nullable = false)
	public TSysRoleInf getTSysRoleInf() {
		return this.TSysRoleInf;
	}

	public void setTSysRoleInf(TSysRoleInf TSysRoleInf) {
		this.TSysRoleInf = TSysRoleInf;
	}

}