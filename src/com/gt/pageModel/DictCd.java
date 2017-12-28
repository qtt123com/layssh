package com.gt.pageModel;

/**
 * 
 * @功能说明：数据字典
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class DictCd extends BasePageForLayUI {
	private static final long serialVersionUID = 1L;
	
	private String uuid;// UUID
	
	private String dictCd;// 字典代码
	
	private String dictNm;// 字典名称
	
	private String dictTypeCd;// 字典类型
	
	private String dictTypeCdNm;// 字典类型-名称

	private String qsr;//请输入
	
	
	public String getQsr() {
		return qsr;
	}

	public void setQsr(String qsr) {
		this.qsr = qsr;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDictCd() {
		return dictCd;
	}

	public void setDictCd(String dictCd) {
		this.dictCd = dictCd;
	}

	public String getDictNm() {
		return dictNm;
	}

	public void setDictNm(String dictNm) {
		this.dictNm = dictNm;
	}

	public String getDictTypeCd() {
		return dictTypeCd;
	}

	public void setDictTypeCd(String dictTypeCd) {
		this.dictTypeCd = dictTypeCd;
	}

	public String getDictTypeCdNm() {
		return dictTypeCdNm;
	}

	public void setDictTypeCdNm(String dictTypeCdNm) {
		this.dictTypeCdNm = dictTypeCdNm;
	}
	
	

}
