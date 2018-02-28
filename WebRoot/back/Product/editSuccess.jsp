<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="../Css/style.css" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/bootstrap.js"></script>
    <script type="text/javascript" src="../Js/ckform.js"></script>
    <script type="text/javascript" src="../Js/common.js"></script>

    <style type="text/css">
    table tbody tr td.productName a:link, a:visited, a:active {
    text-decoration: none;
	}
	table tbody tr td.productName a:hover { color: #D93600; text-decoration: none;}
	table tbody tr td.productName a {
	    color: #333;
	    outline: medium none;
	    text-decoration: none;
		display: block;
		color: #333;
		font-size: 16px;
		line-height: 18px;
		margin-left: 0px;
		text-overflow: ellipsis;
		white-space: nowrap;
		width: 450px;
		overflow: hidden;
		text-align: left;
	}
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
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
<form action="#" method="post" class="definewidth m20">
<!-- <input type="hidden" name="id" value="{$menu.id}" /> -->
<table class="table table-bordered table-hover m10">
    <tr>
        <td class="tableleft">名称</td>
        <td class="productName"><a target="_blank" href="${pageContext.request.contextPath }/getProduct.do?
					productid=${requestScope.product.productId}">
					${requestScope.product.productName}</a></td>
    </tr>
    <tr>
        <td class="tableleft">商品单价</td>
        <td>${requestScope.product.productPrice}</td>
    </tr>
    <tr>
        <td class="tableleft">库存</td>
        <td>${requestScope.product.storeNum}</td>
    </tr>
    <tr>
        <td class="tableleft">商品图片</td>
        <td><a target="_blank" href="${pageContext.request.contextPath }/getProduct.do?
					productid=${requestScope.product.productId }">
					<img src="${pageContext.request.contextPath }/${requestScope.product.productImagePath }" 
					style="cursor: pointer;" 
					height="100" width="100"></a>
        </td>
    </tr>
    <tr>
        <td class="tableleft">状态</td>
        <td >
        	${requestScope.product.productStatus==1?'上架':'下架'}
        </td>
    </tr>
     <tr>
        <td class="tableleft">描述</td>
        <td >
            ${requestScope.product.productDesc }
        </td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="button" class="btn btn-success" name="backid" id="backid">返回商品列表</button>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script>
    $(function () {       
		$('#backid').click(function(){
			window.location.href="${pageContext.request.contextPath}/back/Product/queryProducts.bg?"
			+"currPage=${requestScope.currPage}&criteriaProductName=${requestScope.criteriaProductName}";
		 });
    });
</script>