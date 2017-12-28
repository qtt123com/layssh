package com.gt.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.Json;
import com.gt.pageModel.RoleFunction;
import com.gt.sys.service.IRoleFunctionService;

/**
 * 
 * @功能说明：角色功能信息
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/roleFunction")
public class RoleFunctionController extends BaseController {

	private Logger logger = Logger.getLogger(RoleFunctionController.class);
	private IRoleFunctionService roleFunctionService;

	public IRoleFunctionService getRoleFunctionService() {
		return roleFunctionService;
	}

	@Autowired
	public void setRoleFunctionService(IRoleFunctionService roleFunctionService) {
		this.roleFunctionService = roleFunctionService;
	}

	/**
	 * 新增
	 * 
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(RoleFunction roleFunction, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = roleFunctionService.verifyInfo(roleFunction);
			if (j.isSuccess()) {
				roleFunctionService.add(roleFunction);
			}
			j.setMsg("授权成功");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("授权失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, roleFunction, request, session);

		return j;
	}

}
