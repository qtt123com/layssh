package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysOperInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.OperInf;

/**
 * 
 * @功能说明：系统用户
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IOperInfService extends IBaseService<TSysOperInf> {

	/**
	 * 获取datagrid数据
	 * 
	 * @param operInf
	 * @param writeOperInsCd
	 * @return
	 * @throws Exception
	 */
	public DatagridForLayUI datagrid(OperInf operInf, String writeOperInsCd) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public OperInf add(OperInf inf);

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
	public Json modify(OperInf inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(OperInf inf);

	/**
	 * 密码重置
	 * 
	 * @param operCd
	 * @return
	 */
	public Json redoPwd(String operCd);

	/**
	 * 用户登录
	 * 
	 * @param operInf
	 * @return
	 */
	public Json userLogin(OperInf operInf);

	/**
	 * 修改密码
	 * 
	 * @param operInf
	 * @return
	 */
	public Json changepwd(OperInf operInf);
	
	/**
	 * 获取所有用户数据
	 * @return
	 */
	public List<OperInf> getList();
}
