<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>

<head>
<title>菜单管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<!-- 按钮组 -->
  <div class="layui-elem-quote" >
  <div class="layui-form-item">
   <div class="layui-input-block">
      <label class="layui-form-label">已选菜单</label>
      <div class="layui-input-inline">
        <input type="hidden" name="id" id="id"/>
        <input type="text" name="name" id="name" readOnly="readOnly"   autocomplete="off" class="layui-input" />
    </div>
  </div>
 </div>
 </div>
 
  <ul id="tree" style="margin-left:150px"></ul>
	
	<script>
		layui.use([ 'tree', 'layer' ], function() {
			var layer = layui.layer, $ = layui.jquery;
			var nodes;
			$.ajax({
				url : '<%=request.getContextPath()%>/menuInf/getLayUItree.do',
				dataType : 'json',
				async: false,
				success : function(data) {
					nodes=data;
				}
			});
			
			layui.tree({
				elem : '#tree' //指定元素
				,
				target : '_blank' //是否新选项卡打开（比如节点返回href才有效）
				,
				click : function(item) { //点击节点回调
					$("#id").val( item.id);
					$("#name").val(item.name);
				},
				nodes :nodes
			});
			
		});
	</script>

</body>
</html>