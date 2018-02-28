<%@page import="com.leoshop.beans.OrderMsg"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_商品评价</title>
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
	});
</script>
<style type="text/css">
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
</style>
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
	<div class="register_account">
		<div class="wrap" align="center">
			<h4 class="title" align="left">商品评价</h4>
			<%
				pageContext.setAttribute("orderNum", request.getParameter("orderNum"));
			%>
			<iframe src="getCommentMsg.do?orderNum=${orderNum}" align="middle" frameborder="0" width="100%" name="main" onload="Javascript:iframeAutoFit(this)" scrolling="no"></iframe>
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