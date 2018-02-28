<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_购物车</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<%--gz --%>
<link rel="stylesheet" href="css/cart2.css" type="text/css">
<script type="text/javascript" src="js/checkout.js"></script>
<%--gz --%>
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
	<div class="clear"></div>
	<div class="mycar-index">
		<h1>购物车</h1>
		<div
			style="width: 1200px; height: 1px; overflow: hidden; clear: both;"></div>
		<div id="blackcart">
			<div class="cart-blank">
				您的购物车中暂无商品，赶快<a href="AboutBlank.jsp">挑选心爱的商品</a>吧！
			</div>
		</div>


		<div id="cart-wrapper">
			<c:if test="${empty requestScope.cartProductMap }">
				<div class="cart-blank">
					您的购物车中暂无商品，赶快<a href="AboutBlank.jsp">挑选心爱的商品</a>吧！
				</div>
			</c:if>
			<c:if test="${not (empty requestScope.cartProductMap)}">
				<form action="buyleo.do" method="get" id="cartFormSubmit">
					<table>
						<thead>
							<tr>
								<th></th>
								<th style="width: 600px;">商品名称</th>
								<!-- <th>商品积分</th> -->
								<th>商品单价</th>
								<th>数量</th>
								<th style="width: 100px;">小计</th>
								<th class="cart_last" style="width: 100px;">删除</th>
							</tr>
						</thead>
						<tbody id="cartbody">
							<c:forEach items="${requestScope.cartProductMap}" var="cartProduct">
								<tr>
									<td class="checkboxtd">
											<input checked="checked" name="cartId" value="${cartProduct.key.cartId}" class="checkbox" type="checkbox" >
									</td>
									<td style="width: 600px;"><a target="_blank"
										href="getProduct.do?productid=${cartProduct.value.productId }"
										class="cart_list_img"><img
											src="${cartProduct.value.productImagePath }"
											style="cursor: pointer;" height="50" width="36"></a>
										<p>
											<a target="_blank"
												href="getProduct.do?productid=${cartProduct.value.productId }">${cartProduct.value.productName }</a>
										</p></td>
									<!-- <td style="font-weight:bold; font-size:14px;">0</td> -->
									<td class="mktprice1" style="font-weight: bold; font-size: 14px;">
										<b>${cartProduct.value.productPrice }</b>
									</td>
									<td class="cart-quantity" style="text-align: center;">
										<div class="Numinput">
										<c:if test="${cartProduct.value.productStatus==1}">
											<span class="numadjust decrease">-</span>
											<c:if test="${cartProduct.key.saleCount<=cartProduct.value.storeNum}">
											<input name="num" size="5" value="${cartProduct.key.saleCount}" type="text">
											</c:if> 
											<c:if test="${cartProduct.key.saleCount>cartProduct.value.storeNum}">
											<input class="outOfStoreNum" name="num" size="5" value="${cartProduct.key.saleCount}" type="text">
											</c:if> 
											<span class="numadjust increase">+</span>
										</c:if>
										<c:if test="${cartProduct.value.productStatus==0}">
											<font class="productSaleOut" color="red">已下架</font>
										</c:if>
										</div>
									</td>
									<td class="itemTotal"
										style="color: #ff6700; font-size: 14px; font-weight: bold; width: 100px;">
										<b>
										<fmt:formatNumber type="number" value="${cartProduct.key.saleCount*cartProduct.value.productPrice}" maxFractionDigits="2"/>
										</b>
										</td>
									<td class="cart_last" style="width: 100px;"><a href="#"
										class="delete"></a></td>
									<td class="cartId"><input type="hidden"
										value="${cartProduct.key.cartId }"></td>
									<td class="storeNum"><input type="hidden"
										value="${cartProduct.value.storeNum }"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- 总价 -->
					<div class="cart-wrapper">
						<div class="yes_bonded">
							<p align="left">&nbsp;&nbsp;<input checked="checked" id="checkAll" type="checkbox">全选
							&nbsp;&nbsp;<a id="deleteChecked" href="#">删除</a> </p>
							<span class="c08"> 商品总价: <span class="color03" id="total">
							<fmt:formatNumber value="1" type="currency"></fmt:formatNumber>
							</span>
							</span>
						</div>
						<div class="cart_tools">
							<div class="btn">
								<input class="clean_btn white-btn" value="清空购物车" type="button">
							</div>
							<div class="btn">
								<input class="returnbuy_btn yellow-btn" value="继续购物"
									type="button">
							</div>

							<div class="btn">
								<input id="goToBuyleo" class="checkout-btn green-btn" value="去结算" type="button">
							</div>
						</div>
					</div>
				</form>
			</c:if>
		</div>
	</div>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>