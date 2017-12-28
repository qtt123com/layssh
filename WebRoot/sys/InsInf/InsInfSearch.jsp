<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">机构名称</label>
			<div class="layui-input-inline">
				<input type="text" name="insNm" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">机构级别</label>
			<div class="layui-input-inline">
				<select name="insLvl" id="search_insLvl">
				</select>
			</div>
		</div>
	</div>
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>
<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  
  //初始化机构级别下拉框
  pbInitCombox($,form,'dictTypeCd=ins_lvl','search_insLvl');

});

</script>