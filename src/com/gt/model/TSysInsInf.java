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
 * TSysInsInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_INS_INF", schema = "")
public class TSysInsInf implements java.io.Serializable {

	// Fields

	private String uuid;
	private TSysInsInf TSysInsInf;
	private String insCd;
	private String insDetail;
	private String insLvl;
	private String insNm;
	private String upTwoIns;
	private String upThreeIns;
	private String insTp;
	private String insOper;
	private String insAddres;
	private String insPhone;
	private String mark;
	private Set<TSysOperInf> TSysOperInfs = new HashSet<TSysOperInf>(0);
	private Set<TSysInsInf> TSysInsInfs = new HashSet<TSysInsInf>(0);

	// Constructors

	/** default constructor */
	public TSysInsInf() {
	}

	/** minimal constructor */
	public TSysInsInf(String uuid, String insCd, String insLvl, String insNm) {
		this.uuid = uuid;
		this.insCd = insCd;
		this.insLvl = insLvl;
		this.insNm = insNm;
	}

	/** full constructor */
	public TSysInsInf(String uuid, TSysInsInf TSysInsInf, String insCd, String insDetail, String insLvl, String insNm, String upTwoIns, String upThreeIns, String insTp, String insOper, String insAddres, String insPhone, String mark, Set<TSysOperInf> TSysOperInfs, Set<TSysInsInf> TSysInsInfs) {
		this.uuid = uuid;
		this.TSysInsInf = TSysInsInf;
		this.insCd = insCd;
		this.insDetail = insDetail;
		this.insLvl = insLvl;
		this.insNm = insNm;
		this.upTwoIns = upTwoIns;
		this.upThreeIns = upThreeIns;
		this.insTp = insTp;
		this.insOper = insOper;
		this.insAddres = insAddres;
		this.insPhone = insPhone;
		this.mark = mark;
		this.TSysOperInfs = TSysOperInfs;
		this.TSysInsInfs = TSysInsInfs;
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
	@JoinColumn(name = "PARENT_INS_CD")
	public TSysInsInf getTSysInsInf() {
		return this.TSysInsInf;
	}

	public void setTSysInsInf(TSysInsInf TSysInsInf) {
		this.TSysInsInf = TSysInsInf;
	}

	@Column(name = "INS_CD", nullable = false, length = 20)
	public String getInsCd() {
		return this.insCd;
	}

	public void setInsCd(String insCd) {
		this.insCd = insCd;
	}

	@Column(name = "INS_DETAIL", length = 200)
	public String getInsDetail() {
		return this.insDetail;
	}

	public void setInsDetail(String insDetail) {
		this.insDetail = insDetail;
	}

	@Column(name = "INS_LVL", nullable = false, length = 2)
	public String getInsLvl() {
		return this.insLvl;
	}

	public void setInsLvl(String insLvl) {
		this.insLvl = insLvl;
	}

	@Column(name = "INS_NM", nullable = false, length = 100)
	public String getInsNm() {
		return this.insNm;
	}

	public void setInsNm(String insNm) {
		this.insNm = insNm;
	}

	@Column(name = "UP_TWO_INS", length = 36)
	public String getUpTwoIns() {
		return this.upTwoIns;
	}

	public void setUpTwoIns(String upTwoIns) {
		this.upTwoIns = upTwoIns;
	}

	@Column(name = "UP_THREE_INS", length = 36)
	public String getUpThreeIns() {
		return this.upThreeIns;
	}

	public void setUpThreeIns(String upThreeIns) {
		this.upThreeIns = upThreeIns;
	}

	@Column(name = "INS_TP", length = 2)
	public String getInsTp() {
		return this.insTp;
	}

	public void setInsTp(String insTp) {
		this.insTp = insTp;
	}

	@Column(name = "INS_OPER", length = 50)
	public String getInsOper() {
		return this.insOper;
	}

	public void setInsOper(String insOper) {
		this.insOper = insOper;
	}

	@Column(name = "INS_ADDRES", length = 100)
	public String getInsAddres() {
		return this.insAddres;
	}

	public void setInsAddres(String insAddres) {
		this.insAddres = insAddres;
	}

	@Column(name = "INS_PHONE", length = 20)
	public String getInsPhone() {
		return this.insPhone;
	}

	public void setInsPhone(String insPhone) {
		this.insPhone = insPhone;
	}

	@Column(name = "MARK", length = 100)
	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysInsInf")
	public Set<TSysOperInf> getTSysOperInfs() {
		return this.TSysOperInfs;
	}

	public void setTSysOperInfs(Set<TSysOperInf> TSysOperInfs) {
		this.TSysOperInfs = TSysOperInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysInsInf")
	public Set<TSysInsInf> getTSysInsInfs() {
		return this.TSysInsInfs;
	}

	public void setTSysInsInfs(Set<TSysInsInf> TSysInsInfs) {
		this.TSysInsInfs = TSysInsInfs;
	}

}