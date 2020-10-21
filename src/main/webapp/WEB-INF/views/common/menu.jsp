<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="left-side sidebar-offcanvas">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="static/img/avatar3.png" class="img-circle"
					alt="User Image" />
			</div>
			<div class="pull-left info">
				<p>Hello, ${sessionScope.USER.userName}</p>

				<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>
		<!-- /.search form -->
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="index_">
				<a href="web/index">
					<i class="fa fa-dashboard"></i> <span>首页</span>
				</a>
			</li>
			
			<c:if test="${sessionScope.USER.roleId == 1}">
				<li class="create_">
					<a href="web/create">
						<i class="fa fa-dashboard"></i> <span>创建流程</span>
					</a>
				</li>
				<li class="model_">
					<a href="web/model">
						<i class="fa fa-th"></i> <span>已有的流程</span>
					</a>
				</li>
				<li class="procDef_">
					<a href="web/procDef">
						<i class="fa fa-calendar"></i><span>已部署的流程</span>
					</a>
				</li>
			</c:if>
			
			<c:if test="${sessionScope.USER.roleId == 2}">
				<li class="canStartProc_">
					<a href="web/myProc">
						<i class="fa fa-calendar"></i><span>可启动流程</span>
					</a>
				</li>
				<li class="myUndeal_">
					<a href="web/myUndeal">
						<i class="fa fa-calendar"></i><span>我的待办流程</span>
					</a>
				</li>
			</c:if>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
