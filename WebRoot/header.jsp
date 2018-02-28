<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>
<style type="text/css">
ul.subCartList li i a:link, a:visited, a:active {
    text-decoration: none;
}
ul.subCartList li i a:hover { color: #D93600; text-decoration: none;}
ul.subCartList li i a {
    color: #333;
    outline: medium none;
    text-decoration: none;
	display: block;
	font-size: 16px;
	line-height: 18px;
	margin-left: 15px;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 250px;
	height:30px;
	overflow: hidden;
	text-align: left;
}
ul.subCartList li {
	width: 300px;
}
</style>
</head>
<body>
	 <div class="header-top">
			<div class="wrap"> 
			 <div class="cssmenu">
<script type="text/javascript">
	 $(function(){
		
		$(".goLeoCart").click(function(){
			var userId = "${sessionScope.userId}";
			if(userId > 0){
				return true;
			}
			if(confirm("请先登录，点击确定跳转到登录页面！")){
				window.location.href = "${pageContext.request.contextPath}/login.jsp";
			}
			return false;
		});
		$("#logout").click(function(){
			return confirm('确认退出当前账户？');
		});
		$("body").keydown(function(event) {
            if (event.keyCode == "13") {//keyCode=13是回车键
                $("#serchSubmit").click();
            }
        });
	}); 

</script>
				<ul class="my_index_menu">
					<c:if test="${sessionScope.userId eq null}">
						<li><a href="login.jsp">登录</a></li>
						<li><a href="register.jsp">注册</a></li>
					</c:if>
					<c:if test="${not(sessionScope.userId eq null)}">
						<li><a href="account.jsp">
						<dir id="test1">${requestScope.userHeader.username}</dir>
						</a></li>
						<li><a class="goLeoCart" href="leoCart.do">购物车</a></li>
						<li><a id="logout" href="out.do">退出</a></li>
					</c:if>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="header-bottom">
	    <div class="wrap">
			<div class="header-bottom-left">
				<div class="logo">
					<a href="AboutBlank.jsp"><img src="images/logo.png" alt=""/></a>
				</div>
				<div class="menu">
	            <ul class="megamenu skyblue">
						<li class="active grid"><a href="AboutBlank.jsp">主页</a>
						</li>
						<li class="active grid"><a href="getAllProduct.do">所有商品</a>
						</li>
					</ul>
				</div>
			</div>
	 		<div class="header-bottom-right">
				<div class="search">
					<form id="serchSubmit" action="searchProduct.do" method="get">
						<input type="text" id="searchProductName" 
						name="searchProductName"  class="textbox" value="${requestScope.criteriaProductName==null?'搜索':requestScope.criteriaProductName}"
							onFocus="this.value = '';"
							onBlur="if (this.value == '') {this.value = '${requestScope.criteriaProductName==null?'搜索':requestScope.criteriaProductName}';}"> 
						<input type="submit" id="submit">
					</form>
					<div id="response"></div>
				</div>
				<div class="tag-list">
					<ul class="icon1 sub-icon1 profile_img">
						<li><a class="active-icon c2" href="#"> </a>
							<ul id="smallCartList" class="subCartList sub-icon1 list">
							<c:if test="${empty requestScope.cartProductMap}">
								<li><h3>商品列表</h3><a></a></li>
								<li id="emptyCart" ><p>请点击<a href='AboutBlank.jsp'>这里</a>选择产品</p></li>
							</c:if>
							<c:if test="${not empty requestScope.cartProductMap}">
									 <li><h3>商品列表</h3><a></a></li>
								<c:forEach items="${requestScope.cartProductMap}" var="cartProduct">
									 <c:if test="${cartProduct.value.productId==param.productid}">
									 	<c:set var="hasThisProduct" scope="request" value="${true }"></c:set>
									 </c:if>
									 <li id="${cartProduct.key.cartId }">
										<i><a href="getProduct.do?productid=${cartProduct.value.productId }">
										${cartProduct.value.productName}</a></i>
									 </li>
								</c:forEach>
							</c:if>
							</ul>
						</li>
					</ul>
					<ul id="xiaocart" class="last">
						<li><a class="goLeoCart" href="leoCart.do">购物车(${fn:length(requestScope.cartProductMap)})</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>