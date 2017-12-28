<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>系统功能</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
<input type="hidden" name="functionCd" id="functionCd"/>
   <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">功能名称</label>
      <div class="layui-input-inline">
        <input type="text" name="functionNm" placeholder="请输入" maxlength="50" lay-verify="required"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">所属菜单</label>
      <div class="layui-input-inline">
        <input  type="hidden" name="menuCd" id="menuCd" >
        <input type="text" name="menuNm" lay-verify="required"  id="menuNm" readOnly="readOnly" placeholder="请输入"  autocomplete="off" class="layui-input">
      </div>    	 
      <div class="layui-input-inline">
		<button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label" style="color:#F00">请求地址</label>
    <div class="layui-input-block">
      <input type="text" name="functionUrl"  lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
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
			url : '<%=request.getContextPath()%>/sysFunctionInf/modify.do',
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
  		  content: ['../MenuInfo/MenuInfoAllTree.jsp', 'yes'], //iframe的url，no代表不显示滚动条
  		  btn: ['选择', '关闭']
  		  ,yes: function(index,layero) {
  			  var body=$(layero).find("iframe")[0].contentWindow.document;
  			  var pid = body.getElementById("id").value;
  			  var pidNm = body.getElementById("name").value;
  			  $("#menuNm").val(pidNm);
  			  $("#menuCd").val(pid);
  			  parent.layer.close(index); //关闭当前弹层
  		  }
  		  ,btn2: function(index, layero){
  			  
  		  }
  	    });
  });
  
  
});
</script>

</body>
</html>
