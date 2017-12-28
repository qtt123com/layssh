<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>

<head>
<title>用户管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<!-- layUI -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<!-- 以下为Ztree -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>

<div style="position:fixed; top:5px; left:230px;width: 100%; height: 50px;display:none;">
     <!-- 按钮组 -->
     <button class="layui-btn" id="btnSubmit" >保存</button>
     <button class="layui-btn layui-btn-normal"  id="reSet" >重置</button>
     <button class="layui-btn layui-btn-primary" id="close">关闭</button>
   </div>
<div class="zTreeDemoBackground" style="margin-top:0px;margin-left:200px">
	<ul id="sys_RoleInf_RoleInfEast_ztree" class="ztree"></ul>
</div>
</body>
<script type="text/javascript">
	layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var layer = layui.layer; //弹层
	  
	  
	//获取传参
	var data =JSON.parse(decodeURIComponent(getRequestParam().obj));
	  
	//授权功能
	function sys_RoleInf_RoleInfEast_save(roleCd) {

		var treeObj = $.fn.zTree.getZTreeObj("sys_RoleInf_RoleInfEast_ztree");
		var checkedNodes = treeObj.getCheckedNodes(true);
		var checkArray = new Array();

		var index = layer.load(1);//开启进度条

		for ( var i = 0; i < checkedNodes.length; i++) {
			if (!checkedNodes[i].getCheckStatus().half
					&& !checkedNodes[i].isParent) {
				checkArray.push(checkedNodes[i].id);
			}
		}

		var checkAppList = checkArray.join(",");

		$.ajax({
			url : '<%=request.getContextPath()%>/roleFunction/add.do',
			data : {
				functionCd : checkAppList,
				roleCd : roleCd
			},
			dataType : 'json',
			success : function(r) {

				//重新加载
				var zTreeObj = $.fn.zTree.getZTreeObj("sys_RoleInf_RoleInfEast_ztree");
				zTreeObj.destroy();


				//初始化树
				sys_RoleInf_RoleInfEast_init(roleCd);

				layer.close(index);//关闭   

				if (r.success) {
					pubUtil.msg(r.msg,layer,1,function(){
						$('button#close').click();
					},500);
					
				} else {
					pubUtil.msg(r.msg,layer,2,function(){
					},5*1000);
				}
			}
		});
	}

	var sys_RoleInf_RoleInfEast_setting = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : null
			}
		}
	};

	sys_RoleInf_RoleInfEast_init(data.roleCd);//初始化树

	//选中树
	function sys_RoleInf_RoleInfEast_checkTree(roleCd) {
		var treeObj = $.fn.zTree.getZTreeObj("sys_RoleInf_RoleInfEast_ztree");

		$.ajax({
			url : '<%=request.getContextPath()%>/roleInf/getRoleSelected.do',
			data : {
				roleCd : roleCd
			},
			type : 'POST',
			dataType : "text",
			ContentType : "application/json; charset=UTF-8",
			success : function(r) {
				var data = $.parseJSON(r);
				var rows = [];
				rows = data.rows;
				for ( var i = 0; i < rows.length; i++) {
					var node = treeObj.getNodeByParam("id", rows[i].functionCd,
							null);
					if (node) {
						treeObj.checkNode(node, true, true);
					}
				}
			},
			error : function(msg) {
				pubUtil.msg("初始化树失败",layer,2,function(){
				});
			}
		});
	}

	//初始树
	function sys_RoleInf_RoleInfEast_init(roleCd) {
		var index = layer.load(1);//开启进度条
		$.ajax({
			url : "<%=request.getContextPath()%>/roleInf/getAllTree.do",
			type : 'POST',
			dataType : "text",
			ContentType : "application/json; charset=UTF-8",
			success : function(data) {
				layer.close(index);//关闭   
				zNodes = data;
				$.fn.zTree.init($("#sys_RoleInf_RoleInfEast_ztree"),
						sys_RoleInf_RoleInfEast_setting, eval('(' + zNodes
								+ ')'));
				if (roleCd != '') {
					sys_RoleInf_RoleInfEast_checkTree(roleCd);
				}
			},
			error : function(msg) {
				layer.close(index);//关闭   
				pubUtil.msg("初始化树失败",layer,2,function(){
				});
			}
		});
	}
	
	//保存
	$('button#btnSubmit').on('click', function() {
		sys_RoleInf_RoleInfEast_save(data.roleCd);
	});
	
	//重置
	$('button#reSet').on('click', function() {
		//重新加载
		var zTreeObj = $.fn.zTree.getZTreeObj("sys_RoleInf_RoleInfEast_ztree");
		zTreeObj.destroy();

		//初始化树
		sys_RoleInf_RoleInfEast_init(data.roleCd);
	});
	
	});
</script>
</html>