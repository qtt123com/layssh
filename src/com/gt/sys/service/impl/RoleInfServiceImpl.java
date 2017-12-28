package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.gt.model.TSysFunctionInf;
import com.gt.model.TSysMenuInf;
import com.gt.model.TSysOperInf;
import com.gt.model.TSysRoleFunction;
import com.gt.model.TSysRoleInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.MenuInf;
import com.gt.pageModel.RoleFunction;
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.ZTree;
import com.gt.sys.service.IInsInfService;
import com.gt.sys.service.IMenuInfService;
import com.gt.sys.service.IOperInfService;
import com.gt.sys.service.IRoleFunctionService;
import com.gt.sys.service.IRoleInfService;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：角色管理
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
@Service("roleInfService")
public class RoleInfServiceImpl extends BaseServiceImpl<TSysRoleInf> implements IRoleInfService {

	private IMenuInfService menuInfService;// 菜单管理

	private ISysFunctionInfService sysFunctionInfService;// 权限管理

	private IRoleFunctionService roleFunctionService;// 角色功能信息

	private IOperInfService operInfService;// 人员信息

	private IInsInfService infService;// 机构信息

	public IInsInfService getInfService() {
		return infService;
	}

	@Autowired
	public void setInfService(IInsInfService infService) {
		this.infService = infService;
	}

	public IOperInfService getOperInfService() {
		return operInfService;
	}

	@Autowired
	public void setOperInfService(IOperInfService operInfService) {
		this.operInfService = operInfService;
	}

	public IRoleFunctionService getRoleFunctionService() {
		return roleFunctionService;
	}

	@Autowired
	public void setRoleFunctionService(IRoleFunctionService roleFunctionService) {
		this.roleFunctionService = roleFunctionService;
	}

	public ISysFunctionInfService getSysFunctionInfService() {
		return sysFunctionInfService;
	}

	@Autowired
	public void setSysFunctionInfService(ISysFunctionInfService sysFunctionInfService) {
		this.sysFunctionInfService = sysFunctionInfService;
	}

	public IMenuInfService getMenuInfService() {
		return menuInfService;
	}

	@Autowired
	public void setMenuInfService(IMenuInfService menuInfService) {
		this.menuInfService = menuInfService;
	}

	@Override
	public DatagridForLayUI datagrid(RoleInf roleInf, String insCd) throws Exception {
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlleft = "select a.role_cd,a.JUMP_URL,a.URL_NM,a.remark,a.role_nm ,a.write_oper_cd,c.ins_detail,c.ins_nm as operInsCdNm ";
		String sql = " from t_sys_role_inf a, t_sys_oper_inf b,t_sys_ins_inf c where a.write_oper_cd=b.oper_cd and b.ins_cd=c.uuid  ";

		Map<String, Object> param = new HashMap<String, Object>();

		// 角色代码
		if (!PbUtils.isEmpty(roleInf.getRoleCd())) {
			sql += " and a.role_Cd like:roleCd";
			param.put("roleCd", "%%" + roleInf.getRoleCd() + "%%");
		}
		// 角色名称
		if (!PbUtils.isEmpty(roleInf.getRoleNm())) {
			sql += " and a.role_Nm like:roleNm";
			param.put("roleNm", "%%" + roleInf.getRoleNm() + "%%");
		}
		// 备注
		if (!PbUtils.isEmpty(roleInf.getRemark())) {
			sql += " and a.remark like:remark";
			param.put("remark", "%%" + roleInf.getRemark() + "%%");
		}
		// 数据区分
		if (!PbUtils.isEmpty(insCd)) {
			sql += " and (c.uuid=:insCd or c.parent_ins_cd=:insCd or c.up_two_ins=:insCd or c.up_three_ins=:insCd)";
			param.put("insCd", insCd);
		}

		String totalSql = "select count(*) " + sql;

		if (!PbUtils.isEmpty(roleInf.getSort())) {
			sql += " order by " + roleInf.getSort() + "  " + roleInf.getOrder();
		}

		List<Map> maps = findBySql(sqlleft + sql, param, roleInf.getPage(), roleInf.getLimit());
		MapToBeanUtils<RoleInf> beanUtils = new MapToBeanUtils<RoleInf>();
		List<RoleInf> l = beanUtils.ListMapToJavaBean(maps, RoleInf.class);

		Long total = countBySql(totalSql, param);
		grid.setCount(total);
		grid.setData(l);

		return grid;
	}

