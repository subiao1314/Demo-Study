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
    <jsp:include page="../common/loginHeader.jsp" />
</head>
<script>
    $(function () {
    	$("#login_confirm").on("click", function(){
    		check();
    	});
    	
    	$(document).on("keydown", function(e){
    		var ev = window.event || e;
    		if(ev.keyCode == 13){
    			check();
    		}
    	});
    });
    
    function check(){
    	var name = $("#ACC_NAME").val();
    	var pwd = $("#PASSWORD").val();
    	if(name == ""){
    		alert("用户名不能为空");
    		return false;
    	}
    	if(pwd == ""){
    		alert("密码不能为空");
    		return false;
    	}
    	
    	var data = {
    		name: name,
    		pwd: pwd
    	}
    	
		$.ajax({
			url: "login/doLogin",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				alert(data.msg);
				if(data.succ){
					var location = (window.location+'').split('/');
                    var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
                   	window.location.href=basePath + "/web/index";
				}
			}
		});
    }

</script>

<body>

	<div class="login-wrap">
		<!-- /.login-logo -->
		<div class="gmain">
		    <div class="login-box-body">
		        <p class="login-box-msg">登录</p>
		
		        <form id="loginForm" class="login">
		            <div class="form-group has-feedback">
		                <input id="ACC_NAME" name="accName" type="text" class="form-control" placeholder="账号">
		            </div>
		            <div class="form-group has-feedback">
		                <input id="PASSWORD" name="password" type="password" class="form-control" placeholder="密码">
		            </div>
		            <div class="row">
		                <div class="col-xs-12">
		                    <button id="login_confirm" type="button" class="">登录</button>
		                </div>
		                <!-- /.col -->
		            </div>
		        </form>
		        <!-- /.social-auth-links -->
		    </div>
		</div>
	</div>

</body>
