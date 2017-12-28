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
<title>查看</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">

		<div class="layui-form-item">
			 <#list cloums as c>
			<div class="layui-inline">
				<label class="layui-form-label" >${c.columnComment}</label>
				<div class="layui-input-block">
					<input type="text" name="${c.columnName}"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			</#list>
		</div>
	  
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

