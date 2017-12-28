<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>字典类型</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
   <div class="layui-form-item">
    <label class="layui-form-label" style="color:#F00">类型编号</label>
    <div class="layui-input-block">
      <input type="text" name="dictTypeCd"  readOnly="readOnly" lay-verify="required" maxlength="36" placeholder="请输入" autocomplete="off" class="layui-input" />
    </div>
  </div>
   <div class="layui-form-item">
    <label class="layui-form-label">类型名称</label>
    <div class="layui-input-block">
      <input type="text" name="dictTypeNm"  lay-verify="required" maxlength="100" placeholder="请输入" autocomplete="off" class="layui-input" />
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
			url : '<%=request.getContextPath()%>/dictTp/modify.do',
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

</body>
</html>
