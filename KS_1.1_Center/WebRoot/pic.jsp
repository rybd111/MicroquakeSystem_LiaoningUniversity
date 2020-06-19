<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>矿山组网示意图</title>
    
	<link rel="stylesheet" type="text/css" href="css/search.css">
	<link rel="stylesheet" type="text/css" href="css/head.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

  </head>
  
  <body>
    <div class="head">
        <a href="index.jsp"><img src="image/152569275640874.png"></a>
        <a href="jsp/MyJsp3.jsp">实时数据</a>
        <a href="read2.jsp">微震统计</a>
        <a href="search.jsp">特征查询</a>
        <a href="jsp/MyJsp4.jsp">煤矿微震波形</a>
    </div>
    <img src="image/20180507192208.png" class="pic">
    <div class="clear"></div>
    <div class="foot">
        <span>
            辽宁大学矿山微震大数据平台<br>
            Powered by辽宁大学信息学院  &nbsp;<br>
            地址:沈阳市皇姑区崇山中路66号 电话:024-62202344 邮编:110036
        </span>
    </div>
  </body>
</html>
