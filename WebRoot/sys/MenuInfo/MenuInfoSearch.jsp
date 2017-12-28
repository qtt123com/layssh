<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">菜单名称</label>
			<div class="layui-input-inline">
				<input type="text" name="text" placeholder="请输入" maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">上一级菜单</label>
			<div class="layui-input-inline">
				<input type="hidden" name="pid" id="pid" /> 
				<input type="text" name="pidNm" id="pidNm" readOnly="readOnly" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
			<div class="layui-input-inline">
				<button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">图标</label>
			<div class="layui-input-inline">
				<input type="text" name="iconCls" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">URL</label>
			<div class="layui-input-block">
				<input type="text" name="menuUrl" placeholder="请输入" maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
	
</form>


