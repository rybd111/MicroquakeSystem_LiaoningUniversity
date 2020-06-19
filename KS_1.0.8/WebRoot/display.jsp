<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'display.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	 <b>遍历list集合的全部元素</b>
	 <%-- ${ } --%>
	 <%
        Vector aVector =new Vector();
     	aVector=(Vector)request.getAttribute("bb");
	 	for(int i=0;i<=10;i++){
	 	aVector.get(i);
	 	System.out.print(aVector.get(i));
	 	request.setAttribute("sd", "ooo");
	 	}
	  %>
	<c:forEach items="${requestScope.bb}" var="keyword" varStatus="id" begin="1" end="20">
          ${id.index} &nbsp; ${keyword}<br>
	</c:forEach>
	
   test 	${requestScope.sd}
   outStr    ${requestScope.outStr}
	<br>
</body>
</html>
