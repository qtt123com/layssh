package com.gt.sys.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import com.gt.model.TSysFunctionInf;
import com.gt.model.TSysMenuInf;
import com.gt.model.TSysRoleFunction;
import com.gt.model.TSysRoleInf;
import com.gt.pageModel.Json;
import com.gt.pageModel.SessionInfo;
import com.gt.pageModel.TableFields;
import com.gt.pageModel.TemplateParams;
import com.gt.sys.service.ICodeGeneratorService;
import com.gt.sys.service.IMenuInfService;
import com.gt.sys.service.IRoleFunctionService;
import com.gt.sys.service.IRoleInfService;
import com.gt.sys.service.ISysFunctionInfService;
import com.gt.utils.Contans;
import com.gt.utils.DataConverter;
import com.gt.utils.FileHandle;
import com.gt.utils.MapToBeanUtils;
import com.gt.utils.ParseProperties;
import com.gt.utils.PbUtils;
import com.gt.utils.TemplateHelp;

import freemarker.template.TemplateException;

/**
 * 
 * @功能说明：代码生成器
 * @作者： herun
 * @创建日期：2015-11-09
 * @版本号：V1.0
 */
@Service("CodeGeneratorByMysqlByLayUIServiceImpl")
public class CodeGeneratorByMysqlByLayUIServiceImpl extends BaseServiceImpl<TableFields> implements ICodeGeneratorService {

	@Resource(name = "menuInfService")
	private IMenuInfService menuInfService;// 系统菜单

	@Resource(name = "sysFunctionInfService")
	private ISysFunctionInfService sysFunctionInfService;// 系统功能

	@Resource(name = "roleFunctionService")
	private IRoleFunctionService roleFunctionService;// 角色功能

	@Resource(name = "roleInfService")
	private IRoleInfService roleInfService;// 系统角色

	private String owner = ParseProperties.getProperties().getProperty("jdbc.userName").toUpperCase();// oracle用户名，请大写
	private String database = ParseProperties.getProperties().getProperty("jdbc.database");//
									// 数据库名称

	@Override
	public List<TemplateParams> getTablesList() throws Exception {
		String sql = "select TABLE_NAME  as TABLENAME  from information_schema.TABLES t where t.TABLE_SCHEMA=:database";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("database", database);
		List<Map> maps = findBySql(sql, params);
		MapToBeanUtils utils = new MapToBeanUtils<TemplateParams>();
		List<TemplateParams> l = utils.ListMapToJavaBean(maps, TemplateParams.class);
		return l;
	}

	@Override
	public List<TableFields> getFieldList(String tableName) throws Exception {
		List<Map> maps = getListMap(tableName);
		MapToBeanUtils utils = new MapToBeanUtils<TableFields>();
		List<TableFields> l = utils.ListMapToJavaBean(maps, TableFields.class);
		return l;
	}

