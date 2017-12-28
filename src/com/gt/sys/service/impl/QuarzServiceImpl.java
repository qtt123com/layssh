package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;

import com.gt.model.Quarz;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IDictCdService;
import com.gt.sys.service.IQuarzService;
import com.gt.utils.Contans;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.MyBeanUtils;
import com.gt.utils.PbUtils;
import com.gt.utils.QuarzUtils;

/**
 * 
 * @功能说明：任务调度业务接口实现类
 * @作者： herun
 * @创建日期：2015-11-18
 * @版本号：V1.0
 */
@Service("quarzService")
public class QuarzServiceImpl extends BaseServiceImpl<Quarz> implements IQuarzService {

	@Resource(name = "dictCdService")
	private IDictCdService dictCdService;

	@Override
	public DatagridForLayUI datagrid(Quarz quarz) throws Exception {
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.NID,t.JOB_NAME,t.JOB_GROUP,t.CLASS_PATH,t.CRON_STR,t.STATE,t.MARK ";
		String sql = " from T_SYS_QUARZ t where 1=1 ";

		Map<String, Object> param = new HashMap<String, Object>();

		// 定时器名称
		if (!PbUtils.isEmpty(quarz.getJobName())) {
			sql += " and t.JOB_NAME like:jobName";
			param.put("jobName", "%%" + quarz.getJobName() + "%%");
		}
		// 所属组
		if (!PbUtils.isEmpty(quarz.getJobGroup())) {
			sql += " and t.JOB_GROUP like:jobGroup";
			param.put("jobGroup", "%%" + quarz.getJobGroup() + "%%");
		}
		// 类路径
		if (!PbUtils.isEmpty(quarz.getClassPath())) {
			sql += " and t.CLASS_PATH like:classPath";
			param.put("classPath", "%%" + quarz.getClassPath() + "%%");
		}
		// 执行方法名称
		if (!PbUtils.isEmpty(quarz.getCronStr())) {
			sql += " and t.CRON_STR like:cronStr";
			param.put("cronStr", "%%" + quarz.getCronStr() + "%%");
		}
		// 状态
		if (!PbUtils.isEmpty(quarz.getState())) {
			sql += " and t.STATE like:state";
			param.put("state", "%%" + quarz.getState() + "%%");
		}
		// 备注
		if (!PbUtils.isEmpty(quarz.getMark())) {
			sql += " and t.MARK like:mark";
			param.put("mark", "%%" + quarz.getMark() + "%%");
		}

		String totalsql = "select count(*) " + sql;

		if (!PbUtils.isEmpty(quarz.getSort())) {
			sql += " order by " + quarz.getSort() + "  " + quarz.getOrder();
		}

		List<Map> maps = findBySql(sqlLeft + sql, param, quarz.getPage(), quarz.getLimit());
		List<Quarz> l = new ArrayList<Quarz>();
		MapToBeanUtils<Quarz> beanUtils = new MapToBeanUtils<Quarz>();
		List<Quarz> tl = beanUtils.ListMapToJavaBean(maps, Quarz.class);
		for (Quarz obj : tl) {
			if (!PbUtils.isEmpty(obj.getState())) {
				String dictName = dictCdService.getDictCd(Contans.QUARZ_STATE, obj.getState());// 状态
				if (!PbUtils.isEmpty(dictName)) {
					obj.setStateNm(dictName);
				}
			}
			if (!PbUtils.isEmpty(obj.getJobGroup())) {
				String dictName = dictCdService.getDictCd(Contans.QUARZ_JOB_GROUP, obj.getJobGroup());// 所属组
				if (!PbUtils.isEmpty(dictName)) {
					obj.setJobGroupNm(dictName);
				}
			}
			l.add(obj);

		}
		Long total = countBySql(totalsql, param);
		grid.setCount(total);
		grid.setData(l);

		return grid;
	}

	@Override
	public Json add(Quarz inf, ServletContext context) throws Exception {
		Json j = new Json();
		save(inf);
		j.setSuccess(true);
		j.setMsg("新增成功");
		j.setObj(inf); // 设置返回对象
		QuarzUtils.schedStart(inf, context);
		return j;

	}

	@Override
	public void remove(String ids) throws Exception {
		String[] nids = ids.split(",");
		String sql = "delete from T_SYS_QUARZ  where Nid in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				sql += ",";
			}
			sql += "'" + nids[i] + "'";

			Quarz quarz = getById(Quarz.class, nids[i]);
			if (quarz != null) {
				QuarzUtils.delScheduleJob(quarz.getJobName(), quarz.getJobGroup());// 删除任务
			}
		}
		sql += ")";

		executeSql(sql);

	}

	@Override
	public Json modify(Quarz inf, ServletContext context) throws Exception {
		Json j = new Json();
		Quarz tInf = getById(Quarz.class, inf.getNid());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		Quarz oldObject = new Quarz();
		MyBeanUtils.copyProperties(tInf, oldObject);
		j.setOldObj(oldObject);

		MyBeanUtils.copyProperties(inf, tInf);
		update(tInf);// 更新
		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(tInf);// 设置返回对象

		if (tInf.getState().equals(Contans.QUARZ_STATE_ARRY[0])) {// 运行中
			QuarzUtils.rescheduleJob(tInf, context);
		} else if (tInf.getState().equals(Contans.QUARZ_STATE_ARRY[1])) {// 暂停
			QuarzUtils.pauseJob(tInf.getJobName(), tInf.getJobGroup());
		}

		return j;
	}

	@Override
	public Json verifyInfo(Quarz inf) {
		Json j = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getJobName())) {
			params.put("jobName", inf.getJobName());
		}
		Long total = super.count("Select count(*) from Quarz t where t.jobName =:jobName ", params);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("定时器名称不能重复");
			return j;
		}

		j.setSuccess(true);
		j.setMsg("成功！");
		j.setObj(inf);
		return j;
	}

	@Override
	public List<Quarz> getList() {
		List<Quarz> l = find("from Quarz t");
		return l;
	}
}
