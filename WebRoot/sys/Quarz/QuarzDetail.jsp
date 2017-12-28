<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>查看</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label" >定时器名称</label>
				<div class="layui-input-inline">
					<input type="text" name="jobName" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >所属组别</label>
				<div class="layui-input-inline">
					<input type="text" name="jobGroupNm" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" >类路径</label>
			<div class="layui-input-block">
				<input type="text" name="classPath"  maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" >表达式</label>
			<div class="layui-input-block">
				<input type="text" name="cronStr"  maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" name="mark" maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>