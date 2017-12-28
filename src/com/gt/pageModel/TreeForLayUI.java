package com.gt.pageModel;

import java.util.List;

/**
 * LayUI 树形
 * @author： 黄和润
 * @date：2017-6-21
 */
public class TreeForLayUI  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String id;// 菜单ID
	private String pid;// 上一级菜单id
	private String name;// 基本元素
	private String icon;// 图标
	private boolean spread;// 是否展开
	//private String href;// URL
	private String alias;//别名
	public List<TreeForLayUI> children;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

//	public String getHref() {
//		return href;
//	}
//
//	public void setHref(String href) {
//		this.href = href;
//	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<TreeForLayUI> getChildren() {
		return children;
	}

	public void setChildren(List<TreeForLayUI> children) {
		this.children = children;
	}

}
