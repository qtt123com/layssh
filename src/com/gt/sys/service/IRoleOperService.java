package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysRoleOper;
import com.gt.pageModel.RoleOper;

/**
 * 
 * @功能说明：人员角色service接口
 * @作者： herun
 * @创建日期：2015-10-08
 * @版本号：V1.0
 */
public interface IRoleOperService extends IBaseService<TSysRoleOper> {

	/**
	 * 根据用户CD获取数据
	 * 
	 * @param operCd
	 * @return
	 */
	public List<TSysRoleOper> getList(String operCd);

	/**
	 * 新增
	 * 
	 * @param roleOper
	 * @return
	 */
	public void add(RoleOper roleOper);

	/**
	 * 删除
	 * 
	 * @param operCd
	 */
	public void remove(String operCd);
}
