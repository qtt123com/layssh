<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-inline">
				<input type="text" name="operCd" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">真实姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="operNm" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="telephone" placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>
