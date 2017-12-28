package com.gt.pageModel;

import java.util.List;
import java.util.Map;

/**
 * 
 * @功能说明：菜单表
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class MenuInf extends BasePageForLayUI {

	private static final long serialVersionUID = 1L;

	private String id;// 菜单ID

	private String pid;// 上一级菜单id

	private String pidNm;// 上一级菜单名称

	private String text;// 菜单名称

	private String state;// 文件展开形式

	private String menuUrl;// URL

	private String iconCls;// 图标

	private String menuSort;// 菜单顺序

	private String sort;// 菜单顺序-easyUI自带

	private Map<String, Object> attributes;

	public List<MenuInf> children;    

	
	public List<MenuInf> getChildren() {
		return children;
	}

	public void setChildren(List<MenuInf> children) {
		this.children = children;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getPidNm() {
		return pidNm;
	}

	public void setPidNm(String pidNm) {
		this.pidNm = pidNm;
	}

}
