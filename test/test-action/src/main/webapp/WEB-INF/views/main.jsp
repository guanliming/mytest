<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页面</title>
</head>
<body>
		你好呀，${sessionScope.SESSION_LOGIN_NAME}，欢迎登录！<a href="http://localhost:8080/test-action/signout">注销</a></br>
		<a href="http://localhost:8080/test-action/borrowPage">我要借款</a>
		<a href="http://localhost:8080/test-action/repayPage">我要还款</a>
<%-- 		<%=request.getAttribute("msg")%> --%>
		<%if(request.getAttribute("msg")!=null) {
			%>
			<script type="text/javascript">
				alert("有借款没还清，不能借款!");
			</script>
		<% }%>
		</br>
</body>
</html>