	@Override
	public Json createCode(TemplateParams templateParams, HttpSession session, HttpServletRequest request) throws Exception {
		String jspFilePath = templateParams.getClassPath() + "/WebRoot/app/" + templateParams.getClassName() + "/";
		FileHandle.mkdirs(jspFilePath);// 创建目录
		File f = new File(jspFilePath + templateParams.getFunctionComment() + ".txt");// 创建txt文件
		if (!f.exists()) {
			f.createNewFile();
		}

		jspFilePath += templateParams.getClassName();

		// 1、生成Model代码
		creatModel(templateParams, request);

		// 2、生成Add.jsp
		String addFile = "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Add";
		String addFileNm = jspFilePath + "Add.jsp";
		AddAndEditPubMeth("/LayUIFtl/AddJsp.ftl", templateParams, addFile, addFileNm, request);

		// 3、生成Edit.jsp
		String editFile = "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Edit";
		String editFileNm = jspFilePath + "Edit.jsp";
		AddAndEditPubMeth("/LayUIFtl/EditJsp.ftl", templateParams, editFile, editFileNm, request);

		// 4、生成serach.jsp
		String searchFile = "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Search";
		String searchFileNm = jspFilePath + "Search.jsp";
		AddAndEditPubMeth("/LayUIFtl/Search.ftl", templateParams, searchFile, searchFileNm, request);

		// 5.生成list.jsp
		String listFile = "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "List";
		String ListFileNm = jspFilePath + "List.jsp";
		createListJsp(templateParams, listFile, ListFileNm, request);

		// 6.生成serviceImpl
		creatserviceImpl(templateParams, request);

		// 7.生成Controller类
		creatAction(templateParams, request);

		// 8.生成Iservice接口类
		String IServicefileName = templateParams.getClassPath() + "/src/com/gt/app/service/" + "I" + templateParams.getClassName() + "Service.java";// 生成文件的路径和名称
		creatIServiceAndIDao(templateParams, "/LayUIFtl/IService.ftl", IServicefileName, request);

		// 9.生成IDao类
		// String IDaofileName = templateParams.getClassPath() + "/" + "I" +
		// templateParams.getClassName() + "Dao.java";// 生成文件的路径和名称
		// creatIServiceAndIDao(templateParams, "IDao.ftl",
		// IDaofileName,request);

		// 10.生成DaoImpl类型
		// creatDaoImpl(templateParams,request);

		// 11.是否自动配置菜单
		if (!PbUtils.isEmpty(templateParams.getAddmenu()) && templateParams.getAddmenu().equals("1")) {
			addmenu(templateParams, session);
		}
		
		//12.生成查看页面
		String detailsFile = "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Search";
		String detailsFileNm = jspFilePath + "Detail.jsp";
		AddAndEditPubMeth("/LayUIFtl/DetailsJsp.ftl", templateParams, detailsFile, detailsFileNm, request);
		
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("代码生成成功");
		return j;
	}

