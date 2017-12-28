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
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">用户名</label>
      <div class="layui-input-inline">
        <input type="text" name="operCd" readOnly="readOnly"  autocomplete="off" class="layui-input" />
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">用户状态</label>
      <div class="layui-input-inline">
        <select name="operSt" id="operSt">
        </select>
      </div>
    </div>
     <div class="layui-inline">
      <label class="layui-form-label">真实姓名</label>
      <div class="layui-input-inline">
        <input type="text" name="operNm"  maxlength="30" autocomplete="off" class="layui-input" />
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">手机号码</label>
      <div class="layui-input-inline">
        <input type="text" name="telephone" maxlength="12" autocomplete="off" class="layui-input" />
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">邮箱</label>
      <div class="layui-input-inline">
        <input type="text" name="email" maxlength="100"  lay-verify="email" autocomplete="off" class="layui-input" />
      </div>
    </div>
     <div class="layui-inline">
      <label class="layui-form-label" style="color:#F00">岗位</label>
      <div class="layui-input-inline">
         <select name="job" id="job">
        </select>
      </div>
    </div>
  </div>
 <div class="layui-form-item">
    <label class="layui-form-label" style="color:#F00">所属角色</label>
    <input type="hidden" id="roleCd" name="roleCd" />
    <div class="layui-input-block" id="roles">
    </div>
  </div>
   <div class="layui-form-item">
     <label class="layui-form-label" style="color:#F00">所属机构</label>
      <div class="layui-input-inline">
        <input  type="hidden" name="insCd" id="insCd" >
        <input type="text" name="insCdNm" id="insCdNm" lay-verify="required" readOnly="readOnly" placeholder="请选择"  autocomplete="off" class="layui-input">
      </div>    	 
      <div class="layui-input-inline">
		<button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
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
  
  //获取传参
  var data =JSON.parse(decodeURIComponent(getRequestParam().obj));
	
  //初始化岗位下拉框
  pbInitCombox($,form,'dictTypeCd=job_tp','job',data.job);
  
  //初始化用户状态下拉框
  pbInitCombox($,form,'dictTypeCd=oper_st','operSt',data.operSt);
  
  //动态添加角色
  var roleCd=data.roleCd;
  var roleCdArray=roleCd.split(",");
  
  //获取所有的角色
  $.ajax({
		url : '<%=request.getContextPath()%>/roleInf/getList.do',
		type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
		dataType : 'json',
		async: false,
		success : function(data) {
			for(var i=0;i<data.length;i++){
				if(isExist(roleCdArray,data[i].roleCd)){
					$("#roles").append('<input type="checkbox" value="'+data[i].roleCd+'" name="roleCdCkb" title="'+data[i].roleNm+'" checked="">');
				}else{
					$("#roles").append('<input type="checkbox" value="'+data[i].roleCd+'" name="roleCdCkb" title="'+data[i].roleNm+'" >');

				}
			}
			form.render();//重新渲染
		}
		});
  
    //监听提交
    form.on('submit(btnSubmit)', function(data){
	    //layer.msg(JSON.stringify(data.field));
	    
	    //遍历角色
	    var roleCdArray = new Array();
	    $("input[name='roleCdCkb']").each(function(){    
	        if($(this).is(":checked"))    {    
	        	roleCdArray.push($(this).val());  
	        }    
	    });    
	    if(roleCdArray.length==0){
	    	pubUtil.msg('请至少选择一种角色',layer,2,function(){
			});
	    	return false;
	    }
	    var roleCdStr="";
	    for(var i=0;i<roleCdArray.length;i++){
	    	if(i==(roleCdArray.length-1)){
	    		roleCdStr+=roleCdArray[i];
	    	}else{
	    		roleCdStr+=roleCdArray[i]+",";
	    	}
	    }
	    data.field.roleCd=roleCdStr;
	    //layer.msg(JSON.stringify(data.field));
	   
	   var index = layer.load(1);//开启进度条
       $.ajax({
			url : '<%=request.getContextPath()%>/operInf/modify.do',
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

//是否存在数组里面
function isExist(roleCdArray,roleCd){
	for(var i = 0;i < roleCdArray.length; i++) {
		 if(roleCd==roleCdArray[i]){
			 return true;
		 }
	  }
	return false;
}

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
		  content: ['InsInfoTree.jsp', 'yes'], //iframe的url，no代表不显示滚动条
		  btn: ['选择', '关闭']
		  ,yes: function(index,layero) {
			  var body=$(layero).find("iframe")[0].contentWindow.document;
			  var pid = body.getElementById("id").value;
			  var pidNm = body.getElementById("name").value;
			  $("#insCdNm").val(pidNm);
			  $("#insCd").val(pid);
			  parent.layer.close(index); //关闭当前弹层
		  }
		  ,btn2: function(index, layero){
			  
		  }
	    });
});


</script>