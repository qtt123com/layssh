package com.gt.sys.service;

import java.util.List;

import javax.servlet.ServletContext;

import com.gt.model.Quarz;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;

/**
 * @功能说明：任务调度业务接口类
 * @作者： herun
 * @创建日期：2015-11-18
 * @版本号：V1.0
 */
public interface IQuarzService extends IBaseService<Quarz> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(Quarz inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(Quarz inf, ServletContext context) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids) throws Exception;

	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(Quarz inf, ServletContext context) throws Exception;

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(Quarz inf);

	/**
	 * 获取下拉框数据
	 * 
	 * @return
	 */
	public List<Quarz> getList();

}