	@Override
	public RoleInf add(RoleInf inf) {
		TSysRoleInf tInf = new TSysRoleInf();
		BeanUtils.copyProperties(inf, tInf);
		TSysOperInf tSysOperInf = operInfService.getById(TSysOperInf.class, inf.getWrite_oper_cd());
		tInf.setTSysOperInf(tSysOperInf);
		save(tInf);

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		return inf;
	}

	// 清空缓存
	@CacheEvict(value = {"MenuInfServiceImpl_getTreeByRole","MenuInfServiceImpl_getTreeByRoleByNoSys","SysFunctionInfServiceImpl_countSysByRoleAndRequest"}, allEntries = true)
	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete from TSysRoleInf t where t.roleCd in (";
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
	public Json modify(RoleInf inf) {
		Json j = new Json();

		TSysRoleInf tInf = getById(TSysRoleInf.class, inf.getRoleCd());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		RoleInf oldobject = new RoleInf();
		BeanUtils.copyProperties(tInf, oldobject);
		j.setOldObj(oldobject);

		BeanUtils.copyProperties(inf, tInf);
		// TSysOperInf tSysOperInf=operInfService.getById(TSysOperInf.class,
		// inf.getWrite_oper_cd());
		// tInf.setTSysOperInf(tSysOperInf);

		update(tInf);// 更新

		// 设置返回对象
		BeanUtils.copyProperties(tInf, inf);

		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(inf);
		return j;
	}

	@Override
	public Json verifyInfo(RoleInf inf) {
		Json j = new Json();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getRoleNm())) {
			map.put("roleNm", inf.getRoleNm().trim());
		}
		Long total = super.count("Select count(*) from TSysRoleInf t where trim(t.roleNm) =:roleNm ", map);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("新增失败：角色名称已经存在");
			return j;
		}

		j.setSuccess(true);
		j.setMsg("成功！");
		j.setObj(inf);
		return j;
	}

	@Override
	public List<ZTree> getAllTree() throws Exception {
		List<ZTree> trees = new ArrayList<ZTree>();
		String shql = "from TSysFunctionInf t";
		List<TSysFunctionInf> sInfs = sysFunctionInfService.find(shql);

		if (sInfs != null && sInfs.size() > 0) {
			for (TSysFunctionInf tsFunctionInf : sInfs) {
				ZTree zTree = new ZTree();

				// 设置ID
				if (!PbUtils.isEmpty(tsFunctionInf.getFunctionCd())) {
					zTree.setId(tsFunctionInf.getFunctionCd());
				}
				// 设置text
				if (!PbUtils.isEmpty(tsFunctionInf.getFunctionNm())) {
					zTree.setName(tsFunctionInf.getFunctionNm());
				}

				zTree.setOpen(true);// 展开

				// 判断父节点是否为空
				if (tsFunctionInf.getTSysMenuInf() != null) {
					if (!PbUtils.isEmpty(tsFunctionInf.getTSysMenuInf().getId())) {
						zTree.setpId(tsFunctionInf.getTSysMenuInf().getId());
					}
				}

				// 添加到集合
				trees.add(zTree);

			}
		}

		// List<ZTree> zTrees = getTrees();
		List<ZTree> zTrees = menuTrees();
		for (ZTree z : zTrees) {
			trees.add(z);
		}

		return trees;
	}

	/**
	 * 查询所有父节点
	 * 
	 * @return
	 */
	private List<ZTree> getTrees() {
		List<ZTree> trees = new ArrayList<ZTree>();
		String hql = "from TSysMenuInf t order by t.menuSort asc ";

		// 同步查询节点
		List<TSysMenuInf> tMenuInf = menuInfService.find(hql);
		if (tMenuInf != null && tMenuInf.size() > 0) {
			for (TSysMenuInf t : tMenuInf) {
				ZTree zTree = new ZTree();
				MenuInf menuInf = new MenuInf();
				BeanUtils.copyProperties(t, menuInf);

				if (!PbUtils.isEmpty(menuInf.getId())) {
					zTree.setId(menuInf.getId());
				}

				if (!PbUtils.isEmpty(menuInf.getText())) {
					zTree.setName(menuInf.getText());
				}

				zTree.setOpen(true);

				TSysMenuInf pMenuInf = t.getTSysMenuInf();

				if (pMenuInf != null) {
					zTree.setpId(t.getTSysMenuInf().getId());// 获取父级节点
				}

				trees.add(zTree);
			}
		}
		return trees;
	}

	/**
	 * 查询所有系统功能的父节点
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<ZTree> menuTrees() throws Exception {
		List<ZTree> trees = new ArrayList<ZTree>();
		String sql = "";
		// 总共最多支持6级
		for (int x = 0; x < 6; x++) {
			sql += " select t.id, t.icon_cls, t.menu_sort, t.menu_url, t.text,t.praent_id as pid from  t_sys_menu_inf t where t.id in( ";
			for (int y = 0; y < x; y++) {
				sql += " select distinct(t.praent_id) from  t_sys_menu_inf t where t.id in(";
			}
			sql += " select distinct(a.praent_id) praent_id from  t_sys_menu_inf a where a.id   in(select t.menu_id from t_sys_function_inf t )";
			for (int y = 0; y < x; y++) {
				sql += " )";
			}
			sql += " )";
			sql += " union";
		}
		sql += " select  t.id, t.icon_cls, t.menu_sort, t.menu_url, t.text,t.praent_id as pid from  t_sys_menu_inf t where t.id in(";
		sql += " select distinct(a.praent_id) praent_id from  t_sys_menu_inf a where a.id   in(select t.menu_id from t_sys_function_inf t )";
		sql += " )";
		sql += " union";
		sql += " select  t.id, t.icon_cls, t.menu_sort, t.menu_url, t.text,t.praent_id as pid from  t_sys_menu_inf t where t.id   in(select t.menu_id from t_sys_function_inf t )";

		// 同步查询节点
		List<Map> maps = menuInfService.findBySql(sql);
		List<MenuInf> menuInfs = new ArrayList<MenuInf>();
		MapToBeanUtils<MenuInf> benBeanUtils = new MapToBeanUtils<MenuInf>();
		menuInfs = benBeanUtils.ListMapToJavaBean(maps, MenuInf.class);
		if (menuInfs != null && menuInfs.size() > 0) {
			for (MenuInf t : menuInfs) {
				ZTree zTree = new ZTree();
				MenuInf menuInf = new MenuInf();
				BeanUtils.copyProperties(t, menuInf);

				if (!PbUtils.isEmpty(menuInf.getId())) {
					zTree.setId(menuInf.getId());
				}

				if (!PbUtils.isEmpty(menuInf.getText())) {
					zTree.setName(menuInf.getText());
				}

				zTree.setOpen(true);

				if (t.getPid() != null) {
					zTree.setpId(t.getPid());// 获取父级节点
				}

				trees.add(zTree);
			}
		}
		return trees;

	}

	@Override
	public List<RoleFunction> getRoleSelected(String roleCd) {
		List<RoleFunction> l = new ArrayList<RoleFunction>();

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TSysRoleFunction t where 1=1  and t.TSysRoleInf.roleCd=:roleCd ";

		params.put("roleCd", roleCd);

		List<TSysRoleFunction> tList = roleFunctionService.find(hql, params);
		if (tList != null) {
			for (TSysRoleFunction tFunction : tList) {
				RoleFunction roleFunction = new RoleFunction();
				roleFunction.setFunctionCd(tFunction.getTSysFunctionInf().getFunctionCd());

				l.add(roleFunction);// 添加到集合
			}

		}
		return l;
	}

	@Override
	public List<RoleInf> getList(String insCd, String operCd) throws Exception {
		// 查询自己本机构及下属机构所创建的角色，以及自己上一级授予自己的角色
		List<RoleInf> l = new ArrayList<RoleInf>();
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "select a.role_cd,a.remark,a.role_nm ";
		sql += " from t_sys_role_inf a where a.role_cd in(";
		sql += " select  a.role_cd";
		sql += " from t_sys_role_inf a, t_sys_oper_inf b,t_sys_ins_inf c where a.write_oper_cd=b.oper_cd and b.ins_cd=c.uuid ";
		// 数据区分
		if (!PbUtils.isEmpty(insCd)) {
			sql += " and (c.uuid=:insCd or c.parent_ins_cd=:insCd or c.up_two_ins=:insCd or c.up_three_ins=:insCd)";
			params.put("insCd", insCd);
		}
		sql += " union ";
		sql += "select t.role_cd from t_sys_role_oper t where t.oper_cd=:oper_cd)";
		params.put("oper_cd", operCd);

		List<Map> maps = findBySql(sql, params);
		MapToBeanUtils<RoleInf> beanUtils = new MapToBeanUtils<RoleInf>();
		l = beanUtils.ListMapToJavaBean(maps, RoleInf.class);
		return l;
	}

	@Override
	public List<RoleInf> getAllList() throws Exception {
		List<RoleInf> l = new ArrayList<RoleInf>();
		String sql = "select a.role_cd,a.remark,a.role_nm  from t_sys_role_inf a";
		List<Map> maps = findBySql(sql);
		MapToBeanUtils<RoleInf> beanUtils = new MapToBeanUtils<RoleInf>();
		l = beanUtils.ListMapToJavaBean(maps, RoleInf.class);
		return l;
	}
}
