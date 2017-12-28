package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysRoleInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.RoleFunction;
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.ZTree;

/**
 * 
 * @功能说明：角色管理
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IRoleInfService extends IBaseService<TSysRoleInf> {

	/**
	 * 获取datagrid数据
	 * 
	 * @param inf
	 * @param insCd
	 * @return
	 */
	public DatagridForLayUI datagrid(RoleInf inf, String insCd) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public RoleInf add(RoleInf inf);

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
	public Json modify(RoleInf inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(RoleInf inf);

	/**
	 * 获取菜单信息
	 * 
	 * @return
	 */
	public List<ZTree> getAllTree() throws Exception;

	/**
	 * 获取角色中已有的权限
	 * 
	 * @return
	 */
	public List<RoleFunction> getRoleSelected(String roleCd);

	/**
	 * 获取角色下拉框
	 * 
	 * @param insCd
	 * @param operCd
	 * @return
	 * @throws Exception
	 */
	public List<RoleInf> getList(String insCd, String operCd) throws Exception;

	/**
	 * 获取所有角色信息
	 * 
	 * @return
	 */
	public List<RoleInf> getAllList() throws Exception;
}
