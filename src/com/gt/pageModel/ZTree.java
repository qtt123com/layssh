package com.gt.pageModel;

/**
 * 
 * @功能说明：构造一棵树
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class ZTree implements java.io.Serializable {

	private static final long serialVersionUID = 171489839058998583L;

	private String id;// id

	private String insCd;// 机构编号

	private String pId;// 父节点

	private String name;// 名称

	private boolean open;// 是否展开

	public String getInsCd() {
		return insCd;
	}

	public void setInsCd(String insCd) {
		this.insCd = insCd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
