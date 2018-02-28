<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>付款成功</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--gz --%>
<link rel="stylesheet" href="css/cart.css" type="text/css">
<link rel="stylesheet" href="css/buyleo.css" type="text/css">
<%--gz --%>
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
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
	<div class="encircle">
		<div class="checkout_order_right">
			<h1>
				恭喜，付款成功！
			</h1>
			<div class="checkout_order_summary">
			</div>
			<div class="checkout_order_intro">
				<div class="checkout_order_same">
				</div>
				<div class="checkout_order_same">
				</div>
				<div class="checkout_order_tools ">
					 <a href="AboutBlank.jsp" class="go_continue">继续购物</a>
				</div>
			</div>
		</div>
	</div>

	<!--正文结束-->
	<br /><br /><br /><br /><br /><br /><br /><br />
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>