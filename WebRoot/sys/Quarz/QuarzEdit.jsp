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
  <jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
          
<form class="layui-form layui-form-pane"  style="margin-top: 20px;" method="post" action="">
<input type="hidden" name="nid"></input>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">状态</label>
      <div class="layui-input-inline">
        <select name="state" id="state" lay-verify="required">
        </select>
      </div>
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
var data=null;
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  //获取传参
  var data =JSON.parse(decodeURIComponent(getRequestParam().obj));
  var state=data.state;
 // $("#state").find("option[text='"+data.state+"']").attr("selected",true);
 if(state==0){
	 $("#state").append('<option selected="" value="0">运行中</option>');
	 $("#state").append('<option  value="1">暂停</option>');
 }else{
	 $("#state").append('<option  value="0">运行中</option>');
	 $("#state").append('<option selected="" value="1">暂停</option>');
 }
 

  
  form.render();//重新渲染
  //form.render('select'); //刷新select选择框渲染
  
  //监听提交
  form.on('submit(btnSubmit)', function(data){
  //layer.msg(JSON.stringify(data.field));
  var index = layer.load(1);//开启进度条
      $.ajax({
		url : '<%=request.getContextPath()%>/quarz/modify.do',
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
