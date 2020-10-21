<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>流程图片查看</title>
</head>
<style type="text/css">
	* {
		margin: 0;
		padding: 0;
	}
	body, html {
		width: 100%;
		height: 100%;
	}
	.img {
		width: 100%;
		height: 100%;
		text-align: center;
	}
	.img img {
		max-width: 100%;
		max-height: 100%;
	}
</style>
<body>

	<div class="img">
		<img src="getImage?deploymentId=${deploymentId}" />
	</div>

</body>
</html>