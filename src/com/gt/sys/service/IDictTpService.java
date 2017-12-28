package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysDictTp;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.DictTp;
import com.gt.pageModel.Json;

/**
 * 
 * @功能说明：数据字典类型
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IDictTpService extends IBaseService<TSysDictTp> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(DictTp inf);

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public DictTp add(DictTp inf);

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
	public Json modify(DictTp inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(DictTp inf);

	/**
	 * 获取下拉框数据
	 * 
	 * @return
	 */
	public List<DictTp> getList();
}
