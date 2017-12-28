package com.gt.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.model.TSysOperInf;
import com.gt.model.TSysRoleOper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.OperInf;
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.SessionInfo;
import com.gt.servlet.RandomValidateCode;
import com.gt.sys.service.IOperInfService;
import com.gt.sys.service.IRoleOperService;
import com.gt.utils.Contans;
import com.gt.utils.MD5;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：系统用户
 * @作者： herun
 * @创建日期：2015-09-24
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/operInf")
public class OperInfController extends BaseController {

	private Logger logger = Logger.getLogger(OperInfController.class);

	private IOperInfService operInfService;// 人员信息
	private IRoleOperService roleOperService;// 角色人员信息

	public IRoleOperService getRoleOperService() {
		return roleOperService;
	}

	@Autowired
	public void setRoleOperService(IRoleOperService roleOperService) {
		this.roleOperService = roleOperService;
	}

	public IOperInfService getOperInfService() {
		return operInfService;
	}

	@Autowired
	public void setOperInfService(IOperInfService operInfService) {
		this.operInfService = operInfService;
	}

	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(OperInf operInf, HttpSession session) {
		DatagridForLayUI datagrid = null;
		try {
			// 系统登录用户
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			String insCd = sessionInfo.getOperInf().getInsUuid();
			datagrid = operInfService.datagrid(operInf, insCd);
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
	public Json add(OperInf operInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			operInf.setRecCrtTs(PbUtils.getCurrentTime());// 记录创建时间
			MD5 md5 = new MD5();
			operInf.setOperPwd(md5.getMD5Str(operInf.getOperCd() + Contans.DEFAULT_PASSWORD));// 系统默认密码,
																								// 用户名+密码
			j = operInfService.verifyInfo(operInf);
			if (j.isSuccess()) {
				OperInf inf = operInfService.add(operInf);
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
		writeSysLog(j, operInf, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(OperInf operInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			operInfService.remove(operInf.getOperCd());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, operInf, request, session);

		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(OperInf operInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			operInf.setRecUpdTs(PbUtils.getCurrentTime());// 记录修改时间
			j = operInfService.modify(operInf);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, operInf, request, session);

		return j;
	}

	/**
	 * 密码重置
	 */
	@RequestMapping("/redoPwd")
	@ResponseBody
	public Json redoPwd(OperInf operInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = operInfService.redoPwd(operInf.getOperCd());
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("密码重置失败:" + e.getMessage());
		}
		// 添加系统日志
		writeSysLog(j, operInf, request, session);

		return j;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	@RequestMapping("/execute")
	@ResponseBody
	public Json execute(OperInf operInf, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Json j = new Json();
		String codeSession = null;

		// 获取验证码
		if (session.getAttribute(RandomValidateCode.RANDOMCODEKEY) != null) {
			codeSession = session.getAttribute(RandomValidateCode.RANDOMCODEKEY).toString();
		}

		String code = operInf.getCode() == null ? "" : operInf.getCode();
		codeSession = codeSession == null ? "" : codeSession;

		// 判断验证码是否正确
		if (code.equals(codeSession)) {
			j = operInfService.userLogin(operInf);
			if (j.isSuccess()) {// ////////去掉
				try {
					TSysOperInf tInf = operInfService.getById(TSysOperInf.class, operInf.getOperCd());
					if (tInf != null) {
						OperInf operInfObj = new OperInf();
						BeanUtils.copyProperties(tInf, operInfObj);

						// 设置用户所属机构
						if (tInf.getTSysInsInf() != null) {
							if (!PbUtils.isEmpty(tInf.getTSysInsInf().getUuid())) {
								operInfObj.setInsUuid(tInf.getTSysInsInf().getUuid());// 设置用户所属机构UUID
							}
							if (!PbUtils.isEmpty(tInf.getTSysInsInf().getInsCd())) {
								operInfObj.setInsCd(tInf.getTSysInsInf().getInsCd());// 设置用户所属机构编号
							}
							if (!PbUtils.isEmpty(tInf.getTSysInsInf().getInsNm())) {
								operInfObj.setInsCdNm(tInf.getTSysInsInf().getInsNm());// 设置用户所属机构名称
							}
						}

						// 添加角色信息
						List<RoleInf> listRoleInf = new ArrayList<RoleInf>();// 角色信息
						List<TSysRoleOper> roleOpers = roleOperService.getList(operInfObj.getOperCd());

						for (TSysRoleOper tSysRoleOper : roleOpers) {
							RoleInf roleInf = new RoleInf();
							roleInf.setRoleCd(tSysRoleOper.getTSysRoleInf().getRoleCd());
							roleInf.setRoleNm(tSysRoleOper.getTSysRoleInf().getRoleNm());
							//roleInf.setJumpUrl(tSysRoleOper.getTSysRoleInf().getJumpUrl());
							//roleInf.setUrlNm(tSysRoleOper.getTSysRoleInf().getUrlNm());
							listRoleInf.add(roleInf);
						}
						operInfObj.setRoleInfs(listRoleInf);

						// 添加用户信息到session
						SessionInfo sessionInfo = new SessionInfo();
						sessionInfo.setOperInf(operInfObj);

						MD5 md5 = new MD5();
						String password = md5.getMD5Str(sessionInfo.getOperInf().getOperCd() + Contans.DEFAULT_PASSWORD);
						if (password.equals(operInfObj.getOperPwd())) {

							sessionInfo.setDefaultPwd(true);// 用户使用系统默认密码，登录时建议修改
						}

						// 设置session
						session.setAttribute(Contans.SESSION_BEAN, sessionInfo);
						//j.setOldObj(getListRoleInfs(session));
					} else {
						session.setAttribute(Contans.SESSION_BEAN, new SessionInfo());
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} else {
			j.setSuccess(false);
			j.setMsg("验证码不正确");
		}

		// 新增系统日志
		if (j.isSuccess()) {
			j.setMsg("登录成功");
			writeSysLog(j, operInf, "用户登录", "系统登录", request, session);
		}

		return j;
	}

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "Login";
	}

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/goPage")
	public String goPage(String url) {
		return url;
	}

	/**
	 * 密码修改
	 */
	@RequestMapping("/changepwd")
	@ResponseBody
	public Json changepwd(OperInf operInf, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		OperInf oper = getUser(request);
		MD5 md5 = new MD5();
		String oldPwd = "";// 原始密码

		if (oper != null) {
			if (!PbUtils.isEmpty(oper.getOperCd()) && !PbUtils.isEmpty(operInf.getOperPwd())) {
				oldPwd = md5.getMD5Str(oper.getOperCd() + operInf.getOldOperPwd());
			}

			if (!oper.getOperPwd().equals(oldPwd)) {
				j.setSuccess(false);
				j.setMsg("原密码不正确!");
			} else {
				operInf.setOperCd(oper.getOperCd());
				j = operInfService.changepwd(operInf);
			}
		} else {
			j.setSuccess(false);
			j.setMsg("密码修改失败：无法获取登录人信息");
		}

		// 添加系统日志
		writeSysLog(j, operInf, request, session);
		return j;
	}

	/**
	 * 用户注销
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		if (session != null) {
			session.invalidate();
		}
		return "Login";
	}

	/**
	 * 用户注销
	 * 
	 * @return
	 */
	@RequestMapping("/getRoles")
	@ResponseBody
	public List<RoleInf> getRoles(HttpSession session) {
		List<RoleInf> roleInfs = getListRoleInfs(session);
		return roleInfs;
	}

	private List<RoleInf> getListRoleInfs(HttpSession session) {
		Map<String, RoleInf> maps = new HashMap<String, RoleInf>();
		List<RoleInf> roleInfs = new ArrayList<RoleInf>();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		if (session.getAttribute(Contans.SESSION_BEAN) != null) {
			if (sessionInfo != null && sessionInfo.getOperInf() != null && sessionInfo.getOperInf().getRoleInfs() != null) {
				List<RoleInf> list = sessionInfo.getOperInf().getRoleInfs();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getUrlNm() != null && list.get(i).getUrlNm().length()>0) {
						maps.put(list.get(i).getUrlNm(), list.get(i));
					}
				}
			}

			if (maps != null && !maps.isEmpty()) {
				for (String key : maps.keySet()) {
					RoleInf roleInf = new RoleInf();
					roleInf = maps.get(key);
					roleInfs.add(roleInf);
				}
			}
		}

		return roleInfs;
	}
	
}
