<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
      <label class="layui-form-label">功能名称</label>
      <div class="layui-input-inline">
        <input type="text" name="functionNm" placeholder="请输入" maxlength="50"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">所属菜单</label>
      <div class="layui-input-inline">
        <input type="text" name="menuNm" placeholder="请输入"  autocomplete="off" class="layui-input">
      </div>    	 
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">请求地址</label>
    <div class="layui-input-block">
      <input type="text" name="functionUrl"  placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
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
