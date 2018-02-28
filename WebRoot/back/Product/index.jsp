<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="../Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="../Css/style.css" />
<script type="text/javascript" src="../Js/jquery.js"></script>
<script type="text/javascript" src="../Js/bootstrap.js"></script>
<script type="text/javascript" src="../Js/ckform.js"></script>
<script type="text/javascript" src="../Js/common.js"></script>

<style type="text/css">
table{
	overflow: auto;
	overflow-x:auto;
}
table tbody tr td.checkedtd{
	min-width: 5px;
}
table tbody tr td.productName a:link, a:visited, a:active {
    text-decoration: none;
}
table tbody tr td.productName a:hover { color: #D93600; text-decoration: none;}
table tbody tr td.productName a {
    outline: medium none;
    text-decoration: none;
	display: block;
	color: #333;
	font-size: 16px;
	line-height: 18px;
	margin-left: 5px;
	text-overflow: ellipsis;
	width: 200px;
	height: 36px;
	overflow: hidden;
}


#checkedAllProduct {
    display: block;
    float: left;
    margin: 4px 0 5px 33px;
    width: 0;
}
.changeProductStatus{
	background-color: white;
	border: 0px solid white;
}


body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
</head>
<body>
	<form class="form-inline definewidth m20" action="${pageContext.request.contextPath}/back/Product/
	queryProducts.bg" method="post">
		商品名称： <input type="text" name="criteriaProductName" id="criteriaProductName"
			class="abc input-default" value="${requestScope.criteriaProductName}">&nbsp;&nbsp;
		<button type="submit" class="btn btn-primary">查询</button>
		&nbsp;&nbsp;
		<button type="button" id="resetQuery" class="btn btn-inverse">重置</button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-success" id="addproducts">添加商品</button>
	</form>
	<table class="table table-bordered table-hover definewidth m10">
		<thead>
			<tr>
				<th></th>
				<th>序号</th>
				<th>商品图片</th>
				<th>商品名称</th>
				<th>商品单价</th>
				<th>库存</th>
				<th>销量</th>
				<th>销售额</th>
				<th>综合评分</th>
				<th>状态</th>
				<th style="min-width: 70px">编辑</th>
			</tr>
		</thead>
		<c:if test="${empty requestScope.pager.pageDataList }">
		<tbody>
			<tr>
				<td colspan="7">没有数据</td>
			</tr>
		</tbody>
		</c:if>
		<c:if test="${not (empty requestScope.pager.pageDataList)}">
		<tbody>
			<c:forEach items="${requestScope.pager.pageDataList}" var="product" varStatus="vs">
				<tr>
					<td class="checkedtd"><input class="checkedProduct" type="checkbox" name="productId" 
					value="${product.productId}"></td>
					<td style="min-width: 30px">${vs.count +(requestScope.pager.currPage - 1) * (requestScope.pager.pageSize) }
					</td>
					<td><a class="nohref" target="_blank" href="${pageContext.request.contextPath }/getProduct.do?
					productid=${product.productId }">
					<img src="${pageContext.request.contextPath }/${product.productImagePath }" 
					style="cursor: pointer;height: 40px;width: 40px" 
					></a></td>
					<td class="productName">
					<a class="nohref" target="_blank" href="${pageContext.request.contextPath }/getProduct.do?
					productid=${product.productId}">
					${product.productName}</a></td>
					<td>${product.productPrice}</td>
					<td>${product.storeNum}</td>
					<td>${product.sales==0?"":product.sales}</td>
					<td>${product.salesAmount==0.0?"":product.salesAmount}</td>
					<td>${product.levelStatistic==null?'5.0':product.levelStatistic}</td>
					<td><button class="changeProductStatus" type="button" 
					value="${product.productStatus}">${product.productStatus==0?'<font color="red">已下架</font>':'<font color="blue">上架</font>'}</button></td>
					<td><a href="${pageContext.request.contextPath}/back/Product/toEditProduct.bg?
					productId=${product.productId}&currPage=${requestScope.pager.currPage}
					">编辑</a>&nbsp;/&nbsp;
					<a class="deleteOneProduct nohref" 
					href="${pageContext.request.contextPath}/back/Product/deleteProducts.bg?
					productId=${product.productId}&currPage=${requestScope.pager.currPage}">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
		</c:if>
	</table>
	<div class="inline pull-left page">
		<input type="checkbox" id="checkedAllProduct">&nbsp;&nbsp;&nbsp;&nbsp;全选&nbsp;&nbsp;
		<a class="nohref" id="deleteCheckedProduct" href="${pageContext.request.contextPath}/back/Product/deleteProducts.bg?
		currPage=${requestScope.pager.currPage}">删除</a>
	</div>
	<div class="inline pull-right page">
			${requestScope.pager.dataCount}条记录${requestScope.pager.currPage}/
			${requestScope.pager.pageCount} 页 
				<c:if test="${requestScope.pager.currPage == 1 }">
					<a class="nohref">首页</a><a class="nohref">上一页</a>
				</c:if>
				<c:if test="${requestScope.pager.currPage > 1 }">
					<a href="${pageContext.request.contextPath}/back/Product/queryProducts.bg?
					currPage=1">首页</a>
					<a href="${pageContext.request.contextPath}/back/Product/queryProducts.bg?
					currPage=${requestScope.pager.currPage - 1 }">上一页</a>
				</c:if>
				<c:if test="${requestScope.pager.currPage == requestScope.pager.pageCount }">
					<a class="nohref">下一页</a>
					<a class="nohref">尾页</a>
				</c:if>
				<c:if test="${requestScope.pager.currPage < requestScope.pager.pageCount }">
					<a href="${pageContext.request.contextPath}/back/Product/queryProducts.bg?
					currPage=${requestScope.pager.currPage + 1 }">下一页</a>
					<a href="${pageContext.request.contextPath}/back/Product/queryProducts.bg?
					currPage=${requestScope.pager.pageCount }">尾页</a>
				</c:if>
		</div>

