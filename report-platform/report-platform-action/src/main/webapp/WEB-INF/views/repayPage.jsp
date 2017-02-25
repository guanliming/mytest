<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>还款页面</title>
</head>
<body>
	<div align="center">
		本期应还<%=request.getAttribute("actualShouldRepay") %>
		<form id="registerForm" action="http://localhost:8080/test-action/repay"
			method="post" >
			<table border="0">
				<tr>
					<td>还款金额：</td>
					<td><input type="text" name="repayAccount"/></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="还款" />
						<input type="reset" value="重置"></td>
				</tr>
			</table>

		</form>
	</div>
	
</body>
</html>