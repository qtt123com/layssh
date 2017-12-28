package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSysNointerceptor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_NOINTERCEPTOR", schema = "")
public class TSysNointerceptor implements java.io.Serializable {

	// Fields

	private String uuid;
	private String functionUrl;
	private String note;

	// Constructors

	/** default constructor */
	public TSysNointerceptor() {
	}

	/** minimal constructor */
	public TSysNointerceptor(String uuid, String functionUrl) {
		this.uuid = uuid;
		this.functionUrl = functionUrl;
	}

	/** full constructor */
	public TSysNointerceptor(String uuid, String functionUrl, String note) {
		this.uuid = uuid;
		this.functionUrl = functionUrl;
		this.note = note;
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

	@Column(name = "FUNCTION_URL", nullable = false, length = 400)
	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	@Column(name = "NOTE", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}