<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath}/back/Admin/">

<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="../Css/jquery-ui-1.10.0.custom.css" />
<link rel="stylesheet" type="text/css"
	href="../Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="../Css/style.css" />
<script type="text/javascript" src="../Js/jquery.js"></script>
<script type="text/javascript" src="../Js/bootstrap.js"></script>
<script type="text/javascript" src="../Js/ckform.js"></script>
<script type="text/javascript" src="../Js/common.js"></script>
<script src="../assets/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
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
<script>
	$(function() {
		$('#backid').click(function() {
			window.location.href = "getManagers.bg?criteriaManagerName=${param.criteriaManagerName}";
		});
		var flag = false;
		$("input:password").focus(function(){
			this.style.imeMode = 'disabled';// 禁用输入法,禁止输入中文字符
		}).tooltip();
		$("#managerName").blur(function(){
			$(this).parent("td").children("font").remove();
			var managerName = $(this).val();
			if(!isManagerName(managerName)){
				$(this).parent("td").append("<font color='red'></br><i>账户名为3-16位的字母、数字、下划线组合</i><font>");
				return;
			}
			flag = true;
		}).tooltip();
		$("#managerPassword").blur(function(){
			$(this).parent("td").children("font").remove();
			var pass = $(this).val();
			if(!isPassword(pass)){
				$(this).parent("td").append("<font color='red'></br><i>密码为6-18位的字母、数字、下划线组合</i><font>");
				return;
			}
			flag = true;
		});
		$("#repassword").blur(function(){
			$(this).parent("td").children("font").remove();
			var pass = $("#managerPassword").val();
			var repass = $(this).val();
			if(!isPassword(repass)){
				$(this).parent("td").append("<font color='red'></br><i>密码为6-18位的字母、数字、下划线组合</i><font>");
				return;
			}
			if(pass!=repass){
				$(this).parent("td").append("<font color='red'></br><i>两次输入的密码不相同</i><font>");
				return;
			}
			flag = true;
		});
		$('#addsubmit').click(function() {
			var pass = $("#managerPassword").val();
			var repass = $("#repassword").val();
			$("#repassword").parent("td").children("font").remove();
			if(pass!=repass){
				$("#repassword").parent("td").append("<font color='red'></br><i>两次输入的密码不相同</i><font>");
				return;
			}
			var managerName = $("#managerName").val();
			var managerPassword = $("#managerPassword").val();
			var roleId = $("input[type='radio']:checked").val();//获取权限值
			if($.trim(managerName)=="" || $.trim(managerPassword)==""){
				alert("账户名和密码不能为空！");
				return;
			};
			if(!flag){
				return;
			}
			var json = {"managerName":managerName,"managerPassword":managerPassword,"roleId":roleId};
			var url = "Admin/addManager.bg";
			$.post(url,json,function(data){
				var addManagerStatus = data[0].addManagerStatus;
				if(addManagerStatus != null){
					alert(addManagerStatus);
					return;
				}
				var managerId = data[0].managerId;
				if(managerId == -1){
					alert("添加失败!");
				}else {
					alert("添加成功!");
				}
			},"json");
		});
	});
	function isPassword(pass){
    	var reg = /^[a-zA-Z0-9_]{6,18}$/;
    	return reg.test(pass);
    }
	function isManagerName(managerName){
    	var reg = /^[a-zA-Z0-9_]{3,16}$/;
    	return reg.test(managerName);
    }
</script>
</head>
<form action="addManager.bg" id="addForm" method="post">
	<table class="table table-bordered table-hover definewidth m10">
		<tr>
			<td width="10%" class="tableleft">账户名称</td>
			<td><input type="text" id="managerName" name="managerName" title="账户名为3-16位的字母、数字、下划线组合" /></td>
		</tr>
		<tr>
			<td class="tableleft">账户密码</td>
			<td><input type="password" id="managerPassword" name="managerPassword" title="密码为6-18位的字母、数字、下划线组合" /></td>
		</tr>
		 <tr>
			<td class="tableleft">确认密码</td>
			<td><input type="password" id="repassword" ></td>
		</tr>
		<tr>
			<td class="tableleft">添加权限</td>
			<td><input type="radio" value="1" name="promission">超级管理员&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" value="2" name="promission" checked="checked">业务管理员
			</td>
		</tr>
		<tr>
			<td class="tableleft"></td>
			<td>
				<button id="addsubmit" type="button" class="btn btn-primary" type="button">保存</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-success" name="backid" id="backid">返回</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>

