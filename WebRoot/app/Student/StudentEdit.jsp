<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>新增</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form layui-form-pane" enctype="multipart/form-data" style="margin-top: 20px;" method="post" action="">
		<input type="hidden" name="uuid"/>
		 <!-- 列 -->
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label" style="color:#F00">名字</label>
				<div class="layui-input-block">
					<input type="text" name="name" maxlength="50" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
				
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label" style="color:#F00">出生年月</label>
				<div class="layui-input-block">
					<input type="text" class="layui-input" name="dateBirth" id="dateBirth" lay-verify="required" placeholder="请选择" />
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label" style="color:#F00">性别</label>
				<div class="layui-input-block">
					<select  name="sex" id="sex" lay-verify="required">
      				</select>	
				</div>
			</div>
		</div>
		
		 <!-- 列 -->
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label" style="color:#F00">档案附件</label>
				<div class="layui-input-block">
					 <input type="text" name="archives" lay-verify="required"  id="archives" readOnly="readOnly"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			
			<div class="layui-inline">
				<div class="layui-upload-drag" id="test10">
			 	 <i class="layui-icon"></i>
			 	 <p>点击上传<span style="color:#F00">zip|rar|7z</span>格式文件，或将文件拖拽到此处</p>
				</div>
					<button class="layui-btn layui-btn-normal" type="button" id="btn" >档案上传</button>
			</div>
		</div>
		 <!-- 列 -->
		<div class="layui-form-item">
			<label class="layui-form-label" style="color:#F00">状态</label>
			<div class="layui-input-block">
				<select  name="state" id="state" lay-verify="required">
     				</select>
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
		<div class="layui-form-item" >
			<div class="layui-input-block">
				<button class="layui-btn"  lay-submit="" lay-filter="btnSubmit">立即提交</button>
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

  //获取传参
  var data =JSON.parse(decodeURIComponent(getRequestParam().obj));
  
  //时间控件
  laydate.render({
	    elem: '#createTime'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  //时间控件
  laydate.render({
	    elem: '#updateTime'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
	
  //时间控件
  laydate.render({
	    elem: '#dateBirth'
	    ,type: 'date'
	    ,format: 'yyyy-MM-dd'
  });
  
  
  
  //拖拽上传
  upload.render({
	    elem: '#test10'
	    ,url: '<%=request.getContextPath()%>/fileUpload/upload.do'
	    ,auto: false
	    //,multiple: true
	    ,size: 60*1024 //限制文件大小，单位 KB
	    ,accept: 'file' //普通文件
   		,exts: 'zip|rar|7z' //只允许上传压缩文件
	    ,bindAction: '#btn'
	    ,done: function(data){
	    	if (data.success) {
				pubUtil.msg("文件上传成功", layer, 1, function() {
					$("#archives").val(data.msg);
				}, 500);
			} else {
				pubUtil.msg(data.msg, layer, 2, function() {

				}, 5 * 1000);
			}
	    }
	    ,error: function(index, upload){
	    	pubUtil.msg("文件上传接口异常", layer, 2, function() {
			}, 5 * 1000);
	     }
   });
  
  
  //初始化状态岗位下拉框
  pbInitCombox($,form,'dictTypeCd=student_state','state',data.state);//student_state为数据字典类型编号
  
  //初始化性别下拉框
  pbInitCombox($,form,'dictTypeCd=student_sex','sex',data.sex);//student_sex为数据字典类型编号
  
  
   //监听提交
   form.on('submit(btnSubmit)', function(data){
   // layer.msg(JSON.stringify(data.field));
   var index = layer.load(1);//开启进度条
      $.ajax({
		url : '<%=request.getContextPath()%>/student/modify.do',
					data : data.field,
					type : 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
					dataType : 'json',
					success : function(obj) {
						layer.close(index);//关闭   
						if (obj.success) {
							pubUtil.msg(obj.msg, layer, 1, function() {
								$("#close").click();
							}, 500);
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

