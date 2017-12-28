<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.gt.pageModel.SessionInfo"%>
<%@page import="com.gt.utils.Contans"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String userNm = "";

	if (session.getAttribute(Contans.SESSION_BEAN) != null) {
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute(Contans.SESSION_BEAN);
		if (sessionInfo.getOperInf() != null) {
			if (sessionInfo.getOperInf().getOperCd() != null
					&& sessionInfo.getOperInf().getOperCd().length() > 0) {
				userNm = sessionInfo.getOperInf().getOperCd();
			}
		}
	}
%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">
<title>LaySSH开发框架</title>
<jsp:include page="inc.jsp"></jsp:include>
<link rel="stylesheet" href="css/global.css" media="all">
<link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
</head>

<body>
	<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
		<div class="layui-header header header-demo">
			<div class="layui-main">
				<div class="admin-login-box">
					<a class="logo" style="left: 0;" href="#"> <span
						style="font-size: 22px;">LaySSH开发框架</span>
					</a>
					<div class="admin-side-toggle">
						<i class="fa fa-bars" aria-hidden="true"></i>
					</div>
					<div class="admin-side-full">
						<i class="fa fa-life-bouy" aria-hidden="true"></i>
					</div>
				</div>
				<ul class="layui-nav admin-header-item">

					<li class="layui-nav-item"><a href="javascript:;"
						class="admin-header-user"> <img src="images/0.jpg" /> <span id="main_user"><%=userNm%></span>
					</a>
						<dl class="layui-nav-child">
							<dd id="changePwd">
								<a href="javascript:;"><i class="fa fa-gear"
									aria-hidden="true"></i> 修改密码</a>
							</dd>
							<dd id="sysout">
								<a href="#"><i class="fa fa-sign-out"
									aria-hidden="true"></i> 注销</a>
							</dd>
						</dl></li>
				</ul>
			</div>
		</div>
		<div class="layui-side layui-bg-black" id="admin-side">
			<div class="layui-side-scroll" id="admin-navbar-side"
				lay-filter="side"></div>
		</div>
		<div class="layui-body"
			style="bottom: 0;border-left: solid 2px #1AA094;" id="admin-body">
			<div class="layui-tab admin-nav-card layui-tab-brief"
				lay-filter="admin-tab">
				<ul class="layui-tab-title">
					<li class="layui-this"><i class="fa fa-dashboard"
						aria-hidden="true"></i> <cite>首页</cite></li>
				</ul>
				<div class="layui-tab-content"
					style="min-height: 150px; padding: 5px 0 0 0;">
					<div class="layui-tab-item layui-show">
						<iframe src="wecome.jsp"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-footer footer footer-demo" id="admin-footer">
				<div class="layui-main">
					<p>
						www.layssh.com
					</p>
				</div>
			</div>
			<div class="site-tree-mobile layui-hide">
				<i class="layui-icon">&#xe602;</i>
			</div>
			<div class="site-mobile-shade"></div>
			</div>
		<script type="text/javascript">
		if("<%=userNm%>" == "") {
			window.location.href = "operInf/login.do";
		}
		var navs;
		$.ajax({
			url : '<%=request.getContextPath()%>/menuInf/getTreeByRole.do',
			dataType : 'json',
			async: false,
			success : function(data) {
				navs=data;
			}
		});
		</script>
		<script src="js/index.js"></script>
		<script>
			layui.use('layer', function() {
				var $ = layui.jquery, layer = layui.layer;
				var that = this; 
				
				//系统退出
				$('#sysout').on('click', function() {
					layer.confirm('是否退出系统？', {
					  btn: ['退出','取消'] //按钮
					}, function(){
						location.href = '${pageContext.request.contextPath}/operInf/logout.do';
					}, function(){
					  
					});
				});
				
				//密码修改
				$('#changePwd').on('click', function() {
				  layer.open({
				      type: 2,
				      title: '密码修改',
				      shadeClose: false,//点击遮罩关闭
				      anim: 3,
				      btnAlign: 'c',
				      shade: 0.3,//是否有遮罩，可以设置成false
				      maxmin: true, //开启最大化最小化按钮
				      area: ['550px', '350px'],
				      boolean:true,
					  content: ['sys/OperInf/OperInfChangePwd.jsp', 'yes'], //iframe的url，no代表不显示滚动条
					  success: function(layero, lockIndex) {
						var body = layer.getChildFrame('body', lockIndex);
						//绑定解锁按钮的点击事件
						body.find('button#close').on('click', function() {
							layer.close(lockIndex);
						});
					  }
				    });
				});
			});
		</script>
</body>

</html>