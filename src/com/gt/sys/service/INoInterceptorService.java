package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysNointerceptor;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.NoInterceptor;

/**
 * 
 * @功能说明：不需要拦截的URL配置业务接口类
 * @作者： herun
 * @创建日期：2015-09-30
 * @版本号：V1.0
 */
public interface INoInterceptorService extends IBaseService<TSysNointerceptor> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(NoInterceptor inf);

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(NoInterceptor inf) throws Exception;

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
	public Json modify(NoInterceptor inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(NoInterceptor inf);

	/**
	 * 获取下拉框数据
	 * 
	 * @return
	 */
	public List<NoInterceptor> getList();

	/**
	 * 是否需要拦截
	 * 
	 * @param url
	 * @return
	 */
	public boolean isNeedInterceptor(String url);
}
