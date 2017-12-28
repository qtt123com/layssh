package com.gt.pageModel;

/**
 * 
 * @功能说明：Session信息
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class SessionInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private OperInf operInf;// 用户信息

	private boolean isDefaultPwd = false;//是否为默认密码
	

	public boolean isDefaultPwd() {
		return isDefaultPwd;
	}

	public void setDefaultPwd(boolean isDefaultPwd) {
		this.isDefaultPwd = isDefaultPwd;
	}

	public OperInf getOperInf() {
		return operInf;
	}

	public void setOperInf(OperInf operInf) {
		this.operInf = operInf;
	}

}