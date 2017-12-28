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
<title>新增</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"  style="color:#F00">字典代码</label>
				<div class="layui-input-block">
					<input type="text" name="dictCd" lay-verify="required" maxlength="36" placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  style="color:#F00">字典名称</label>
				<div class="layui-input-block">
					<input type="text" name="dictNm" lay-verify="required" maxlength="100" placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
	    <div class="layui-form-item">
				<label class="layui-form-label"  style="color:#F00">字典类型</label>
			    <div class="layui-input-block">
					<select id="select" name="dictTypeCd" lay-filter="select" lay-verify="required">
					</select>			   
				 </div>
		</div>
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>
	<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  
  //初始化下拉框
  pbInitComCombox($,form,'dictTp/getList.do','select','dictTypeCd','dictTypeNm');
  
   //监听提交
   form.on('submit(btnSubmit)', function(data){
   // layer.msg(JSON.stringify(data.field));
   var index = layer.load(1);//开启进度条
      $.ajax({
		url : '<%=request.getContextPath()%>/dictCd/add.do',
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

		});
	</script>
</body>
</html>
