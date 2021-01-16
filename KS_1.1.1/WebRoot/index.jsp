<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
  
    <meta charset="UTF-8">
    <title>矿山微震大数据平台</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
  </head>
  
  <body>
  	 <div class="head">
  	 	<div class="logo">
            <img src="image/lndx.png" id="p1">
            <img src="image/1525748529_422029.png">
        </div>
  	 </div>
    <div class="center">
       <!--  <h2>矿山微震大数据平台</h2> -->
        <div class="service-shadow">
            <a href="jsp/MyJsp5.jsp">
                <img src="image/1520908753.jpg">
            </a>
            <a href="jsp/MyJsp5.jsp" class="button">
            	<span class="line line-top"></span>
                <span class="line line-left"></span>
                <span class="line line-right"></span>
                <span class="line line-bottom"></span>
                <p>监测数据</p>
            </a>
        </div>
        <div class="service-shadow">
            <a href="read2.jsp">
                <img src="image/1520908587.jpg">
            </a>
            <a href="read2.jsp" class="button">
            	<span class="line line-top"></span>
                <span class="line line-left"></span>
                <span class="line line-right"></span>
                <span class="line line-bottom"></span>
                <p>微震统计</p>
            </a>
        </div>
       <!--  <div class="service-shadow">
            <a href="search.jsp">
                <img src="image/22222222.png">
            </a>
            <a href="search.jsp" class="button">
            	<span class="line line-top"></span>
                <span class="line line-left"></span>
                <span class="line line-right"></span>
                <span class="line line-bottom"></span>
                <p>特征查询</p>
            </a>
        </div> -->
        <div class="service-shadow">
            <a href="jsp/MyJsp4.jsp">
                <img src="image/111111111.png">
            </a>
            <a href="jsp/MyJsp4.jsp" class="button">
            	<span class="line line-top"></span>
                <span class="line line-left"></span>
                <span class="line line-right"></span>
                <span class="line line-bottom"></span>
                <p>微震波形图</p>
            </a>
        </div>
    </div>
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
