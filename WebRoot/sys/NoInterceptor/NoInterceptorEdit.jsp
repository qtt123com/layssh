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
  <title>修改</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
          
<form class="layui-form layui-form-pane"  style="margin-top: 20px;" method="post" action="">
	<input type="hidden" name="uuid">
   <div class="layui-form-item">
		<label class="layui-form-label" style="color:#F00">URL</label>
		<div class="layui-input-block">
			<input type="text" name="functionUrl" lay-verify="required" maxlength="200" autocomplete="off" class="layui-input" />
		</div>
	</div>
	
    <div class="layui-form-item">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<input type="text" name="note"  maxlength="200"  autocomplete="off" class="layui-input" />
		</div>
	</div>

   <!-- 按钮组 -->
  <div class="layui-form-item">
   <div class="layui-input-block">
     <button class="layui-btn" lay-submit="" lay-filter="btnSubmit" >立即提交</button>
     <button class="layui-btn layui-btn-primary" id="close">关闭</button>
     </div>
 </div>
 
</form>
 
<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  
    //监听提交
    form.on('submit(btnSubmit)', function(data){
    	 var index = layer.load(1);//开启进度条
         $.ajax({
  			url : '<%=request.getContextPath()%>/noInterceptor/modify.do',
  			data :data.field,
  			type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
  			dataType : 'json',
  			success : function(obj) {
  				layer.close(index);//关闭   
  				if (obj.success) {
  					pubUtil.msg(obj.msg,layer,1,function(){
  						$("#close").click();
  					},500);
  				} else {
  					pubUtil.msg(obj.msg,layer,2,function(){
  						
  					},5*1000);
  				}
  			}
  		});
    	 
         return false;
    });
    
});
</script>