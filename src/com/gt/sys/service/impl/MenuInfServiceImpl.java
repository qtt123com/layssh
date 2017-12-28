package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gt.model.TSysMenuInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.MenuForLayUI;
import com.gt.pageModel.MenuInf;
import com.gt.pageModel.TreeForLayUI;
import com.gt.pageModel.ZTree;
import com.gt.sys.service.IMenuInfService;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.utils.DataConverter;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.PbUtils;
import com.gt.utils.ReadFile;

/**
 * 
 * @功能说明：菜单功能service实现
 * @作者： herun
 * @创建日期：2015-09-23
 * @版本号：V1.0
 */
@Service("menuInfService")
public class MenuInfServiceImpl extends BaseServiceImpl<TSysMenuInf> implements IMenuInfService {

	private ISysFunctionInfService sysFunctionInfService;// 系统功能信息

	public ISysFunctionInfService getSysFunctionInfService() {
		return sysFunctionInfService;
	}

	@Autowired
	public void setSysFunctionInfService(ISysFunctionInfService sysFunctionInfService) {
		this.sysFunctionInfService = sysFunctionInfService;
	}

	@Override
	public List<MenuInf> getTree(String id) {
		List<MenuInf> pmInfs = new ArrayList<MenuInf>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "";

		// 异步查询节点
		if (id == null || id.equals("")) {
			hql = "from TSysMenuInf t where t.TSysMenuInf is null ";// 查询跟节点
		} else {
			hql = "from TSysMenuInf t where t.TSysMenuInf.id =:id ";// 查询子节点
			params.put("id", id);
		}

		List<TSysMenuInf> tMenuInf = find(hql, params);
		if (tMenuInf != null && tMenuInf.size() > 0) {
			for (TSysMenuInf t : tMenuInf) {
				MenuInf menuInf = new MenuInf();
				BeanUtils.copyProperties(t, menuInf);
				Set<TSysMenuInf> set = t.getTSysMenuInfs();// 查询是否有子节点
				if (set != null && !set.isEmpty()) {
					menuInf.setState("closed");// 节点以文件夹形式展现
				} else {
					menuInf.setState("open");// 节点以文件形式展现
				}

				pmInfs.add(menuInf);
			}
		}
		return pmInfs;

	}

	@Override
	public List<MenuInf> getAllTree() {
		List<MenuInf> pmInfs = new ArrayList<MenuInf>();
		String hql = "from TSysMenuInf t order by t.menuSort asc ";

		// 同步查询节点
		List<TSysMenuInf> tMenuInf = find(hql);
		if (tMenuInf != null && tMenuInf.size() > 0) {
			for (TSysMenuInf t : tMenuInf) {
				MenuInf menuInf = new MenuInf();
				BeanUtils.copyProperties(t, menuInf);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", t.getMenuUrl());
				attributes.put("iconCls", t.getIconCls());

				menuInf.setAttributes(attributes);
				TSysMenuInf pMenuInf = t.getTSysMenuInf();
				if (pMenuInf != null) {
					menuInf.setPid(t.getTSysMenuInf().getId());// 获取父级节点
				}

				pmInfs.add(menuInf);
			}
		}
		return pmInfs;
	}

