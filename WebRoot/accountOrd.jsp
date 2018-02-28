<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_我的订单</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	.mycar-index{
		margin: 0 auto;
		width: 1200px;
	}
	#cart-wrapper{
		width: 1200px;
		/* height:330px; */
	}
	#cart-wrapper table{
		width: 1200px;
		border-left:1px solid #f5f5f5;
		border-right:1px solid #f5f5f5;
	}
	#cart-wrapper table th{
		background: #f5f5f5;
		height: 30px;
		text-align:center;
	}
	#cart-wrapper table td{
		border-bottom: 1px solid #f5f5f5;
		color: #333;
		height: 70px;
		font-size: 14px;
	}
	#page{
		color: #333;
		font-size: 14px;
	}
	a:LINK{
		text-decoration: none;
		color: #555;
	}
	a:hover{
		text-decoration: none;
		cursor:pointer;
		background:#4CB1CA;
	}
	a:VISITED{
		text-decoration: none;
		color: #555;
	}
	.order-blank{  
	    height:100px;
	    line-height:100px;
	    font-size:16px;
	    text-align:center;
	   	width:100%;
	    margin:30px 0px;
    }
    img{
		float: left;
		display: block;
		width: 60px;
		height: 60px;
		cursor: pointer;
		margin-top:2px;
		margin-left: 10px;
	}
    .pname{
		float: left;
		width: 310px;
		margin-top:20px;
		margin-left:10px;
		text-algin: left;
    }
</style>
</head>
<body>
	<div class="mycar-index">
		<c:choose>
			<c:when test="${!(empty ordermsg)}">
				<div id="cart-wrapper">
					<table cellspacing="1.5">
						<thead>
							<tr>
								<th width="100px">订单编号</th>
								<th width="400px">商品名称</th>
								<th width="125px">商品单价</th> 
								<th width="125px">数量</th>
								<th width="125px">订单合计</th>
								<th width="150px">备注</th>
								<th width="175px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ordermsg}" var="order">
								<c:if test="${fn:length(order.product)==1}">
									<tr>
										<td align="center">
											${order.orderNum}
										</td>
										<c:forEach items="${order.product}" var="product">
											<td align="left">
												<a target="_blank" href="getProduct.do?productid=${product.productId }">
													<img src="${product.productImagePath }"></a>
												<div class="pname"><a target="_blank" href="getProduct.do?productid=${product.productId }">${product.productName}</a></div>
											</td>
											<td align="center">
												￥<fmt:formatNumber value="${product.productPrice}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center">
												${product.saleCount}
											</td>
											<td align="center">
												￥<fmt:formatNumber value="${product.productPrice*product.saleCount}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
										</c:forEach>
										<td align="center">
											<c:if test="${!(empty order.note)}">${order.note}</c:if>
											<c:if test="${empty order.note}">无</c:if>
										</td>
										<td align="center"><a href="deleteOrderById.do?orderNum=${order.orderNum}" onclick="return confirm('您确定删除吗？');">删除订单</a><br/>
											<c:if test="${order.orderStatus==0}"><a target="_blank" href="payment.do?orderNum=${order.orderNum}"><font color="red">去付款</font></a></c:if>
											<c:if test="${order.orderStatus==1}">待发货</c:if>
											<c:if test="${order.orderStatus==2}"><a href="receiveProduct.do?orderNum=${order.orderNum}"><font color="red">确认收货</font></a></c:if>
											<c:if test="${order.orderStatus==3}">
												<c:if test="${order.level==0}"><a onclick="window.parent.location.href='comment.jsp?orderNum=${order.orderNum}'"><font color="red">去评价</font></a></c:if>
												<c:if test="${order.level!=0}">已完成<br/><a onclick="window.parent.location.href='comment.jsp?orderNum=${order.orderNum}'"><font color="red">查看评价</font></a></c:if>
											</c:if>
										</td>
									</tr>
								</c:if>
								<c:if test="${fn:length(order.product)!=1}">
									<tr>
										<td align="center" rowspan="${fn:length(order.product)}">
											${order.orderNum}
										</td>
										<c:forEach items="${order.product}" var="product" begin="0" end="0">
											<td align="left">
												<a target="_blank" href="getProduct.do?productid=${product.productId }">
													<img src="${product.productImagePath }"></a>
												<div class="pname"><a target="_blank" href="getProduct.do?productid=${product.productId }">${product.productName}</a></div>
											</td>
											<td align="center">
												￥<fmt:formatNumber value="${product.productPrice}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center">
												${product.saleCount}
											</td>
											<td align="center">
												￥<fmt:formatNumber value="${product.productPrice*product.saleCount}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center" rowspan="${fn:length(order.product)}">
												<c:if test="${!(empty order.note)}">${order.note}</c:if>
												<c:if test="${empty order.note}">无</c:if>
											</td>
											<td align="center" rowspan="${fn:length(order.product)}"><a href="deleteOrderById.do?orderNum=${order.orderNum}" onclick="return confirm('您确定删除吗？');">删除订单</a><br/>
												<c:if test="${order.orderStatus==0}"><a target="_blank" href="payment.do?orderNum=${order.orderNum}"><font color="red">去付款</font></a></c:if>
												<c:if test="${order.orderStatus==1}">待发货</c:if>
												<c:if test="${order.orderStatus==2}"><a href="receiveProduct.do?orderNum=${order.orderNum}"><font color="red">确认收货</font></a></c:if>
												<c:if test="${order.orderStatus==3}">
													<c:if test="${order.level==0}"><a onclick="window.parent.location.href='comment.jsp?orderNum=${order.orderNum}'"><font color="red">去评价</font></a></c:if>
													<c:if test="${order.level!=0}">已完成<br/><a onclick="window.parent.location.href='comment.jsp?orderNum=${order.orderNum}'"><font color="red">查看评价</font></a></c:if>
												</c:if>
											</td>
										</c:forEach>
									</tr>
									<c:forEach items="${order.product}" var="product" begin="1" end="${fn:length(order.product)-1}">
										<tr>
											<td align="left">
												<a target="_blank" href="getProduct.do?productid=${product.productId }">
													<img src="${product.productImagePath }"></a>
												<div class="pname"><a target="_blank" href="getProduct.do?productid=${product.productId }">${product.productName}</a></div>
											</td>
											<td align="center">
												￥<fmt:formatNumber value="${product.productPrice}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center">
												${product.saleCount}
											</td>
											<td align="center">
												￥<fmt:formatNumber value="${product.productPrice*product.saleCount}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="order-blank">
					您还没有订单信息，赶快<a onclick="window.parent.location.href='AboutBlank.jsp'"><font color="#555">去挑选心爱的商品</font></a>吧！
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<br/>
</body>
</html>