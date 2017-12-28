<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>代码生成器</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
		<input type="hidden" name="pattern"  value="1"/>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"  style="color:#F00">数据库表</label>
				<div class="layui-input-block">
					<select name="tableName" lay-filter="tableName" lay-verify="required" id="tableName">
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  style="color:#F00">表主键</label>
				<div class="layui-input-block">
					<select name="pkColumn"  lay-verify="required" id="pkColumn">
					</select>
				</div>
			</div>
		</div>
		 <div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"  style="color:#F00">添加菜单</label>
				<div class="layui-input-block">
					<select name="addmenu"  id="addmenu" lay-verify="required" >
						<option value="1" selected="">是</option>
						<option value="0">否</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
		      <label class="layui-form-label">上一级菜单</label>
		      <div class="layui-input-inline">
		        <input  type="hidden" name="pid" id="pidSelect" >
		        <input type="text" name="pidNm" id="pidNm" readOnly="readOnly" placeholder="请输入"  autocomplete="off" class="layui-input">
		      </div>    	 
		      <div class="layui-input-inline">
				<button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
		      </div>
		    </div>
		</div> 
		<div class="layui-form-item">
			<div style="display:none;">
			<select name="sortColumn"  lay-verify="required" id="sortColumn">
					</select>
			</div>
			<label class="layui-form-label"  style="color:#F00">Java类名称</label>
				<div class="layui-input-block">
					<input type="text" name="className" maxlength="100" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
		 </div>
		 
		 <div class="layui-form-item">
			<label class="layui-form-label"  style="color:#F00">功能说明</label>
		    <div class="layui-input-block">
				<input type="text" name="functionComment" maxlength="100" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
			 </div>
		 </div>
		  <div class="layui-form-item">
			<label class="layui-form-label"  style="color:#F00">工程根路径</label>
		    <div class="layui-input-block">
				<input type="text" name="classPath" value="D:/workspace/myeclise/layssh/" maxlength="200" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
			 </div>
		 </div>
		<div class="layui-form-item">
			<label class="layui-form-label"  style="color:#F00">作者</label>
		    <div class="layui-input-block">
				<input type="text" name="author" value="herun" maxlength="100" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
			 </div>
		 </div>
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  
  //初始化下拉框
  pbInitComCombox($,form,'codeGenerator/getTablesList.do','tableName','tableName','tableName');
  
  //监听数据库表下拉框
  form.on('select(tableName)', function (data) {
	  var tableName=data.value;
	  //字段集合
	  var url='codeGenerator/getFieldList.do?tableName='+tableName;
	  $.ajax({
			url : '<%=request.getContextPath()%>/'+url,
			type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
			dataType : 'json',
			async: true,
			success : function(data) {
				//layer.msg(JSON.stringify(data));
				
				//表主键
				$("#pkColumn").html("");
				$("#pkColumn").append('<option selected="" value="">请选择</option>');
				for(var i=0;i<data.length;i++){
					$("#pkColumn").append('<option selected="" value='+data[i].columnName+'>'+data[i].columnName+'</option>');
				}
				
				 //排序字段
			     $("#sortColumn").html("");
				 var sortUrl='codeGenerator/getFieldList.do?tableName='+tableName;
				 $.ajax({
						url : '<%=request.getContextPath()%>/'+sortUrl,
						type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
						dataType : 'json',
						async: true,
						success : function(data) {
							$("#sortColumn").html("");
							$("#sortColumn").append('<option selected="" value="">请选择</option>');
							for(var i=0;i<data.length;i++){
								$("#sortColumn").append('<option selected="" value='+data[i].columnName+'>'+data[i].columnName+'</option>');
							}
							form.render('select');//重新渲染
						}
				 });
				form.render('select');//重新渲染
			}
			
		});
	  
  });
  
   //监听提交
   form.on('submit(btnSubmit)', function(data){
    // layer.msg(JSON.stringify(data.field));
   var addmenu = $("#addmenu").val();
   var pidSelect = $("#pidSelect").val();
   if (addmenu == "1" && pidSelect == "") {
    pubUtil.msg("请选择上一级菜单", layer, 2, function() {
		}, 5 * 1000);
      return false;
	}
   var index = layer.load(1);//开启进度条
    $.ajax({
	url : '<%=request.getContextPath()%>/codeGenerator/createCode.do',
			data : data.field,
			type : 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
			dataType : 'json',
			success : function(obj) {
				layer.close(index);//关闭   
				if (obj.success) {
					pubUtil.msg(obj.msg, layer, 1, function() {
						$("#close").click();
					}, 500);
				} else {
					pubUtil.msg(obj.msg, layer, 2, function() {

					}, 5 * 1000);
				}
			}
		});
		return false;
	});
   
   //按钮的点击事件
   $('button#treeSelect').on('click', function() {
   	parent.layer.open({
   	      type: 2,
   	      title : '选择<span style="color:red">[请单击选中]</span>',
   	      shadeClose: false,//点击遮罩关闭
   	      anim: public_anim,
   	      btnAlign: 'c',
   	      shade: public_shade,//是否有遮罩，可以设置成false
   	      maxmin: true, //开启最大化最小化按钮
   	      area: ['600px', '300px'],
   	      boolean:true,
   		  content: ['sys/MenuInfo/MenuInfoAllTree.jsp', 'yes'], //iframe的url，no代表不显示滚动条
   		  btn: ['选择', '关闭']
   		  ,yes: function(index,layero) {
   			  var body=$(layero).find("iframe")[0].contentWindow.document;
   			  var pid = body.getElementById("id").value;
   			  var pidNm = body.getElementById("name").value;
   			  $("#pidNm").val(pidNm);
   			  $("#pidSelect").val(pid);
   			  parent.layer.close(index); //关闭当前弹层
   		  }
   		  ,btn2: function(index, layero){
   			  
   		  }
   	    });
   });

});
	</script>