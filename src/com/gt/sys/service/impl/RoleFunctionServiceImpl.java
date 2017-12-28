package com.gt.sys.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.gt.model.TSysFunctionInf;
import com.gt.model.TSysRoleFunction;
import com.gt.model.TSysRoleInf;
import com.gt.pageModel.Json;
import com.gt.pageModel.RoleFunction;
import com.gt.sys.service.IRoleFunctionService;
import com.gt.sys.service.IRoleInfService;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.utils.PbUtils;

/**
 * 
 * @功能说明：角色功能信息
 * @作者： herun
 * @创建日期：2015-09-22
 * @版本号：V1.0
 */
@Service("roleFunctionService")
public class RoleFunctionServiceImpl extends BaseServiceImpl<TSysRoleFunction> implements IRoleFunctionService {

	private IRoleInfService roleInfService;// 角色信息

	private ISysFunctionInfService sysFunctionInfService;// 功能信息

	public ISysFunctionInfService getSysFunctionInfService() {
		return sysFunctionInfService;
	}

	@Autowired
	public void setSysFunctionInfService(ISysFunctionInfService sysFunctionInfService) {
		this.sysFunctionInfService = sysFunctionInfService;
	}

	public IRoleInfService getRoleInfService() {
		return roleInfService;
	}

	@Autowired
	public void setRoleInfService(IRoleInfService roleInfService) {
		this.roleInfService = roleInfService;
	}

	// 清空缓存
	@CacheEvict(value = { "MenuInfServiceImpl_getTreeByRole","MenuInfServiceImpl_getTreeByRoleByNoSys", "SysFunctionInfServiceImpl_countSysByRoleAndRequest" }, allEntries = true)
	@Override
	public void add(RoleFunction function) {

		String functionCds[] = function.getFunctionCd().split(",");

		// 先删除之前的功能信息之后，才重新添加
		remove(function.getRoleCd());

		// 角色信息
		TSysRoleInf troleInf = roleInfService.getById(TSysRoleInf.class, function.getRoleCd());
		for (String functionCd : functionCds) {

			// 必须写在括号里面，每次添加前重新实例化一个对象
			TSysRoleFunction tInf = new TSysRoleFunction();

			// 功能信息
			TSysFunctionInf tSysFunction = sysFunctionInfService.getById(TSysFunctionInf.class, functionCd);

			if (tSysFunction != null) {
				tInf.setUuid(UUID.randomUUID().toString());// 设置UUID
				tInf.setTSysRoleInf(troleInf);// 角色信息
				tInf.setTSysFunctionInf(tSysFunction);// 功能信息

				// 重新添加功能信
				save(tInf);
			}

		}

	}

	// 清空缓存
	@CacheEvict(value = {"MenuInfServiceImpl_getTreeByRole","MenuInfServiceImpl_getTreeByRoleByNoSys","SysFunctionInfServiceImpl_countSysByRoleAndRequest"}, allEntries = true)
	@Override
	public void remove(String roleCd) {
		if (PbUtils.isEmpty(roleCd)) {
			return;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleCd", roleCd);
		String hql = "delete from TSysRoleFunction t where t.TSysRoleInf.roleCd=:roleCd ";
		executeHql(hql, params);

	}

	@Override
	public Json verifyInfo(RoleFunction function) {
		Json j = new Json();
		Map<String, Object> map = new HashMap<String, Object>();

		if (!PbUtils.isEmpty(function.getRoleCd())) {
			map.put("roleCd", function.getRoleCd().trim());
		}
		Long total = super.count("Select count(*) from TSysRoleInf t where trim(t.roleCd) =:roleCd ", map);

		if (total == 0) {
			j.setSuccess(false);
			j.setMsg("保存失败：角色信息不存在");
			return j;
		}

		j.setSuccess(true);
		return j;
	}

}
