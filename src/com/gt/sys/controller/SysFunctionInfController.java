package com.gt.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.SysFunctionInf;
import com.gt.sys.service.IMenuInfService;

/**
 * 
 * @功能说明：系统功能
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/sysFunctionInf")
public class SysFunctionInfController extends BaseController {

	private Logger logger = Logger.getLogger(SysFunctionInfController.class);

	public IMenuInfService menuInfService;// 菜单功能
	
	
	public IMenuInfService getMenuInfService() {
		return menuInfService;
	}
	
	@Autowired
	public void setMenuInfService(IMenuInfService menuInfService) {
		this.menuInfService = menuInfService;
	}
	
	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(SysFunctionInf sftinfo) {
		return sysFunctionInfService.datagrid(sftinfo);
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SysFunctionInf sftinfo, HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		try {
			boolean v = sysFunctionInfService.isExistUrl(sftinfo.getFunctionUrl());
			if (!v) {
				v=menuInfService.isLastMenu(sftinfo.getMenuCd());
				if(v){
					SysFunctionInf sysFunctionInf = sysFunctionInfService.add(sftinfo);
					j.setSuccess(true);
					j.setObj(sysFunctionInf);
					j.setMsg("新增成功");
				}else {
					j.setSuccess(false);
					j.setMsg("新增失败：所属菜单必须选择最后一级菜单");
				}
			} else {
				j.setSuccess(false);
				j.setMsg("新增失败：请求地址已经存在");
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("新增失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, sftinfo, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(SysFunctionInf sftinfo, HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		try {
			sysFunctionInfService.remove(sftinfo.getFunctionCd());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setSuccess(false);
			logger.error(e.getMessage());
			j.setMsg("删除失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, sftinfo, request, session);

		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(SysFunctionInf sftinfo, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			boolean v=menuInfService.isLastMenu(sftinfo.getMenuCd());
			if(v){
				j = sysFunctionInfService.modify(sftinfo);
			}else {
				j.setSuccess(false);
				j.setMsg("修改失败：所属菜单必须选择最后一级菜单");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, sftinfo, request, session);

		return j;
	}

}
