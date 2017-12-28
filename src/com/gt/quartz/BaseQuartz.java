package com.gt.quartz;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.hibernate.Transaction;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gt.model.Quarz;
import com.gt.sys.dao.IBaseThreadDao;
import com.gt.utils.QuarzUtils;

/**
 * 基础Quartz
 * 
 * @功能说明：
 * @作者： herun
 * @创建日期：2015-11-19
 * @版本号：V1.0
 */
public class BaseQuartz {

	/**
	 * 更改调度状态,且停止任务
	 */
	public void updateQuartzState(Quarz quarz, ServletContext servletContext) {
		ApplicationContext aContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IBaseThreadDao<Quarz> QuarzDao = (IBaseThreadDao<Quarz>) aContext.getBean("baseDaoThread");
		Transaction transaction = QuarzDao.openSession().beginTransaction();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nid", quarz.getNid());
		QuarzDao.executeHql("update Quarz t set t.state='2' where t. nid=:nid", params);
		transaction.commit();
		QuarzDao.closeSession();

		try {
			QuarzUtils.pauseJob(quarz.getJobName(), quarz.getJobGroup());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
