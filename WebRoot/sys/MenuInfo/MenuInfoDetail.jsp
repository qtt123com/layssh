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
<title>查看</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
   <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">菜单名称</label>
      <div class="layui-input-inline">
        <input type="text" name="text" placeholder="请输入" maxlength="50" lay-verify="required"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">上一级菜单</label>
      <div class="layui-input-inline">
        <input type="text" name="pidNm" id="pidNm" readOnly="readOnly" placeholder="请输入"  autocomplete="off" class="layui-input">
      </div>    	 
    </div>
  </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">菜单排序</label>
      <div class="layui-input-inline">
        <input type="tel" name="menuSort" placeholder="请输入" maxlength="3" lay-verify="required"   autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">图标</label>
      <div class="layui-input-inline">
        <input type="text" name="iconCls" placeholder="请输入" lay-verify="required" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">URL</label>
    <div class="layui-input-block">
      <input type="text" name="menuUrl"  placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
  <!-- 按钮组 -->
  <div class="layui-form-item">
    <div class="layui-input-block">
	    <button class="layui-btn layui-btn-primary" id="close">关闭</button>
	    </div>
  </div>
</form>
</html>