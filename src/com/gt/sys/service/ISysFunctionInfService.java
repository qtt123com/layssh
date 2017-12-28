package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysFunctionInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.SysFunctionInf;

/**
 * 
 * @功能说明：系统功能service接口
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface ISysFunctionInfService extends IBaseService<TSysFunctionInf> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(SysFunctionInf sysFunctionInf);

	/**
	 * 新增
	 * 
	 * @param sftinfo
	 * @return
	 */
	public SysFunctionInf add(SysFunctionInf sftinfo);

	/**
	 * 删除
	 * 
	 * @param menuCd
	 */
	public void remove(String isd);

	/**
	 * 修改
	 * 
	 * @param sftinfo
	 * @return
	 */
	public Json modify(SysFunctionInf sftinfo);

	/**
	 * 判断URL是否存在
	 * 
	 * @param functionUrl
	 * @return
	 */
	public boolean isExistUrl(String functionUrl);

	/**
	 * 根据角色判断是否该角色有权限
	 * 
	 * @param listRoleInf
	 * @param requestPath
	 * @return
	 */
	public boolean checkAuth(List<RoleInf> listRoleInf, String requestPath);

	/**
	 * 根据URL查询功能名称
	 * 
	 * @param url
	 * @return
	 */
	public String getFunctionNm(String url);

	/**
	 * 根据URL获取对象
	 * 
	 * @param url
	 * @return
	 */
	public SysFunctionInf getFunction(String url);

	/**
	 * 根据请求地址和角色CD统计权限
	 * 
	 * @param requestPath
	 * @param roleCd
	 * @return
	 */
	public Integer countSysByRoleAndRequest(String requestPath, String roleCd);

	/**
	 * 获取所有数据
	 * 
	 * @return
	 */
	public List<SysFunctionInf> getList();

}
