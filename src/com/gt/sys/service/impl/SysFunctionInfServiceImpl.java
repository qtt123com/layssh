package com.gt.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.gt.model.TSysFunctionInf;
import com.gt.model.TSysMenuInf;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.pageModel.RoleInf;
import com.gt.pageModel.SysFunctionInf;
import com.gt.sys.service.IMenuInfService;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：系统功能
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
@Service("sysFunctionInfService")
public class SysFunctionInfServiceImpl extends BaseServiceImpl<TSysFunctionInf> implements ISysFunctionInfService {

	private IMenuInfService menuInfService;// 菜单管理

	public IMenuInfService getMenuInfService() {
		return menuInfService;
	}

	@Autowired
	public void setMenuInfService(IMenuInfService menuInfService) {
		this.menuInfService = menuInfService;
	}

	@Override
	public DatagridForLayUI datagrid(SysFunctionInf sysFunctionInf) {
		DatagridForLayUI grid = new DatagridForLayUI();
		String hql = "from TSysFunctionInf t where 1=1";

		Map<String, Object> param = new HashMap<String, Object>();

		// 功能名称
		if (sysFunctionInf.getFunctionNm() != null && !sysFunctionInf.getFunctionNm().trim().equals("")) {
			hql += " and t.functionNm like:functionNm";
			param.put("functionNm", "%%" + sysFunctionInf.getFunctionNm() + "%%");
		}

		// 所属菜单
		if (sysFunctionInf.getMenuCd() != null && !sysFunctionInf.getMenuCd().trim().equals("")) {
			hql += " and t.TSysMenuInf.id like:menuCd";
			param.put("menuCd", "%%" + sysFunctionInf.getMenuCd() + "%%");
		}

		// 请求地址
		if (sysFunctionInf.getFunctionUrl() != null && !sysFunctionInf.getFunctionUrl().trim().equals("")) {
			hql += " and t.functionUrl like:functionUrl";
			param.put("functionUrl", "%%" + sysFunctionInf.getFunctionUrl() + "%%");
		}

		String totalHql = "select count(*) " + hql;

		if (sysFunctionInf.getSort() != null) {
			hql += " order by " + sysFunctionInf.getSort() + "  " + sysFunctionInf.getOrder();
		}

		List<TSysFunctionInf> tl = find(hql, param, sysFunctionInf.getPage(), sysFunctionInf.getLimit());
		List<SysFunctionInf> l = new ArrayList<SysFunctionInf>();

		if (tl != null && tl.size() > 0) {
			for (TSysFunctionInf t : tl) {
				SysFunctionInf inf = new SysFunctionInf();
				BeanUtils.copyProperties(t, inf);

				// 判断是否有外键
				if (t.getTSysMenuInf() != null) {
					inf.setMenuNm(t.getTSysMenuInf().getText());// 外键名称
					inf.setMenuCd(t.getTSysMenuInf().getId());// 外键ID
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
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole", "MenuInfServiceImpl_getTreeByRoleByNoSys","SysFunctionInfServiceImpl_isExistUrl", "SysFunctionInfServiceImpl_countSysByRoleAndRequest" }, allEntries = true)
	@Override
	public SysFunctionInf add(SysFunctionInf sftinfo) {

		TSysFunctionInf tInf = new TSysFunctionInf();
		BeanUtils.copyProperties(sftinfo, tInf);

		// 是否有有所属菜单
		if (sftinfo.getMenuCd() != null && sftinfo.getMenuCd().length() > 0) {

			// 查找菜单对象
			TSysMenuInf fInf = menuInfService.getById(TSysMenuInf.class, sftinfo.getMenuCd());
			if (fInf != null) {
				tInf.setTSysMenuInf(fInf);// 添加所属菜单
			}
		}
		tInf.setFunctionCd(UUID.randomUUID().toString());

		// 保存对象
		save(tInf);

		// 设置返回对象
		BeanUtils.copyProperties(tInf, sftinfo);

		if (tInf.getTSysMenuInf() != null) {
			if (tInf.getTSysMenuInf().getId() != null && tInf.getTSysMenuInf().getId().length() > 0) {
				sftinfo.setMenuCd(tInf.getTSysMenuInf().getId());// 所属菜单菜单ID

			}
			if (tInf.getTSysMenuInf() != null && tInf.getTSysMenuInf().getText().length() > 0) {
				sftinfo.setMenuNm(tInf.getTSysMenuInf().getText());// 所属菜单名称
			}
		}

		return sftinfo;
	}

	// 清空缓存
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole", "MenuInfServiceImpl_getTreeByRoleByNoSys","SysFunctionInfServiceImpl_isExistUrl", "SysFunctionInfServiceImpl_countSysByRoleAndRequest" }, allEntries = true)
	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String hql = "delete from TSysFunctionInf t where t.functionCd in (";
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
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole","MenuInfServiceImpl_getTreeByRoleByNoSys", "SysFunctionInfServiceImpl_isExistUrl", "SysFunctionInfServiceImpl_countSysByRoleAndRequest" }, allEntries = true)
	@Override
	public Json modify(SysFunctionInf sftinfo) {
		Json json = new Json();

		TSysFunctionInf tInf = getById(TSysFunctionInf.class, sftinfo.getFunctionCd());
		if (tInf == null) {
			json.setSuccess(false);
			json.setMsg("修改失败：找不到要修改的信息");
			return json;
		}

		BeanUtils.copyProperties(sftinfo, tInf);

		// 是否有有外键
		if (sftinfo.getMenuCd() != null && sftinfo.getMenuCd().length() > 0) {
			TSysMenuInf fInf = menuInfService.getById(TSysMenuInf.class, sftinfo.getMenuCd());
			if (fInf != null) {
				tInf.setTSysMenuInf(fInf);// 添加所属菜单
			}
		} else {
			tInf.setTSysMenuInf(null);// 添加所属菜单
		}

		update(tInf);// 更新

		// 设置返回对象
		BeanUtils.copyProperties(tInf, sftinfo);

		if (tInf.getTSysMenuInf() != null) {
			if (tInf.getTSysMenuInf().getId() != null && tInf.getTSysMenuInf().getId().length() > 0) {
				sftinfo.setMenuCd(tInf.getTSysMenuInf().getId());// 外键ID

			}
			if (tInf.getTSysMenuInf() != null && tInf.getTSysMenuInf().getText().length() > 0) {
				sftinfo.setMenuNm(tInf.getTSysMenuInf().getText());// 外键名称
			}
		}
		json.setSuccess(true);
		json.setMsg("更新成功");
		json.setObj(sftinfo);
		return json;
	}

	@Cacheable(value = "SysFunctionInfServiceImpl_isExistUrl", key = "#functionUrl")
	@Override
	public boolean isExistUrl(String functionUrl) {
		//System.out.println("SysFunctionInfServiceImpl_isExistUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("functionUrl", "%%" + functionUrl + "%%");
		String sql = "select count(*) from TSysFunctionInf A  where trim(A.functionUrl) like:functionUrl";
		Long count = count(sql, params);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkAuth(List<RoleInf> listRoleInf, String requestPath) {
		boolean v = false;
		for (RoleInf roleInf : listRoleInf) {
			Integer result = countSysByRoleAndRequest(requestPath, roleInf.getRoleCd());
			if (result > 0) {
				// 只要一个角色有权限即可
				v = true;
				break;
			}
		}
		return v;
	}

	/**
	 * 根据请求地址和角色CD统计权限
	 * 
	 * @param requestPath
	 * @param roleCd
	 * @return
	 */
	@Cacheable(value = "SysFunctionInfServiceImpl_countSysByRoleAndRequest", key = "#requestPath+#roleCd")
	@Override
	public Integer countSysByRoleAndRequest(String requestPath, String roleCd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleCd", roleCd);
		params.put("requestPath", requestPath);
		String sql = "select count(*) from T_SYS_ROLE_FUNCTION A,T_SYS_FUNCTION_INF B where A.FUNCTION_CD=B.FUNCTION_CD and A.ROLE_CD =:roleCd  and trim(B.FUNCTION_URL)=:requestPath";

		Long count = countBySql(sql, params);
		Integer result = Integer.parseInt(count.toString());
		return result;
	}

	@Override
	public String getFunctionNm(String url) {
		if (PbUtils.isEmpty(url)) {
			return null;
		}

		String functionNm = null;
		String hql = "from TSysFunctionInf t where trim(t.functionUrl)like:functionUrl";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("functionUrl", "%%" + url + "%%");

		List<TSysFunctionInf> list = find(hql, params);
		if (list != null && list.size() > 0) {
			TSysFunctionInf tInf = list.get(0);
			if (!PbUtils.isEmpty(tInf.getFunctionUrl())) {
				functionNm = tInf.getFunctionNm();
			}
		}
		return functionNm;
	}

	@Override
	public SysFunctionInf getFunction(String url) {
		if (PbUtils.isEmpty(url)) {
			return null;
		}
		SysFunctionInf sysFunctionInf = new SysFunctionInf();

		String hql = "from TSysFunctionInf t where trim(t.functionUrl) like:functionUrl";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("functionUrl", "%%" + url + "%%");

		List<TSysFunctionInf> list = find(hql, params);
		if (list != null && list.size() > 0) {
			TSysFunctionInf tInf = list.get(0);
			BeanUtils.copyProperties(tInf, sysFunctionInf);

			// 菜单名称
			if (tInf.getTSysMenuInf() != null) {
				if (!PbUtils.isEmpty(tInf.getTSysMenuInf().getText())) {
					sysFunctionInf.setMenuNm(tInf.getTSysMenuInf().getText());
				}
			}

		}
		return sysFunctionInf;
	}

	@Override
	public List<SysFunctionInf> getList() {
		List<SysFunctionInf> l = new ArrayList<SysFunctionInf>();
		String hql = " from TSysFunctionInf t";
		List<TSysFunctionInf> tl = new ArrayList<TSysFunctionInf>();
		tl = find(hql);

		if (tl != null && tl.size() > 0) {
			for (TSysFunctionInf t : tl) {
				SysFunctionInf sysFunctionInf = new SysFunctionInf();
				BeanUtils.copyProperties(t, sysFunctionInf);
				l.add(sysFunctionInf);
			}
		}
		return l;
	}

}
