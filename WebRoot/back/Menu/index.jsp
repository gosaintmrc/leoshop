<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css" />
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

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
		#addnew_div{
			width: 1210px;
			height: 50px;
		}
		#addnew{
			margin-left: 1100px;
			margin-top: 20px;
		}

    </style>
</head>
<body>
<!-- <form class="form-inline definewidth m20" action="index.jsp" method="get">
	菜单名称：
    <input type="text" name="menuname" id="menuname"class="abc input-default" placeholder="" value="">&nbsp;&nbsp; 
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp;
	<button type="button" class="btn btn-success" id="addnew">新增菜单</button>
</form> -->
<div id="addnew_div">
	<button type="button" class="btn btn-success" id="addnew">新增菜单</button>
</div>
<table class="table table-bordered table-hover definewidth m10">
    <thead>
	    <tr>
	        <th>菜单标题</th>
	        <th>菜单编号</th>
	        <th>上级菜单编号</th>
	        <th>菜单链接</th>
	        <th>菜单状态</th>
	        <th>管理操作</th>
	    </tr>
    </thead>
    <tbody>
    	<c:forEach items="${requestScope.menumap}" var="menu">
			<tr>
				<td>${menu.key.menuName}</td>
				<td>${menu.key.menuId}</td>
				<td colspan="4"></td>
			</tr>
    		<c:forEach items="${menu.value}" var="son">
    			<tr>
					<td>${son.menuName}</td>
					<td>${son.menuId}</td>
					<td>${son.parentMenu}</td>
					<td>${son.menuUrl}</td>
					<td>
						<c:if test="${son.menuStatus==1}">启用</c:if>
						<c:if test="${son.menuStatus==0}">禁用</c:if>
					</td>
					<td><a href="${pageContext.request.contextPath}/back/Menu/getMenu.bg?menuId=${son.menuId}">编辑</a>&nbsp;&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/back/Menu/deleteMenu.bg?menuId=${son.menuId}" onclick="return confirm('您确定删除吗？重新登陆生效！');">删除</a>
					</td>
				</tr>
    		</c:forEach>
	    </c:forEach>
    </tbody>
</table>
</body>
</html>
<script>
    $(function () {
        

		$('#addnew').click(function(){

				window.location.href="add.jsp";
		 });


    });
</script>