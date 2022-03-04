<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>

<%
EmployeeVO empVO = (EmployeeVO) request.getAttribute("empVO");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
</head>
<body>
	<%@ include file="/back_end/header.file" %> <!-- Header -->
	<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
		
		
		<div class="main-content card card-body table-responsive">

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmployeeServlet.do" name="form1">
<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td>員工編號:</td>
		<td><%=empVO.getEmp_no()%></td>
	</tr>
	<tr>
		<td>員工密碼:</td>
		<td><%=empVO.getEmp_password()%></td>
	</tr>
	<tr>
		<td>員工姓名:</td>
		<td><%=empVO.getEmp_name()%></td>
	</tr>
	<tr>
		<td>員工郵件:</td>
		<td><%=empVO.getEmp_mail()%></td>
	</tr>
	<tr>
		<td>員工狀態</td>
		<td>${empVO.emp_state}</td>
	</tr>

	<jsp:useBean id="deptSvc" scope="page" class="com.department.model.DepService" />
	<tr>
		<td>部門:</td>
		<td>
			${deptSvc.getOnePK(empVO.dep_no).dep_name}
		</td>
	</tr>

</table>
</FORM>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("員工資料");
		</script>
</body>
</html>