	/**
	 * 生成DaoImpl.java类
	 * 
	 * @param templateParams
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void creatDaoImpl(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", templateParams.getClassName());// JavaBean名称
		data.put("HiTable", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getTableName())));// hibbernate
																														// 对象
		data.put("functionComment", templateParams.getFunctionComment());// 功能说明
		data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
		data.put("date", PbUtils.getCurrentDate());// 日期
		String ftlname = "/LayUIFtl/DaoImpl.ftl";
		// 生成文件的路径和名称
		String fileName = templateParams.getClassPath() + "/" + templateParams.getClassName() + "DaoImpl.java";

		TemplateHelp.creatTemplate(data, ftlname, fileName, request);
	}

	/**
	 * 生成IService类、IDao类
	 * 
	 * @param templateParams
	 * @param ftlname
	 *            ftl文件名称
	 * @param fileName
	 *            生成文件的全路径
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void creatIServiceAndIDao(TemplateParams templateParams, String ftlname, String fileName, HttpServletRequest request) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", templateParams.getClassName());// JavaBean名称
		data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
		data.put("HiTable", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getTableName())));// hibbernate
		// 作者
		if (!PbUtils.isEmpty(templateParams.getAuthor())) {
			data.put("author", templateParams.getAuthor());
		}
		// 对象
		data.put("functionComment", templateParams.getFunctionComment());// 功能说明
		data.put("date", PbUtils.getCurrentDate());// 日期
		TemplateHelp.creatTemplate(data, ftlname, fileName, request);
	}

	/**
	 * 生成Action类
	 * 
	 * @param templateParams
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void creatAction(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", templateParams.getClassName());// JavaBean名称
		data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
		data.put("functionComment", templateParams.getFunctionComment());// 功能说明
		data.put("date", PbUtils.getCurrentDate());// 日期
		// 自动判断大小写
		if (templateParams.getPkColumn().substring(1, 2).equals("_")) {
			data.put("PkColumn", PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn()));// 主键字段-第一字母小写，去掉下划线，下划线紧跟的字母大写
		} else {
			data.put("PkColumn", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 列名称，首字母大写，去下划线
		}

		// 作者
		if (!PbUtils.isEmpty(templateParams.getAuthor())) {
			data.put("author", templateParams.getAuthor());
		}
		// 生成文件的路径和名称
		// String fileName = templateParams.getClassPath() + "/" +
		// templateParams.getClassName() + "Action.java";
		String fileName = templateParams.getClassPath() + "/src/com/gt/app/controller/" + templateParams.getClassName() + "Controller.java";

		// ftl文件名称
		String ftlname = "/LayUIFtl/controller.ftl";

		TemplateHelp.creatTemplate(data, ftlname, fileName, request);
	}

	/**
	 * 生成serviceImpl.java类
	 * 
	 * @param templateParams
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void creatserviceImpl(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();

		String sqlselect = "select ";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {

					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						oMap.put("columnNameBySql", map.get(key).toString());// 列名称,sql使用

						// 自动判断大小写
						if (map.get(key).toString().substring(1, 2).equals("_")) {
							oMap.put("UpUmnName", reStr);// 列名称，首字母小写，去下划线
						} else {
							oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线

						}

						if (i == (list.size() - 1)) {
							sqlselect += "t." + map.get(key).toString() +" as "+reStr;
						} else {
							sqlselect += "t." + map.get(key).toString() +" as "+reStr+ ",";
						}

					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

				}
				clList.add(oMap);// 添加到集合
			}
		}
		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("cloums", clList);// 属性
			data.put("filePath", templateParams.getFilePath());// 文件路径,定义在方法和Id前，例如sys_menuInf_MenuInfList
			data.put("action", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// action名称-第一个字母小写
			data.put("Action", PbUtils.fristStrToUpperCase(templateParams.getClassName()));// 第一个字母大写
			//data.put("pkColumn", PbUtils.fristStrToLowerCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 主键字段-第一字母小写，去掉下划线，下划线紧跟的字母大写
			//data.put("PkColumn", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 主键字段-第一字母大写，去掉下划线，下划线紧跟的字母大写
			data.put("pkColumn", PbUtils.fristStrToLowerCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 列名称，首字母大写，去下划线
			data.put("PkColumn", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 列名称，首字母大写，去下划线
			data.put("oldpkColumn", templateParams.getPkColumn());// 主键字段
			data.put("sortColumn", templateParams.getSortColumn());// 排序字段
			data.put("HiTable", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getTableName())));// hibbernate
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			data.put("date", PbUtils.getCurrentDate());// 日期
			data.put("tableName", templateParams.getTableName());// 表名称
			data.put("sqlselect", sqlselect);// sql查询语句
			data.put("PkColumnBySql", templateParams.getPkColumn());// 主键字段
			// 作者
			if (!PbUtils.isEmpty(templateParams.getAuthor())) {
				data.put("author", templateParams.getAuthor());
			}
			// 生成文件的路径和名称
			// String fileName = templateParams.getClassPath() + "/" +
			// templateParams.getClassName() + "ServiceImpl.java";
			String fileName = templateParams.getClassPath() + "/src/com/gt/app/service/impl/" + templateParams.getClassName() + "ServiceImpl.java";

			String ftlname = "";
			// ftl文件名称
			ftlname = "/LayUIFtl/ServiceImplBySql.ftl";
			TemplateHelp.creatTemplate(data, ftlname, fileName, request);
		}

	}

	/**
	 * 生成List.jsp 代码
	 * 
	 * @param templateParams
	 * @param filePath
	 *            Id名称，例如sys_menuInf_MenuInfList
	 * @param fileName
	 *            文件生成的路径 例如 c://Add.jsp
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void createListJsp(TemplateParams templateParams, String filePath, String fileName, HttpServletRequest request) throws IOException, TemplateException {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {

					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						// if (up) {
						// oMap.put("columnName",
						// PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
						// } else if (listIsLower) {
						// oMap.put("columnName",
						// PbUtils.fristAndSecondStrToLowerCase(reStr));//
						// 列名称，首字母小写,第二个字母小写，去下划线
						// } else {
						// oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						// }
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("cloums", clList);// 属性
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			data.put("filePath", filePath);// 文件路径,定义在方法和Id前，例如sys_menuInf_MenuInfList
			data.put("action", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// action名称
			data.put("Action", PbUtils.fristStrToUpperCase(templateParams.getClassName()));// 第一个字母大写
			data.put("pkColumn", templateParams.getPkColumn());// 主键字段
			data.put("sortColumn", templateParams.getSortColumn());// 排序字段
			data.put("pkColumnDel", PbUtils.fristStrToLowerCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 主键字段
			data.put("pkColumn_f", PbUtils.strRelplacetoLowerCase(templateParams.getSortColumn()));// 主键字段
			data.put("searchFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Search");// List页面的searchFilePath属性
			data.put("addFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Add");// List页面的addFilePath属性
			data.put("editFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Edit");// List页面的editFilePath属性
			TemplateHelp.creatTemplate(data, "/LayUIFtl/List.ftl", fileName, request);
		}

	}

	/**
	 * 生成JavaBean代码
	 * 
	 * @param templateParams
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void creatModel(TemplateParams templateParams, HttpServletRequest request) throws Exception {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();
		// List<Map> isNullList =
		// getIsNullAble(templateParams.getTableName().toUpperCase());

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {
					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线

						// 自动判断大小写
						if (map.get(key).toString().substring(1, 2).equals("_")) {
							oMap.put("UpUmnName", reStr);// 列名称，首字母小写，去下划线
						} else {
							oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
						}

					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

					// 列类型
					if (key.equals("COLUMNTYPE")) {
						String columnType = map.get("COLUMNTYPE").toString();// 列类型
						String columnName = map.get("COLUMNNAME").toString();// 列名称
						String IsNullAble = map.get("ISNULLABLE").toString();// 是否为空
						
						String dataLength="10";
						if (map.containsKey("DATALENGTH") && map.get("DATALENGTH") !=null) {
							 dataLength = map.get("DATALENGTH").toString();// 长度
						}
						String cloums_top = "";

						// 判断是否为主键
						if ((columnName.toUpperCase()).equals(templateParams.getPkColumn().toUpperCase())) {
							cloums_top += "@Id ";
						}
						if (columnType.contains("timestamp")) {
							oMap.put("cloumsType", "Timestamp");
						}
						if (columnType.contains("long") || columnType.contains("interval") || columnType.contains("blob") || columnType.contains("varchar") || columnType.contains("char")) {
							oMap.put("cloumsType", "String");
						}
						if (columnType.contains("decimal") || columnType.contains("float") || columnType.contains("double") || columnType.contains("integer") || columnType.contains("longtext")) {
							oMap.put("cloumsType", "Long");
						}
						if (columnType.contains("date") || columnType.contains("datetime")) {
							oMap.put("cloumsType", "Date");
							cloums_top += " @Temporal(TemporalType.TIMESTAMP) ";
						}
						if (columnType.contains("int")) {
							oMap.put("cloumsType", "Integer");
						}

						cloums_top += "@Column( name = \"" + columnName + "\" ";
						if (columnName.equals(templateParams.getPkColumn().toUpperCase())) {
							cloums_top += ", unique = true ";
						}
						
						if (IsNullAble.equals("NO")) {
							cloums_top += " ,nullable = false ";
						}

						// 判断是否唯一性约束
						// if (isNullList.contains(key.equals("COLUMNTYPE"))) {
						// cloums_top += " , unique = true ";
						// }

						// 长度
						if (!columnType.contains("decimal")) {
							cloums_top += " , length = " + dataLength + " ";
						} else {
							// NUMBER 类型才有
							cloums_top += " , precision = " + dataLength + ", scale = 0 ";
						}

						cloums_top += " )";

						oMap.put("cloums_top", cloums_top);

					}

				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			data.put("className", templateParams.getClassName());// 类名称
			data.put("cloums", clList);// 属性
			data.put("date", PbUtils.getCurrentDate());// 日期
			// 作者
			if (!PbUtils.isEmpty(templateParams.getAuthor())) {
				data.put("author", templateParams.getAuthor());
			}
			data.put("Table_NAME", templateParams.getTableName());// 表名称
			// 模板名称
			String ftlName = "/LayUIFtl/Model.ftl";

			// 生成文件的路径和名称
			// String fileName = templateParams.getClassPath() + "/" +
			// templateParams.getClassName() + ".java";
			String fileName = templateParams.getClassPath() + "/src/com/gt/model/" + templateParams.getClassName() + ".java";
			TemplateHelp.creatTemplate(data, ftlName, fileName, request);
		}
	}

	/**
	 * 创建editjsp和addJsp的公用方法
	 * 
	 * @param ftlName
	 *            例如：AddJsp.ftl
	 * @param templateParams
	 * @param filePath
	 *            Id名称，例如sys_menuInf_MenuInfList
	 * @param fileName
	 *            文件生成的路径 例如 c://Add.jsp
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void AddAndEditPubMeth(String ftlName, TemplateParams templateParams, String filePath, String fileName, HttpServletRequest request) throws IOException, TemplateException {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {

					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						oMap.put("UpUmnName", reStr);
					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

					// 设置列长度,既maxlength
					if (key.equals("COLUMNTYPE")) {
						String columnType = map.get(key).toString();

						if (columnType.contains("varchar") || columnType.contains("char")) {
							// 设置列长度,既maxlength
							if (map.get("DATALENGTH") != null) {
								oMap.put("maxlength", (Integer.parseInt(map.get("DATALENGTH").toString())));
							}

						} else {
							// num类型,长度为2
							oMap.put("maxlength", 2);
						}
					}

					// 是否必填
					if (map.get("ISNULLABLE") != null) {
						oMap.put("IsNullAble", map.get("ISNULLABLE").toString().equals("NO") ? "NO" : "YES");
					}

					// 列类型
					if (key.equals("COLUMNTYPE")) {
						oMap.put("IsDate", "NO");
						String columnType = map.get("COLUMNTYPE").toString();// 列类型
						if (columnType.contains("timestamp") || columnType.contains("date") || columnType.contains("datetime")) {
							oMap.put("IsDate", "YES");
							Map<String, Object> dateMap = new HashMap<String, Object>();
							String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
							dateMap.put("id", reStr);//ID
							datesList.add(dateMap);
						}
					}
				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("cloums", clList);// 属性
			data.put("datesList", datesList);// 时间空间集合
			data.put("action", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// action名称
			data.put("filePath", filePath);// Id名称，例如sys_menuInf_MenuInfList
			data.put("listPagePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "List");// search页面的listPagePath属性
			TemplateHelp.creatTemplate(data, ftlName, fileName, request);
		}
	}

	/**
	 * 获取列数据
	 * 
	 * @param templateParams
	 * @return
	 */
	private List<Map> getListMap(String tableName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table_name", tableName);// 表名称
		params.put("database", database);// 数据库

		String sql = "SELECT COLUMN_NAME COLUMNNAME,COLUMN_TYPE COLUMNTYPE,COLUMN_KEY COLUMNKEY ,CHARACTER_MAXIMUM_LENGTH DATALENGTH, ";
		sql += " IS_NULLABLE ISNULLABLE,COLUMN_COMMENT COLUMNCOMMENT FROM INFORMATION_SCHEMA.COLUMNS T ";
		sql += " WHERE TABLE_NAME = :table_name ";
		sql += " AND T.TABLE_SCHEMA=:database";

		List<Map> maps = findBySql(sql, params);
		return maps;
	}