	@Override
	public DatagridForLayUI datagrid(MenuInf menuInf) {
		DatagridForLayUI grid = new DatagridForLayUI();
		String hql = "from TSysMenuInf t where 1=1 ";

		Map<String, Object> param = new HashMap<String, Object>();

		// 菜单名称
		if (menuInf.getText() != null && !menuInf.getText().trim().equals("")) {
			hql += " and t.text like:text";
			param.put("text", "%%" + menuInf.getText() + "%%");
		}

		// 图标
		if (menuInf.getIconCls() != null && !menuInf.getIconCls().trim().equals("")) {
			hql += " and t.iconCls like:iconCls";
			param.put("iconCls", "%%" + menuInf.getIconCls() + "%%");
		}

		// URL
		if (menuInf.getMenuUrl() != null && !menuInf.getMenuUrl().trim().equals("")) {
			hql += " and t.menuUrl like:menuUrl";
			param.put("menuUrl", "%%" + menuInf.getMenuUrl() + "%%");
		}

		// 排序
		if (menuInf.getMenuSort() != null && !menuInf.getMenuSort().trim().equals("")) {
			hql += " and t.menuSort like:menuSort";
			param.put("menuSort", "%%" + menuInf.getMenuSort() + "%%");
		}

		// 上一级菜单
		if (menuInf.getPid() != null && !menuInf.getPid().trim().equals("")) {
			hql += " and t.TSysMenuInf.id like:pid";

			// 查找上一级菜单
			TSysMenuInf fInf = getById(TSysMenuInf.class, menuInf.getPid());
			if (fInf != null && fInf.getId() != null && !fInf.getId().equals("")) {
				menuInf.setPid(fInf.getId());
			}

			param.put("pid", "%%" + menuInf.getPid() + "%%");
		}

		String totalHql = "select count(*) " + hql;

		if (menuInf.getSort() != null) {
			hql += " order by " + menuInf.getSort() + "  " + menuInf.getOrder();
		}

		List<TSysMenuInf> tl = find(hql, param, menuInf.getPage(), menuInf.getLimit());
		List<MenuInf> l = new ArrayList<MenuInf>();

		if (tl != null && tl.size() > 0) {
			for (TSysMenuInf t : tl) {
				MenuInf inf = new MenuInf();
				BeanUtils.copyProperties(t, inf);

				// 判断是否有父级菜单
				if (t.getTSysMenuInf() != null) {
					inf.setPidNm(t.getTSysMenuInf().getText());// 上一级菜单名称
					inf.setPid(t.getTSysMenuInf().getId());// 上一级菜单ID
				}

				l.add(inf);
			}
		}

		Long total = count(totalHql, param);
		grid.setCount(total);
		grid.setData(l);

		return grid;
	}

	@Cacheable(value = "MenuInfServiceImpl_treegrid", key = "'allTreegrid'")
	@Override
	public List<MenuInf> treegrid() {
		String hql = "from TSysMenuInf t where 1=1 order by t.menuSort desc";
		List<MenuInf> pmInfs = new ArrayList<MenuInf>();
		// 同步查询节点
		List<TSysMenuInf> tMenuInf = find(hql);

		// 最顶层的菜单
		List<TSysMenuInf> topInfs = new ArrayList<TSysMenuInf>();
		if (tMenuInf != null && tMenuInf.size() > 0) {
			for (TSysMenuInf t : tMenuInf) {
				if (t.getTSysMenuInf() == null) {
					topInfs.add(t);
				}
			}
		}

		Map<Integer, TSysMenuInf> map = sortList(topInfs);
		Integer maxInt = getMax(topInfs) + 1;
		for (int i = 0; i < maxInt; i++) {
			if (map.get(i) != null) {
				setTreeData(pmInfs, map.get(i));
			}
		}

		return pmInfs;
	}

	/**
	 * 添加节点
	 * 
	 * @param pmInfs
	 * @param fatherMInf
	 */
	private void setTreeData(List<MenuInf> pmInfs, TSysMenuInf fatherMInf) {
		MenuInf menuInf = new MenuInf();
		BeanUtils.copyProperties(fatherMInf, menuInf);
		recursiveAddChild(fatherMInf, menuInf);
		pmInfs.add(menuInf);

	}

	/**
	 * 递归查询
	 * 
	 * @param fatherMInf
	 * @param menuInf
	 */
	private void recursiveAddChild(TSysMenuInf fatherMInf, MenuInf menuInf) {
		// 查询子节点
		if (fatherMInf.getTSysMenuInfs() != null && fatherMInf.getTSysMenuInfs().size() > 0) {
			List<MenuInf> children = new ArrayList<MenuInf>();

			Map<Integer, TSysMenuInf> map = sortList(fatherMInf.getTSysMenuInfs());
			Integer maxInt = getMax(fatherMInf.getTSysMenuInfs()) + 1;
			for (int i = 0; i < maxInt; i++) {
				MenuInf childMenuInf = new MenuInf();
				if (map.get(i) != null) {
					TSysMenuInf tSysMenuInf = map.get(i);
					BeanUtils.copyProperties(tSysMenuInf, childMenuInf);
					childMenuInf.setPid(fatherMInf.getId());// 上一级菜单ID
					childMenuInf.setPidNm(fatherMInf.getText());// 上一级菜单名称

					children.add(childMenuInf);
					recursiveAddChild(tSysMenuInf, childMenuInf);// 递归添加
				}
			}
			menuInf.setChildren(children);
		}
	}

