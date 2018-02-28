var xmlhttp;
function verify() {

	var userName = document.getElementById("username").value;

	if (window.XMLHttpRequest) {

		xmlhttp = new XMLHttpRequest();

		if (xmlhttp.overrideMimeType) {
			xmlhttp.overrideMimeType("text/xml");
		}
	} else if (window.ActiveXObject) {

		var activexName = [ "MSXML2.XMLHTTP", "Microsoft.XMLHTTP" ];
		for ( var i = 0; i < activexName.length; i++) {
			try {

				xmlhttp = new ActiveXObject(activexName[i]);
				break;
			} catch (e) {
			}
		}
	}

	if (!xmlhttp) {
		alert("XMLHttpRequest对象创建失败!!");
		return;
	} else {
		//alert(xmlhttp.readyState);
	}

	xmlhttp.onreadystatechange = callback;

	xmlhttp.open("GET", "servlet/CheckUsernameIsRegServlet?username=" + userName, true);

	xmlhttp.send(null);
}

function callback() {

	if (xmlhttp.readyState == 4) {

		if (xmlhttp.status == 200) {

			var responseText = xmlhttp.responseText;
			document.getElementById("verify_result").value=responseText.replace(/^\s*/,"").replace(/\s*$/,"");
			var divNode = document.getElementById("username_msg");
		    
			/*if(document.getElementById("verify_result").value=="error")
			{
		   	    divNode.innerHTML = "<b style='color:red'>&nbsp;&nbsp;用户名必须大于等于6位！</b>";
				return;
			}*/
			
			if(document.getElementById("verify_result").value=="false")
            {
			  divNode.innerHTML = "<b style='color:red'>&nbsp;&nbsp;该用户名已被注册！</b>";
            }
            else
            {
			  divNode.innerHTML = "<b style='color:green'>&nbsp;&nbsp;恭喜你，该用户名可以使用！</b>";
            }			
		} else {
			alert("出错了！！！");
		}
	}
}