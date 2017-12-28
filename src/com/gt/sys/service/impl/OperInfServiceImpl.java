package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.model.TSysInsInf;
import com.gt.model.TSysOperInf;
import com.gt.model.TSysRoleInf;
import com.gt.model.TSysRoleOper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.OperInf;
import com.gt.pageModel.RoleOper;
import com.gt.sys.service.IDictCdService;
import com.gt.sys.service.IInsInfService;
import com.gt.sys.service.IOperInfService;
import com.gt.sys.service.IRoleInfService;
import com.gt.sys.service.IRoleOperService;
import com.gt.utils.Contans;
import com.gt.utils.MD5;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：系统用户
 * @作者： herun
 * @创建日期：2015-09-23
 * @版本号：V1.0
 */
@Service("operInfService")
public class OperInfServiceImpl extends BaseServiceImpl<TSysOperInf> implements IOperInfService {

	private IDictCdService dictCdService;// 数据字典

	private IRoleInfService roleInfService;// 用户角色

	private IInsInfService insInfService;// 机构信息

	private IRoleOperService roleOperService;// 角色人员

	public IRoleOperService getRoleOperService() {
		return roleOperService;
	}

	@Autowired
	public void setRoleOperService(IRoleOperService roleOperService) {
		this.roleOperService = roleOperService;
	}

	public IRoleInfService getRoleInfService() {
		return roleInfService;
	}

	@Autowired
	public void setRoleInfService(IRoleInfService roleInfService) {
		this.roleInfService = roleInfService;
	}

	public IInsInfService getInsInfService() {
		return insInfService;
	}

	@Autowired
	public void setInsInfService(IInsInfService insInfService) {
		this.insInfService = insInfService;
	}

	public IDictCdService getDictCdService() {
		return dictCdService;
	}

	@Autowired
	public void setDictCdService(IDictCdService dictCdService) {
		this.dictCdService = dictCdService;
	}