	/**
	 * 排序
	 * 
	 * @param tSet
	 * @return
	 */
	private Map<Integer, TSysMenuInf> sortList(Set<TSysMenuInf> tSet) {
		Map<Integer, TSysMenuInf> tMap = new HashMap<Integer, TSysMenuInf>();
		for (TSysMenuInf tInf : tSet) {
			tMap.put(Integer.parseInt(tInf.getMenuSort()), tInf);
		}
		return tMap;
	}

	/**
	 * 排序
	 * 
	 * @param tSet
	 * @return
	 */
	private Map<Integer, TSysMenuInf> sortList(List<TSysMenuInf> tSet) {
		Map<Integer, TSysMenuInf> tMap = new HashMap<Integer, TSysMenuInf>();
		for (TSysMenuInf tInf : tSet) {
			tMap.put(Integer.parseInt(tInf.getMenuSort()), tInf);
		}
		return tMap;
	}

	/**
	 * 获取最大数
	 * 
	 * @return
	 */
	private Integer getMax(Set<TSysMenuInf> tSet) {
		Integer max = 0;
		for (TSysMenuInf tInf : tSet) {
			if (Integer.parseInt(tInf.getMenuSort()) > max) {
				max = Integer.parseInt(tInf.getMenuSort());
			}
		}
		return max;
	}

	/**
	 * 获取最大数
	 * 
	 * @return
	 */
	private Integer getMax(List<TSysMenuInf> tSet) {
		Integer max = 0;
		for (TSysMenuInf tInf : tSet) {
			if (Integer.parseInt(tInf.getMenuSort()) > max) {
				max = Integer.parseInt(tInf.getMenuSort());
			}
		}
		return max;
	}

	// 清空缓存
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole", "MenuInfServiceImpl_treegrid", "MenuInfServiceImpl_getTreeByRoleByNoSys" }, allEntries = true)
	@Override
	public MenuInf add(MenuInf menuInf) {
		TSysMenuInf tInf = new TSysMenuInf();

		// 左补0，使用字符串排序
		if (!PbUtils.isEmpty(menuInf.getMenuSort())) {
			menuInf.setMenuSort(DataConverter.addZeroLeft(menuInf.getMenuSort(), 3));
		}

		BeanUtils.copyProperties(menuInf, tInf);

		// 是否有上一级菜单
		if (menuInf.getPid() != null && menuInf.getPid().length() > 0) {
			TSysMenuInf fInf = getById(TSysMenuInf.class, menuInf.getPid());
			if (fInf != null) {
				tInf.setTSysMenuInf(fInf);// 添加上一级菜单
			}
		}

		tInf.setId(UUID.randomUUID().toString());
		save(tInf);

		// 设置返回对象
		BeanUtils.copyProperties(tInf, menuInf);

		if (tInf.getTSysMenuInf() != null) {
			if (tInf.getTSysMenuInf().getId() != null && tInf.getTSysMenuInf().getId().length() > 0) {
				menuInf.setPid(tInf.getTSysMenuInf().getId());// 上一级菜单ID

			}
			if (tInf.getTSysMenuInf() != null && tInf.getTSysMenuInf().getText().length() > 0) {
				menuInf.setPidNm(tInf.getTSysMenuInf().getText());// 上一级菜单名称
			}
		}

		return menuInf;
	}

