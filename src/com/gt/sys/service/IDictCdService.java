package com.gt.sys.service;

import java.util.List;

import com.gt.model.TSysDictCd;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.DictCd;
import com.gt.pageModel.Json;


/**
 * 
 * @功能说明：数据字典
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface IDictCdService extends IBaseService<TSysDictCd> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(DictCd dictCd);

	/**
	 * 新增
	 * 
	 * @param dictCd
	 * @return
	 */
	public DictCd add(DictCd dictCd);


	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);

	/**
	 * 修改
	 * 
	 * @param dictCd
	 */
	public Json modify(DictCd dictCd);

	/**
	 * 信息验证
	 * 
	 * @param dictCd
	 * @return
	 */
	public Json verifyInfo(DictCd dictCd);
	
	/**
	 * 根据字典类型返回数据
	 * 
	 * @param dictCd
	 *            字典类型
	 * @return
	 */
	public List<DictCd> getList(DictCd dictCd);

	/**
	 * 根据字典类型和字典编号查询
	 * 
	 * @param dictCd
	 * @return
	 */
	public List<DictCd> charFormat(DictCd dictCd);

	/**
	 * 根据条件查询
	 * 
	 * @param dictTypeCd
	 *            字典类型
	 * @param dictCd
	 *            字典编号
	 * @return 字典名称
	 */
	public String getDictCd(String dictTypeCd, String dictCd);
	
	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<DictCd> getList();

}
