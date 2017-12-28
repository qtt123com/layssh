package com.gt.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.SessionInfo;
import com.gt.pageModel.ZTree;
import com.gt.sys.service.IRoleInfService;
import com.gt.utils.Contans;

/**
 * 
 * @功能说明：角色管理
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/roleInf")
public class RoleInfController extends BaseController {

	private Logger logger = Logger.getLogger(RoleInfController.class);
	private IRoleInfService roleInfService;

	public IRoleInfService getRoleInfService() {
		return roleInfService;
	}

	@Autowired
	public void setRoleInfService(IRoleInfService roleInfService) {
		this.roleInfService = roleInfService;
	}

	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(RoleInf roleInf, HttpSession session) {
		// 系统登录用户
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		String insCd = sessionInfo.getOperInf().getInsUuid();
		DatagridForLayUI datagrid = null;
		try {
			datagrid = roleInfService.datagrid(roleInf, insCd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datagrid;
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(RoleInf roleInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			// 系统登录用户
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			roleInf.setWrite_oper_cd(sessionInfo.getOperInf().getOperCd());
			j = roleInfService.verifyInfo(roleInf);
			if (j.isSuccess()) {
				roleInf.setRoleCd(UUID.randomUUID().toString());// 设置UUID
				RoleInf inf = roleInfService.add(roleInf);
				j.setMsg("新增成功");
				j.setObj(inf);
				j.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("添加失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, roleInf, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(RoleInf roleInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			roleInfService.remove(roleInf.getRoleCd());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, roleInf, request, session);

		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(RoleInf roleInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			// 系统登录用户
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			roleInf.setWrite_oper_cd(sessionInfo.getOperInf().getOperCd());
			j = roleInfService.modify(roleInf);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, roleInf, request, session);

		return j;
	}

	/**
	 * 获取菜单信息
	 * 
	 * @return
	 */
	@RequestMapping("/getAllTree")
	@ResponseBody
	public List<ZTree> getAllTree() {
		List<ZTree> list = null;
		try {
			list = roleInfService.getAllTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取角色中权限功能
	 */
	@RequestMapping("/getRoleSelected")
	@ResponseBody
	public Map<String, Object> getRoleSelected(RoleInf roleInf) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", roleInfService.getRoleSelected(roleInf.getRoleCd()));
		return map;
	}

	/**
	 * 获取角色下拉框
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<RoleInf> getList(HttpSession session) {
		// 系统登录用户
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		String insCd = sessionInfo.getOperInf().getInsUuid();
		String operCd = sessionInfo.getOperInf().getOperCd();
		List<RoleInf> list = new ArrayList<RoleInf>();
		try {
			list = roleInfService.getList(insCd, operCd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
