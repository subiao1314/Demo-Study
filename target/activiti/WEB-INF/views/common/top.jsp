<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- header logo: style can be found in header.less -->
<header class="header">
	<a href="index.html" class="logo"> <!-- Add the class icon to your logo image or logo icon to add the margining -->
		Activiti
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span> <span
			class="icon-bar"></span> <span class="icon-bar"></span> <span
			class="icon-bar"></span>
		</a>
		<div class="navbar-right">
            <i class="user-pic"></i> <span class="user-name">欢迎您：${sessionScope.USER.userName}</span><i class="user-line"></i> <a href="javascript:;" id="logout" class="logout">退出</a>
        </div>
	</nav>
	
	<script>
	
		$(function(){
			$("#logout").on("click", function(){
				$.ajax({
					url: "logout",
					type: "post",
					dataType: "json",
					success: function(data){
						if(data.succ){
							var location = (window.location+'').split('/');
		                    var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
		                   	window.location.href=basePath + "/login";
						} else {
							alert(data.msg);
							return;
						}
					}
				});
			});
		})	
	
	</script>
</header>