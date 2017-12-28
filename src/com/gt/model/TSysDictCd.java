package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TSysDictCd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_DICT_CD", schema = "")
public class TSysDictCd implements java.io.Serializable {

	// Fields

	private String uuid;
	private TSysDictTp TSysDictTp;
	private String dictCd;
	private String dictNm;

	// Constructors

	/** default constructor */
	public TSysDictCd() {
	}

	/** minimal constructor */
	public TSysDictCd(String uuid, TSysDictTp TSysDictTp, String dictCd) {
		this.uuid = uuid;
		this.TSysDictTp = TSysDictTp;
		this.dictCd = dictCd;
	}

	/** full constructor */
	public TSysDictCd(String uuid, TSysDictTp TSysDictTp, String dictCd, String dictNm) {
		this.uuid = uuid;
		this.TSysDictTp = TSysDictTp;
		this.dictCd = dictCd;
		this.dictNm = dictNm;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DICT_TYPE_CD", nullable = false)
	public TSysDictTp getTSysDictTp() {
		return this.TSysDictTp;
	}

	public void setTSysDictTp(TSysDictTp TSysDictTp) {
		this.TSysDictTp = TSysDictTp;
	}

	@Column(name = "DICT_CD", nullable = false, length = 72)
	public String getDictCd() {
		return this.dictCd;
	}

	public void setDictCd(String dictCd) {
		this.dictCd = dictCd;
	}

	@Column(name = "DICT_NM", length = 200)
	public String getDictNm() {
		return this.dictNm;
	}

	public void setDictNm(String dictNm) {
		this.dictNm = dictNm;
	}

}