package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gt.model.TSysDictCd;
import com.gt.model.TSysDictTp;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.DictCd;
import com.gt.pageModel.Json;
import com.gt.sys.service.IDictCdService;
import com.gt.sys.service.IDictTpService;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：数据字典
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
@Service("dictCdService")
public class DictCdServiceImpl extends BaseServiceImpl<TSysDictCd> implements IDictCdService {

	private IDictTpService dictTpService;// 数据字典类型

	public IDictTpService getDictTpService() {
		return dictTpService;
	}

	@Autowired
	public void setDictTpService(IDictTpService dictTpService) {
		this.dictTpService = dictTpService;
	}

	@Override
	public DatagridForLayUI datagrid(DictCd dictCd) {
		DatagridForLayUI grid = new DatagridForLayUI();
		String hql = "from TSysDictCd t where 1=1";

		Map<String, Object> param = new HashMap<String, Object>();

		// UUID
		if (!PbUtils.isEmpty(dictCd.getUuid())) {
			hql += " and t.uuid like:uuid";
			param.put("uuid", "%%" + dictCd.getUuid() + "%%");
		}
		// 字典代码
		if (!PbUtils.isEmpty(dictCd.getDictCd())) {
			hql += " and t.dictCd like:dictCd";
			param.put("dictCd", "%%" + dictCd.getDictCd() + "%%");
		}
		// 字典名称
		if (!PbUtils.isEmpty(dictCd.getDictNm())) {
			hql += " and t.dictNm like:dictNm";
			param.put("dictNm", "%%" + dictCd.getDictNm() + "%%");
		}
		// 字典类型
		if (!PbUtils.isEmpty(dictCd.getDictTypeCd())) {
			hql += " and t.TSysDictTp.dictTypeCd =:dictTypeCd";
			param.put("dictTypeCd", dictCd.getDictTypeCd());
		}

		String totalHql = "select count(*) " + hql;

		if (!PbUtils.isEmpty(dictCd.getSort())) {
			hql += " order by " + dictCd.getSort() + "  " + dictCd.getOrder();
		}

		List<TSysDictCd> tl = find(hql, param, dictCd.getPage(), dictCd.getLimit());
		List<DictCd> l = new ArrayList<DictCd>();

		if (tl != null && tl.size() > 0) {
			for (TSysDictCd t : tl) {
				DictCd inf = new DictCd();
				BeanUtils.copyProperties(t, inf);
				if (t.getTSysDictTp() != null) {
					if (!PbUtils.isEmpty(t.getTSysDictTp().getDictTypeCd())) {
						inf.setDictTypeCd(t.getTSysDictTp().getDictTypeCd());// 数据字典类型
					}
					if (!PbUtils.isEmpty(t.getTSysDictTp().getDictTypeNm())) {
						inf.setDictTypeCdNm(t.getTSysDictTp().getDictTypeNm());// 数据字典类型-名称
					}
				}
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
	public DictCd add(DictCd inf) {
		TSysDictCd tInf = new TSysDictCd();
		BeanUtils.copyProperties(inf, tInf);
		if (inf.getDictTypeCd() != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dictTypeCd", inf.getDictTypeCd());
			TSysDictTp tp = dictTpService.getByHql(" from TSysDictTp t where t.dictTypeCd=:dictTypeCd", params);
			tInf.setTSysDictTp(tp);
		}

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
		String hql = "delete from TSysDictCd t where t.uuid in (";
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
	public Json modify(DictCd inf) {
		Json j = new Json();

		TSysDictCd tInf = getById(TSysDictCd.class, inf.getUuid());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		DictCd oldObject = new DictCd();
		BeanUtils.copyProperties(tInf, oldObject);
		if (tInf.getTSysDictTp() != null) {
			oldObject.setDictTypeCd(tInf.getTSysDictTp().getDictTypeCd());
			oldObject.setDictTypeCdNm(tInf.getTSysDictTp().getDictTypeNm());
		}
		j.setOldObj(oldObject);

		BeanUtils.copyProperties(inf, tInf, new String[] { "uuid" });

		if (inf.getDictTypeCd() != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dictTypeCd", inf.getDictTypeCd());
			TSysDictTp tp = dictTpService.getByHql(" from TSysDictTp t where t.dictTypeCd=:dictTypeCd", params);
			tInf.setTSysDictTp(tp);
		}

		update(tInf);// 更新

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(inf);
		return j;
	}

	@Override
	public Json verifyInfo(DictCd inf) {
		Json j = new Json();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getDictCd())) {
			map.put("dictCd", inf.getDictCd().trim());
		}
		Long total = super.count("Select count(*) from TSysDictCd t where trim(t.dictCd) =:dictCd ", map);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("新增失败：字典代码已经存在");
			return j;
		}

		j.setSuccess(true);
		j.setObj(inf);
		return j;
	}

	@Cacheable(value = "DictCdServiceImpl_getList", key = "#dictCd.getDictTypeCd()+'_'+#dictCd.getQsr()")
	@Override
	public List<DictCd> getList(DictCd dictCd) {
		//System.out.println("-----DictCdServiceImpl_getList------");
		String hql = " from TSysDictCd t where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();

		if (dictCd.getDictTypeCd() != null && dictCd.getDictTypeCd().length() > 0) {
			hql += " and TSysDictTp.dictTypeCd=:dictTypeCd";
			params.put("dictTypeCd", dictCd.getDictTypeCd());
		}

		List<TSysDictCd> tl = find(hql, params);
		List<DictCd> olist = new ArrayList<DictCd>();
		
		if(!PbUtils.isEmpty(dictCd.getQsr()) &&  dictCd.getQsr().equals("1")){
			DictCd object=new DictCd();
			object.setDictCd("  ");
			object.setDictNm("--请选择--");
			olist.add(object);
			
		}
		
		for (TSysDictCd t : tl) {
			DictCd oCd = new DictCd();
			BeanUtils.copyProperties(t, oCd);
			olist.add(oCd);
		}
		return olist;
	}

	@Override
	public List<DictCd> charFormat(DictCd dictCd) {
		String hql = " from TSysDictCd t where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();
		if (dictCd.getDictTypeCd() != null && dictCd.getDictTypeCd().length() > 0) {
			hql += " and TSysDictTp.dictTypeCd =:dictTypeCd";
			params.put("dictTypeCd", dictCd.getDictTypeCd().trim());
		}

		if (dictCd.getDictCd() != null && dictCd.getDictCd().length() > 0) {
			hql += " and dictCd=:dictCd";
			params.put("dictCd", dictCd.getDictCd().trim());
		}

		List<TSysDictCd> tl = find(hql, params);
		List<DictCd> olist = new ArrayList<DictCd>();
		for (TSysDictCd t : tl) {
			DictCd oCd = new DictCd();
			BeanUtils.copyProperties(t, oCd);
			olist.add(oCd);
		}
		return olist;
	}

	@Cacheable(value = "DictCdServiceImpl_getDictCd", key = "#dictTypeCd+#dictCd")
	@Override
	public String getDictCd(String dictTypeCd, String dictCd) {
		//System.out.println("------------DictCdServiceImpl_getDictCd-----------------");
		String hql = " from TSysDictCd t where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();

		if (dictTypeCd != null && dictTypeCd.length() > 0) {
			hql += " and TSysDictTp.dictTypeCd =:dictTypeCd";
			params.put("dictTypeCd", dictTypeCd.trim());
		}
		if (dictCd != null && dictCd.length() > 0) {
			hql += " and dictCd=:dictCd";
			params.put("dictCd", dictCd.trim());
		}

		List<TSysDictCd> tl = find(hql, params);
		if (tl != null && tl.size() > 0) {
			TSysDictCd t = tl.get(0);
			if (t.getDictNm() != null && t.getDictNm().length() > 0) {
				return t.getDictNm();
			}
		}
		return "";
	}

	@Override
	public List<DictCd> getList() {
		List<DictCd> l = new ArrayList<DictCd>();
		String hql = " from TSysDictCd t";
		List<TSysDictCd> tl = new ArrayList<TSysDictCd>();
		tl = find(hql);

		if (tl != null && tl.size() > 0) {
			for (TSysDictCd t : tl) {
				DictCd dictCd = new DictCd();
				BeanUtils.copyProperties(t, dictCd);
				if (t.getTSysDictTp() != null) {
					dictCd.setDictTypeCd(t.getTSysDictTp().getDictTypeCd());
				}
				l.add(dictCd);
			}
		}
		return l;
	}

}
