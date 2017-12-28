<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>查看</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form layui-form-pane" style="margin-top: 20px;" method="post" action="">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">名字</label>
				<div class="layui-input-block">
					<input type="text" name="name" placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<input type="text" name="sexNm" placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<input type="text" name="stateNm" placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">创建时间</label>
				<div class="layui-input-block">
					<input type="text" name="createTime" placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">修改时间</label>
				<div class="layui-input-block">
					<input type="text" name="updateTime" placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		<!-- 列 -->
		<div class="layui-form-item">
			<label class="layui-form-label">档案</label>
				<div class="layui-input-block">
				<input type="text" name="archives" id="archives" placeholder="无" autocomplete="off" class="layui-input" />
			</div>
		</div>
		 <!-- 列 -->
		  <div class="layui-form-item">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <input type="text" name="note"   placeholder="请输入" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>
<script>
layui.use(['form', 'layedit', 'laydate','jquery','upload'], function(){
	  var form = layui.form;
	  var $ = layui.jquery;
	  var laydate= layui.laydate;
	  var upload = layui.upload;
  
   //监听提交
  $('button#button').on('click', function() {
   var index = layer.load(1);//开启进度条
      $.ajax({
		url : '<%=request.getContextPath()%>/fileUpload/downloadFile.do',
					data : {
						fileName:$("#archives").val()
					},
					type : 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
					dataType : 'json',
					success : function(obj) {
						layer.close(index);//关闭   
						if (obj.success) {
							window.location.href ="DownLoad.jsp?fileName="+$("#archives").val();
						} else {
							pubUtil.msg(obj.msg, layer, 2, function() {

							}, 5 * 1000);
						}
					}
				});
				return false;
			});

		});
	</script>
</body>
</html>

