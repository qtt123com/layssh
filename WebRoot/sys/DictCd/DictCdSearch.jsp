<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">字典代码</label>
			<div class="layui-input-inline">
				<input type="text" name="dictCd" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">字典名称</label>
			<div class="layui-input-inline">
				<input type="text" name="dictNm" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">字典类型</label>
			<div class="layui-input-inline">
				<select id="dictTypeCd" name="dictTypeCd"  lay-verify="required">
				</select>			
			</div>
		</div>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>
<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  
  //初始化下拉框
  pbInitComCombox($,form,'dictTp/getList.do','dictTypeCd','dictTypeCd','dictTypeNm');

});


</script>