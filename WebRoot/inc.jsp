<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<!-- layUI -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/layui-v2/css/layui.css" media="all" />
<script type="text/javascript"src="<%=request.getContextPath()%>/plugins/layui-v2/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/public.js"></script>
<!-- 公共组件 -->
<jsp:include page="plugins/my/pbPlugins.jsp"></jsp:include>

<style>
.content {
    width:960px;
    height:2024px;
    border:1px solid #000;
    margin:0 auto;
}
.nav {
    width:100%;
    height:100px;
    margin:0 auto;
    position:fixed;
    bottom:0;
}
</style>