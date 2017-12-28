package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gt.model.TSysLog;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.SysLog;
import com.gt.pageModel.TemplateParams;
import com.gt.sys.service.ISysLogService;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.ParseProperties;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：系统日志
 * @作者： herun
 * @创建日期：2015-09-23
 * @版本号：V1.0
 */
@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<TSysLog> implements ISysLogService {

	@Override
	public DatagridForLayUI datagrid(SysLog sysLog) {
		DatagridForLayUI grid = new DatagridForLayUI();
		String hql = "from TSysLog t where 1=1";

		Map<String, Object> param = new HashMap<String, Object>();

		// 操作用户编号
		if (!PbUtils.isEmpty(sysLog.getOperUsrId())) {
			hql += " and t.operUsrId like:operUsrId";
			param.put("operUsrId", "%%" + sysLog.getOperUsrId() + "%%");
		}
		// 操作类型
		if (!PbUtils.isEmpty(sysLog.getOperMethod())) {
			hql += " and t.operMethod like:operMethod";
			param.put("operMethod", "%%" + sysLog.getOperMethod() + "%%");
		}
		// 操作方法
		if (!PbUtils.isEmpty(sysLog.getOperModule())) {
			hql += " and t.operModule like:operModule";
			param.put("operModule", "%%" + sysLog.getOperModule() + "%%");
		}
		// 操作关键字
		if (!PbUtils.isEmpty(sysLog.getOperDesc())) {
			hql += " and t.operDesc like:operDesc";
			param.put("operDesc", "%%" + sysLog.getOperDesc() + "%%");
		}
		// 变更内容
		if (!PbUtils.isEmpty(sysLog.getCompareInf())) {
			hql += " and t.compareInf like:compareInf";
			param.put("compareInf", "%%" + sysLog.getCompareInf() + "%%");
		}
		// 操作人IP地址
		if (!PbUtils.isEmpty(sysLog.getOperIp())) {
			hql += " and t.operIp like:operIp";
			param.put("operIp", "%%" + sysLog.getOperIp() + "%%");
		}

		// 操作起始日期
		if (!PbUtils.isEmpty(sysLog.getStarOperDt())) {
			hql += " and t.operDt >:starOperDt ";
			param.put("starOperDt", sysLog.getStarOperDt().replace("-", "").replace(":", "").trim());
		}
		// 操作结束日期
		if (!PbUtils.isEmpty(sysLog.getEndOperDt())) {
			hql += " and t.operDt <:endOperDt ";
			param.put("endOperDt", sysLog.getEndOperDt().replace("-", "").replace(":", "").trim());
		}

		String totalHql = "select count(*) " + hql;

		hql += " order by OPER_DT desc";
		

		List<TSysLog> tl = find(hql, param, sysLog.getPage(), sysLog.getLimit());
		List<SysLog> l = new ArrayList<SysLog>();

		if (tl != null && tl.size() > 0) {
			for (TSysLog t : tl) {
				SysLog inf = new SysLog();
				BeanUtils.copyProperties(t, inf);

				l.add(inf);
			}
		}

		Long total = count(totalHql, param);
		grid.setCount(total);
		grid.setData(l);

		return grid;
	}

	@Override
	public void add(SysLog sysLog) {
		TSysLog tLog = new TSysLog();
		BeanUtils.copyProperties(sysLog, tLog);
		save(tLog);

	}

	@Cacheable(value = "SysLogServiceImpl_getAllTablePK", key = "'all'")
	@Override
	public String[] getAllTablePK() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select TABLE_NAME  as tableName  from information_schema.TABLES t where t.TABLE_SCHEMA=:database";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("database", ParseProperties.getProperties().get("jdbc.database").toString());
		List<Map> maps = findBySql(sql, params);
		MapToBeanUtils<TemplateParams> utils = new MapToBeanUtils<TemplateParams>();
		List<TemplateParams> l = utils.ListMapToJavaBean(maps, TemplateParams.class);
		for (TemplateParams templateParams : l) {
			sql = "show columns from "+templateParams.getTableName()+" where (`key`)='PRI'";
			maps = findBySql(sql);
			for (int i = 0; i < maps.size(); i++) {
				if (maps.get(i).get("Field") != null) {
					map.put(maps.get(i).get("Field").toString(), maps.get(i).get("Field").toString());
				}
			}
		}

		String[] result = new String[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			result[i] = map.get(key).toString();
			i++;
		}

		return result;

	}
}
