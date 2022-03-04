<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>

<%
EmployeeVO empVO = (EmployeeVO) request.getAttribute("empVO");
%>
<html>
<head>
<style>
#example4{
 display: inline-block;
 width:70%!important;
 margin:0;
}
</style>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<meta charset="UTF-8">
</head>
<body>
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
		
<div class="main-content card card-body table-responsive">
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmployeeServlet.do" name="form1">
<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td>員工編號:</td>
		<td><%=empVO.getEmp_no()%></td>
	</tr>
	<tr>
		<td>員工密碼:</td>
		<td><input type="TEXT" name="emp_password" size="20" value="<%=empVO.getEmp_password()%>" /></td>
	</tr>
	<tr>
		<td>員工姓名:</td>
		<td><input type="TEXT" name="emp_name" size="20" value="<%=empVO.getEmp_name()%>" /></td>
	</tr>
	<tr>
		<td>員工郵件:</td>
		<td><input type="TEXT" name="emp_mail" size="50" value="<%=empVO.getEmp_mail()%>" /></td>
	</tr>
	<tr>
		<td>員工狀態:</td>
		<td><input type="radio" id="true" name="emp_state"  value="true" ${(empVO.emp_state==true)?'checked':'' }>在職</td>
		<td><input type="radio" id="false" name="emp_state"  value="false" ${(empVO.emp_state==false)?'checked':'' }>離職</td>
	</tr>

	<jsp:useBean id="deptSvc" scope="page" class="com.department.model.DepService" />
	<tr>
		<td>部門:</td>
		<td><select size="1" name="dep_no" >
			<c:forEach var="DepartmentVO" items="${deptSvc.all}">
				<option value="${DepartmentVO.dep_no}" ${(empVO.dep_no==DepartmentVO.dep_no)?'selected':'' } >${DepartmentVO.dep_name}
			</c:forEach>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="emp_no" value="<%=empVO.getEmp_no()%>">
<input type="submit" value="送出修改" class="btn btn-primary"></FORM>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("修改員工資料");
		</script>
</body>
</html>