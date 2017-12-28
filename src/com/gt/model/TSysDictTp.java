package com.gt.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TSysDictTp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_DICT_TP", schema = "")
public class TSysDictTp implements java.io.Serializable {

	// Fields

	private String dictTypeCd;
	private String dictTypeNm;
	private Set<TSysDictCd> TSysDictCds = new HashSet<TSysDictCd>(0);

	// Constructors

	/** default constructor */
	public TSysDictTp() {
	}

	/** minimal constructor */
	public TSysDictTp(String dictTypeCd, String dictTypeNm) {
		this.dictTypeCd = dictTypeCd;
		this.dictTypeNm = dictTypeNm;
	}

	/** full constructor */
	public TSysDictTp(String dictTypeCd, String dictTypeNm, Set<TSysDictCd> TSysDictCds) {
		this.dictTypeCd = dictTypeCd;
		this.dictTypeNm = dictTypeNm;
		this.TSysDictCds = TSysDictCds;
	}

	@Id
	@Column(name = "DICT_TYPE_CD", nullable = false, length = 72)
	public String getDictTypeCd() {
		return this.dictTypeCd;
	}

	public void setDictTypeCd(String dictTypeCd) {
		this.dictTypeCd = dictTypeCd;
	}

	@Column(name = "DICT_TYPE_NM", nullable = false, length = 200)
	public String getDictTypeNm() {
		return this.dictTypeNm;
	}

	public void setDictTypeNm(String dictTypeNm) {
		this.dictTypeNm = dictTypeNm;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysDictTp")
	public Set<TSysDictCd> getTSysDictCds() {
		return this.TSysDictCds;
	}

	public void setTSysDictCds(Set<TSysDictCd> TSysDictCds) {
		this.TSysDictCds = TSysDictCds;
	}

}