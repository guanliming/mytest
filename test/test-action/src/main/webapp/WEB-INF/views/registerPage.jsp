<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
</head>
<body>
	<div align="center" >
		<form action="http://localhost:8080/test-action/register"
			method="post">
			<table border="0" >
				<tr>
					<td>用户名：</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td>密码确认：</td>
					<td><input type="password" name="passwordConfirm" /></td>
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