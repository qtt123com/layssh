package com.gt.pageModel;

/**
 * 
 * @功能说明：用户信息管理
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class UserInfo extends BasePageForLayUI {
	private static final long serialVersionUID = 1L;
	
	private String userCd;//用户信息编号

	private String userInfo;//用户描述

	private String typeUser;//用户类型

	private String typeApp;//应用类型

	private String appName;//应用名

	private String typeCer;//证书类型

	private String typeCre;//证件类型

	private String userName;//名字

	private String engName;//英文名

	private String address;//地址

	private String telNo;//电话号码

	private String email;//电子邮件

	private String orgCd;//机构编号

	private String extDomain;//扩展域名

	private String cerLife;//证书期限

	private String enterTime;//录入时间

	private String userState;//用户信息状态 0-注销 1-停用 2-启用


	
	public String getUserCd() {
		return  userCd;
	}

	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}

	
	public String getUserInfo() {
		return  userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	
	public String getTypeUser() {
		return  typeUser;
	}

	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}

	
	public String getTypeApp() {
		return  typeApp;
	}

	public void setTypeApp(String typeApp) {
		this.typeApp = typeApp;
	}

	
	public String getAppName() {
		return  appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	
	public String getTypeCer() {
		return  typeCer;
	}

	public void setTypeCer(String typeCer) {
		this.typeCer = typeCer;
	}

	
	public String getTypeCre() {
		return  typeCre;
	}

	public void setTypeCre(String typeCre) {
		this.typeCre = typeCre;
	}

	
	public String getUserName() {
		return  userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getEngName() {
		return  engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	
	public String getAddress() {
		return  address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getTelNo() {
		return  telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	
	public String getEmail() {
		return  email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getOrgCd() {
		return  orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	
	public String getExtDomain() {
		return  extDomain;
	}

	public void setExtDomain(String extDomain) {
		this.extDomain = extDomain;
	}

	
	public String getCerLife() {
		return  cerLife;
	}

	public void setCerLife(String cerLife) {
		this.cerLife = cerLife;
	}

	
	public String getEnterTime() {
		return  enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	
	public String getUserState() {
		return  userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

}
