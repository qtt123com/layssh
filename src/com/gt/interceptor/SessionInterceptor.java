package com.gt.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.SessionInfo;
import com.gt.sys.service.INoInterceptorService;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;

/**
 * @功能说明：session拦截器
 * @作者： herun
 * @创建日期：2014-9-25
 * @版本号：V1.0
 */
public class SessionInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);
	@Resource(name = "noInterceptorService")
	private INoInterceptorService noInterceptorService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Contans.SESSION_BEAN);
		String requestPath = PbUtils.getRequestPath(request);// 用户访问的资源地址
		logger.info("进入session拦截器->访问路径为[" + requestPath + "]");
		if (requestPath.equals("operInf/execute.do")) {
			String operCd = request.getParameter("operCd");
			if (operCd != null && operCd.length() != 0) {
				return true;
			} else {
				//if (sessionInfo != null) {
				//	request.getRequestDispatcher("/main.jsp").forward(request, response);
				//	return false;
				//} else {
					request.getRequestDispatcher("/Login.jsp").forward(request, response);
					return false;
				//}
			}
		}
		// 无需拦截的URL
		boolean v = noInterceptorService.isNeedInterceptor(requestPath);
		if (!v) {
			return true;
		}

		if (sessionInfo == null) {
			String errMsg = "您已经登录超时，请重新登录！";
			logger.info(errMsg);
			if (!requestPath.contains("datagrid.do")) {
				Json j = new Json();
				j.setSuccess(false);
				j.setMsg(errMsg);
				PbUtils.writeJson(j, response);
			}else{
				dataGridJson(response, errMsg);
			}
			return false;
			
		}
		return true;
	}

	/**
	 * 登录超时提示
	 * @param response
	 * @param msg
	 */
	public void dataGridJson(HttpServletResponse response,String msg){
		DatagridForLayUI grid = new DatagridForLayUI();
		grid.setCount(null);
		grid.setData(null);
		grid.setMsg(msg);
		PbUtils.writeJson(grid, response);
	}
}
