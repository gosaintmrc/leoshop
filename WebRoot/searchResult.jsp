<%@ page import="com.leoshop.beans.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<style type="text/css">
	#div1{
		margin-left: 450px;
		margin-bottom: 10px;
	}
	.cont {
    display: block;
    float: left;
    margin: 0 0;
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
<script type="text/javascript">
	$(function(){
		//给a连接添加事件，将查询信息带上
		$("a.pageturn").click(function(){
			var href = this.href;
			var criteriaProduct = $("#searchProductName").serialize();
			href = href + "&" +criteriaProduct;
			window.location.href = href;
			return false;
		});
	
	});

</script>
</head>
<body>
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
	<div class="main" style="margin: 0">
		<div class="wrap">
			<div class="section group">
				<div class="cont span_2_of_3" style="width: 100%">
					<h2 class="head">搜索结果...</h2>
					<c:forEach items="${pager.pageDataList}" var="out" varStatus="outvs">
						<c:if test="${(outvs.count-1)%4==0 }">
							<div class="top-box"> 
							<c:forEach items="${pager.pageDataList}" var="a" varStatus="vs" begin="${outvs.count - 1}" end="${outvs.count + 2}" >
							<div class="col_1_of_3 span_1_of_3" style="width: 23%">
									<a href="getProduct.do?productid=${a.productId}" >
										<div class="inner_content clearfix">
											<div class="product_image">
												<img src="${a.productImagePath}"/>
											</div>
											<div class="sale-box">
												<span class="on_sale title_shop">新品</span>
											</div>
											<div class="price">
												<div class="cart-left">
													<p class="title">${fn:substring(a.productName,0,20)}...</p>
													<br />
													<div class="price1">
														<span class="actual">￥${a.productPrice}</span>
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
					<div id="div1">
					<c:if test="${pager.currPage==1}">
   						首页 &nbsp;&nbsp;上一页
   					</c:if>
					<c:if test="${pager.currPage>1 }">
					<a class="pageturn" href="searchProduct.do?currPage=1">首页</a>&nbsp;&nbsp;
					<a class="pageturn" href="searchProduct.do?currPage=${pager.currPage-1}">上一页</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${pager.currPage==pager.pageCount}">
   					          下一页&nbsp;&nbsp;尾页
   					</c:if>
					<c:if test="${pager.currPage<pager.pageCount}">
					<a class="pageturn" href="searchProduct.do?currPage=${pager.currPage+1}">下一页</a>&nbsp;&nbsp;				
					<a class="pageturn" href="searchProduct.do?currPage=${pager.pageCount}">尾页</a>&nbsp;&nbsp;
					</c:if>
						${requestScope.pager.dataCount}条记录${requestScope.pager.currPage}/
						${requestScope.pager.pageCount} 页 
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>