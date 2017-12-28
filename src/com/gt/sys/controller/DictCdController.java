package com.gt.sys.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.DictCd;
import com.gt.pageModel.Json;
import com.gt.sys.service.IDictCdService;

/**
 * 
 * @功能说明：数据字典
 * @作者： herun
 * @创建日期：2015-09-25
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/dictCd")
public class DictCdController extends BaseController {

	private Logger logger = Logger.getLogger(DictCdController.class);
	private IDictCdService dictCdService;

	public IDictCdService getDictCdService() {
		return dictCdService;
	}

	@Autowired
	public void setDictCdService(IDictCdService dictCdService) {
		this.dictCdService = dictCdService;
	}

	/**
	 * 获取datagrid数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DatagridForLayUI datagrid(DictCd dictCd) {
		DatagridForLayUI datagrid = dictCdService.datagrid(dictCd);
		return datagrid;
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DictCd dictCd, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			dictCd.setUuid(UUID.randomUUID().toString());// 设置UUID
			DictCd inf = dictCdService.add(dictCd);
			j.setMsg("新增成功");
			j.setObj(inf);
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("添加失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, dictCd, request, session);

		return j;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Json remove(DictCd dictCd, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			dictCdService.remove(dictCd.getUuid());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败:" + e.getMessage());
		}

		// 添加系统日志
		writeSysLog(j, dictCd, request, session);

		return j;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Json modify(DictCd dictCd, HttpServletRequest request, HttpSession session) {
		Json j = new Json();
		try {
			j = dictCdService.modify(dictCd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改失败:" + e.getMessage());
		}
		// 添加系统日志
		writeSysLog(j, dictCd, request, session);

		return j;
	}

	/**
	 * 根据字典类型返回数据
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public List<DictCd> getList(DictCd dictCd) {
		List<DictCd> list = dictCdService.getList(dictCd);
		return list;
	}

	/**
	 * 根据字典类型和字典编号查询
	 */
	@RequestMapping("/charFormat")
	@ResponseBody
	public Json charFormat(DictCd dictCd) {
		Json j = new Json();
		try {
			List<DictCd> list = dictCdService.charFormat(dictCd);
			DictCd oCd = list.get(0);
			j.setSuccess(true);
			j.setObj(list);
			if (oCd.getDictNm() != null && oCd.getDictNm().length() > 0) {
				j.setMsg(oCd.getDictNm());
			} else {
				j.setMsg("");
			}

		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("失败");
			logger.error(e.getStackTrace());
		}
		return j;
	}
}
