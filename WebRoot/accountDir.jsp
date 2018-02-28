<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
		width: 800px;
	}
	#cart-wrapper{
		width: 800px;
		height:330px;
	}
	#cart-wrapper table{
		width: 800px;
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
	.btn {
		padding:10px 30px;
		color: #FFF;
		cursor: pointer;
		background:#555;
		border:none;
		outline:none;
		font-family: 'Exo 2', sans-serif;
		font-size:1em;
		margin-left: 75%;
	}
	.btn:hover{
		background:#4CB1CA;
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
</style>
</head>
<body>
	<div class="mycar-index">
		<c:choose>
			<c:when test="${!(empty pager.pageDataList)}">
				<div id="cart-wrapper">
					<form action="accountDirTab.jsp">
						<table cellspacing="1.5">
							<thead>
								<tr>
									<th width="100px">序号</th>
									<th width="550px">收货地址</th>
									<th width="150px">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.pageDataList}" var="add" varStatus="statu">
									<tr>
										<td align="center">${statu.count+(pager.currPage-1)*pager.pageSize}</td>
										<td>${add.sendPlace}&nbsp;&nbsp;(&nbsp;${add.sendMan}&nbsp;&nbsp;&nbsp;收&nbsp;)&nbsp;&nbsp;${add.sendPhone}</td>
										<td align="center"><a href="deleteAddressById.do?addId=${add.addressId}" onclick="return confirm('您确定删除吗？');">删除</a>
											&nbsp;&nbsp;&nbsp;&nbsp;<a href="getAddressById.do?addId=${add.addressId}">修改</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div><br/></div>
						<c:if test="${pager.currPage==pager.pageCount}">
							<input type="submit" class="btn" value="添加收货地址">
						</c:if>
					</form>
				</div>
				<div><br/><br/><br/></div>
				<div align="center" id="page">
					<c:if test="${pager.currPage==1}">
						首页&nbsp;&nbsp;&nbsp;
						上一页&nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${pager.currPage>1}">
						<a href="getAddressPager.do?currPage=1">首页</a>&nbsp;&nbsp;&nbsp;
						<a href="getAddressPager.do?currPage=${pager.currPage-1}">上一页</a>&nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${pager.currPage==pager.pageCount}">
						下一页&nbsp;&nbsp;&nbsp;
						尾页&nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${pager.currPage<pager.pageCount}">
						<a href="getAddressPager.do?currPage=${pager.currPage+1}">下一页</a>&nbsp;&nbsp;&nbsp;
						<a href="getAddressPager.do?currPage=${pager.pageCount}">尾页</a>&nbsp;&nbsp;&nbsp;
					</c:if>
					第${pager.currPage}/${pager.pageCount}页&nbsp;&nbsp;共${pager.dataCount}条数据
				</div>
			</c:when>
			<c:otherwise>
				<div class="order-blank">
					您还没有填写收货地址信息，赶快<a href="accountDirTab.jsp"><font color="#555">点击添加</font></a>吧！
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<br/>
</body>
</html>