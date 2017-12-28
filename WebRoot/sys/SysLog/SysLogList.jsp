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
<title>日志管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<div class="layui-tab">
  
</div>

<div class="layui-tab">
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
	  <jsp:include page="SysLogSearch.jsp"></jsp:include>
    </div>
  </div>
</div>
<table class="layui-hide" id="table" lay-filter="table" ></table> 
<script>
layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var layer = layui.layer; //弹层
	  var laydate = layui.laydate; //日期
	  var laypage = layui.laypage; //分页
	  var table = layui.table; //表格
	  var carousel = layui.carousel; //轮播
	  var upload = layui.upload; //上传
	  var element = layui.element; //元素操作
	  
	  
	  //时间控件
	  laydate.render({
		    elem: '#date1'
		    ,type: 'datetime'
		    ,format: 'yyyy-MM-dd HH:mm:dd'
	  });
	  laydate.render({
		    elem: '#date2'
		    ,type: 'datetime'
		    ,format: 'yyyy-MM-dd HH:mm:dd'
	  });
	  
	  var index = layer.load(1);//开启进度条
	  //绑定table
	  table.render({
		  elem: '#table' ,//table id
		  url: '<%=request.getContextPath()%>/sysLog/datagrid.do',
		  method: 'POST', //方式
		  page: true,//是否开启分页
		  limits: [10,20,30,60,90,100],
		  limit: 20, //默认采用20
		  width: 'auto',
		  height:'auto',
	      even: true, //开启隔行背景
	      id: 'searchID',
	      done: function(res, curr, count){
				//加载后回调
				layer.close(index);//关闭   
				noAuthTip(res);//无权限提示
			},
		  cols: [[ //标题栏
	            {field: 'operUsrId', title: '用户编号',align:'center', width: 150},
	            {field:'operDt',  title: '操作日期', align:'center',width:200, sort: true,templet: '#operDtTpl'},
	            {field:'operMethod', title: '操作方法',align:'center', width:150},
	            {field:'operModule', title: '操作模块',align:'center', width:150},
	            {field:'operIp', title: '操作人IP地址', align:'center',width:150, sort: true},
	            {field:'resultMsg', title: '操作结果', align:'center',width:150},
	            {fixed: 'right', width:160,  title: '操作' ,align:'center', toolbar: '#toobar'}
	          ]]
		});
	  

		//监听工具条
		table.on('tool(table)', function(obj){ //注：tool是工具条事件名，table是table原始容器的属性 lay-filter="对应的值"
		  var data = obj.data //获得当前行数据
		  ,layEvent = obj.event; //获得 lay-event 对应的值
		  if(layEvent === 'detail'){
			  openView(data);
		  }
		});
	  
		//打开查看按钮
		function openView(data){
			layer.open({
			      type: 2,
			      title: '查看',
			      shadeClose: false,//点击遮罩关闭
			      anim: public_anim,
			      btnAlign: 'c',
			      shade: public_shade,//是否有遮罩，可以设置成false
			      maxmin: true, //开启最大化最小化按钮
			      //area: ['700px', '400px'],
			      area: ['100%', '100%'],
			      boolean:true,
				  content: ['SysLogView.jsp', 'yes'], //iframe的url，no代表不显示滚动条
				  success: function(layero, lockIndex) {
					var body = layer.getChildFrame('body', lockIndex);
					//绑定解锁按钮的点击事件
					body.find('button#close').on('click', function() {
						layer.close(lockIndex);
						//location.reload();//刷新
					});
					
					pubUtil.load(body,data);//填充表单
				  }
			    });
		}
		
		
		//查询按钮
	   $('#btnSearch').on('click', function() {
		   index = layer.load(1);//开启进度条  
		   var searchform=pubUtil.serializeObject($("#searchform"));//查询页面表单ID
		   //alert(JSON.stringify(searchform));
		   table.reload('searchID', {
			   where: searchform
			 });
	   });
		
	   //重置按钮 
		$('#btnRetSet').on('click', function() {
			 index = layer.load(1);//开启进度条  
			 table.reload('searchID', {
				 where: ""
			});
		});
		
	});
	
	
</script>
<script type="text/html" id="toobar">
  <a class="llayui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
</script>
<script type="text/html" id="operDtTpl">
{{#  if(d.operDt.length == 14){ }}
   {{ d.operDt.substring(0, 4)+ '-' + d.operDt.substring(4, 6)+'-' + d.operDt.substring(6, 8)+' ' + d.operDt.substring(8, 10)+ ':'+d.operDt.substring(10, 12)+':' +d.operDt.substring(12, 14)
   }}
 {{#  } }}
</script>
</body>
</html>