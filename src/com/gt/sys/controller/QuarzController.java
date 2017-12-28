package com.gt.sys.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.model.Quarz;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IQuarzService;
import com.gt.utils.PbUtils;

/**
 * @功能说明：调度任务
 * @作者：herun
 * @创建日期：2015-11-18
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/quarz")
public class QuarzController extends BaseController {
	private Logger logger = Logger.getLogger(QuarzController.class);
	private IQuarzService quarzService;

	public IQuarzService getQuarzService() {
		return quarzService;
	}

	@Autowired
	public void setQuarzService(IQuarzService quarzService) {
		this.quarzService = quarzService;
	}

	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(Quarz quarz) {
		DatagridForLayUI datagrid = null;
		try {
			datagrid = quarzService.datagrid(quarz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return datagrid;
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Quarz quarz, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		//ServletContext context = request.getServletContext();
		ServletContext context=request.getSession().getServletContext();
		try {
			quarz.setNid(PbUtils.getUUID());
			j = quarzService.verifyInfo(quarz);
			if (j.isSuccess()) {
				quarz.setState("0");// 状态
				j = quarzService.add(quarz, context);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
			j.setMsg("添加失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, quarz, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(Quarz quarz, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			quarzService.remove(quarz.getNidStr());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, quarz, request, session);

		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(Quarz quarz, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		ServletContext context = request.getServletContext();
		try {
			j = quarzService.modify(quarz, context);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 添加系统日志
		writeSysLog(j, quarz, request, session);
		return j;
	}
	
	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<Quarz> getList() {
		List<Quarz> list = quarzService.getList();
		return list;
	}

}
