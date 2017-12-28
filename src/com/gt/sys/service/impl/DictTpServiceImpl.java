package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.gt.model.TSysDictTp;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.DictTp;
import com.gt.pageModel.Json;
import com.gt.sys.service.IDictTpService;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：数据字典类型
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
@Service("dictTpService")
public class DictTpServiceImpl extends BaseServiceImpl<TSysDictTp> implements IDictTpService {
	@Override
	public DatagridForLayUI datagrid(DictTp dictTp) {

		DatagridForLayUI grid = new DatagridForLayUI();
		String hql = "from TSysDictTp t where 1=1";

		Map<String, Object> param = new HashMap<String, Object>();

		// 字典类型编号
		if (!PbUtils.isEmpty(dictTp.getDictTypeCd())) {
			hql += " and t.dictTypeCd like:dictTypeCd";
			param.put("dictTypeCd", "%%" + dictTp.getDictTypeCd() + "%%");
		}
		// 字典类型名称
		if (!PbUtils.isEmpty(dictTp.getDictTypeNm())) {
			hql += " and t.dictTypeNm like:dictTypeNm";
			param.put("dictTypeNm", "%%" + dictTp.getDictTypeNm() + "%%");
		}

		String totalHql = "select count(*) " + hql;

		if (!PbUtils.isEmpty(dictTp.getSort())) {
			hql += " order by " + dictTp.getSort() + "  " + dictTp.getOrder();
		}

		List<TSysDictTp> tl = find(hql, param, dictTp.getPage(), dictTp.getLimit());
		List<DictTp> l = new ArrayList<DictTp>();

		if (tl != null && tl.size() > 0) {
			for (TSysDictTp t : tl) {
				DictTp inf = new DictTp();
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
	@CacheEvict(value = { "DictCdServiceImpl_getList", "DictCdServiceImpl_getDictCd" }, allEntries = true)
	@Override
	public DictTp add(DictTp inf) {
		TSysDictTp tInf = new TSysDictTp();
		BeanUtils.copyProperties(inf, tInf);

		save(tInf);

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		return inf;
	}

	// 清空缓存
	@CacheEvict(value = { "DictCdServiceImpl_getList", "DictCdServiceImpl_getDictCd" }, allEntries = true)
	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete from TSysDictTp t where t.dictTypeCd in (";
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
	@CacheEvict(value = { "DictCdServiceImpl_getList", "DictCdServiceImpl_getDictCd" }, allEntries = true)
	@Override
	public Json modify(DictTp inf) {
		Json j = new Json();

		TSysDictTp tInf = getById(TSysDictTp.class, inf.getDictTypeCd());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		DictTp oldObject = new DictTp();
		BeanUtils.copyProperties(tInf, oldObject);
		j.setOldObj(oldObject);

		BeanUtils.copyProperties(inf, tInf, new String[] { "uuid" });
		update(tInf);// 更新

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(inf);
		return j;
	}

	@Override
	public Json verifyInfo(DictTp inf) {
		Json j = new Json();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getDictTypeCd())) {
			map.put("dictTypeCd", inf.getDictTypeCd().trim());
		}
		Long total = super.count("Select count(*) from TSysDictTp t where trim(t.dictTypeCd) =:dictTypeCd ", map);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("新增失败：数据字典编号已经存在");
			return j;
		}

		j.setSuccess(true);
		j.setMsg("新增成功！");
		j.setObj(inf);
		return j;
	}

	@Override
	public List<DictTp> getList() {
		List<DictTp> l = new ArrayList<DictTp>();
		String hql = " from TSysDictTp t";
		List<TSysDictTp> tl = new ArrayList<TSysDictTp>();
		tl = find(hql);

		if (tl != null && tl.size() > 0) {
			for (TSysDictTp t : tl) {
				DictTp dictTp = new DictTp();
				BeanUtils.copyProperties(t, dictTp);
				l.add(dictTp);
			}
		}
		return l;
	}

}
