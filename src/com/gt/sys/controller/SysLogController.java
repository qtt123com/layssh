package com.gt.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.SysLog;
import com.gt.sys.service.ISysLogService;

/**
 * 
 * @功能说明：系统管理
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {

	private ISysLogService sysLogService;

	public ISysLogService getSysLogService() {
		return sysLogService;
	}

	@Autowired
	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(SysLog sysLog,HttpServletRequest request) {
		// String numString=request.getParameter("limit");
		// System.out.println(numString);
		// String page=request.getParameter("page");
		// System.out.println("--->"+page);
		return sysLogService.datagrid(sysLog);
	}

}
