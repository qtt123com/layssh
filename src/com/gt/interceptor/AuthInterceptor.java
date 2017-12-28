package com.gt.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.DictCd;
import com.gt.pageModel.Json;
import com.gt.pageModel.SessionInfo;
import com.gt.sys.service.IDictCdService;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.utils.Contans;
import com.gt.utils.PbUtils;
 
/**
 * 权限拦截器
 * 
 * @author herun
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	private ISysFunctionInfService sysFunctionInfService;// 系统功能
	private IDictCdService dictCdService;// 数据字典

	public IDictCdService getDictCdService() {
		return dictCdService;
	}

	@Autowired
	public void setDictCdService(IDictCdService dictCdService) {
		this.dictCdService = dictCdService;
	}

	public ISysFunctionInfService getSysFunctionInfService() {
		return sysFunctionInfService;
	}

	@Autowired
	public void setSysFunctionInfService(ISysFunctionInfService sysFunctionInfService) {
		this.sysFunctionInfService = sysFunctionInfService;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Contans.SESSION_BEAN);
		String requestPath = PbUtils.getRequestPath(request);// 用户访问的资源地址
		logger.info("进入权限拦截器->访问的资源为：[" + requestPath + "]");
		if (sessionInfo != null && sessionInfo.getOperInf().getOperCd().equals("admin")) {// 超级管理员不需要验证权限
			return true;
		} else {
			// 只能超级管理操作
			if (sessionInfo != null && !(sessionInfo.getOperInf().getOperCd().equals(Contans.ADMIN)) && isAdmin(requestPath)) {
				String msg = "对不起,该模块只能超级管理员["+Contans.ADMIN+"]操作";
				// 如果是页面加载数据时需要屏蔽掉如下代码，否则权限提示不了。
				// 表单提交和ajax提交有区别，此方式主要是让表单提交时获取无权限的提示ajax提交后台页面无法跳转
				if (!requestPath.contains("datagrid.do")) {
					Json j = new Json();
					j.setSuccess(false);
					j.setMsg(msg);
					PbUtils.writeJson(j, response);
					return false;
				}else {
					dataGridJson(response, msg);
					return false;
				}

			}

			// 判断此URL是否需要权限验证
			if (sysFunctionInfService.isExistUrl(requestPath)) {// 需要权限验证

				// 判断是否有权限
				if (sysFunctionInfService.checkAuth(sessionInfo.getOperInf().getRoleInfs(), requestPath)) {// 有权限
					return true;
				} else {// 没有权限
					String functionNm = sysFunctionInfService.getFunctionNm(requestPath);
					String msg = "您没有[" + functionNm + "]的权限，请联系管理员给您赋予相应权限";

					// 如果是页面加载数据时需要屏蔽掉如下代码，否则权限提示不了。
					// 表单提交和ajax提交有区别，此方式主要是让表单提交时获取无权限的提示ajax提交后台页面无法跳转
					if (!requestPath.contains("datagrid.do")) {
						Json j = new Json();
						j.setSuccess(false);
						j.setMsg(msg);
						PbUtils.writeJson(j, response);
						return false;
					}else {
						dataGridJson(response,msg);
						return false;
					}
				}
			} else {// 不需要权限验证
				return true;
			}
		}
	}


	/**
	 * 只能超级管理员才能操作的模块
	 * 
	 * @return
	 */
	private boolean isAdmin(String requestPath) {
		boolean v = false;
		DictCd dictCd = new DictCd();
		dictCd.setDictTypeCd(Contans.ADMINOPER);
		List<DictCd> list = dictCdService.getList(dictCd);
		for (DictCd object : list) {
			if (object.getDictCd().equals(requestPath) && object.getDictNm().equals("true")) {
				v = true;
				break;
			}
		}

		return v;
	}

	/**
	 * 无权限操作
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
