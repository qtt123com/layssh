<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">功能名称</label>
			<div class="layui-input-inline">
				<input type="text" name="functionNm" placeholder="请输入" maxlength="50" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">所属菜单</label>
			<div class="layui-input-inline">
				<input type="hidden" name="menuCd" id="menuCd"> 
				<input type="text" name="menuNm" id="menuNm" readOnly="readOnly" placeholder="请输入" autocomplete="off" class="layui-input" />
			</div>
			<div class="layui-input-inline">
				<button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
			</div>
		</div>
		<div class="layui-form-item">
	    <label class="layui-form-label">请求地址</label>
	    <div class="layui-input-block">
	      <input type="text" name="functionUrl"   placeholder="请输入" autocomplete="off" class="layui-input" />
	    </div>
  </div>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>
<script>
layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var layer = layui.layer; //弹层
//请选择按钮
$('button#treeSelect').on('click', function() {
	layer.open({
		type : 2,
		title : '选择<span style="color:red">[请单击选中]</span>',
		shadeClose : false,//点击遮罩关闭
		anim : public_anim,
		btnAlign : 'c',
		shade : public_shade,//是否有遮罩，可以设置成false
		maxmin : true, //开启最大化最小化按钮
		area : [ '600px', '300px' ],
		//area: ['100%', '100%'],
		boolean : true,
		content : [ '../MenuInfo/MenuInfoAllTree.jsp', 'yes' ], //iframe的url，no代表不显示滚动条
		btn : [ '选择', '关闭' ],
		yes : function(index, layero) {
			var body = $(layero).find("iframe")[0].contentWindow.document;
			var pid = body.getElementById("id").value;
			var pidNm = body.getElementById("name").value;
			$("#menuNm").val(pidNm);
			$("#menuCd").val(pid);
			layer.close(index); //关闭当前弹层
		},
		btn2 : function(index, layero) {

		}
	});
	});
});
</script>