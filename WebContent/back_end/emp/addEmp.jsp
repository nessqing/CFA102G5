<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>
<%
EmployeeVO empVO = (EmployeeVO) request.getAttribute("empVO");
%>
<html>
<head>
<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<title>Insert title here</title>
<style>

#example4{
 display: inline-block;
 width:70%!important;
 margin:0;
}

</style>
</head>
<body>
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="main-content card card-body table-responsive" id="fi">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmployeeServlet.do" name="form1">
<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" maxlength="5" name="emp_name" size="20" 
			 value="<%= (empVO==null)? "" : empVO.getEmp_name()%>" /></td>
	</tr>
	<tr>
		<td>郵件:</td>
		<td><input type="TEXT" maxlength="50" name="emp_mail" size="20"
			 value="<%=(empVO==null)? "" : empVO.getEmp_mail()%>" /></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="text" maxlength="15" name="emp_password" size="20"
			 value="<%=(empVO==null)? "" :empVO.getEmp_password()%>" /></td>
	</tr>
	<tr>
		<td style="width:50px">狀態:</td>
		<td style="width:50px"><input type="radio" name="emp_state" value="true" checked>在職</td>
		<td><input type="radio" name="emp_state" value="false">離職</td>
	</tr>

	<jsp:useBean id="deptSvc" scope="page" class="com.department.model.DepService" />
	<tr>
		<td>部門:</td>
		<td><select size="1" name="dep_no">
			<c:forEach var="deptVO" items="${deptSvc.all}">
				<option value="${deptVO.dep_no}" ${(empVO.dep_no==deptVO.dep_no)? 'selected':'' } >${deptVO.dep_name}
			</c:forEach>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增" class="btn btn-primary"></FORM>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("新增員工");
		</script>
</body>
</html>