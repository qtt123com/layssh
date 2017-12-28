package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.I${Action}Service;
import com.gt.model.${Action};
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;


/**
 * 
 * @功能说明：${functionComment}业务接口实现类
 * @作者： ${author}
 * @创建日期：${date}
 * @版本号：V1.0
 */
@Service("${action}Service")
public class ${Action}ServiceImpl extends BaseServiceImpl<${Action}> implements I${Action}Service {

	@Override
	public DatagridForLayUI datagrid(${Action} ${action}) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "${sqlselect} " ;
		String sql = " from ${tableName} t where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		<#list cloums as c>
		// ${c.columnComment}
		if (!PbUtils.isEmpty(${action}.get${c.UpUmnName}())) {
			sql += " and t.${c.columnNameBySql} like:${c.columnName}";
			param.put("${c.columnName}", "%%" + ${action}.get${c.UpUmnName}() + "%%");
		}
		</#list>	
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ${oldpkColumn} desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, ${action}.getPage(), ${action}.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}
	
	
	@Override
	public Json add(${Action} inf) {
		Json j = new Json();
		save(inf);
		j.setSuccess(true);
		j.setMsg("新增成功");
		j.setObj(inf);	// 设置返回对象
		return j;

	}

	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String sql = "delete from ${tableName}  where ${PkColumnBySql} in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				sql += ",";
			}
			sql += "'" + nids[i] + "'";
		}
		sql += ")";
		executeSql(sql);

	}

	@Override
	public Json modify(${Action} inf) {
		Json j = new Json();

		${Action} tInf = getById(${Action}.class, inf.get${PkColumn}());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		${Action} oldObject = new ${Action}();
		MyBeanUtils.copyProperties(tInf, oldObject);
		j.setOldObj(oldObject);
		
		MyBeanUtils.copyProperties(inf, tInf);
		update(tInf);// 更新
		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(tInf);// 设置返回对象
		return j;
	}

	
	@Override
	public Json verifyInfo(${Action} inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.get${PkColumn}())) {
			params.put("${pkColumn}", inf.get${PkColumn}());
		}
		Long total = super.count("Select count(*) from ${Action} t where t.${PkColumn} =:${pkColumn} ", params);

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
		List<${Action}> l = find("from ${Action} t");
		return l;
	}
}
