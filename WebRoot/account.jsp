<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_账户信息</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
		$("#msg").click(function(){
			$("#main").removeAttr("id");
		});
		$("#dir").click(function(){
			$("#main").removeAttr("id");
		});
		$("#ord").click(function(){
			$("#main").removeAttr("id");
		});
		var href = $("a#default").attr("href");
		window.open(href,target="main");
		$("#msg").click();
	});
</script>
<style type="text/css">
	#account_msg {
		width:90%;
		height: 35px;
		margin-bottom: 20px;
	}
	#account_msg_menu li{
		list-style: none;
		float: left;
		margin-left: 2%; 
		font-size: 1em;
		text-align: center;
		color: black;
		line-height: 35px;
	}
	#account_msg_menu a:LINK{
		color: #555;
	}
	#account_msg_menu a:HOVER{
		color: #555;
		cursor: pointer;
	}
	#account_msg_menu a:VISITED{
		color: #555;
	}
	#main{
		height: 300px;
	}
</style>
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
	<div class="register_account">
		<div class="wrap" align="center" id="main">
			<h4 class="title" align="left">我的账户</h4>
			<div id="account_msg" align="left">
				<ul id="account_msg_menu">
	    			<li id="msg"><a id="default" href="getUserById.do" target="main">个人信息</a></li>
	   				<li id="dir"><a href="getAddressPager.do" target="main">地址管理</a></li>
	   				<li id="ord"><a href="getOrderMsgs.do" target="main">我的订单</a></li>
	    		</ul>
			</div>
			<iframe id="iframeMain" align="middle" frameborder="0" width="100%" name="main" onload="Javascript:iframeAutoFit(this)" scrolling="no"></iframe>
<script type="text/javascript">
	function iframeAutoFit(iframeObj){ 
		setTimeout(function(){
			if(!iframeObj)
			return;
			iframeObj.height=(iframeObj.Document?iframeObj.Document.body.scrollHeight:iframeObj.contentDocument.body.offsetHeight);
		},200);
	}
</script>
		</div>
	</div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>