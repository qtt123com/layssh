package com.gt.app.service;

import java.util.List;
import com.gt.model.Student;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：学生管理业务接口类
 * @作者： herun
 * @创建日期：2017-12-13
 * @版本号：V1.0
 */
public interface IStudentService extends IBaseService<Student> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(Student inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(Student inf) throws Exception;

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
	public Json modify(Student inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(Student inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<Student> getList();

	
}
