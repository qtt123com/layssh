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
 * TSysOperInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_OPER_INF", schema = "")
public class TSysOperInf implements java.io.Serializable {

	// Fields

	private String operCd;
	private TSysInsInf TSysInsInf;
	private String email;
	private String operNm;
	private String operPwd;
	private String operSt;
	private String recCrtTs;
	private String recUpdTs;
	private String telephone;
	private String job;
	private Set<TSysRoleInf> TSysRoleInfs = new HashSet<TSysRoleInf>(0);
	private Set<TSysRoleOper> TSysRoleOpers = new HashSet<TSysRoleOper>(0);

	// Constructors

	/** default constructor */
	public TSysOperInf() {
	}

	/** minimal constructor */
	public TSysOperInf(String operCd, String operNm, String operPwd, String operSt, String job) {
		this.operCd = operCd;
		this.operNm = operNm;
		this.operPwd = operPwd;
		this.operSt = operSt;
		this.job = job;
	}

	/** full constructor */
	public TSysOperInf(String operCd, TSysInsInf TSysInsInf, String email, String operNm, String operPwd, String operSt, String recCrtTs, String recUpdTs, String telephone, String job, Set<TSysRoleInf> TSysRoleInfs, Set<TSysRoleOper> TSysRoleOpers) {
		this.operCd = operCd;
		this.TSysInsInf = TSysInsInf;
		this.email = email;
		this.operNm = operNm;
		this.operPwd = operPwd;
		this.operSt = operSt;
		this.recCrtTs = recCrtTs;
		this.recUpdTs = recUpdTs;
		this.telephone = telephone;
		this.job = job;
		this.TSysRoleInfs = TSysRoleInfs;
		this.TSysRoleOpers = TSysRoleOpers;
	}

	// Property accessors
	@Id
	@Column(name = "OPER_CD", unique = true, nullable = false, length = 60)
	public String getOperCd() {
		return this.operCd;
	}

	public void setOperCd(String operCd) {
		this.operCd = operCd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INS_CD")
	public TSysInsInf getTSysInsInf() {
		return this.TSysInsInf;
	}

	public void setTSysInsInf(TSysInsInf TSysInsInf) {
		this.TSysInsInf = TSysInsInf;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "OPER_NM", nullable = false, length = 60)
	public String getOperNm() {
		return this.operNm;
	}

	public void setOperNm(String operNm) {
		this.operNm = operNm;
	}

	@Column(name = "OPER_PWD", nullable = false, length = 200)
	public String getOperPwd() {
		return this.operPwd;
	}

	public void setOperPwd(String operPwd) {
		this.operPwd = operPwd;
	}

	@Column(name = "OPER_ST", nullable = false, length = 2)
	public String getOperSt() {
		return this.operSt;
	}

	public void setOperSt(String operSt) {
		this.operSt = operSt;
	}

	@Column(name = "REC_CRT_TS", length = 28)
	public String getRecCrtTs() {
		return this.recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	@Column(name = "REC_UPD_TS", length = 28)
	public String getRecUpdTs() {
		return this.recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	@Column(name = "TELEPHONE", length = 24)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "JOB", nullable = false, length = 4)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysOperInf")
	public Set<TSysRoleInf> getTSysRoleInfs() {
		return this.TSysRoleInfs;
	}

	public void setTSysRoleInfs(Set<TSysRoleInf> TSysRoleInfs) {
		this.TSysRoleInfs = TSysRoleInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysOperInf")
	public Set<TSysRoleOper> getTSysRoleOpers() {
		return this.TSysRoleOpers;
	}

	public void setTSysRoleOpers(Set<TSysRoleOper> TSysRoleOpers) {
		this.TSysRoleOpers = TSysRoleOpers;
	}

}