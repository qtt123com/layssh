<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">角色名</label>
			<div class="layui-input-inline">
				<input type="text" name=roleNm placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
		<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
	</div>
</form>