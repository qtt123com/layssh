package com.gt.pageModel;

/**
 * 
 * @功能说明：系统日志
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class SysLog extends BasePageForLayUI {
	private static final long serialVersionUID = 1L;
	
	private String uuid;// UUID

	private String operUsrId;// 操作用户编号

	private String operDt;// 操作日期

	private String operMethod;// 操作方法

	private String operModule;// 操作模块

	private String operDesc;// 操作关键字

	private String compareInf;// 变更内容

	private String operIp;// 操作人IP地址
	
	private String resultMsg;//操作结果

	private String starOperDt;//操作起始日期
	
	private String endOperDt;//操作结束日期
	
	public String getStarOperDt() {
		return starOperDt;
	}

	public void setStarOperDt(String starOperDt) {
		this.starOperDt = starOperDt;
	}

	public String getEndOperDt() {
		return endOperDt;
	}

	public void setEndOperDt(String endOperDt) {
		this.endOperDt = endOperDt;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOperUsrId() {
		return operUsrId;
	}

	public void setOperUsrId(String operUsrId) {
		this.operUsrId = operUsrId;
	}

	public String getOperDt() {
		return operDt;
	}

	public void setOperDt(String operDt) {
		this.operDt = operDt;
	}

	public String getOperMethod() {
		return operMethod;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public void setOperMethod(String operMethod) {
		this.operMethod = operMethod;
	}

	public String getOperModule() {
		return operModule;
	}

	public void setOperModule(String operModule) {
		this.operModule = operModule;
	}

	public String getOperDesc() {
		return operDesc;
	}

	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}

	public String getCompareInf() {
		return compareInf;
	}

	public void setCompareInf(String compareInf) {
		this.compareInf = compareInf;
	}

	public String getOperIp() {
		return operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

}
