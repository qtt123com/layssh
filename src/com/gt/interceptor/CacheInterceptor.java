package com.gt.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gt.cache.ICacheProcess;

/**
 * @功能说明：缓存预热
 * @作者： herun
 * @创建日期：2015-10-19
 * @版本号：V1.0
 */
public class CacheInterceptor implements HandlerInterceptor {

	@Resource(name = "dictCacheProcess")
	private ICacheProcess dictCacheProcess;// 数据字典缓存预热

	@Resource(name = "menutCacheProcess")
	private ICacheProcess menutCacheProcess;// 菜单缓存预热

	@Resource(name = "sysCacheProcess")
	private ICacheProcess sysCacheProcess;// 系统权限缓存预热

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
		//dictCacheProcess.starCache();
		//menutCacheProcess.starCache();
		//sysCacheProcess.starCache();
		return true;
	}
}
