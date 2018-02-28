<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_服务协议</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
    <div class="login">
     <div class="wrap">
	    <ul class="breadcrumb breadcrumb__t"><a class="home" href="AboutBlank.jsp">主页</a>  / 服务协议</ul>
		<h5 class="m_6">服务协议</h5>	
		<p class="m_text">一二三四五。上山打老虎。老虎不在家。找到小松鼠。松鼠有几只。一二三四五。</p>
		<ul class="delivery-list">
			<li><a href="#">一二三四五</a></li>
			<li><a href="#">上山打老虎</a></li>
			<li><a href="#">老虎不在家</a></li>
			<li><a href="#">找到小松鼠</a></li>
			<li><a href="#">松鼠有几只</a></li>
			<li><a href="#">一二三四五</a></li>
        </ul>
		<p class="m_text">一二三四五。上山打老虎。老虎不在家。找到小松鼠。松鼠有几只。一二三四五。</p>
	</div>	
   </div>
   <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>