<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<script type="text/javascript">
	var userNameCanUser = false;

	function validateUsername() {
		if (document.getElementById("usernameTxInput").value == "") {
			document.getElementById("usernamePrompt").innerHTML = "用户名不能为空";
			return;
		}
		var xmlHttp;
		if (window.XMLHttpRequest) { // code for IE7+
			xmlHttp = new XMLHttpRequest();
		} else { // code for IE5/IE6
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				//document.getElementById("myDiv").innerHTML=xmlHttp.responseText;
				if (xmlHttp.responseText == "false") {
					document.getElementById("usernamePrompt").innerHTML = "用户名不可用";
				} else {
					document.getElementById("usernamePrompt").innerHTML = "";
					userNameCanUser = true;
				}
			}
		}
		var a = document.getElementById("usernameTxInput").value;
		// get
		xmlHttp.open("GET", "/report-platform-action/account.validate?account=" + a, true);
		xmlHttp.send();
	}

	function delData1() {
		document.getElementById("usernameTxInput").innerHTML = "";
	}

	function confirmPassword() {
		if (document.getElementById("passwordTxInput").value != document
				.getElementById("passwordConfirmTxInput").value) {
			document.getElementById("passwordConfirmPrompt").innerHTML = "密码不一致";
			return;
		}
		document.getElementById("passwordConfirmPrompt").innerHTML = "";
	}

	function validateBeforeSubmit() {
		if(userNameCanUser==true && document.getElementById("passwordTxInput").value == document
				.getElementById("passwordConfirmTxInput").value){
			document.getElementById("registerForm").submit();
			return ;
		}
		alert("看请提示！");
	}
</script>
</head>
<body>
	<div align="center">
		<form id="registerForm" action="http://localhost:8080/report-platform-action/register"
			method="post" >
			<table border="0">
				<tr>
					<td>用户名：</td>
					<td><input id="usernameTxInput" type="text" name="username"
						onblur="validateUsername();" onfocus="delData1();" value="" /></td>
					<td><div id="usernamePrompt"></div></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input id="passwordTxInput" type="password"
						name="password" value="" /></td>
					<td><div id="passwordPrompt"></div></td>
				</tr>
				<tr>
					<td>密码确认：</td>
					<td><input id="passwordConfirmTxInput" type="password"
						name="passwordConfirm" onchange="confirmPassword();" /></td>
					<td><div id="passwordConfirmPrompt"></div></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
<!-- 						<input type="submit" value="注册"> -->
						<input type="button" value="注册" onclick="validateBeforeSubmit()"/>
						<input type="reset" value="重置"></td>
				</tr>
			</table>

		</form>
	</div>
</body>
</html>