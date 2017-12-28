package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gt.model.TSysNointerceptor;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.NoInterceptor;
import com.gt.sys.service.INoInterceptorService;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：不需要拦截的URL配置业务接口实现类
 * @作者： herun
 * @创建日期：2015-09-30
 * @版本号：V1.0
 */
@Service("noInterceptorService")
public class NoInterceptorServiceImpl extends BaseServiceImpl<TSysNointerceptor> implements INoInterceptorService {

	@Override
	public DatagridForLayUI datagrid(NoInterceptor noInterceptor) {

		DatagridForLayUI grid = new DatagridForLayUI();
		String hql = "from TSysNointerceptor t where 1=1";

		Map<String, Object> param = new HashMap<String, Object>();

		// 不需要拦截的URL
		if (!PbUtils.isEmpty(noInterceptor.getFunctionUrl())) {
			hql += " and t.functionUrl like:functionUrl";
			param.put("functionUrl", "%%" + noInterceptor.getFunctionUrl() + "%%");
		}

		// 备注
		if (!PbUtils.isEmpty(noInterceptor.getNote())) {
			hql += " and t.note like:note";
			param.put("note", "%%" + noInterceptor.getNote() + "%%");
		}

		String totalHql = "select count(*) " + hql;

		if (!PbUtils.isEmpty(noInterceptor.getSort())) {
			hql += " order by " + noInterceptor.getSort() + "  " + noInterceptor.getOrder();
		}

		List<TSysNointerceptor> tl = find(hql, param, noInterceptor.getPage(), noInterceptor.getLimit());
		List<NoInterceptor> l = new ArrayList<NoInterceptor>();

		if (tl != null && tl.size() > 0) {
			for (TSysNointerceptor t : tl) {
				NoInterceptor inf = new NoInterceptor();
				BeanUtils.copyProperties(t, inf);

				l.add(inf);
			}
		}

		Long total = count(totalHql, param);
		grid.setCount(total);
		grid.setData(l);

		return grid;
	}

	// 清空缓存
	@CacheEvict(value = { "NoInterceptorServiceImpl_isNeedInterceptor" }, allEntries = true)
	@Override
	public Json add(NoInterceptor inf) {
		Json j = new Json();
		TSysNointerceptor tInf = new TSysNointerceptor();
		BeanUtils.copyProperties(inf, tInf);

		save(tInf);

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		j.setSuccess(true);
		j.setMsg("新增成功");
		j.setObj(inf);
		return j;

	}

	// 清空缓存
	@CacheEvict(value = { "NoInterceptorServiceImpl_isNeedInterceptor" }, allEntries = true)
	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete from TSysNointerceptor t where t.uuid in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		executeHql(hql);

	}

	// 清空缓存
	@CacheEvict(value = { "NoInterceptorServiceImpl_isNeedInterceptor" }, allEntries = true)
	@Override
	public Json modify(NoInterceptor inf) {
		Json j = new Json();

		TSysNointerceptor tInf = getById(TSysNointerceptor.class, inf.getUuid());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		NoInterceptor oldObject = new NoInterceptor();
		BeanUtils.copyProperties(tInf, oldObject);
		j.setOldObj(oldObject);

		BeanUtils.copyProperties(inf, tInf);

		update(tInf);// 更新

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(inf);
		return j;
	}

	@Override
	public Json verifyInfo(NoInterceptor inf) {
		Json j = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getUuid())) {
			params.put("uuid", inf.getUuid().trim());
		}
		Long total = super.count("Select count(*) from TSysNointerceptor t where t.uuid =:uuid ", params);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("此信息已经存在");
			return j;
		}

		j.setSuccess(true);
		j.setMsg("成功！");
		j.setObj(inf);
		return j;
	}

	@Override
	public List<NoInterceptor> getList() {
		List<NoInterceptor> l = new ArrayList<NoInterceptor>();
		List<TSysNointerceptor> tl = find("from TSysNointerceptor t");
		if (tl != null && tl.size() > 0) {
			for (TSysNointerceptor t : tl) {
				NoInterceptor inf = new NoInterceptor();
				BeanUtils.copyProperties(t, inf);
				l.add(inf);
			}
		}
		return l;
	}

	@Cacheable(value = "NoInterceptorServiceImpl_isNeedInterceptor", key = "#url")
	@Override
	public boolean isNeedInterceptor(String url) {
		//System.out.println("-------isNeedInterceptor-------");
		String hql = "select count(*) from TSysNointerceptor t where t.functionUrl=:functionUrl";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("functionUrl", url);
		Long count = count(hql, params);
		if (count > 0) {
			return false;
		}
		return true;
	}
}
