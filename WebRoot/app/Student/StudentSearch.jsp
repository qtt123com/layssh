<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">名字</label>
			<div class="layui-input-inline">
				<input type="text" name="name" maxlength="50" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">性别</label>
			<div class="layui-input-inline">
				<select  name="sex" id="sex" ></select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">状态</label>
			<div class="layui-input-inline">
				<select  name="state" id="state" > </select>
			</div>
		</div>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>
<script>
	layui.use([ 'form', 'layedit', 'laydate', 'jquery' ], function() {
		var form = layui.form;
		var $ = layui.jquery;
	
	   //初始化状态岗位下拉框
	   pbInitCombox($,form,'dictTypeCd=student_state','state');//student_state为数据字典类型编号
	  
	   //初始化性别下拉框
	   pbInitCombox($,form,'dictTypeCd=student_sex','sex');//student_sex为数据字典类型编号

	});
</script>


