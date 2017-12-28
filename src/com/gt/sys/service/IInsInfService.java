package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysInsInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.InsInf;
import com.gt.pageModel.Json;
import com.gt.pageModel.TreeForLayUI;
import com.gt.pageModel.ZTree;


/**
 * 
 * @功能说明：机构信息
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IInsInfService extends IBaseService<TSysInsInf> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(InsInf inf,String insCd);

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(InsInf inf);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);

	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(InsInf inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(InsInf inf);

	/**
	 * 获取下拉框数据
	 * 
	 * @return
	 */
	public List<InsInf> getList();

	/**
	 * 根据角色权限获取树形菜单
	 * 
	 * @param insCd
	 * @return
	 */
	public List<ZTree> getInsList(String insCd,String insTp);
	

	/**
	 * 根据级别获取菜单
	 * @param insCd
	 * @param insTp
	 * @return
	 */
	public List<TreeForLayUI> getInsListForLayUI(String insCd, String insTp);
	
	/**
	 * 获取机构信息
	 * @return
	 */
	public List<ZTree> getInsList();
}
