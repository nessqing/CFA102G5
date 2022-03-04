<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    EmpService empSvc = new EmpService();
    List<EmployeeVO> list = empSvc.getAllEmp();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="deptSvc" scope="page" class="com.department.model.DepService" />
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
</head>
<body bgcolor='white'>
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

	<table id="example4" class="display" style="min-width: 845px">
	<thead>
	<tr>
		<th>員工編號</th>
		<th>名字</th>
		<th>郵件</th>
		<th>狀態</th>
		<th>所在部門</th>
		<th>修改</th>

	</tr>
	</thead>
	<c:forEach var="EmployeeVO" items="${list}">
		
		<tr>
			<td>${EmployeeVO.emp_no}</td>
			<td>${EmployeeVO.emp_name}</td>
			<td>${EmployeeVO.emp_mail}</td>
			<td>${EmployeeVO.emp_state}</td>
			<td>
				${deptSvc.getOnePK(EmployeeVO.dep_no).dep_name}
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmployeeServlet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" class="btn btn-secondary">
			     <input type="hidden" name="emp_no" value="${EmployeeVO.emp_no}">
			     <input type="hidden" name="action"	value="Update_One"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("員工列表");
		</script>
</body>
</html>