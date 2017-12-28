package com.gt.app.service;

import java.util.List;
import com.gt.model.${className};
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：${functionComment}业务接口类
 * @作者： ${author}
 * @创建日期：${date}
 * @版本号：V1.0
 */
public interface I${className}Service extends IBaseService<${className}> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(${className} inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(${className} inf) throws Exception;

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
	public Json modify(${className} inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(${className} inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<${className}> getList();

	
}
