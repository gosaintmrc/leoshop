<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>LeoShop_注册</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<script src="js/jquery.easydropdown.js"></script>
<!-- <script type="text/javascript" src="js/verifyown.js"></script> -->
<style type="text/css">
	.submit {
		padding:10px 30px;
		color: #FFF;
		cursor: pointer;
		background:#555;
		border:none;
		outline:none;
		font-family: 'Exo 2', sans-serif;
		font-size:1em;
		margin-left: 80;
	}
	.submit:hover{
		background:#4CB1CA;
	}
	p.msg a, p.note a {
		text-decoration: underline;
		color:#555;
	}
	p.msg {
		float: right;
		font-size: 12px;
		padding: 15px 60px 0 15px;
	}
</style>
<script type="text/javascript" language="javascript">
	   $(function(){
		   var flag = false;//标记验证是否通过
		   var $username = $("#username");
		   var $password = $("#password");
		   var $confirmpass = $("#confirmpass");
		   var $username_msg = $("#username_msg");
		   var $password_msg = $("#password_msg");
		   var $confirmpass_msg = $("#confirmpass_msg");
		   var url = "regServlet.do";
		   
		   $username.focus(function(){
			   this.style.imeMode = 'disabled';// 禁用输入法,禁止输入中文字符
			   $username_msg.html("<br/>");
		   }).blur(function(){
			   var username = $username.val();
			   if(!isUsername(username)){
				   $username_msg.text("长度8-16个字符,以字母开头,可包含数字和下划线,不能含有特殊字符.");
				   return;
			   }
			   var json = {"username":username};
			   $.post(url,json,function(data){
				   var regStatus = data[0].regStatus;
				   if(regStatus=="hasThisUser"){
					   $username_msg.text("用户名已存在！");
				   }else {
					   $username_msg.html("<img src='images/ok.jpg'>");
					   flag = true;
				   }
				   if(regStatus=="regSuccess"){
					   flag = true;
				   }
			   },"json");
			   
		   });
		   $password.focus(function(){
			   this.style.imeMode = 'disabled';// 禁用输入法,禁止输入中文字符
			   $password_msg.html("<br/>");
		   }).blur(function(){
			   var password = $password.val();
			   if(!isPassword(password)){
				   $password_msg.text("密码长度6-18个字符,必须包含字母、数字、下划线等特殊字符.");
				   return;
			   }
			   flag = true;
			   $password_msg.html("<img src='images/ok.jpg'>");
		   });
		   $confirmpass.focus(function(){
			   this.style.imeMode = 'disabled';// 禁用输入法,禁止输入中文字符
			   $confirmpass_msg.html("<br/>");
		   }).blur(function(){
			   var confirmpass = $confirmpass.val();
			   var password = $password.val();
			   if(!isPassword(confirmpass)){
				   $confirmpass_msg.text("密码长度6-18个字符,必须包含字母、数字、下划线等特殊字符.");
				   return;
			   }
			   if(!isSame(confirmpass, password)){
				   $confirmpass_msg.text("两次输入的密码不同");
				   return;
			   }
			   flag = true;
			   $confirmpass_msg.html("<img src='images/ok.jpg'>");
		   });
		   $("#registerSubmit").click(function(){
			   var username = $username.val();
			   var password = $password.val();
			   var confirmpass = $confirmpass.val();
			   if($.trim(username)=="" || $.trim(confirmpass)==""){
				   alert("用户名和密码不能为空");
				   return false;
			   }
			   if(!isUsername(username)){
				   $username_msg.text("长度8-16个字符,以字母开头,可包含数字和下划线,不能含有特殊字符.");
				   return false;
			   }
			   if(!isPassword(password)){
				   $password_msg.text("密码长度6-18个字符,必须包含字母、数字、下划线等特殊字符.");
				   return false;
			   }
			   if(!isSame(confirmpass, password)){
				   $confirmpass_msg.text("两次输入的密码不同");
				   return false;
			   }
			   if(!flag){
				   return false;
			   }
			   var json = {"username":username,"password":password};
			   $.post(url,json,function(data){
				   var regStatus = data[0].regStatus;
				   if(regStatus=="hasThisUser"){
					   $username_msg.text("用户名已存在！");
				   }
				   if(regStatus=="regSuccess"){
					   if(confirm("注册成功，点击确定进入主页")){
						   window.location.href = "AboutBlank.jsp";
					   }
				   }
				   if(regStatus=="regFail"){
					   alert("服务器异常，注册失败");
				   }
			   },"json");
			   return false;
		   });
	   });
	   
	   function isSame(str1,str2){
		   return str1==str2;
	   }
	   
	   function isUsername(username){
		   var name = new RegExp("^[a-zA-z][a-zA-Z0-9_]{7,15}$");
		   return name.test(username);
	   };
	  
	   function isPassword(password){
		   var word = new RegExp("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,18}");
		   return word.test(password);
	   }
	   
</script>
</head>
<body> 
<!-- 每个页面均以这样的方式包含 header 提交到servlet的地址（即request.getServletPath()获得到的地址） 是以.jsp结尾的 -->
	<jsp:include page='responseHeaderInfo.do' flush="true"></jsp:include>
<!-- 在baseServlet中加判断当请求的地址是以.jsp结尾时 调用方法 responseHeaderInfo;目前没有找到更好的解决方法-->
          <div class="register_account">
          	<div class="wrap">
    	      <h4 class="title">创建一个帐户</h4>
	    		<div class="register_account_form">
	    		   <form action="#" method="post">
		    			<table id="register_account_table">
		    				<tr>
		    					<td width="15%">用户名</td>
		    					<td width="100%">
		    					<input value="" type="text" id="username" name="username"><br/>
		    					<span id="username_msg" style="color:red;font-size: 0.8em;line-height: 24px">
		    					<br/></span>
		    					</td>
		    				</tr>
		    				<tr>
		    					<td>密码</td>
		    					<td>
		    					<input value="" id="password" type="password" name="password"><br/>
		    					<span id="password_msg" style="color:red;font-size: 0.8em;line-height: 24px"><br/></span>
		    					</td>
		    				</tr>
		    				<tr>
		    					<td>确认密码</td>
		    					<td align="left">
		    					<input value="" id="confirmpass" type="password" name="confirmpass"><br/>
		    					<span id="confirmpass_msg" style="color:red;font-size: 0.8em;line-height: 24px"><br/></span>
		    					</td>
		    				</tr>
		    			</table>
		    			<div align="center">
		    				<label style="color: #555">
		    					用户名长度8-16个字符,以字母开头,可包含数字和下划线,不能含有特殊字符.<br/>
		    					密码长度6-18个字符,必须包含字母、数字、下划线等特殊字符.
		    				</label>
		    			</div>
		    			<input type="submit" id="registerSubmit" value="注册" class="submit">
						<p class="msg">点击同意<a target="_blank" href="delivery.jsp">《条款和条件》</a>并“创建帐户”.</p>
					</form>
		    	 </div>
		    <div class="clear"></div>
    	</div>
    </div>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>