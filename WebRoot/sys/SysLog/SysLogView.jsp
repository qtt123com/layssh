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
<title>菜单管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
   <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">用户编号</label>
      <div class="layui-input-inline">
        <input type="text" name="operUsrId" readOnly="readOnly" maxlength="50" lay-verify="required"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">操作日期</label>
      <div class="layui-input-inline">
        <input type="text" name="operDt"  readOnly="readOnly" autocomplete="off" class="layui-input">
      </div>    	 
    </div>
  </div>
   <!-- 列 -->
   <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">操作方法</label>
      <div class="layui-input-inline">
        <input type="text" name="operModule" readOnly="readOnly" maxlength="50" lay-verify="required"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">操作人IP</label>
      <div class="layui-input-inline">
        <input type="text" name="operIp"  readOnly="readOnly" autocomplete="off" class="layui-input">
      </div>    	 
    </div>
  </div>
  <!-- 列 -->
  <div class="layui-form-item">
    <label class="layui-form-label">操作结果</label>
    <div class="layui-input-block">
      <input type="text" name="resultMsg"  readOnly="readOnly" placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
   <!-- 列 -->
  <div class="layui-form-item">
    <label class="layui-form-label">操作关键字</label>
    <div class="layui-input-block">
      <input type="text" name="operDesc"  readOnly="readOnly" placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
   <!-- 列 -->
  <div class="layui-form-item">
    <label class="layui-form-label">操作内容</label>
    <div class="layui-input-block">
    	<textarea name="compareInf" readOnly="readOnly"  class="layui-textarea"></textarea>
    </div>
    
  </div>
 <!-- 按钮组 -->
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn layui-btn-primary" id="close">关闭</button>
		</div>
	</div>
</form>
<script>

</script>

</body>
</html>