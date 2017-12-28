package com.gt.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gt.pageModel.Json;
import com.gt.pageModel.TableFields;
import com.gt.pageModel.TemplateParams;

/**
 * 
 * @功能说明：代码生成器
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
public interface ICodeGeneratorService {

	/**
	 * 获取所有的表名称
	 * 
	 * @return
	 */
	public List<TemplateParams> getTablesList() throws Exception;

	/**
	 * 根据表名称获取字段集合
	 * 
	 * @param templateParams
	 * @return
	 */
	public List<TableFields> getFieldList(String tableName) throws Exception;

	/**
	 * 生成代码
	 * 
	 * @param templateParams
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Json createCode(TemplateParams templateParams, HttpSession session, HttpServletRequest request) throws Exception;

}
