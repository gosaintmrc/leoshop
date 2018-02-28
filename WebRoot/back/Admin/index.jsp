<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		$("#addnew").click(function() {
			var criteriaManager = $("#criteriaManagerName").serialize();
			window.location.href = "add.jsp?"+criteriaManager;
		});
		//修改添加事件 带上查询信息
		$(".editManager").click(function(){
			var href = this.href;
			var criteriaManager = $("#criteriaManagerName").serialize();
			href = href + "&" +criteriaManager;
			window.location.href = href;
			return false;
		});
		//删除事件
		$(".deleteManager").click(function(){
			var name = $(this).parent("td").prev("td").text();
			if(confirm("确认删除管理员：" + name)){
				var href = this.href;
				var criteriaManager = $("#criteriaManagerName").serialize();
				href = href + "&" +criteriaManager;
				window.location.href = href;
			}
			return false;
		});
	});
	
</script>
</head>
<body>
	<form class="form-inline definewidth m20" action="Admin/getManagers.bg"
		method="post">
		账户名称： <input type="text" name="criteriaManagerName" id="criteriaManagerName"
			class="abc input-default" placeholder="" value="${requestScope.criteriaManagerName }">&nbsp;&nbsp;
		<button type="submit" id="querybtn" class="btn btn-primary">查询</button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-success" id="addnew">新增管理账户</button>
	</form>
	<table id="managerTable" class="table table-bordered table-hover definewidth m10">
		<thead>
			<tr>
				<th>序号</th>
				<th>管理员ID</th>
				<th>账户名称</th>
				<th>管理操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.managers}" var="manager" varStatus="vs">
				<tr>
					<td>${vs.count}</td>
					<td>${manager.managerId}</td>
					<td>${manager.managerName}</td>
					<td><a href="toEditManager.bg?managerId=${manager.managerId}"
						class="editManager">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="deleteManager" 
						href="deleteManager.bg?managerId=${manager.managerId}">
						删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>
