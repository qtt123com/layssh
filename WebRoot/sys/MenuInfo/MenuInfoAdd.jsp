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
      <label class="layui-form-label" style="color:#F00">菜单名称</label>
      <div class="layui-input-inline">
        <input type="text" name="text" placeholder="请输入" maxlength="50" lay-verify="required"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">上一级菜单</label>
      <div class="layui-input-inline">
        <input  type="hidden" name="pid" id="pid" >
        <input type="text" name="pidNm" id="pidNm" readOnly="readOnly" placeholder="请输入"  autocomplete="off" class="layui-input">
      </div>    	 
      <div class="layui-input-inline">
		<button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
      </div>
    </div>
  </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">菜单排序</label>
      <div class="layui-input-inline">
        <input type="tel" name="menuSort" placeholder="请输入" maxlength="3" lay-verify="required"   autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">图标</label>
      <div class="layui-input-inline">
        <input type="text" name="iconCls" value="fa-cubes" placeholder="请输入" lay-verify="required" autocomplete="off" class="layui-input">
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
			url : '<%=request.getContextPath()%>/menuInf/add.do',
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

//按钮的点击事件
$('button#treeSelect').on('click', function() {
	parent.layer.open({
	      type: 2,
	      title : '选择<span style="color:red">[请单击选中]</span>',
	      shadeClose: false,//点击遮罩关闭
	      anim: public_anim,
	      btnAlign: 'c',
	      shade: public_shade,//是否有遮罩，可以设置成false
	      maxmin: true, //开启最大化最小化按钮
	      area: ['600px', '300px'],
	      //area: ['100%', '100%'],
	      boolean:true,
		  content: ['MenuInfoTree.jsp', 'yes'], //iframe的url，no代表不显示滚动条
		  btn: ['选择', '关闭']
		  ,yes: function(index,layero) {
			  var body=$(layero).find("iframe")[0].contentWindow.document;
			  var pid = body.getElementById("id").value;
			  var pidNm = body.getElementById("name").value;
			  $("#pidNm").val(pidNm);
			  $("#pid").val(pid);
			  parent.layer.close(index); //关闭当前弹层
		  }
		  ,btn2: function(index, layero){
			  
		  }
	    });
});
</script>

</body>
</html>