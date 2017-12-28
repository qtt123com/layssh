<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<jsp:include page="../../plugins/my/pbPlugins.jsp"></jsp:include>
</head>
<body>
	<form class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">机构编号</label>
				<div class="layui-input-inline">
					<input type="text" name="insCd" lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">机构名称</label>
				<div class="layui-input-inline">
					<input type="text" name="insNm" lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">机构级别</label>
				<div class="layui-input-inline">
					<input type="text" name="insLvlNm" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">机构类型</label>
				<div class="layui-input-inline">
					<input type="text" name="insTpNm" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">机构联系人</label>
				<div class="layui-input-inline">
					<input type="text" name="insOper" maxlength="36" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">联系电话</label>
				<div class="layui-input-inline">
					<input type="text" name="insPhone" maxlength="36" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">上级机构</label>
				<div class="layui-input-inline">
					<input type="text" name="parentInsCdNm" id="parentInsCdNm" readOnly="readOnly" placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">机构地址</label>
			<div class="layui-input-block">
				<input type="text" name="insAddres" maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" name="mark" maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>