	@Override
	public DatagridForLayUI datagrid(OperInf operInf, String writeOperInsCd) throws Exception {
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlleft = " select a.oper_cd, a.job,  a.email,  a.oper_nm,  a.oper_pwd,  a.oper_st, a.rec_crt_ts,  a.rec_upd_ts,  a.telephone, a.ins_cd,b.ins_nm as insCdNm";
		String sql = " from t_sys_oper_inf a,t_sys_ins_inf b where a.ins_cd=b.uuid ";

		Map<String, Object> param = new HashMap<String, Object>();

		// 用户代码
		if (!PbUtils.isEmpty(operInf.getOperCd())) {
			sql += " and a.oper_Cd like:operCd";
			param.put("operCd", "%%" + operInf.getOperCd() + "%%");
		}
		// 机构编号
		if (!PbUtils.isEmpty(operInf.getInsCd())) {
			sql += " and a.ins_Cd =:insCd";
			param.put("insCd", operInf.getInsCd());
		}
		// 数据所属机构
		if (!PbUtils.isEmpty(writeOperInsCd)) {
			sql += " and (b.uuid=:insCd or b.parent_ins_cd=:insCd or b.up_two_ins=:insCd or b.up_three_ins=:insCd)";
			param.put("insCd", writeOperInsCd);
		}
		// 角色代码
		if (!PbUtils.isEmpty(operInf.getRoleCd())) {
			sql += " and a.oper_cd in ( SELECT t.oper_cd from t_sys_role_oper t where t.role_cd =:roleCd)";
			param.put("roleCd", operInf.getRoleCd());
		}
		// 用户名称
		if (!PbUtils.isEmpty(operInf.getOperNm())) {
			sql += " and a.oper_Nm like:operNm";
			param.put("operNm", "%%" + operInf.getOperNm() + "%%");
		}
		// 用户状态
		if (!PbUtils.isEmpty(operInf.getOperSt())) {
			sql += " and a.oper_St =:operSt";
			param.put("operSt", operInf.getOperSt());
		}
		// 电话号码
		if (!PbUtils.isEmpty(operInf.getTelephone())) {
			sql += " and a.telephone like:telephone";
			param.put("telephone", "%%" + operInf.getTelephone() + "%%");
		}
		// EMAIL
		if (!PbUtils.isEmpty(operInf.getEmail())) {
			sql += " and a.email like:email";
			param.put("email", "%%" + operInf.getEmail() + "%%");
		}
		// 用户密码
		if (!PbUtils.isEmpty(operInf.getOperPwd())) {
			sql += " and a.oper_Pwd like:operPwd";
			param.put("operPwd", "%%" + operInf.getOperPwd() + "%%");
		}
		// 记录创建时间
		if (!PbUtils.isEmpty(operInf.getRecCrtTs())) {
			sql += " and a.rec_Crt_Ts like:recCrtTs";
			param.put("recCrtTs", "%%" + operInf.getRecCrtTs() + "%%");
		}
		// 记录修改时间
		if (!PbUtils.isEmpty(operInf.getRecUpdTs())) {
			sql += " and a.rec_Upd_Ts like:recUpdTs";
			param.put("recUpdTs", "%%" + operInf.getRecUpdTs() + "%%");
		}
		// 岗位
		if (!PbUtils.isEmpty(operInf.getJob())) {
			sql += " and a.job =:job";
			param.put("job", operInf.getJob());
		}

		String totalSql = "select count(*) " + sql;

		if (!PbUtils.isEmpty(operInf.getSort())) {
			sql += " order by " + operInf.getSort() + "  " + operInf.getOrder();
		}

		List<Map> maps = findBySql(sqlleft + sql, param, operInf.getPage(), operInf.getLimit());
		List<OperInf> tl = new ArrayList<OperInf>();
		MapToBeanUtils<OperInf> benBeanUtils = new MapToBeanUtils<OperInf>();
		tl = benBeanUtils.ListMapToJavaBean(maps, OperInf.class);

		List<OperInf> l = new ArrayList<OperInf>();
		if (tl != null && tl.size() > 0) {
			for (OperInf inf : tl) {
				// 用户角色
				List<TSysRoleOper> roleOpers = roleOperService.getList(inf.getOperCd());
				String roleCd = "";
				String roleNm = "";
				for (int i = 0; i < roleOpers.size(); i++) {
					if (i == roleOpers.size() - 1) {
						roleCd += roleOpers.get(i).getTSysRoleInf().getRoleCd();
						roleNm += roleOpers.get(i).getTSysRoleInf().getRoleNm();
					} else {
						roleCd += roleOpers.get(i).getTSysRoleInf().getRoleCd() + ",";
						roleNm += roleOpers.get(i).getTSysRoleInf().getRoleNm() + ",";
					}
				}
				inf.setRoleCd(roleCd);// 角色编号
				inf.setRoleCdNm(roleNm);// 角色名称

				// 用户状态
				if (!PbUtils.isEmpty(inf.getOperSt())) {
					String dictName = dictCdService.getDictCd(Contans.OPER_ST, inf.getOperSt());
					if (!PbUtils.isEmpty(dictName)) {
						inf.setOperStNm(dictName);
					}
				}
				// 岗位
				if (!PbUtils.isEmpty(inf.getJob())) {
					String dictName = dictCdService.getDictCd(Contans.JOB_TP, inf.getJob());
					if (!PbUtils.isEmpty(dictName)) {
						inf.setJobNm(dictName);
					}
				}
				l.add(inf);
			}
		}

		Long total = countBySql(totalSql, param);
		grid.setCount(total);
		grid.setData(l);

		return grid;
	}

	@Override
	public OperInf add(OperInf inf) {
		TSysOperInf tInf = new TSysOperInf();
		BeanUtils.copyProperties(inf, tInf);

		// 用户所属机构
		if (!PbUtils.isEmpty(inf.getInsCd())) {
			TSysInsInf insInf = insInfService.getById(TSysInsInf.class, inf.getInsCd());
			if (insInf != null) {
				tInf.setTSysInsInf(insInf);
			}
		}

		save(tInf);

		// 用户所属角色
		if (!PbUtils.isEmpty(inf.getRoleCd())) {
			roleOperService.remove(inf.getOperCd());
			String[] rolseCds = inf.getRoleCd().split(",");
			for (int i = 0; i < rolseCds.length; i++) {
				RoleOper roleOper = new RoleOper();
				roleOper.setUuid(UUID.randomUUID().toString());
				roleOper.setOperCd(inf.getOperCd());
				roleOper.setRoleCd(rolseCds[i]);
				roleOperService.add(roleOper);
			}
		}

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		return inf;
	}

	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete from TSysOperInf t where t.operCd in (";
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
	public Json modify(OperInf inf) {
		Json j = new Json();

		TSysOperInf tInf = getById(TSysOperInf.class, inf.getOperCd());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		OperInf oldObject = new OperInf();
		BeanUtils.copyProperties(tInf, oldObject);
		if (tInf.getTSysRoleInfs() != null) {
			String roleCds = "";
			for (TSysRoleInf tSysRoleOper : tInf.getTSysRoleInfs()) {
				if (!PbUtils.isEmpty(tSysRoleOper.getRoleCd())) {
					roleCds += tSysRoleOper.getRoleCd();
				}
			}
			oldObject.setRoleCd(roleCds);

		}
		if (tInf.getTSysInsInf() != null) {
			if (PbUtils.isEmpty(tInf.getTSysInsInf().getInsCd())) {
				oldObject.setInsCd(tInf.getTSysInsInf().getInsCd());
			}
		}
		j.setOldObj(oldObject);

		BeanUtils.copyProperties(inf, tInf, new String[] { "operPwd", "operCd", "recCrtTs" });

		// 用户所属角色
		if (!PbUtils.isEmpty(inf.getRoleCd())) {
			roleOperService.remove(inf.getOperCd());
			String[] rolseCds = inf.getRoleCd().split(",");
			for (int i = 0; i < rolseCds.length; i++) {
				RoleOper roleOper = new RoleOper();
				roleOper.setUuid(UUID.randomUUID().toString());
				roleOper.setOperCd(inf.getOperCd());
				roleOper.setRoleCd(rolseCds[i]);
				roleOperService.add(roleOper);
			}
		}

		// 用户所属机构
		if (!PbUtils.isEmpty(inf.getInsCd())) {
			TSysInsInf insInf = insInfService.getById(TSysInsInf.class, inf.getInsCd());
			if (insInf != null) {
				tInf.setTSysInsInf(insInf);
			}
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
	public Json verifyInfo(OperInf inf) {
		Json j = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getOperCd())) {
			params.put("operCd", inf.getOperCd().trim());
		}
		Long total = super.count("Select count(*) from TSysOperInf t where t.operCd =:operCd ", params);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("新增失败：用户名已经存在");
			return j;
		}

		j.setSuccess(true);
		j.setMsg("成功！");
		j.setObj(inf);
		return j;
	}

