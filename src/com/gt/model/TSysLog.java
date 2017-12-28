package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSysLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_LOG", schema = "")
public class TSysLog implements java.io.Serializable {

	// Fields

	private String uuid;
	private String compareInf;
	private String operDesc;
	private String operDt;
	private String operIp;
	private String operMethod;
	private String operModule;
	private String operUsrId;
	private String resultMsg;

	// Constructors

	/** default constructor */
	public TSysLog() {
	}

	/** minimal constructor */
	public TSysLog(String uuid) {
		this.uuid = uuid;
	}

	/** full constructor */
	public TSysLog(String uuid, String compareInf, String operDesc, String operDt, String operIp, String operMethod, String operModule, String operUsrId, String resultMsg) {
		this.uuid = uuid;
		this.compareInf = compareInf;
		this.operDesc = operDesc;
		this.operDt = operDt;
		this.operIp = operIp;
		this.operMethod = operMethod;
		this.operModule = operModule;
		this.operUsrId = operUsrId;
		this.resultMsg = resultMsg;
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

	@Column(name = "COMPARE_INF", length = 4000)
	public String getCompareInf() {
		return this.compareInf;
	}

	public void setCompareInf(String compareInf) {
		this.compareInf = compareInf;
	}

	@Column(name = "OPER_DESC", length = 4000)
	public String getOperDesc() {
		return this.operDesc;
	}

	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}

	@Column(name = "OPER_DT", length = 28)
	public String getOperDt() {
		return this.operDt;
	}

	public void setOperDt(String operDt) {
		this.operDt = operDt;
	}

	@Column(name = "OPER_IP", length = 200)
	public String getOperIp() {
		return this.operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	@Column(name = "OPER_METHOD", length = 120)
	public String getOperMethod() {
		return this.operMethod;
	}

	public void setOperMethod(String operMethod) {
		this.operMethod = operMethod;
	}

	@Column(name = "OPER_MODULE", length = 120)
	public String getOperModule() {
		return this.operModule;
	}

	public void setOperModule(String operModule) {
		this.operModule = operModule;
	}

	@Column(name = "OPER_USR_ID", length = 60)
	public String getOperUsrId() {
		return this.operUsrId;
	}

	public void setOperUsrId(String operUsrId) {
		this.operUsrId = operUsrId;
	}

	@Column(name = "RESULT_MSG", length = 2000)
	public String getResultMsg() {
		return this.resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}