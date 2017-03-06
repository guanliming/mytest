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
		<%
			if (request.getAttribute("instalmentBo") != null) {
		%>
		<div>
			<div>借款金额:${instalmentBo.borrowAmount}</div>
			<div>借款类型:分期还款</div>
		</div>
		<div>
			<div>还款期限:${instalmentBo.period}</div>
			<div>每期应还:${instalmentBo.monthlyRepay}</div>
		</div>
		<div>当前挂账金额:${instalmentBo.onAccount}</div>
		<%
			}
		%>
		<%
			if (request.getAttribute("oneOffBo") != null) {
		%>
		<div>
			<div>借款金额:${oneOffBo.borrowAmount}</div>
			<div>借款类型:一次还本付息</div>
		</div>
		<div>
			<div>当期应还:${oneOffBo.monthlyRepay}</div>
			<div>当前挂账金额:${oneOffBo.onAccount}</div>
		</div>
		<%
			}
		%>
		<table border="1">
			<tr>
				<td>当期期数</td>
				<td>当期应还</td>
				<td>当期实际偿还</td>
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
		<a href="http://localhost:8080/report-platform-action/main">返回</a>
	</div>
</body>
</html>