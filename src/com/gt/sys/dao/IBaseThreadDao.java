package com.gt.sys.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 基于多线程的BaseDao基础接口，其他dao接口继续此类,必须手动关闭session,手动提交事物
 * 
 * @功能说明：
 * @作者： herun
 * @创建日期：2015-11-16
 * @版本号：V1.0
 */
public interface IBaseThreadDao<T> extends IBaseDao<T> {

	/**
	 * 获取session
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public Session openSession() throws HibernateException;

	/**
	 * 关闭session
	 * 
	 * @throws HibernateException
	 */
	public void closeSession() throws HibernateException;


}
