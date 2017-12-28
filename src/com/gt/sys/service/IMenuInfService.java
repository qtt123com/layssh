package com.gt.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gt.model.TSysMenuInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.MenuForLayUI;
import com.gt.pageModel.MenuInf;
import com.gt.pageModel.TreeForLayUI;
import com.gt.pageModel.ZTree;

/**
 * 
 * @功能说明：菜单功能service接口
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IMenuInfService extends IBaseService<TSysMenuInf> {

	/**
	 * 异步加载树
	 * 
	 * @param id
	 * @return
	 */
	public List<MenuInf> getTree(String id);

	/**
	 * 加载全部树
	 * 
	 * @return
	 */
	public List<MenuInf> getAllTree();

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(MenuInf MenuInf);

	/**
	 * 新增
	 * 
	 * @param menuInf
	 * @return
	 */
	public MenuInf add(MenuInf menuInf);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);

	/**
	 * 获取下拉框数据
	 * 
	 * @return
	 */
	public List<MenuInf> getMenuList();

	/**
	 * 获取下拉框数据,带有URL
	 * 
	 * @return
	 */
	public List<MenuInf> getMenuUrlNotNullList();

	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(MenuInf menuInf);

	/**
	 * 根据角色权限获取菜单
	 * 
	 * @return
	 */
	public List<MenuForLayUI> getTreeByRole(String operCd) throws Exception;

	/**
	 * 根据角色权限获取菜单-去除系统管理菜单
	 * @param operCd
	 * @return
	 * @throws Exception
	 */
	public List<MenuInf> getTreeByRoleByNoSys(String operCd) throws Exception ;
	/**
	 * 获取树形下拉框
	 * 
	 * @return
	 */
	public List<ZTree> getZTree();

	/**
	 * 获取树形JSON
	 * 
	 * @param menuInf
	 * @return
	 */
	public List<MenuInf> treegrid();

	/**
	 * 验证
	 * 
	 * @param menuInf
	 * @return
	 */
	public boolean vaild(MenuInf menuInf);

	/**
	 * 获取图标下拉框
	 * 
	 * @param request
	 * @return
	 */
	public List<MenuInf> getIconsList(HttpServletRequest request);

	/**
	 * 获取layUI tree
	 * @return
	 */
	public List<TreeForLayUI> getLayUItree() throws Exception;
	
	/**
	 * 获取所有layUI tree
	 * @return
	 * @throws Exception
	 */
	public List<TreeForLayUI> getAllLayUItree() throws Exception;
	
	/**
	 * 是否为做后一级菜单
	 * @param pid
	 * @return
	 */
	public boolean isLastMenu(String pid) throws Exception;

}
