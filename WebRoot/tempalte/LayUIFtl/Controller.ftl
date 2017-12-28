package com.gt.app.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.model.${className};
import com.gt.pageModel.Json;
import com.gt.sys.controller.BaseController;
import com.gt.app.service.I${className}Service;


/**
 * @功能说明：${functionComment}
 * @作者：${author}
 * @创建日期：${date}
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/${classNameToL}")

public class ${className}Controller extends BaseController {
	private Logger logger = Logger.getLogger(${className}Controller.class);
	private I${className}Service ${classNameToL}Service;

	public I${className}Service get${className}Service() {
		return  ${classNameToL}Service;
	}

	@Autowired
	public void set${className}Service(I${className}Service ${classNameToL}Service) {
		this.${classNameToL}Service = ${classNameToL}Service;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(${className} ${classNameToL}) {
		DatagridForLayUI datagrid=null;
		try {
			datagrid = ${classNameToL}Service.datagrid(${classNameToL});
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return datagrid;
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(${className} ${classNameToL}, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = ${classNameToL}Service.add(${classNameToL});
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, ${classNameToL}, request, session);
		
		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(${className} ${classNameToL}, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			${classNameToL}Service.remove(${classNameToL}.get${PkColumn}().toString());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, ${classNameToL},request, session);
		
		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(${className} ${classNameToL}, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = ${classNameToL}Service.modify(${classNameToL});
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}
	
		//添加系统日志
		writeSysLog(j, ${classNameToL},request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<${className}>  getList() {
		List<${className}> list = ${classNameToL}Service.getList();
		return list;
	}

}
