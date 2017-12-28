package com.gt.sys.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gt.sys.dao.IBaseDao;
import com.gt.sys.service.IBaseService;

/**
 * 
 * @功能说明：baseService实现类，其他serviceImpl基础此类
 * @作者： herun
 * @创建日期：2015-09-23
 * @版本号：V1.0
 */
@Service("baseService")
public class BaseServiceImpl<T> implements IBaseService<T> {

	private IBaseDao<T> baseDao;

	public IBaseDao<T> getBaseDao() {
		return baseDao;
	}

	@Autowired
	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Serializable save(T o) {
		return baseDao.save(o);
	}

	@Override
	public void delete(T o) {
		baseDao.delete(o);
	}

	@Override
	public void update(T o) {
		baseDao.update(o);
	}

	@Override
	public void saveOrUpdate(T o) {
		baseDao.saveOrUpdate(o);
	}

	@Override
	public T getById(Class<T> c, Serializable id) {
		return baseDao.getById(c, id);
	}

	@Override
	public T getByHql(String hql) {
		return baseDao.getByHql(hql);
	}

	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		return baseDao.getByHql(hql, params);
	}

	@Override
	public List<T> find(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		return baseDao.find(hql, page, rows);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		return baseDao.find(hql, params, page, rows);
	}

	@Override
	public Long count(String hql) {
		return baseDao.count(hql);
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		return baseDao.count(hql, params);
	}

	@Override
	public int executeHql(String hql) {
		return baseDao.executeHql(hql);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}

	@Override
	public List<Map> findBySql(String sql) {
		return baseDao.findBySql(sql);
	}
	
	@Override
	public List<Map> findBySql(String sql, int page, int rows) {
		return baseDao.findBySql(sql, page, rows);
	}
	
	@Override
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		return baseDao.findBySql(sql, params);
	}

	@Override
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		return baseDao.findBySql(sql, params, page, rows);
	}

	@Override
	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		return baseDao.executeSql(sql, params);
	}

	@Override
	public Long countBySql(String sql) {
		return baseDao.countBySql(sql);
	}
	
	@Override
	public Long countBySql(String sql, Map<String, Object> params) {
		return baseDao.countBySql(sql, params);
	}
	
	@Override
	public List findByHqL(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public List findByHqL(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List findByHqL(String hql, int page, int rows) {
		return baseDao.find(hql, page, rows);
	}

	@Override
	public List findByHqL(String hql, Map<String, Object> params, int page, int rows) {
		return baseDao.find(hql, params, page, rows);
	}
	
}
