package com.gt.cache.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gt.cache.ICacheProcess;
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.SysFunctionInf;
import com.gt.sys.service.INoInterceptorService;
import com.gt.sys.service.IRoleInfService;
import com.gt.sys.service.ISysFunctionInfService;

/**
 * @功能说明：系统权限缓存预热
 * @作者： herun
 * @创建日期：2015-10-19
 * @版本号：V1.0
 */
@Service("sysCacheProcess")
public class SysCacheProcessImpl implements ICacheProcess {

	public static boolean v = true;
	private Logger logger = Logger.getLogger(SysCacheProcessImpl.class);

	// 系统功能
	@Resource(name = "sysFunctionInfService")
	private ISysFunctionInfService sysFunctionInfService;

	// 角色信息
	@Resource(name = "roleInfService")
	private IRoleInfService roleInfService;

	// 无需拦截的URL
	@Resource(name = "noInterceptorService")
	private INoInterceptorService noInterceptorService;

	@Override
	public void starCache() {
		if (v) {
			logger.info("系统权限缓存预热...");
			try {
				List<RoleInf> list = roleInfService.getAllList();// 查询所有角色信息
				List<SysFunctionInf> sysFunctionInfs = sysFunctionInfService.getList();// 查询所有系统功能
				for (RoleInf roleInf : list) {
					for (SysFunctionInf sysFunctionInf : sysFunctionInfs) {
						sysFunctionInfService.countSysByRoleAndRequest(sysFunctionInf.getFunctionUrl(), roleInf.getRoleCd());
					}
				}
				for (SysFunctionInf sysFunctionInf : sysFunctionInfs) {
					sysFunctionInfService.isExistUrl(sysFunctionInf.getFunctionUrl());
				}
				for (SysFunctionInf sysFunctionInf : sysFunctionInfs) {
					noInterceptorService.isNeedInterceptor(sysFunctionInf.getFunctionUrl());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			v = false;
		}

	}
}
