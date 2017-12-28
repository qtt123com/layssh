package com.gt.cache.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gt.cache.ICacheProcess;
import com.gt.pageModel.OperInf;
import com.gt.sys.service.IMenuInfService;
import com.gt.sys.service.IOperInfService;

/**
 * @功能说明：菜单数据缓存预热
 * @作者： herun
 * @创建日期：2015-10-19
 * @版本号：V1.0
 */
@Service("menutCacheProcess")
public class MenutCacheProcessImpl implements ICacheProcess {

	public static boolean v = true;
	private Logger logger = Logger.getLogger(MenutCacheProcessImpl.class);

	@Resource(name = "menuInfService")
	private IMenuInfService menuInfService;

	@Resource(name = "operInfService")
	private IOperInfService operInfService;

	@Override
	public void starCache() {
		if (v) {
			logger.info("菜单缓存预热中...");
			List<OperInf> list = operInfService.getList();
			for (OperInf operInf : list) {
				try {
					menuInfService.getTreeByRole(operInf.getOperCd());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (OperInf operInf : list) {
				try {
					menuInfService.getTreeByRoleByNoSys(operInf.getOperCd());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			v = false;
		}

	}
}
