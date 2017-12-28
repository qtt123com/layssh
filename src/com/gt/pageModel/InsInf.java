package com.gt.pageModel;

/**
 * 
 * @功能说明：机构信息
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class InsInf extends BasePageForLayUI {
	private static final long serialVersionUID = 1L;

	private String uuid;// uuid

	private String insCd;// 机构编号

	private String parentInsCd;// 上一级机构编号

	private String parentInsCdNm;// 上一级机构编号-名称

	private String insNm;// 机构名称

	private String insLvl;// 机构级别

	private String upTwoIns;// 上两级机构

	private String upTwoInsNm;// 上两级机构-名称

	private String upThreeIns;// 上三级机构

	private String upThreeInsNm;// 上三级机构-名称

	private String insTp;// 机构类型

	private String insTpNm;// 机构类型-名称

	private String insOper;// 机构联系人

	private String insAddres;// 机构地址

	private String insPhone;// 机构联系电话

	private String mark;// 备注

	public String getInsTpNm() {
		return insTpNm;
	}

	public void setInsTpNm(String insTpNm) {
		this.insTpNm = insTpNm;
	}

	public String getInsTp() {
		return insTp;
	}

	public void setInsTp(String insTp) {
		this.insTp = insTp;
	}

	public String getInsOper() {
		return insOper;
	}

	public void setInsOper(String insOper) {
		this.insOper = insOper;
	}

	public String getInsAddres() {
		return insAddres;
	}

	public void setInsAddres(String insAddres) {
		this.insAddres = insAddres;
	}

	public String getInsPhone() {
		return insPhone;
	}

	public void setInsPhone(String insPhone) {
		this.insPhone = insPhone;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUpTwoInsNm() {
		return upTwoInsNm;
	}

	public void setUpTwoInsNm(String upTwoInsNm) {
		this.upTwoInsNm = upTwoInsNm;
	}

	public String getUpThreeInsNm() {
		return upThreeInsNm;
	}

	public void setUpThreeInsNm(String upThreeInsNm) {
		this.upThreeInsNm = upThreeInsNm;
	}

	public String getUpTwoIns() {
		return upTwoIns;
	}

	public void setUpTwoIns(String upTwoIns) {
		this.upTwoIns = upTwoIns;
	}

	public String getUpThreeIns() {
		return upThreeIns;
	}

	public void setUpThreeIns(String upThreeIns) {
		this.upThreeIns = upThreeIns;
	}

	public String getParentInsCdNm() {
		return parentInsCdNm;
	}

	public void setParentInsCdNm(String parentInsCdNm) {
		this.parentInsCdNm = parentInsCdNm;
	}

	private String insLvlNm;// 机构级别-名称

	public String getInsLvlNm() {
		return insLvlNm;
	}

	public void setInsLvlNm(String insLvlNm) {
		this.insLvlNm = insLvlNm;
	}

	private String insDetail;// 机构详情

	public String getInsCd() {
		return insCd;
	}

	public void setInsCd(String insCd) {
		this.insCd = insCd;
	}

	public String getParentInsCd() {
		return parentInsCd;
	}

	public void setParentInsCd(String parentInsCd) {
		this.parentInsCd = parentInsCd;
	}

	public String getInsNm() {
		return insNm;
	}

	public void setInsNm(String insNm) {
		this.insNm = insNm;
	}

	public String getInsLvl() {
		return insLvl;
	}

	public void setInsLvl(String insLvl) {
		this.insLvl = insLvl;
	}

	public String getInsDetail() {
		return insDetail;
	}

	public void setInsDetail(String insDetail) {
		this.insDetail = insDetail;
	}

}
