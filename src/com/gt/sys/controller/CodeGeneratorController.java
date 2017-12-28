package com.gt.sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.Json;
import com.gt.pageModel.TableFields;
import com.gt.pageModel.TemplateParams;
import com.gt.sys.service.ICodeGeneratorService;

/**
 * 
 * @功能说明：代码生成器,生成Action、JavaBean、IDao、DaoImpl、ServiceImpl、IService类
 * @作者： herun
 * @创建日期：2015-09-25
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/codeGenerator")
public class CodeGeneratorController extends BaseController {
	private Logger logger = Logger.getLogger(CodeGeneratorController.class);

	@Resource(name = "CodeGeneratorByMysqlByLayUIServiceImpl")
	private ICodeGeneratorService codeGeneratorService;
	
	
	/**
	 * 获取所有数据库表下拉框数据
	 */
	@RequestMapping("/getTablesList")
	@ResponseBody
	public List<TemplateParams> getTablesList() {
		List<TemplateParams> listParams = null;
		try {
			listParams = codeGeneratorService.getTablesList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return listParams;
	}

	/**
	 * 根据表名称获取字段集合
	 */
	@RequestMapping("/getFieldList")
	@ResponseBody
	public List<TableFields> getFieldList(TemplateParams templateParams) {
		List<TableFields> tableFields = null;
		try {
			tableFields = codeGeneratorService.getFieldList(templateParams.getTableName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return tableFields;
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/createCode")
	@ResponseBody
	public Json createCode(TemplateParams templateParams, HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		try {
			j = codeGeneratorService.createCode(templateParams, session, request);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("代码生成失败");
			e.printStackTrace();
		}
		return j;
	}

}
