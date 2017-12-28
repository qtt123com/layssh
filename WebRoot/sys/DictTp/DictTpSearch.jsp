<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">类型编号</label>
			<div class="layui-input-block">
				<input type="text" name="dictTypeCd"  maxlength="36" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">类型名称</label>
			<div class="layui-input-block">
				<input type="text" name="dictTypeNm"  maxlength="100" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>