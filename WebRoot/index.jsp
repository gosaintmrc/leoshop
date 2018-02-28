<%@ page import="com.leoshop.beans.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">

</style>
<title>LeoShop旗舰店</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/form.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/nivo-slider.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<style type="text/css">
	#div1{
		position: absolute;
		margin-left: 450px;
	}
</style>
<script type="text/javascript" src="js/megamenu.js"></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
	});
</script>
<!--start slider -->
<link rel="stylesheet" href="css/fwslider.css" media="all">
<script src="js/jquery-ui.min.js"></script>
<script src="js/css3-mediaqueries.js"></script>
<script src="js/fwslider.js"></script>
<!--end slider -->
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
	<!-- start slider -->
	<div id="fwslider">
		<div class="slider_container">
			<div class="slide">
				<!-- Slide image -->
				<img src="images/banner.jpg" alt="" />
				<!-- /Slide image -->
				<!-- Texts container -->
				<div class="slide_content">
					<div class="slide_content_wrap">
						<!-- Text title -->
						<h4 class="title">雷朋经典</h4>
						<!-- /Text title -->

						<!-- Text description -->
						<p class="description">俱乐部</p>
						<!-- /Text description -->
					</div>
				</div>
				<!-- /Texts container -->
			</div>
			<!-- /Duplicate to create more slides -->
			<div class="slide">
				<img src="images/banner1.jpg" alt="" />
				<div class="slide_content">
					<div class="slide_content_wrap">
						<h4 class="title">简约时尚</h4>
						<p class="description">创意雷朋</p>
					</div>
				</div>
			</div>
			<!--/slide -->
		</div>
		<div class="timers"></div>
		<div class="slidePrev">
			<span></span>
		</div>
		<div class="slideNext">
			<span></span>
		</div>
	</div>
	<!--/slider -->
	<div class="main">
		<div class="wrap">
			<div class="section group">
				<div class="cont span_2_of_3">
					<h2 class="head">热卖商品</h2>
					<c:forEach items="${pager.pageDataList}" var="out" varStatus="outvs">
						<c:if test="${(outvs.count-1)%3==0 }">
							<div class="top-box"> 
							<c:forEach items="${pager.pageDataList}" var="a" varStatus="vs" begin="${outvs.count - 1}" end="${outvs.count + 1}" >
							<div class="col_1_of_3 span_1_of_3">
									<a href="getProduct.do?productid=${a.productId}" >
										<div class="inner_content clearfix">
											<div class="product_image">
												<img src="${a.productImagePath}"/>
											</div>
											<div class="sale-box">
												<span class="on_sale title_shop">热卖</span>
											</div>
											<div class="price">
												<div class="cart-left">
													<p class="title">${fn:substring(a.productName,0,20)}...</p>
													<br />
													<div class="price1">
														<span class="actual"><fmt:formatNumber value="${a.productPrice}" type="currency"></fmt:formatNumber></span>
													</div>
												</div>
												<div class="cart-right"></div>
												<div class="clear"></div>
											</div>
										</div>
									 </a>
									</div>
								</c:forEach>
								<div class="clear"></div>
							</div> 
						</c:if>
				</c:forEach>
				<br/><br/><br/>
				</div>
				<div class="rsidebar span_1_of_left">
					<div class="top-border"></div>
					<div class="border">
						
						<script src="js/jquery.nivo.slider.js"></script>
						<script type="text/javascript">
							$(window).load(function() {
								$('#slider').nivoSlider();
							});
						</script>
						<div class="slider-wrapper theme-default">
							<div id="slider" class="nivoSlider">
								<img src="images/t-img1.jpg"  alt="" />
				               	<img src="images/t-img2.jpg"  alt="" />
				                <img src="images/t-img3.jpg"  alt="" />
							</div>
						</div>
					</div>
					<div class="top-border"></div>
					<div class="sidebar-bottom">
						<h2 class="m_1">
							免费在线客服<br> 客服阿Q为您服务
						</h2>
						<p class="m_text">24小时在线</p>
						<div class="subscribe">
							<form>
								<input name="userName" type="text" class="textbox"> <input
									type="submit" value="联系客服">
							</form>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>