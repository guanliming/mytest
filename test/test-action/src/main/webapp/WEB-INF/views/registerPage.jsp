<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<script type="text/javascript">
	function validateUsername(){
		  if (document.getElementById("usernameTxInput").value == "") {
              document.getElementById("usernamePrompt").innerHTML = "用户名不能为空";
              return;
          }
		   var xmlHttp;
           if(window.XMLHttpRequest) { // code for IE7+
               xmlHttp = new XMLHttpRequest();
           }
           else { // code for IE5/IE6
               xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
           }

           xmlHttp.onreadystatechange = function () {
               if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                   //document.getElementById("myDiv").innerHTML=xmlHttp.responseText;
                   if (xmlHttp.responseText == "false") {
                       document.getElementById("usernamePrompt").innerHTML = "用户名不可用";
                   }
//                    else {
//                        document.getElementById("accDiv").innerHTML = "用户名可用";
//                    }
               }
           }
           var a = document.getElementById("usernameTxInput").value;
           // get
           xmlHttp.open("GET", "/test-action/account.validate?account=" + a , true);
           xmlHttp.send();
	}
	
	  function delData1() {
          document.getElementById("usernameTxInput").value = "";
      }
	  
	  function confirmPassword(){
		  if(document.getElementById("passwordTxInput").value!=document.getElementById("passwordConfirmTxInput").value){
			  document.getElementById("passwordConfirmPrompt").value="密码不一致";
		  }
	  }

</script>
</head>
<body>
	<div align="center" >
		<form action="http://localhost:8080/test-action/register"
			method="post">
			<table border="0" >
				<tr>
					<td>用户名：</td>
					<td><input id="usernameTxInput" type="text" name="username"  onblur="validateUsername();" onfocus="delData1();" /></td>
					<td><div id="usernamePrompt"></div></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input id="passwordTxInput" type="password" name="password" /></td>
					<td><div id="passwordPrompt"></div></td>
				</tr>
				<tr>
					<td>密码确认：</td>
					<td><input id="passwordConfirmTxInput" type="password" name="passwordConfirm" onchange="confirmPassword();" /></td>
					<td><div id="passwordConfirmPrompt"></div></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="注册"> <input
						type="reset" value="重置"></td>
				</tr>
			</table>

		</form>
	</div>
</body>
</html>