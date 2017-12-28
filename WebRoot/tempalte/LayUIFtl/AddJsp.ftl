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
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
	  
	  <div class="layui-form-item">
			 <#list cloums as c>
			<div class="layui-inline">
				<label class="layui-form-label"  <#if c.IsNullAble=="NO">style="color:#F00"</#if>>${c.columnComment}</label>
				<div class="layui-input-block">
					 <#if c.IsDate=="NO">
					<input type="text" name="${c.columnName}" maxlength="${c.maxlength}" <#if c.IsNullAble=="NO">lay-verify="required"</#if> placeholder="请输入" autocomplete="off" class="layui-input" />
					 </#if>
					  <#if c.IsDate=="YES">
					  <input type="text" class="layui-input"  name="${c.columnName}"  id="${c.columnName}" lay-verify="required"  placeholder="请选择" />
					  </#if>
				</div>
			</div>
			</#list>
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
  var laydate= layui.laydate;
  
  <#list datesList as c>
  //时间控件
  laydate.render({
	    elem: '#${c.id}'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  </#list>
  
   //监听提交
   form.on('submit(btnSubmit)', function(data){
   // layer.msg(JSON.stringify(data.field));
   var index = layer.load(1);//开启进度条
      $.ajax({
		url : '<%=request.getContextPath()%>/${action}/add.do',
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

