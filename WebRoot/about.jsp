<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_关于我们</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
	<div class="login">
		<div class="wrap">
			<ul class="breadcrumb breadcrumb__t">
				<a class="home" href="AboutBlank.jsp">主页</a> / 关于
			</ul>
			<div class="section group">
				<div class="labout span_1_of_about">
					<h3>授权鉴定书</h3>
					<div class="testimonials ">
						<div class="testi-item">
							<blockquote class="testi-item_blockquote">
								<a href="#"> 1、请求办理的条件：

									专利证书证明只向专利权人出具。对处于年费滞纳期、恢复期或者已经终止的专利，国家知识产权局不再出具专利证书证明。

									2、请求办理所需材料： (1)需提交专权利人或其本案代理机构签章的《办理证明文件请求书》。

									(2)专利权人当面、邮寄方式办理的，需提供专利权人的身份证明。专利权人委托他人来办理的，需要提交委托书原件、经办人身份证明、专利权人身份证明复印件。

									3、请求方式： (1)面交请求可以通过专利局受理大厅专利申请流程服务窗口办理。 (2)邮寄请求

									请求书邮寄地址：北京市海淀区蓟门桥西土城路6号

									收件人名称：国家知识产权局专利局初审及流程管理部业务发文管理处(专利局初审部发文处) 邮政编码：100088 </a>
								<div class="clear"></div>
							</blockquote>
							<small class="testi-meta"><span class="user">我们的网址</span>,
								<span class="info">坐特网</span><br> <a href="#">http://demolink.org</a>
							</small>
						</div>
					</div>
					<div class="testimonials ">
						<div class="testi-item"></div>
					</div>
				</div>
				<div class="cont span_2_of_about">
					<h3>我们的团队</h3>
					<p>五大股东</p>
					<p>请假逃学五人组</p>
					<h5 class="m_6">模特</h5>
					<div class="section group">
						<div class="col_1_of_about-box span_1_of_about-box">
							<a class="popup-with-zoom-anim" href="#small-dialog3"> <span
								class="rollover"></span><img src="images/a-img.jpg"
								title="continue" alt="" /> </a>
							<div id="small-dialog3" class="mfp-hide">
								<div class="pop_up2">
									<h2>值得拥有</h2>
									<p>请假逃学五人组请假逃学五人组请假逃学五人组请假逃学五人组请假逃学五人组</p>
								</div>
							</div>
							<h4 class="m_7">
								<a href="#">男士墨镜</a>
							</h4>
							<p>主要是靠本身对光线的吸收来达到 颜色墨镜 颜色墨镜
								降低阳光强度的目的。在玻璃中加入不同的着色化合物，制成不同颜色的墨镜，它们吸收的光波波长也不尽相同。</p>
						</div>
						<div class="col_1_of_about-box span_1_of_about-box">
							<a class="popup-with-zoom-anim" href="#small-dialog3"> <span
								class="rollover"></span><img src="images/a-img1.jpg"
								title="continue" alt="" /> </a>
							<h4 class="m_7">
								<a href="#">妇女墨镜</a>
							</h4>
							<p>主要是靠本身对光线的吸收来达到 颜色墨镜 颜色墨镜
								降低阳光强度的目的。在玻璃中加入不同的着色化合物，制成不同颜色的墨镜，它们吸收的光波波长也不尽相同。</p>
						</div>
						<div class="col_1_of_about-box span_1_of_about-box">
							<a class="popup-with-zoom-anim" href="#small-dialog3"> <span
								class="rollover"></span><img src="images/a-img2.jpg"
								title="continue" alt="" /> </a>
							<h4 class="m_7">
								<a href="#">少女墨镜</a>
							</h4>
							<p>主要是靠本身对光线的吸收来达到 颜色墨镜 颜色墨镜
								降低阳光强度的目的。在玻璃中加入不同的着色化合物，制成不同颜色的墨镜，它们吸收的光波波长也不尽相同。</p>
						</div>
						<div class="clear"></div>

					</div>
					<!-- Add fancyBox main JS and CSS files -->
					<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
					<link href="css/magnific-popup.css" rel="stylesheet"
						type="text/css">
					<script>
						$(document).ready(function() {
							$('.popup-with-zoom-anim').magnificPopup({
								type : 'inline',
								fixedContentPos : false,
								fixedBgPos : true,
								overflowY : 'auto',
								closeBtnInside : true,
								preloader : false,
								midClick : true,
								removalDelay : 300,
								mainClass : 'my-mfp-zoom-in'
							});
						});
					</script>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>