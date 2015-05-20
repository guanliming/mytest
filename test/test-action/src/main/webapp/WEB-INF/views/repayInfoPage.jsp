<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>还款信息</title>
<style type="text/css">
td {
	align: center
}
</style>
</head>
<body>
	<div align="center">
		<table border="1">
			<tr>
				<td>期数</td>
				<td>当期应还</td>
				<td>实际偿还</td>
				<td>累计总应还款</td>
			</tr>
			<c:forEach items="${repayList}" var="item" varStatus="status">
				<tr>
					<td>${item.period}</td>
					<td>${item.actualShouldRepay}</td>
					<td>${item.repay}</td>
					<td>${item.shouldRepayNoOnAccount}</td>
				</tr>
			</c:forEach>

		</table>
	</div>
</body>
</html>