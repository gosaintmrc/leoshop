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


    </style>
    <script>
	    $(function () {       
			$("#backid").click(function(){
					window.location.href="${pageContext.request.contextPath}/back/Menu/getMenus.bg";
			 });
			$("#submit").click(function() {
				var menuName = $("#menuName").val();
				var menuId = $("#menuId").val();
				var parentMenu = $("#parentMenu").val();
				var menuUrl = $("#menuUrl").val();
				var note = $("#note").val();
				var menuStatus = $("input:checked").val();
				var oldmenuName = $("#oldmenuName").val();
				var oldmenuId = $("#oldmenuId").val();
				var oldmenuUrl = $("#oldmenuUrl").val();
				if($.trim(menuName)=="" || $.trim(menuId)=="" || $.trim(menuUrl)==""){
					alert("编号、名称或链接不能为空！");
					$("#menuName").val(oldmenuName);
					$("#menuId").val(oldmenuId);
					$("#menuUrl").val(oldmenuUrl);
					return;
				};
				var json = {"menuName":menuName,"menuId":menuId,"parentMenu":parentMenu,"menuUrl":menuUrl,"note":note,"menuStatus":menuStatus,"oldmenuId":oldmenuId};
				var url = "${pageContext.request.contextPath}/back/Menu/editMenu.bg";
				$.post(url,json,function(data){
					var msg = data[0].str;
					if(msg == "hasSameMenuId"){
						alert("您输入的编号已存在，请重新输入！");
						$("#menuId").val(oldmenuId);
						return;
					}
					alert(msg);
				},"json");
			});
			$("#parentMenu").change(function() {
				var parentMenu = $("#parentMenu").val();
				$("#label").html("编号格式："+parentMenu+"×（如:"+parentMenu+"1,"+parentMenu+"2,"+parentMenu+"3...）");
			});
	    });
	</script>
</head>
<body>
<form action="" method="post" class="definewidth m20">
<input type="hidden" name="id" value="{$menu.id}" />
<table class="table table-bordered table-hover m10">
    <tr>
        <td width="10%" class="tableleft">上级</td>
        <td>
            <select name="parentMenu" id="parentMenu">
	           	<c:if test="${menu.parentMenu==1}">
		            <option value='1' selected="selected">1--系统管理</option>
	            	<option value='2'>2--业务管理</option>
	            </c:if>
	            <c:if test="${menu.parentMenu==2}">
		            <option value='1'>1--系统管理</option>
	            	<option value='2' selected="selected">2--业务管理</option>
	            </c:if>
            </select>
        </td>
    </tr>
    <tr>
        <td class="tableleft">编号</td>
        <td><input type="text" name="menuId" id="menuId" value="${menu.menuId}">
        	<font size='2.75em' color='red' id="label">编号格式：${menu.parentMenu}×（如:${menu.parentMenu}1,${menu.parentMenu}2,${menu.parentMenu}3...）</font>
        </td>
    </tr>
    <tr>
        <td class="tableleft">名称</td>
        <td><input type="text" name="menuName" id="menuName" value="${menu.menuName}"></td>
    </tr>
    <tr>
        <td class="tableleft">链接</td>
        <td><input type="text" name="menuUrl" id="menuUrl" value="${menu.menuUrl}"></td>
    </tr>
    <tr>
        <td class="tableleft">备注</td>
        <td><input type="text" name="note" id="note" value="${menu.note}"></td>
    </tr>
    <tr>
        <td class="tableleft">状态</td>
        <td>
            <c:if test="${menu.menuStatus==1}">
	            <input type="radio" name="menuStatus" value="1" checked="checked"> 启用
	            <input type="radio" name="menuStatus" value="0"> 禁用
            </c:if>
            <c:if test="${menu.menuStatus==0}">
	            <input type="radio" name="menuStatus" value="1"> 启用
	            <input type="radio" name="menuStatus" value="0" checked="checked"> 禁用
            </c:if>
        </td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button class="btn btn-primary" type="button" id="submit">保存</button> &nbsp;&nbsp;
            <button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
    </tr>
</table>
	<input type="hidden" value="${menu.menuName}" id="oldmenuName">
	<input type="hidden" value="${menu.menuId}" id="oldmenuId">
	<input type="hidden" value="${menu.menuUrl}" id="oldmenuUrl">
</form>
</body>
</html>
