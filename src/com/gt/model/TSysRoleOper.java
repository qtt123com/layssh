package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TSysRoleOper entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_ROLE_OPER", schema = "")
public class TSysRoleOper implements java.io.Serializable {

	// Fields

	private String uuid;
	private TSysOperInf TSysOperInf;
	private TSysRoleInf TSysRoleInf;

	// Constructors

	/** default constructor */
	public TSysRoleOper() {
	}

	/** minimal constructor */
	public TSysRoleOper(String uuid) {
		this.uuid = uuid;
	}

	/** full constructor */
	public TSysRoleOper(String uuid, TSysOperInf TSysOperInf, TSysRoleInf TSysRoleInf) {
		this.uuid = uuid;
		this.TSysOperInf = TSysOperInf;
		this.TSysRoleInf = TSysRoleInf;
	}

	// Property accessors
	@Id
	@Column(name = "UUID", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPER_CD")
	public TSysOperInf getTSysOperInf() {
		return this.TSysOperInf;
	}

	public void setTSysOperInf(TSysOperInf TSysOperInf) {
		this.TSysOperInf = TSysOperInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_CD")
	public TSysRoleInf getTSysRoleInf() {
		return this.TSysRoleInf;
	}

	public void setTSysRoleInf(TSysRoleInf TSysRoleInf) {
		this.TSysRoleInf = TSysRoleInf;
	}

}