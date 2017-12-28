package com.gt.pageModel;

/**
 * 
 * @功能说明：不需要拦截的URL配置
 * @作者： herun
 * @创建日期：2015-09-30
 * @版本号：V1.0
 */
public class NoInterceptor extends BasePageForLayUI {
	private static final long serialVersionUID = 1L;
	
	private String uuid;//UUID

	private String functionUrl;//不需要拦截的URL

	private String note;//备注
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUuid() {
		return  uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getFunctionUrl() {
		return  functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

}
