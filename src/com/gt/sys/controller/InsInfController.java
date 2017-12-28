package com.gt.sys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.InsInf;
import com.gt.pageModel.Json;
import com.gt.pageModel.SessionInfo;
import com.gt.pageModel.TreeForLayUI;
import com.gt.pageModel.ZTree;
import com.gt.sys.service.IInsInfService;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：机构信息
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/insInf")
public class InsInfController extends BaseController {

	private Logger logger = Logger.getLogger(InsInfController.class);
	private IInsInfService insInfService;

	public IInsInfService getInsInfService() {
		return insInfService;
	}

	@Autowired
	public void setInsInfService(IInsInfService insInfService) {
		this.insInfService = insInfService;
	}

	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(InsInf insInf, HttpSession session) {
		// 系统登录用户
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		String insCd = sessionInfo.getOperInf().getInsUuid();
		DatagridForLayUI datagrid = insInfService.datagrid(insInf,insCd);
		return datagrid;
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(InsInf insInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			insInf.setUuid(PbUtils.getUUID());
			j = insInfService.verifyInfo(insInf);
			if (j.isSuccess()) {
				j = insInfService.add(insInf);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("添加失败:" + e.getMessage());
		}
		// 添加系统日志
		writeSysLog(j, insInf, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(InsInf insInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			insInfService.remove(insInf.getUuid());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
		}
		// 添加系统日志
		writeSysLog(j, insInf, request, session);

		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(InsInf insInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = insInfService.verifyInfo(insInf);
			if (j.isSuccess()) {
				j = insInfService.modify(insInf);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}
		// 添加系统日志
		writeSysLog(j, insInf, request, session);

		return j;
	}

	/**
	 * 获取下拉框的数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<InsInf> getList() {
		List<InsInf> list = insInfService.getList();
		return list;
	}

	/**
	 * 获取树形机构信息
	 */
	@RequestMapping("/getTree")
	@ResponseBody
	public List<ZTree> getTree(HttpSession session,String insTp) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		List<ZTree> list = new ArrayList<ZTree>();
		if (sessionInfo != null) {
			list = insInfService.getInsList(sessionInfo.getOperInf().getInsUuid(),insTp);
		}
		return list;
	}
	
	/**
	 * 获取树形机构信息
	 */
	@RequestMapping("/getInsListForLayUI")
	@ResponseBody
	public List<TreeForLayUI> getInsListForLayUI(HttpSession session,String insTp) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		if (sessionInfo != null) {
			return insInfService.getInsListForLayUI(sessionInfo.getOperInf().getInsUuid(),insTp);
		}
		return null;
	}
}
