package com.gt.servlet;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gt.model.Quarz;
import com.gt.sys.dao.IBaseThreadDao;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.QuarzUtils;

/**
 * @功能说明：初始化quartz
 * @作者： herun
 * @创建日期：2015-11-19
 * @版本号：V1.0
 */
public class QuartzInItServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		IBaseThreadDao<Quarz> baseThreadDao = (IBaseThreadDao<Quarz>) context.getBean("baseDaoThread");
		String sql = "select *  from T_SYS_QUARZ t where t.state='0' ";
		List<Map> maps = baseThreadDao.findBySql(sql);
		MapToBeanUtils<Quarz> beanUtils = new MapToBeanUtils<Quarz>();
		List<Quarz> list = null;
		try {
			list = beanUtils.ListMapToJavaBean(maps, Quarz.class);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (Quarz quarz : list) {
			try {
				QuarzUtils.schedStart(quarz, getServletContext());
			} catch (SchedulerException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}
}
