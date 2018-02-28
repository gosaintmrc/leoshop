<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath}/back/">

<!DOCTYPE HTML>
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
<link href="assets/css/bui-min.css" rel="stylesheet" type="text/css" />
<link href="assets/css/main-min.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<div class="header">

		<div class="dl-title">
			<img width="100px" height="20px" src="assets/img/logo.png">
		</div>

		<div class="dl-log">
			欢迎您，<span class="dl-log-user">${sessionScope.manager.managerName }</span><a
				href="logout.bg" title="退出系统"
				class="dl-log-quit" onclick="return confirm('您确定退出吗？');">[退出]</a>
		</div>
	</div>
	<div class="content">
		<div class="dl-main-nav">
			<div class="dl-inform">
				<div class="dl-inform-title">
					<s class="dl-inform-icon dl-up"></s>
				</div>
			</div>
			<ul id="J_Nav" class="nav-list ks-clear">
				<c:forEach items="${sessionScope.menumap}" var="menu">
					<c:if test="${menu.key.menuId==1}">
						<li class="nav-item dl-selected"><div
								class="nav-item-inner nav-home">${menu.key.menuName}</div></li>
					</c:if>
					<c:if test="${menu.key.menuId==2}">
						<li class="nav-item dl-selected"><div
								class="nav-item-inner nav-order">${menu.key.menuName}</div></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<ul id="J_NavContent" class="dl-tab-conten">

		</ul>
	</div>
	<script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="assets/js/bui-min.js"></script>
	<script type="text/javascript" src="assets/js/common/main-min.js"></script>
	<script type="text/javascript" src="assets/js/config-min.js"></script>
	<script>
		$(function(){
			
		});
		BUI.use('common/main', function() {
			var config = ${sessionScope.menuStr};
			new PageUtil.MainPage({
				modulesConfig : config
			});
		});
		$(window).resize(function() {
			
	    });
	</script>
	<div style="text-align: center;"></div>
</body>
</html>