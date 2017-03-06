<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>借款页面</title>
<script type="text/javascript">
	function showPeriodRow(value) {
		if (value == 0) {
			document.getElementById("periodRow").style.visibility = "hidden";
			document.getElementById("periodRow").style.display = "none";
		}
		if (value == 1) {
			document.getElementById("periodRow").style.visibility = "visible";
			document.getElementById("periodRow").style.display = "";
		}
	}
	
	function autoModify(txt) {
			txt.value = txt.value.replace(/[^\d]/g, '');
			if (txt.value > 1000) {
				txt.value = 1000;
			}
		}
		
		function borrow(){
			document.getElementById("borrowForm").submit();
		}
</script>
</head>
<body>
	<div align="center">
		<form id="borrowForm" action="http://localhost:8080/report-platform-action/borrow" method="post">
			<table>
				<tr>
					<td>借款金额:</td>
					<td><input id="borrowAmountTxt" type="text" name="borrowAmount"
						onkeyup="autoModify(this)" /></td>
					<!-- 					<td><input type="text" name="borrowAmount" onkeyup="this.value=this.value.replace(/[^\d,^\.]/g,'')"/></td> -->
				</tr>
				<tr>
					<td colspan="1">还款方式:</td>
					<td><select name="mode"
						onchange="showPeriodRow(this.options.selectedIndex);">
							<option value="00" selected="selected">一次全额还款</option>
							<option value="01">分期还款</option>
					</select></td>
				</tr>
				<tr id="periodRow" style="visibility: hidden; display: none">
					<td>选择分期</td>
					<td><select name="period">
							<option value="3">3个月</option>
							<option value="6">6个月</option>
							<option value="9">9个月</option>
							<option value="12">12个月</option>
							<option value="24">24个月</option>
							<option value="36">36个月</option>
					</select></td>
				</tr>
				<tr align="center">
					<td colspan="2"><input  type="button" value="借款" onclick="borrow();" />
<!-- 						<input type="reset" value="重置"></td> -->
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>