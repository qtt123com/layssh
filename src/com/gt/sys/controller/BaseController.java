package com.gt.sys.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gt.pageModel.Json;
import com.gt.pageModel.OperInf;
import com.gt.pageModel.SessionInfo;
import com.gt.pageModel.SysFunctionInf;
import com.gt.pageModel.SysLog;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.sys.service.ISysLogService;
import com.gt.utils.Contans;
import com.gt.utils.DataConverter;
import com.gt.utils.GetIpUtil;
import com.gt.utils.ParseProperties;
import com.gt.utils.PbUtils;

/**
 * @功能说明：
 * @作者： herun
 * @创建日期：2015-09-25
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	private Logger logger = Logger.getLogger(BaseController.class);

	public ISysLogService sysLogService;// 系统日志

	public ISysFunctionInfService sysFunctionInfService;// 系统功能

	public ISysLogService getSysLogService() {
		return sysLogService;
	}

	@Autowired
	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	public ISysFunctionInfService getSysFunctionInfService() {
		return sysFunctionInfService;
	}

	@Autowired
	public void setSysFunctionInfService(ISysFunctionInfService sysFunctionInfService) {
		this.sysFunctionInfService = sysFunctionInfService;
	}

	/**
	 * 获取当前系统用户
	 * 
	 * @return
	 */
	protected OperInf getUser(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Contans.SESSION_BEAN);
		if (sessionInfo == null) {
			return null;
		}

		return sessionInfo.getOperInf();
	}

	/**
	 * 系统日志
	 * 
	 * @param j
	 * @param obj
	 * @param request
	 * @param session
	 */
	public void writeSysLog(Json j, Object obj, HttpServletRequest request, HttpSession session) {
		try {
			Json json = j;
			if (json == null) {
				json = new Json();
				json.setMsg("");
			}
			// 系统登录用户
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			// 系统访问地址
			String requestPath = PbUtils.getRequestPath(request);// 用户访问的资源地址
			// 登入人IP
			String operIp = GetIpUtil.getIpAddr(request);//request.getRemoteAddr();

			SysLog sysLog = new SysLog();
			sysLog.setOperIp(operIp);// 登录人IP
			sysLog.setUuid(UUID.randomUUID().toString());// uuid
			sysLog.setOperDt(PbUtils.getCurrentTime());// 操作日期

			// 用户编号
			if (!PbUtils.isEmpty((sessionInfo.getOperInf().getOperCd()))) {
				sysLog.setOperUsrId(sessionInfo.getOperInf().getOperCd());
			}

			// 根据URL获取操作对象
			SysFunctionInf functionInf = sysFunctionInfService.getFunction(requestPath);
			if (functionInf != null) {
				// 操作方法
				if (!PbUtils.isEmpty(functionInf.getFunctionNm())) {
					sysLog.setOperMethod(functionInf.getFunctionNm());
				}

				// 所属模块
				if (!PbUtils.isEmpty(functionInf.getMenuNm())) {
					sysLog.setOperModule(functionInf.getMenuNm());
				}
			}

			// 操作结果
			if (!PbUtils.isEmpty(json.getMsg())) {
				sysLog.setResultMsg(json.getMsg());
			}

			// 操作内容
			sysLog.setCompareInf(PbUtils.getObjReflect(obj));

			// 旧对象
			if (j.getOldObj() != null) {
				String compareInf = sysLog.getCompareInf() + "-----原先数据如下：-->";
				compareInf += PbUtils.getObjReflect(j.getOldObj());
				sysLog.setCompareInf(compareInf);
			}
			// 获取所有主键
			String[] pks = sysLogService.getAllTablePK();

			// 操作关键字
			sysLog.setOperDesc(PbUtils.getObjReflectKeword(obj, pks));

			// 新增日志
			sysLogService.add(sysLog);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}
	}

	/**
	 * 系统日志
	 * 
	 * @param j
	 *            json对象
	 * @param obj
	 *            javabean对象
	 * @param method
	 *            方法名称
	 * @param module
	 *            所属模块
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void writeSysLog(Json j, Object obj, String method, String module, HttpServletRequest request, HttpSession session) {
		try {
			Json json = j;
			if (json == null) {
				json = new Json();
				json.setMsg("");
			}

			// 系统登录用户
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
			// 登入人IP
			String operIp = getIpAddr(request);

			SysLog sysLog = new SysLog();
			sysLog.setOperIp(operIp);// 登录人IP
			sysLog.setUuid(UUID.randomUUID().toString());// uuid
			sysLog.setOperDt(PbUtils.getCurrentTime());// 操作日期

			// 用户编号
			if (!PbUtils.isEmpty((sessionInfo.getOperInf().getOperCd()))) {
				sysLog.setOperUsrId(sessionInfo.getOperInf().getOperCd());
			}

			// 操作方法
			if (!PbUtils.isEmpty(method)) {
				sysLog.setOperMethod(method);
			}

			// 所属模块
			if (!PbUtils.isEmpty(module)) {
				sysLog.setOperModule(module);
			}

			// 操作结果
			if (!PbUtils.isEmpty(json.getMsg())) {
				sysLog.setResultMsg(json.getMsg());
			}

			// 操作内容
			sysLog.setCompareInf(PbUtils.getObjReflect(obj));

			// 旧对象
			if (j.getOldObj() != null) {
				String compareInf = sysLog.getCompareInf() + "-----原先数据如下：-->";
				compareInf += PbUtils.getObjReflect(j.getOldObj());
				sysLog.setCompareInf(compareInf);
			}

			// 获取所有主键
			String[] pks = sysLogService.getAllTablePK();

			// 操作关键字
			sysLog.setOperDesc(PbUtils.getObjReflectKeword(obj, pks));

			// 新增日志
			sysLogService.add(sysLog);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}

	}

	// 设置自动增长的主键
	public synchronized String getIncreId(String table) {
		String hql = "select count(*) from " + table;
		Long count = sysLogService.count(hql);
		count += 1L;// 自增长1
		String id = DataConverter.addZeroLeft(String.valueOf(count), 8);
		return id;
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!PbUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!PbUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	// 时间格式化
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}

	/**
	 * 图片保存公共方法
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	public String fileSave(MultipartFile file, HttpServletRequest request, String path) {
		String work_path = ParseProperties.getProperties().getProperty("work_path");// request.getSession().getServletContext().getRealPath("upload");//获取绝对路径
		logger.info("获取绝对路径:" + work_path);

		String db_path = "upload/" + path + "/";// 获取相对文件路径,放在工程目录下
		String fileName = file.getOriginalFilename();// 获取文件名称

		if (fileName == null || fileName.length() == 0) {
			return "";
		}

		fileName = PbUtils.getCurrenForMilli() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);// 修改文件名称,按时间保存
		logger.info(path);
		File targetFile = new File(work_path + "/" + path, fileName);
		logger.info("文件保存路径:" + work_path + "/" + path + fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			return db_path + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