	@Override
	public Json redoPwd(String operCd) {
		Json j = new Json();
		String pwd = null;

		TSysOperInf operInf = getById(TSysOperInf.class, operCd);
		if (operInf == null) {
			j.setMsg("密码重置失败：找不到要重置的用户信息");
		}

		MD5 md5 = new MD5();
		if (!PbUtils.isEmpty(operInf.getOperPwd())) {
			pwd = md5.getMD5Str(operInf.getOperCd() + Contans.DEFAULT_PASSWORD);// 系统默认密码,
																				// 用户名+密码
		}

		String sql = "update t_sys_oper_inf set oper_pwd=:oper_pwd  where trim(oper_cd)=:oper_cd";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oper_pwd", pwd);
		params.put("oper_cd", operCd.trim());
		executeSql(sql, params);

		j.setSuccess(true);
		j.setMsg("密码重置成功");
		return j;
	}

	@Override
	public Json userLogin(OperInf operInf) {

		Json j = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("operCd", operInf.getOperCd());// 用户名

		MD5 md5 = new MD5();
		String pwd = md5.getMD5Str(operInf.getOperCd() + operInf.getOperPwd());// MD5密码=用户名+密码
		params.put("operPwd", pwd);

		TSysOperInf TSysOperInf = getByHql("from TSysOperInf where operCd=:operCd and operPwd=:operPwd", params);
		if (TSysOperInf == null) {
			j.setSuccess(false);
			j.setMsg("用户名或者密码不正确");
			return j;
		}

		if (!PbUtils.isEmpty(TSysOperInf.getOperSt()) && TSysOperInf.getOperSt().equals(Contans.OPER_ST_NO)) {
			j.setSuccess(false);
			j.setMsg("该用户名处于禁用状态，禁止登录系统");
			return j;
		}

		if (!PbUtils.isEmpty(TSysOperInf.getOperSt()) && TSysOperInf.getOperSt().equals(Contans.OPER_ST_CANCEL)) {
			j.setSuccess(false);
			j.setMsg("该用户名处于注销用状态，禁止登录系统");
			return j;
		}

		j.setSuccess(true);
		return j;
	}

	@Override
	public Json changepwd(OperInf operInf) {
		Json j = new Json();
		MD5 md5 = new MD5();
		String pwd = md5.getMD5Str(operInf.getOperCd() + operInf.getOperPwd());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oper_pwd", pwd);
		params.put("oper_cd", operInf.getOperCd());
		String sql = "update t_sys_oper_inf set oper_pwd=:oper_pwd where oper_cd=:oper_cd";
		executeSql(sql, params);

		j.setMsg("密码修改成功");
		j.setSuccess(true);
		return j;
	}

	@Override
	public List<OperInf> getList() {
		List<OperInf> l = new ArrayList<OperInf>();
		String hql = " from TSysOperInf t";
		List<TSysOperInf> tl = new ArrayList<TSysOperInf>();
		tl = find(hql);

		if (tl != null && tl.size() > 0) {
			for (TSysOperInf t : tl) {
				OperInf operInf = new OperInf();
				BeanUtils.copyProperties(t, operInf);
				l.add(operInf);
			}
		}
		return l;
	}
}
