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
import com.gt.pageModel.Json;
import com.gt.pageModel.MenuForLayUI;
import com.gt.pageModel.MenuInf;
import com.gt.pageModel.SessionInfo;
import com.gt.pageModel.TreeForLayUI;
import com.gt.pageModel.ZTree;
import com.gt.sys.service.IMenuInfService;
import com.gt.sys.service.IRoleInfService;
import com.gt.utils.Contans;

/**
 * @功能说明：菜单功能
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/menuInf")
public class MenuInfController extends BaseController {

	private Logger logger = Logger.getLogger(MenuInfController.class);

	private IMenuInfService menuInfService;// 菜单功能

	private IRoleInfService roleInfService;// 系统角色

	public IRoleInfService getRoleInfService() {
		return roleInfService;
	}

	@Autowired
	public void setRoleInfService(IRoleInfService roleInfService) {
		this.roleInfService = roleInfService;
	}

	public IMenuInfService getMenuInfService() {
		return menuInfService;
	}

	@Autowired
	public void setMenuInfService(IMenuInfService menuInfService) {
		this.menuInfService = menuInfService;
	}

	/**
	 * 获取异步树
	 */
	@RequestMapping("/getTree")
	@ResponseBody
	public List<MenuInf> getTree(MenuInf menuInf) {
		String id = menuInf.getId();
		List<MenuInf> list = menuInfService.getTree(id);
		return list;
	}

	/**
	 * 获取同步树
	 */
	@RequestMapping("/getAllTree")
	@ResponseBody
	public List<MenuInf> getAllTree() {
		List<MenuInf> list = menuInfService.getAllTree();
		return list;
	}

	/**
	 * 获取树
	 */
	@RequestMapping("/getZTree")
	@ResponseBody
	public List<ZTree> getZTree() {
		List<ZTree> list = menuInfService.getZTree();
		return list;
	}
	
	
	/**
	 * 获取layUI树
	 */
	@RequestMapping("/getLayUItree")
	@ResponseBody
	public List<TreeForLayUI> getLayUItree() {
		List<TreeForLayUI> list = null;
		try {
			list = menuInfService.getLayUItree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取layUI树
	 */
	@RequestMapping("/getAllLayUItree")
	@ResponseBody
	public List<TreeForLayUI> getAllLayUItree() {
		List<TreeForLayUI> list = null;
		try {
			list = menuInfService.getAllLayUItree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	/**
	 * 根据角色权限获取菜单
	 */
	@RequestMapping("/getTreeByRole")
	@ResponseBody
	public List<MenuForLayUI> getTreeByRole(HttpSession session) {
		List<MenuForLayUI> list = new ArrayList<MenuForLayUI>();
		if (session.getAttribute(Contans.SESSION_BEAN) != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			if (sessionInfo.getOperInf() != null) {
				if (sessionInfo.getOperInf().getOperCd() != null) {
					try {
						list = menuInfService.getTreeByRole(sessionInfo.getOperInf().getOperCd());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		}
		return list;
	}
	
	/**
	 * 根据角色权限获取菜单
	 */
	@RequestMapping("/getTreeByRoleByNoSys")
	@ResponseBody
	public List<MenuInf> getTreeByRoleByNoSys(HttpSession session) {
		List<MenuInf> list = new ArrayList<MenuInf>();
		if (session.getAttribute(Contans.SESSION_BEAN) != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			if (sessionInfo.getOperInf() != null) {
				if (sessionInfo.getOperInf().getOperCd() != null) {
					try {
						list = menuInfService.getTreeByRoleByNoSys(sessionInfo.getOperInf().getOperCd());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		}
		return list;
	}

	/**
	 * 获取datagrid数据
	 * @throws InterruptedException 
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(MenuInf menuInf) throws InterruptedException {
		DatagridForLayUI datagrid = menuInfService.datagrid(menuInf);
		return datagrid;

	}

//	/**
//	 * 获取treegrid数据
//	 */
//	@RequestMapping("/datagrid")
//	@ResponseBody
//	public List<MenuInf> datagrid(MenuInf menuInf) {
//		List<MenuInf> list = menuInfService.treegrid();
//		return list;
//
//	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MenuInf menuInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			boolean v = menuInfService.vaild(menuInf);
			if (v) {
				MenuInf m = menuInfService.add(menuInf);
				j.setSuccess(true);
				j.setMsg("添加成功！");
				j.setObj(m);
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！菜单顺序在该级已经被使用,请重新选择菜单排序");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("添加失败:" + e.getMessage());
		}
		// 添加系统日志
		writeSysLog(j, menuInf, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(MenuInf menuInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			menuInfService.remove(menuInf.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, menuInf, request, session);

		return j;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getMenuList")
	@ResponseBody
	public List<MenuInf> getMenuList() {
		List<MenuInf> menuInfs = menuInfService.getMenuList();
		return menuInfs;
	}

	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getMenuUrlNotNullList")
	@ResponseBody
	public List<MenuInf> getMenuUrlNotNullList() {
		List<MenuInf> menuInfs = menuInfService.getMenuUrlNotNullList();
		return menuInfs;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(MenuInf menuInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			boolean v = menuInfService.vaild(menuInf);
			if (v) {
				j = menuInfService.modify(menuInf);
			} else {
				j.setSuccess(false);
				j.setMsg("修改失败！菜单顺序在该级已经被使用,请重新选择菜单排序");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, menuInf, request, session);

		return j;
	}

	/**
	 * 获取图标下拉框
	 * 
	 * @return
	 */
	@RequestMapping("/getIconsList")
	@ResponseBody
	public List<MenuInf> getIconsList(HttpServletRequest request) {
		List<MenuInf> menuInfs = null;
		try {
			menuInfs = menuInfService.getIconsList(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return menuInfs;
	}
}
