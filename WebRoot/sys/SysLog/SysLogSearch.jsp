<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">用户编号</label>
			<div class="layui-input-inline">
				<input type="text" name="operUsrId" placeholder="请输入" maxlength="50" lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">起始日期</label>
			<div class="layui-input-inline">
				<input type="text" class="layui-input" name="starOperDt" id="date1" placeholder="请选择">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">截止日期</label>
			<div class="layui-input-inline">
				<input type="text" class="layui-input" name="endOperDt" id="date2" placeholder="请选择">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">操作方法</label>
			<div class="layui-input-inline">
				<input type="text" name="operMethod" placeholder="请输入" maxlength="50" lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">操作模块</label>
			<div class="layui-input-inline">
				<input type="text" name="operModule" placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">操作人IP</label>
			<div class="layui-input-inline">
				<input type="text" name="operIp" placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>