	// 清空缓存
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole", "MenuInfServiceImpl_getTreeByRoleByNoSys", "MenuInfServiceImpl_treegrid" }, allEntries = true)
	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete TSysMenuInf t where t.id in (";
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
	public List<MenuInf> getMenuList() {
		List<TSysMenuInf> tl = find("from TSysMenuInf t");
		List<MenuInf> l = new ArrayList<MenuInf>();
		for (TSysMenuInf t : tl) {
			MenuInf menuInf = new MenuInf();
			BeanUtils.copyProperties(t, menuInf);
			l.add(menuInf);
		}
		return l;
	}

	// 清空缓存
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole", "MenuInfServiceImpl_getTreeByRoleByNoSys", "MenuInfServiceImpl_treegrid" }, allEntries = true)
	@Override
	public Json modify(MenuInf menuInf) {
		Json json = new Json();
		TSysMenuInf tInf = getById(TSysMenuInf.class, menuInf.getId());
		if (tInf == null) {
			json.setSuccess(false);
			json.setMsg("修改失败：找不到要修改的信息");
			return json;
		}

		// 旧对象
		MenuInf oldObject = new MenuInf();
		BeanUtils.copyProperties(tInf, oldObject);
		if (tInf.getTSysMenuInf() != null) {
			if (PbUtils.isEmpty(tInf.getTSysMenuInf().getId())) {
				oldObject.setPid(tInf.getTSysMenuInf().getId());
			}

		}
		json.setOldObj(oldObject);

		// 左补0，使用字符串排序
		if (!PbUtils.isEmpty(menuInf.getMenuSort())) {
			menuInf.setMenuSort(DataConverter.addZeroLeft(menuInf.getMenuSort(), 3));
		}

		BeanUtils.copyProperties(menuInf, tInf);

		// 是否有上一级菜单
		if (menuInf.getPid() != null && menuInf.getPid().length() > 0) {
			TSysMenuInf fInf = getById(TSysMenuInf.class, menuInf.getPid());
			if (fInf != null) {
				tInf.setTSysMenuInf(fInf);// 添加上一级菜单
			}
		} else {
			tInf.setTSysMenuInf(null);// 添加上一级菜单
		}

		update(tInf);// 更新

		// 设置返回对象
		BeanUtils.copyProperties(tInf, menuInf);

		if (tInf.getTSysMenuInf() != null) {
			if (tInf.getTSysMenuInf().getId() != null && tInf.getTSysMenuInf().getId().length() > 0) {
				menuInf.setPid(tInf.getTSysMenuInf().getId());// 上一级菜单ID

			}
			if (tInf.getTSysMenuInf() != null && tInf.getTSysMenuInf().getText().length() > 0) {
				menuInf.setPidNm(tInf.getTSysMenuInf().getText());// 上一级菜单名称
			}
		}
		json.setSuccess(true);
		json.setMsg("更新成功");
		json.setObj(menuInf);
		return json;
	}

	@Override
	public List<MenuInf> getMenuUrlNotNullList() {
		List<TSysMenuInf> tl = find("from TSysMenuInf t where  t.menuUrl !='' ");
		List<MenuInf> l = new ArrayList<MenuInf>();
		for (TSysMenuInf t : tl) {
			MenuInf menuInf = new MenuInf();
			BeanUtils.copyProperties(t, menuInf);
			l.add(menuInf);
		}
		return l;
	}

	
	@Cacheable(value = "MenuInfServiceImpl_getTreeByRole", key = "#operCd")
	@Override
	public List<MenuForLayUI> getTreeByRole(String operCd) throws Exception {
		if (operCd == null) {
			return null;
		}

		List<MenuForLayUI> menuInfs = new ArrayList<MenuForLayUI>();
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = " select a.id id, a.TEXT text ,a.MENU_URL menuUrl ,a.MENU_SORT menuSort,a.ICON_CLS iconCls,a.praent_id as pid ";
		sql += " from t_sys_menu_inf a  where a.ID IN( ";
		sql += "select distinct(t.menu_id) as id  from t_sys_function_inf t where t.function_cd in(";
		sql += "		select distinct(t.function_cd) from t_sys_role_function t where t.role_cd in(";
		sql += "		select t.role_cd  from t_sys_role_oper t where t.oper_cd=:oper_cd";
		sql += "		) )";
		sql += "		union";
		sql += "	select t.id from t_sys_menu_inf t where t.id not in(select t.menu_id from t_sys_function_inf t )";
		sql += ")";
		sql += " ORDER BY a.menu_sort asc ";

		params.put("oper_cd", operCd);
		List<Map> maps = findBySql(sql, params);
		MapToBeanUtils<MenuInf> utils = new MapToBeanUtils<MenuInf>();
		List<MenuInf> tl = utils.ListMapToJavaBean(maps, MenuInf.class);

		// 寻找根节点
		String rootPid = "";
		for (MenuInf menuInf : tl) {
			if (menuInf.getPid() == null || menuInf.getPid().length() == 0) {
				rootPid = menuInf.getId();
				break;
			}
		}

		// 寻找一级目录
		List<MenuForLayUI> oneMenuForLayUI = new ArrayList<MenuForLayUI>();
		for (MenuInf menuInf : tl) {
			if (menuInf.getPid() != null && rootPid.equals(menuInf.getPid())) {
				MenuForLayUI object = new MenuForLayUI();
				object.setId(menuInf.getId());
				object.setPid(menuInf.getPid());
				object.setTitle(menuInf.getText());
				object.setIcon(menuInf.getIconCls());
				object.setHref(menuInf.getMenuUrl());
				object.setSpread(true);
				oneMenuForLayUI.add(object);
			}
		}

		// 寻找二级目录
		List<MenuForLayUI> twoMenuForLayUI = new ArrayList<MenuForLayUI>();
		for (MenuForLayUI menuForLayUI : oneMenuForLayUI) {
			List<MenuForLayUI> children = new ArrayList<MenuForLayUI>();
			for (MenuInf menuInf : tl) {
				if (menuForLayUI.getId() != null && menuInf.getPid() != null && menuInf.getPid().length() > 0 && menuInf.getPid().equals(menuForLayUI.getId())) {
					MenuForLayUI object = new MenuForLayUI();
					object.setId(menuInf.getId());
					object.setPid(menuInf.getPid());
					object.setTitle(menuInf.getText());
					object.setHref(menuInf.getMenuUrl());
					object.setIcon(menuInf.getIconCls());
					children.add(object);
				}
			}
			menuForLayUI.setChildren(children);
			menuInfs.add(menuForLayUI);
		}

		return menuInfs;
	}

	@Cacheable(value = "MenuInfServiceImpl_getTreeByRoleByNoSys", key = "#operCd")
	@Override
	public List<MenuInf> getTreeByRoleByNoSys(String operCd) throws Exception {
		if (operCd == null) {
			return null;
		}

		List<MenuInf> menuInfs = new ArrayList<MenuInf>();
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = " select a.id id, a.TEXT text ,a.MENU_URL menuUrl ,a.MENU_SORT menuSort,a.ICON_CLS iconCls,a.praent_id as pid ";
		sql += " from t_sys_menu_inf a  where a.ID IN( ";
		sql += "select distinct(t.menu_id) as id  from t_sys_function_inf t where t.function_cd in(";
		sql += "		select distinct(t.function_cd) from t_sys_role_function t where t.role_cd in(";
		sql += "		select t.role_cd  from t_sys_role_oper t where t.oper_cd=:oper_cd";
		sql += "		) )";
		sql += "		union";
		sql += "	select t.id from t_sys_menu_inf t where t.id not in(select t.menu_id from t_sys_function_inf t )";
		sql += ")";
		sql += "  and  a.id!='0928b643-2953-4223-96ad-ef796841849f' and a.id!='dbab5c16-c6b8-4b1e-a08b-1899032f9c19' and a.praent_id!='dbab5c16-c6b8-4b1e-a08b-1899032f9c19'";
		sql += " ORDER BY a.menu_sort asc ";

		params.put("oper_cd", operCd);
		List<Map> maps = findBySql(sql, params);
		MapToBeanUtils<MenuInf> utils = new MapToBeanUtils<MenuInf>();
		List<MenuInf> tl = utils.ListMapToJavaBean(maps, MenuInf.class);

		return tl;
	}

	@Override
	public List<ZTree> getZTree() {
		String hql = " from TSysMenuInf t order by t.menuSort asc";
		List<TSysMenuInf> list = find(hql);
		List<ZTree> retList = new ArrayList<ZTree>();

		for (TSysMenuInf tInf : list) {
			ZTree treeNode = new ZTree();

			// 菜单ID
			if (!PbUtils.isEmpty(tInf.getId())) {
				treeNode.setId(tInf.getId());
			}

			// 上一级菜单
			if (tInf.getTSysMenuInf() != null) {
				if (!PbUtils.isEmpty(tInf.getTSysMenuInf().getId())) {
					treeNode.setpId(tInf.getTSysMenuInf().getId());
				}
			}

			// 菜单名称
			if (!PbUtils.isEmpty(tInf.getText())) {
				treeNode.setName(tInf.getText());
			}

			if (PbUtils.isEmpty(tInf.getId())) {
				treeNode.setOpen(true);
			}

			retList.add(treeNode);
		}

		return retList;
	}

	@Override
	public boolean vaild(MenuInf menuInf) {
		if (!PbUtils.isEmpty(menuInf.getMenuSort())) {
			menuInf.setMenuSort(DataConverter.addZeroLeft(menuInf.getMenuSort(), 3));
		}

		// 查询父节点
		if (menuInf != null && menuInf.getPid() != null && menuInf.getPid().length() > 0) {
			String hql = "select count(*) from TSysMenuInf t where t.TSysMenuInf.id=:pid and t.menuSort=:menuSort and t.id !=:id";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("pid", menuInf.getPid());
			params.put("id", menuInf.getId() == null ? "null" : menuInf.getId());
			params.put("menuSort", menuInf.getMenuSort());
			Long count = count(hql, params);
			if (count > 0) {
				return false;
			}
		} else {
			String hql = "select count(*) from TSysMenuInf t where t.TSysMenuInf.id is null and t.menuSort=:menuSort and t.id !=:id";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("menuSort", menuInf.getMenuSort());
			params.put("id", menuInf.getId());
			Long count = count(hql, params);
			if (count > 0) {
				return false;
			}
		}

		return true;
	}

	@Override
	public List<MenuInf> getIconsList(HttpServletRequest request) {
		ReadFile readFile = new ReadFile();
		String fileName = request.getRealPath("/") + "/js/jquery-easyui-1.4.3/themes/icon.css";
		List<String> list = readFile.readFileByLines(fileName);
		List<MenuInf> menuInfs = new ArrayList<MenuInf>();
		for (String icon : list) {
			MenuInf menuInf = new MenuInf();
			menuInf.setIconCls(icon.replace(".", ""));
			menuInf.setState("open");// 节点以文件形式展现
			menuInf.setText(icon.replace(".", ""));
			menuInfs.add(menuInf);
		}
		return menuInfs;
	}

	@Override
	public List<TreeForLayUI> getLayUItree() throws Exception {
		List<TreeForLayUI> menuInfs = new ArrayList<TreeForLayUI>();

		String sql = " select a.id id, a.TEXT text ,a.MENU_URL menuUrl ,a.MENU_SORT menuSort,a.ICON_CLS iconCls,a.praent_id as pid ";
		sql += " from t_sys_menu_inf as a ";

		List<Map> maps = findBySql(sql);
		MapToBeanUtils<MenuInf> utils = new MapToBeanUtils<MenuInf>();
		List<MenuInf> tl = utils.ListMapToJavaBean(maps, MenuInf.class);

		// 寻找根节点
		TreeForLayUI rootTreeForLayUI = new TreeForLayUI();
		String rootPid = "";
		for (MenuInf menuInf : tl) {
			if (menuInf.getPid() == null || menuInf.getPid().length() == 0) {
				rootPid = menuInf.getId();
				rootTreeForLayUI.setId(menuInf.getId());
				rootTreeForLayUI.setName(menuInf.getText());
				rootTreeForLayUI.setIcon(menuInf.getIconCls());
				rootTreeForLayUI.setSpread(true);
				rootPid = menuInf.getId();
			}
		}

		// 寻找一级目录
		List<TreeForLayUI> oneTreeForLayUI = new ArrayList<TreeForLayUI>();
		for (MenuInf menuInf : tl) {
			if (menuInf.getPid() != null && rootPid.equals(menuInf.getPid())) {
				TreeForLayUI object = new TreeForLayUI();
				object.setId(menuInf.getId());
				object.setPid(menuInf.getPid());
				object.setName(menuInf.getText());
				object.setIcon(menuInf.getIconCls());
				object.setSpread(true);
				oneTreeForLayUI.add(object);
			}
		}

		rootTreeForLayUI.setChildren(oneTreeForLayUI);
		menuInfs.add(rootTreeForLayUI);
		return menuInfs;
	}

	@Override
	public List<TreeForLayUI> getAllLayUItree() throws Exception {
		List<TreeForLayUI> menuInfs = new ArrayList<TreeForLayUI>();

		String sql = " select a.id id, a.TEXT text ,a.MENU_URL menuUrl ,a.MENU_SORT menuSort,a.ICON_CLS iconCls,a.praent_id as pid ";
		sql += " from t_sys_menu_inf as a ";

		List<Map> maps = findBySql(sql);
		MapToBeanUtils<MenuInf> utils = new MapToBeanUtils<MenuInf>();
		List<MenuInf> tl = utils.ListMapToJavaBean(maps, MenuInf.class);

		// 寻找根节点
		TreeForLayUI rootTreeForLayUI = new TreeForLayUI();
		String rootPid = "";
		for (MenuInf menuInf : tl) {
			if (menuInf.getPid() == null || menuInf.getPid().length() == 0) {
				rootPid = menuInf.getId();
				rootTreeForLayUI.setId(menuInf.getId());
				rootTreeForLayUI.setName(menuInf.getText());
				rootTreeForLayUI.setIcon(menuInf.getIconCls());
				rootTreeForLayUI.setSpread(true);
				rootPid = menuInf.getId();
			}
		}

		// 寻找一级目录
		List<TreeForLayUI> oneTreeForLayUI = new ArrayList<TreeForLayUI>();
		for (MenuInf menuInf : tl) {
			if (menuInf.getPid() != null && rootPid.equals(menuInf.getPid())) {
				TreeForLayUI object = new TreeForLayUI();
				object.setId(menuInf.getId());
				object.setPid(menuInf.getPid());
				object.setName(menuInf.getText());
				object.setIcon(menuInf.getIconCls());
				object.setSpread(true);
				oneTreeForLayUI.add(object);
			}
		}

		// 寻找二级目录
		List<TreeForLayUI> twoTreeForLayUI = new ArrayList<TreeForLayUI>();
		for (TreeForLayUI treeForLayUI : oneTreeForLayUI) {
			List<TreeForLayUI> childList = new ArrayList<TreeForLayUI>();
			for (MenuInf menuInf : tl) {
				if (menuInf.getPid() != null && menuInf.getPid().length() > 0 && treeForLayUI.getId().equals(menuInf.getPid())) {
					TreeForLayUI object = new TreeForLayUI();
					object.setId(menuInf.getId());
					object.setPid(menuInf.getPid());
					object.setName(menuInf.getText());
					object.setIcon(menuInf.getIconCls());
					object.setSpread(true);
					childList.add(object);
				}
			}
			treeForLayUI.setChildren(childList);
			
			twoTreeForLayUI.add(treeForLayUI);
		}

		rootTreeForLayUI.setChildren(twoTreeForLayUI);
		menuInfs.add(rootTreeForLayUI);
		return menuInfs;
	}
	
	@Override
	public boolean isLastMenu(String pid) throws Exception {
		String sql = " select a.id id, a.TEXT text ,a.MENU_URL menuUrl ,a.MENU_SORT menuSort,a.ICON_CLS iconCls,a.praent_id as pid ";
		sql += " from t_sys_menu_inf as a ";

		List<Map> maps = findBySql(sql);
		MapToBeanUtils<MenuInf> utils = new MapToBeanUtils<MenuInf>();
		List<MenuInf> tl = utils.ListMapToJavaBean(maps, MenuInf.class);

		for (MenuInf menuInf : tl) {
			if (menuInf.getPid()!=null && menuInf.getPid().length()>0 && pid.equals(menuInf.getPid())) {
				return false;
			}
		}
		return true;
	}

}
