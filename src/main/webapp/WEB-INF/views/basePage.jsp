<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath %>" />
    <meta charset="UTF-8">
    <title>activiti</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <link href="title.ico" rel="SHORTCUT ICON" />
    <jsp:include page="common/header.jsp" />
</head>
<body class="skin-black">

	<jsp:include page="common/top.jsp" />
	<div class="wrapper row-offcanvas row-offcanvas-left">
	<jsp:include page="common/menu.jsp" />
	<jsp:include page="${_pageUrl}.jsp" />
	</div><!-- ./wrapper -->
	
	<c:if test="${_modalUrl != null}">
		<jsp:include page="${_modalUrl}.jsp" />
	</c:if>
	
	<jsp:include page="common/footer.jsp" />
	
	<script>
	
	var page_name = "${_pageName}";
	
	</script>
	
</body>
</html>