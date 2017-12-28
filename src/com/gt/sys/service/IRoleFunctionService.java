package com.gt.sys.service;

import com.gt.model.TSysRoleFunction;
import com.gt.pageModel.Json;
import com.gt.pageModel.RoleFunction;

/**
 * 
 * @功能说明：角色功能信息
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IRoleFunctionService extends IBaseService<TSysRoleFunction> {


	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public void  add(RoleFunction function);


	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String roleCd);


	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(RoleFunction function);
	
}