	/**
	 * 新增菜单
	 */
	private void addmenu(TemplateParams templateParams, HttpSession session) {
		String menuUuid = UUID.randomUUID().toString();
		String fucntion = templateParams.getFunctionComment();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Contans.SESSION_BEAN);
		TSysRoleInf tSysRoleInf = roleInfService.getById(TSysRoleInf.class, sessionInfo.getOperInf().getRoleInfs().get(0).getRoleCd().trim());// 角色

		// 添加菜单
		TSysMenuInf tmenuInf = new TSysMenuInf();
		tmenuInf.setIconCls("fa-cubes");
		tmenuInf.setId(menuUuid);
		tmenuInf.setMenuUrl("app/" + templateParams.getClassName() + "/" + templateParams.getClassName() + "List.jsp");
		tmenuInf.setText(fucntion);
		tmenuInf.setMenuSort(countMenuInf(templateParams.getPid()));
		TSysMenuInf tfatherMenuInf = new TSysMenuInf();
		tfatherMenuInf.setId(templateParams.getPid());
		tmenuInf.setTSysMenuInf(tfatherMenuInf);
		menuInfService.save(tmenuInf);

		// 添加系统功能
		// if (!CtUtils.isEmpty(templateParams.getAdd()) &&
		// templateParams.getAdd().equals("1")) {
		addSysfunction(tmenuInf, templateParams, tSysRoleInf, "新增", "add");
		// }
		// if (!CtUtils.isEmpty(templateParams.getDelete()) &&
		// templateParams.getDelete().equals("1")) {
		addSysfunction(tmenuInf, templateParams, tSysRoleInf, "删除", "remove");
		// }
		// if (!CtUtils.isEmpty(templateParams.getUpdate()) &&
		// templateParams.getUpdate().equals("1")) {
		addSysfunction(tmenuInf, templateParams, tSysRoleInf, "修改", "modify");
		// }
		// if (!CtUtils.isEmpty(templateParams.getSelect()) &&
		// templateParams.getSelect().equals("1")) {
		addSysfunction(tmenuInf, templateParams, tSysRoleInf, "查询", "datagrid");
		// }

	}

	/**
	 * 添加系统权限
	 * 
	 * @param tmenuInf
	 * @param templateParams
	 * @param tSysRoleInf
	 * @param functionNm
	 * @param oper
	 */
	private void addSysfunction(TSysMenuInf tmenuInf, TemplateParams templateParams, TSysRoleInf tSysRoleInf, String functionNm, String oper) {
		// 添加系统功能
		TSysFunctionInf tsysInf = new TSysFunctionInf();
		tsysInf.setFunctionCd(UUID.randomUUID().toString());
		tsysInf.setFunctionNm(functionNm);
		tsysInf.setFunctionUrl(PbUtils.fristStrToLowerCase(templateParams.getClassName()) + "/" + oper + ".do");
		tsysInf.setTSysMenuInf(tmenuInf);
		sysFunctionInfService.save(tsysInf);

		// 为角色添加系统权限
		TSysRoleFunction tfunction = new TSysRoleFunction();
		tfunction.setUuid(UUID.randomUUID().toString());
		tfunction.setTSysRoleInf(tSysRoleInf);
		tfunction.setTSysFunctionInf(tsysInf);
		roleFunctionService.save(tfunction);
	}

	/**
	 * 根据URL统计,查询排序
	 * 
	 * @param menuUrl
	 * @return
	 */
	private String countMenuInf(String pid) {
		// String hql =
		// "select max(t.menuSort) menuSort from TSysMenuInf t where t.TSysMenuInf.id=:pid";
		String sql = "select max(t.menu_sort) menuSort from T_SYS_MENU_INF t where t.praent_id=:pid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		Map map = menuInfService.findBySql(sql, params).get(0);
		Integer count = 1;
		if (map != null && map.get("MENUSORT") != null) {
			count = Integer.parseInt(map.get("MENUSORT").toString()) + 1;
		}
		String countStr = DataConverter.addZeroLeft(String.valueOf(count), 3);
		return countStr;
	}

	/**
	 * 根据表查询唯一性约束
	 * 
	 * @param tableName
	 * @return
	 */
	private List<Map> getIsNullAble(String tableName) {
		// String sql =
		// "select distinct( column_name ) as columnName from user_cons_columns cu, user_constraints au where cu.constraint_name=au.constraint_name and cu.table_name=:table_name";
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("table_name", tableName);
		// return findBySql(sql, params);
		return null;
	}
}
