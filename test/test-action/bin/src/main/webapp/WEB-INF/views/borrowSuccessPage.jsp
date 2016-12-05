<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>借款成功</title>
</head>
<body>
	${sessionScope.SESSION_LOGIN_NAME}，您已成功借款${param.borrowAmount}元。</br>
	<% if(request.getParameter("mode")!=null && StringUtils.equals(request.getParameter("mode").toString(), "01")){ %>
	您将进行为期${param.period}个月的分期付款，每期的应还金额为${monthlyRepay} !
	<%} %>
	<% if(request.getParameter("mode")!=null && StringUtils.equals(request.getParameter("mode").toString(), "00")){ %>
	您选择在当期全额偿还,偿还金额为${monthlyRepay}!
	<%} %>
	<a href="http://localhost:8080/test-action/main">返回</a>
</body>
</html>