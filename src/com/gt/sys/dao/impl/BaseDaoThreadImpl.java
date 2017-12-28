package com.gt.sys.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gt.sys.dao.IBaseThreadDao;

/**
 * 基于多线程的baseDao基础类，其他dao继承此类,必须手动关闭session,手动提交事物
 * 
 * @功能说明：
 * @作者： herun
 * @创建日期：2015-11-16
 * @版本号：V1.0
 */
@Repository("baseDaoThread")
public class BaseDaoThreadImpl<T> implements IBaseThreadDao<T> {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 多线程session
	 * 
	 * @return
	 * @throws HibernateException
	 */
	@Override
	public Session openSession() throws HibernateException {
		Session s = session.get();
		if (s == null) {
			System.out.println("------------------------创建openSession------------------------");
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	@Override
	public void closeSession() throws HibernateException {
		System.out.println("------------------------关闭closeSession------------------------");
		Session s = session.get();
		if (s != null) {
			s.close();
		}
		session.set(null);
	}
	
	@Override
	public Serializable save(T o) {
		if (o != null) {
			return openSession().save(o);
		}
		return null;
	}

	@Override
	public T getById(Class<T> c, Serializable id) {
		return (T) openSession().get(c, id);
	}

	@Override
	public T getByHql(String hql) {
		Query q = openSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			openSession().delete(o);
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			openSession().update(o);
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		if (o != null) {
			openSession().saveOrUpdate(o);
		}
	}

	@Override
	public List<T> find(String hql) {
		Query q = openSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = openSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql) {
		Query q = openSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = openSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List<Map> findBySql(String sql) {
		SQLQuery q = openSession().createSQLQuery(sql);
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, int page, int rows) {
		SQLQuery q = openSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = openSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = openSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public int executeSql(String sql) {
		SQLQuery q = openSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = openSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public Long countBySql(String sql) {
		SQLQuery q = openSession().createSQLQuery(sql);
		Object o = q.uniqueResult();
		return Long.parseLong(o.toString());
	}

	@Override
	public Long countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = openSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		Object o = q.uniqueResult();
		return Long.parseLong(o.toString());
	}

	@Override
	public List findByHqL(String hql) {
		Query q = openSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List findByHqL(String hql, Map<String, Object> params) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List findByHqL(String hql, int page, int rows) {
		Query q = openSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List findByHqL(String hql, Map<String, Object> params, int page, int rows) {
		Query q = openSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
}