</body>
</html>
<script>
	$(function() {
		//为checkbox添加事件
		$(".checkedProduct").click(function(){
			var cbl = $(".checkedProduct").length;//.checkedProduct的长度
			$("#checkedAllProduct").prop("checked",$(".checkedProduct:checked").length==cbl);
		});
		$("#checkedAllProduct").click(function(){
			$(".checkedProduct").prop("checked",this.checked);
		});
		$("#resetQuery").click(function(){
			$("#criteriaProductName").val("");
		});
		$("#deleteCheckedProduct").click(function(){
			var cbl = $(".checkedProduct:checked").length;
			if(cbl <= 0){
				alert("请选择要操作的商品！");
				return false;
			}
			if(!confirm("确认删除选中的商品吗？")){
				return false;
			}
			var href = this.href;
			var checkedProduct = $(".checkedProduct").serialize();
			href = href + "&" +checkedProduct;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			window.location.href = href;
			//alert(href);
			return false;
		});
		//删除某一件商品
		$(".deleteOneProduct").click(function(){
			var href = this.href;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			if(confirm("确认删除该商品吗？")){
				window.location.href = href;
			}
			return false;
		});
		$('#addproducts').click(function() {
			var criteriaProduct = $("#criteriaProductName").serialize();
			window.location.href = "addproducts.jsp?currPage=${requestScope.pager.currPage}&" + criteriaProduct;
		});
		//给a连接添加事件，将查询信息带上
		$("a").not(".nohref").click(function(){
			var href = this.href;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			window.location.href = href;
			return false;
		});
		$("button.changeProductStatus").click(function(){
			if($(this).val()==1){
				$(this).html("<font color='red'>已下架</font>");
				$(this).val(0);
			}else {
				$(this).html("<font color='blue'>上架</font>");
				$(this).val(1);
			}
			var productStatus = $(this).val();
			var productId = $(this).parent("td").siblings().children(".checkedProduct").val();
			var json = {"productStatus":productStatus,"productId":productId};
			var url = "changeProductStatus.bg";
			$.post(url,json,'json');
		});
	});
</script>