package com.gt.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 测试Quartz
 * @author： herun
 * @date：2017-12-7
 */
public class TestQuartz extends BaseQuartz implements Job  {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("正在运行......");
		
	}

}
