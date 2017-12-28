package com.gt.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.I${Action}Service;
import com.gt.model.${HiTable};
import com.gt.pageModel.${Action};
import com.gt.pageModel.Datagrid;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import com.gt.utils.MapToBeanUtils;

/**
 * 
 * @功能说明：${functionComment}业务接口实现类
 * @作者： ${author}
 * @创建日期：${date}
 * @版本号：V1.0
 */
@Service("${action}Service")
public class ${Action}ServiceImpl extends BaseServiceImpl<${HiTable}> implements I${Action}Service {

	@Override
	public Datagrid datagrid(${Action} ${action}) throws Exception{

		Datagrid grid = new Datagrid();
		String hql = "from ${HiTable} t where 1=1";
		
		Map<String, Object> param = new HashMap<String, Object>();
			
		<#list cloums as c>
		// ${c.columnComment}
		if (!PbUtils.isEmpty(${action}.get${c.UpUmnName}())) {
			hql += " and t.${c.columnName} like:${c.columnName}";
			param.put("${c.columnName}", "%%" + ${action}.get${c.UpUmnName}() + "%%");
		}
		</#list>
		
		String totalHql = "select count(*) " + hql;

		if (!PbUtils.isEmpty(${action}.getSort())) {
			hql += " order by " + ${action}.getSort() + "  " + ${action}.getOrder();
		}

		List<${HiTable}> tl = find(hql, param, ${action}.getPage(), ${action}.getRows());
		List<${Action}> l = new ArrayList<${Action}>();

		if (tl != null && tl.size() > 0) {
			for (${HiTable} t : tl) {
				${Action} inf = new ${Action}();
				BeanUtils.copyProperties(t, inf);

				l.add(inf);
			}
		}

		Long total = count(totalHql,param);
		grid.setTotal(total);
		grid.setRows(l);

		return grid;
	}

	
	
	@Override
	public Json add(${Action} inf) {
		Json j = new Json();
		${HiTable} tInf = new ${HiTable}();
		BeanUtils.copyProperties(inf, tInf);

		save(tInf);

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);
		
		j.setSuccess(true);
		j.setMsg("新增成功");
		j.setObj(inf);
		return j;

	}

	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete from ${HiTable} t where t.${pkColumn} in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		executeHql(hql);

	}

	@Override
	public Json modify(${Action} inf) {
		Json j = new Json();

		${HiTable} tInf = getById(${HiTable}.class, inf.get${PkColumn}());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		${Action} oldObject = new ${Action}();
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
	public Json verifyInfo(${Action} inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.get${PkColumn}())) {
			params.put("${pkColumn}", inf.get${PkColumn}().trim());
		}
		Long total = super.count("Select count(*) from ${HiTable} t where t.${pkColumn} =:${pkColumn} ", params);

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
	public List<${Action}> getList() {
		List<${Action}> l = new ArrayList<${Action}>();
		List<${HiTable}> tl = find("from ${HiTable} t");
		if (tl != null && tl.size() > 0) {
			for (${HiTable}  t : tl) {
				${Action} inf = new ${Action}();
				BeanUtils.copyProperties(t, inf);
				l.add(inf);
			}
		}
		return l;
	}
}
