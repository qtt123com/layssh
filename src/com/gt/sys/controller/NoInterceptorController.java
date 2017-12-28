package com.gt.sys.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.NoInterceptor;
import com.gt.sys.service.INoInterceptorService;


/**
 * 
 * @功能说明：不需要拦截的URL配置
 * @作者： herun
 * @创建日期：2015-09-30
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/noInterceptor")

public class NoInterceptorController extends BaseController {
	private Logger logger = Logger.getLogger(NoInterceptor.class);
	private INoInterceptorService noInterceptorService;

	public INoInterceptorService getNoInterceptorService() {
		return  noInterceptorService;
	}

	@Autowired
	public void setNoInterceptorService(INoInterceptorService noInterceptorService) {
		this.noInterceptorService = noInterceptorService;
	}


	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(NoInterceptor noInterceptor) {
		DatagridForLayUI datagrid=noInterceptorService.datagrid(noInterceptor);
		return datagrid;
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(NoInterceptor noInterceptor, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			noInterceptor.setUuid(UUID.randomUUID().toString());
			j = noInterceptorService.add(noInterceptor);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("添加失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, noInterceptor, request, session);
		
		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(NoInterceptor noInterceptor, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			noInterceptorService.remove(noInterceptor.getUuid());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
		}
		
		//添加系统日志
		writeSysLog(j, noInterceptor,request, session);
		
		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(NoInterceptor noInterceptor, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = noInterceptorService.modify(noInterceptor);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}
	
		//添加系统日志
		writeSysLog(j, noInterceptor,request,session );
		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<NoInterceptor>  getList() {
		List<NoInterceptor> list = noInterceptorService.getList();
		return list;
	}

}
