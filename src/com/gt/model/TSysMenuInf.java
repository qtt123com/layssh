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
 * TSysMenuInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SYS_MENU_INF", schema = "")
public class TSysMenuInf implements java.io.Serializable {

	// Fields

	private String id;
	private TSysMenuInf TSysMenuInf;
	private String iconCls;
	private String menuSort;
	private String menuUrl;
	private String text;
	private Set<TSysFunctionInf> TSysFunctionInfs = new HashSet<TSysFunctionInf>(0);
	private Set<TSysMenuInf> TSysMenuInfs = new HashSet<TSysMenuInf>(0);

	// Constructors

	/** default constructor */
	public TSysMenuInf() {
	}

	/** minimal constructor */
	public TSysMenuInf(String id, String menuSort, String text) {
		this.id = id;
		this.menuSort = menuSort;
		this.text = text;
	}

	/** full constructor */
	public TSysMenuInf(String id, TSysMenuInf TSysMenuInf, String iconCls, String menuSort, String menuUrl, String text, Set<TSysFunctionInf> TSysFunctionInfs, Set<TSysMenuInf> TSysMenuInfs) {
		this.id = id;
		this.TSysMenuInf = TSysMenuInf;
		this.iconCls = iconCls;
		this.menuSort = menuSort;
		this.menuUrl = menuUrl;
		this.text = text;
		this.TSysFunctionInfs = TSysFunctionInfs;
		this.TSysMenuInfs = TSysMenuInfs;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 72)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRAENT_ID")
	public TSysMenuInf getTSysMenuInf() {
		return this.TSysMenuInf;
	}

	public void setTSysMenuInf(TSysMenuInf TSysMenuInf) {
		this.TSysMenuInf = TSysMenuInf;
	}

	@Column(name = "ICON_CLS", length = 40)
	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Column(name = "MENU_SORT", nullable = false, length = 20)
	public String getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}

	@Column(name = "MENU_URL", length = 400)
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name = "TEXT", nullable = false, length = 200)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysMenuInf")
	public Set<TSysFunctionInf> getTSysFunctionInfs() {
		return this.TSysFunctionInfs;
	}

	public void setTSysFunctionInfs(Set<TSysFunctionInf> TSysFunctionInfs) {
		this.TSysFunctionInfs = TSysFunctionInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSysMenuInf")
	public Set<TSysMenuInf> getTSysMenuInfs() {
		return this.TSysMenuInfs;
	}

	public void setTSysMenuInfs(Set<TSysMenuInf> TSysMenuInfs) {
		this.TSysMenuInfs = TSysMenuInfs;
	}

}