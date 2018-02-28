<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath}/back/Admin/">

<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="../Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="../Css/style.css" />
<script type="text/javascript" src="../Js/jquery.js"></script>
<script type="text/javascript" src="../Js/bootstrap.js"></script>
<script type="text/javascript" src="../Js/ckform.js"></script>
<script type="text/javascript" src="../Js/common.js"></script>

<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
<script>
	$(function() {
		/*var size = "${fn:length(requestScope.managers)}";
		alert(size);*/
	});
	function changePromission(obj) {
		var managerId = obj.name.split("-")[1];
		var roleId = obj.value;
		/* alert(managerId+"||"+roleId); */
		var json = {"managerId":managerId,"roleId":roleId};
		var url = "changePromission.bg";
		$.post(url,json,function(){
			
		},"json");
		alert("修改成功！");
	}
</script>
</head>
<body>
	<form class="form-inline definewidth m20" action="Admin/getPermissions.bg"
		method="post">
		账户名称： <input type="text" name="criteriaManagerName" id="criteriaManagerName"
			class="abc input-default" placeholder="" value="${requestScope.criteriaManagerName }">&nbsp;&nbsp;
		<button type="submit" id="querybtn" class="btn btn-primary">查询</button>
	</form>
	<table id="managerTable" class="table table-bordered table-hover definewidth m10">
		<thead>
			<tr>
				<th>序号</th>
				<th>管理员ID</th>
				<th>账户名称</th>
				<th>权限操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.managers}" var="manager" varStatus="vs">
				<tr>
					<td>${vs.count}</td>
					<td>${manager.managerId}</td>
					<td>${manager.managerName}</td>
					<td id="radio">
						<c:if test="${manager.roleId==1}">
							<input type="radio" value="1" name="promission-${manager.managerId}" checked="checked" onclick="changePromission(this)">超级管理员&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="promission-${manager.managerId}" onclick="changePromission(this)">业务管理员
						</c:if>
						<c:if test="${manager.roleId==2}">
							<input type="radio" value="1" name="promission-${manager.managerId}" onclick="changePromission(this)">超级管理员&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="promission-${manager.managerId}" checked="checked" onclick="changePromission(this)">业务管理员
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
