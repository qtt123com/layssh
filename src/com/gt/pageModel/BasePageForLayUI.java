package com.gt.pageModel;


/**
 * 
 * @功能说明：基础页面对象，其他对象继续次对象
 * @作者： herun
 * @创建日期：2015-09-19
 * @版本号：V1.0
 */
public class BasePageForLayUI implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	protected int page = 1;// 当前页

	protected int limit = 20;// 每页显示记录数

	protected String sort;// 排序字段

	protected String order = "asc";// asc/desc
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
