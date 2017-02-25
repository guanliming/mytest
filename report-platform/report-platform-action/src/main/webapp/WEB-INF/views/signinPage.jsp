<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<script type="text/javascript">
	function validateUsername(){
		
	}
	
	function validateBeforeSubmit(){
		if(document.getElementById("usernameTxInput").value==""||
				document.getElementById("passwordTxInput").value==""){
			alert("请填写完成");
			return;
		}
		document.getElementById("signinForm").submit();
	}

</script>
</head>
<body>
<div align="center">
		<form id="signinForm" action="http://localhost:8080/test-action/signin"
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
					<td colspan="2" align="center">
<!-- 						<input type="submit" value="注册"> -->
						<input type="button" value="登录" onclick="validateBeforeSubmit()"/>
						<input type="reset" value="重置"></td>
				</tr>
			</table>

		</form>
	</div>
</body>
</html>