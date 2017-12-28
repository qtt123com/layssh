package com.gt.sys.service;

import com.gt.model.TSysLog;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.SysLog;

/**
 * 
 * @功能说明：系统日志
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface ISysLogService extends IBaseService<TSysLog> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(SysLog inf);

	/**
	 * 新增
	 * 
	 * @param sysLog
	 */
	public void add(SysLog sysLog);

	/**
	 * 获取所有表的主键
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getAllTablePK() throws Exception;
}
