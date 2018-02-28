<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="JavaScript">
	if(window != top){
		top.location.href=location.href;
	}
</script>
<title>LeoShop_登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
<script type="text/javascript">
	$(function(){
		var responseMsg = "${param.responseMsg}";
		if(responseMsg == "userIsNotLogin"){
			$("#errorMsg").text("请登录！！！"); 
		}
		$("#loginSubmit").click(function(){
			var username = $.trim($("#login_username").val());
			var password = $("#login_passwd").val();
			if(username==""||password==""){
				$("#errorMsg").text("请输入用户名和密码");
				return false;
			}else{
				$("#errorMsg").text("");
			}
			var json = {"username":username,"password":password};
			var url = "login.do";
			$.post(url,json,function(data){
				var logStatus = (data[0].logStatus=="true");
				$("#errorMsg").text("");
				if(logStatus){
					window.location.href = "AboutBlank.jsp";
				}else {
					$("#errorMsg").text("用户名或密码错误");
				}
			},"json");
			return false; 
		 });
	});
</script>

</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
        <div class="login">
          	<div class="wrap">
				<div class="col_1_of_login span_1_of_login">
					<h4 class="title">新用户注册</h4>
					<p>如果没有账号，可以免费注册！！</p>
					<div class="button1">
					   <a href="register.jsp"><input type="button" name="Submit" value="用户注册"></a>
					 </div>
					 <div class="clear"></div>
				</div>
				<div class="col_1_of_login span_1_of_login">
				<div class="login-title">
	           		<h4 class="title">用户登录</h4>
					<div id="loginbox" class="loginbox">
						<form action="login.do" method="post" name="login" id="login-form">
						  <fieldset class="input">
						    <p id="login-form-username">
						      <label for="modlgn_username">用户名</label>
						      <input id="login_username" type="text" name="username" class="inputbox" size="18" autocomplete="off"><input type="hidden" id="verify_result" value="false">
						    </p>
						    <p id="login-form-password">
						      <label for="modlgn_passwd">登录密码</label>
						      <input id="login_passwd" type="password" name="password" class="inputbox" size="18" autocomplete="off">
						    </p>
						    <div class="remember">
							    <p id="login-form-remember">
							      <label for="modlgn_remember"><font id="errorMsg" style="font-style:italic;font-size:1.5em;color: red"></font></label>
							   </p>
							    <input id="loginSubmit" type="submit" name="Submit" class="loginbtn" value="登录">
							 </div>
						  </fieldset>
						 </form>
					</div>
			    </div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>