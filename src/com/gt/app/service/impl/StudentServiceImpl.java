package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.IStudentService;
import com.gt.model.Student;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：学生管理业务接口实现类
 * @作者： herun
 * @创建日期：2017-12-13
 * @版本号：V1.0
 */
@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements IStudentService {

	@Override
	public DatagridForLayUI datagrid(Student student) throws Exception {
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select  b.dict_nm as sexNm,c.dict_nm as stateNm,";// 数据字典
		sqlLeft += "t.UUID as uuid,t.NAME as name,t.SEX as sex,t.STATE as state,t.NOTE as note,t.DATE_BIRTH as dateBirth,t.ARCHIVES as archives,date_format(t.CREATE_TIME,'%Y-%c-%d %h:%i:%s') as createTime,date_format(t.UPDATE_TIME,'%Y-%c-%d %h:%i:%s') as updateTime ";

		String sql = " from t_app_student t  ";

		sql += " left join t_sys_dict_cd  b on t.sex=b.dict_cd";
		sql += " and b. dict_type_cd='student_sex'";//学生性别-数据字典
		
		sql += " left join t_sys_dict_cd  c on t.state=c.dict_cd";
		sql += " and c. dict_type_cd='student_state'";//学生状态-数据字典
		sql += " where 1=1 ";

		Map<String, Object> param = new HashMap<String, Object>();

		// 名字
		if (!PbUtils.isEmpty(student.getName())) {
			sql += " and t.NAME like:name";
			param.put("name", "%%" + student.getName() + "%%");
		}
		// 性别
		if (!PbUtils.isEmpty(student.getSex())) {
			sql += " and t.SEX =:sex";
			param.put("sex", student.getSex());
		}
		// 状态
		if (!PbUtils.isEmpty(student.getState())) {
			sql += " and t.STATE =:state";
			param.put("state",student.getState());
		}
		

		String totalsql = "select count(*) " + sql;

		// 排序
		sql += " order by  UUID desc";

		List<Map> maps = findBySql(sqlLeft + sql, param, student.getPage(), student.getLimit());
		Long total = countBySql(totalsql, param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

	@Override
	public Json add(Student inf) {
		Json j = new Json();
		save(inf);
		j.setSuccess(true);
		j.setMsg("新增成功");
		j.setObj(inf); // 设置返回对象
		return j;

	}

	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String sql = "delete from t_app_student  where UUID in (";
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
	public Json modify(Student inf) {
		Json j = new Json();

		Student tInf = getById(Student.class, inf.getUuid());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		Student oldObject = new Student();
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
	public Json verifyInfo(Student inf) {
		Json j = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getUuid())) {
			params.put("uuid", inf.getUuid());
		}
		Long total = super.count("Select count(*) from Student t where t.Uuid =:uuid ", params);

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
	public List<Student> getList() {
		List<Student> l = find("from Student t");
		return l;
	}
}
