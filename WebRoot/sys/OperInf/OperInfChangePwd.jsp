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
<title>修改密码</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form class="layui-form layui-form-pane"  style="margin-top: 20px;" method="post" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">原密码</label>
    <div class="layui-input-block">
      <input type="password" name="oldOperPwd"  lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">新密码</label>
    <div class="layui-input-block">
      <input type="password" name="operPwd" id="operPwd" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">确认密码</label>
    <div class="layui-input-block">
      <input type="password" name="reOperPwd" id="reOperPwd" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
   <!-- 按钮组 -->
   <div class="layui-input-block">
     <button class="layui-btn" lay-submit="" lay-filter="btnSubmit" >立即提交</button>
     <button type="reset" class="layui-btn layui-btn-primary" >重置</button>
     <button class="layui-btn layui-btn-primary" id="close">关闭</button>
    </div>
 
</form>
<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
	  var form = layui.form;
	  var $ = layui.jquery;

  
  //监听提交
  form.on('submit(btnSubmit)', function(data){
	if($("#operPwd").val() !=$("#reOperPwd").val()){
		pubUtil.msg('前后密码不一致',layer,2,function(){
		});
		
		return false;
	}
	var index = layer.load(1);//开启进度条
    $.ajax({
			url : '<%=request.getContextPath()%>/operInf/changepwd.do',
			data :data.field,
			type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
			dataType : 'json',
			success : function(obj) {
				layer.close(index);//关闭   
				if (obj.success) {
					pubUtil.msg(obj.msg,layer,1,function(){
						$("#close").click();
					});
				} else {
					pubUtil.msg(obj.msg,layer,2,function(){
						
					});
				}
			}
		});
    return false;
  });
  
  
});
</script>

</body>
</html>