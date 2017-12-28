<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<#list cloums as c>
	    <div class="layui-inline">
		<label class="layui-form-label">${c.columnComment}</label>
			<div class="layui-input-inline">
			<#if c.IsDate=="NO">
			<input type="text" name="${c.columnName}" maxlength="${c.maxlength}"  placeholder="请输入" autocomplete="off" class="layui-input" />
			</#if>
			<#if c.IsDate=="YES">
			<input type="text" class="layui-input"  name="${c.columnName}"  id="${c.columnName}" lay-verify="required"  placeholder="请选择" />
			</#if>
		</div>
	</div>
		</#list>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
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
  

		});
</script>


