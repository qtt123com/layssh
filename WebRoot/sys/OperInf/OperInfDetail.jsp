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
  <title>查看详情</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <jsp:include page="../../inc.jsp"></jsp:include>
  <jsp:include page="../../plugins/my/pbPlugins.jsp"></jsp:include>
</head>
<body>
          
<form class="layui-form layui-form-pane"  style="margin-top: 20px;" method="post" action="">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">用户名</label>
      <div class="layui-input-inline">
        <input type="text" name="operCd" readOnly="readOnly"  autocomplete="off" class="layui-input" />
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">用户状态</label>
      <div class="layui-input-inline">
        	<input type="text" name="operStNm" placeholder="无" autocomplete="off" class="layui-input" />
      </div>
    </div>
     <div class="layui-inline">
      <label class="layui-form-label">真实姓名</label>
      <div class="layui-input-inline">
        <input type="text" name="operNm"  maxlength="30" placeholder="无" autocomplete="off" class="layui-input" />
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">手机号码</label>
      <div class="layui-input-inline">
        <input type="text" name="telephone" maxlength="12" placeholder="无" autocomplete="off" class="layui-input" />
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">邮箱</label>
      <div class="layui-input-inline">
        <input type="text" name="email" maxlength="100" placeholder="无" lay-verify="email" autocomplete="off" class="layui-input" />
      </div>
    </div>
     <div class="layui-inline">
      <label class="layui-form-label">岗位</label>
      <div class="layui-input-inline">
          <input type="text" name="jobNm" placeholder="无" autocomplete="off" class="layui-input" />
        
      </div>
    </div>
  </div>
 <div class="layui-form-item">
    <label class="layui-form-label">所属角色</label>
    <input type="hidden" id="roleCd" name="roleCd" />
    <div class="layui-input-block" id="roles">
    </div>
  </div>
   <div class="layui-form-item">
     <label class="layui-form-label">所属机构</label>
      <div class="layui-input-inline">
        <input  type="hidden" name="insCd" id="insCd" >
        <input type="text" name="insCdNm" id="insCdNm" readOnly="readOnly" placeholder="请选择"  autocomplete="off" class="layui-input">
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
//Demo
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


</script>