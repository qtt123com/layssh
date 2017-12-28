package com.gt.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gt.pageModel.Json;
import com.gt.pageModel.SessionInfo;
import com.gt.utils.Contans;

/**
 * 
 * @功能说明：动态创建按钮
 * @作者： herun
 * @创建日期：2015-09-30
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/buttonCreate")
public class ButtonCreateController extends BaseController {

	private Logger logger = Logger.getLogger(ButtonCreateController.class);

	/**
	 * 验证权限
	 * 
	 * @param request
	 * @param requestPath
	 * @param session
	 * @return
	 */
	@RequestMapping("/checkAuth")
	@ResponseBody
	public Json checkAuth(HttpServletRequest request, String requestPath, HttpSession session) {
		Json j = new Json();
		String[] auths = requestPath.split(",");

		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Contans.SESSION_BEAN);
			boolean v = false;
			for (int i = 0; i < auths.length; i++) {
				// 是否需要权限验证
				if (sysFunctionInfService.isExistUrl(auths[i])) {
					v = sysFunctionInfService.checkAuth(sessionInfo.getOperInf().getRoleInfs(), auths[i]);// 有权限
					auths[i] = (v == true ? "1" : "0");

				} else {
					auths[i] = ("1");// 不需要验则默认拥有权限
				}
			}

			j.setMsg("有权限");
			j.setSuccess(true);
			j.setObj(auths);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("权限查询失败:" + e.getMessage());
		}

		return j;
	}
}
