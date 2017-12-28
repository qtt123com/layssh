package com.gt.pageModel;

import java.util.List;

/**
 * 
 * @功能说明：菜单表
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class MenuForLayUI  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String id;// 菜单ID
	private String pid;// 上一级菜单id
	private String title;// 基本元素
	private String icon;// 图标
	private boolean spread;// 是否展开
	private String href;// URL
	public List<MenuForLayUI> children;
	
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<MenuForLayUI> getChildren() {
		return children;
	}

	public void setChildren(List<MenuForLayUI> children) {
		this.children = children;
	}

}
