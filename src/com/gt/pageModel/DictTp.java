package com.gt.pageModel;

/**
 * 
 * @功能说明：数据字典类型
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class DictTp extends BasePageForLayUI{

	private static final long serialVersionUID = 1L;

	private String dictTypeCd;// 字典类型编号

	private String dictTypeNm;// 字典类型名称


	public String getDictTypeCd() {
		return dictTypeCd;
	}

	public void setDictTypeCd(String dictTypeCd) {
		this.dictTypeCd = dictTypeCd;
	}

	public String getDictTypeNm() {
		return dictTypeNm;
	}

	public void setDictTypeNm(String dictTypeNm) {
		this.dictTypeNm = dictTypeNm;
	}

}
