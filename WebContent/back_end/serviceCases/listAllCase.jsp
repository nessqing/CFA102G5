<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.serviceCases.model.*"%>
    
<%
    ServiceCasesService scSvc = new ServiceCasesService();
	List<ServiceCasesVO> list = scSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<%@ include file="/back_end/commonCSS.file" %>
<title>所有案件資料 - listAllCase.jsp</title>
<style>
  
</style>
</head>

<body>
<%-- 		<%@ include file="/back_end/loading.file" %> <!-- loading --> --%>
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
		
<div class="main-content card card-body table-responsive">
<table id="example4" class="display" style="min-width: 845px">
<thead>
	<tr>
		<th>案件編號</th>
		<th>會員編號</th>
		<th>案件標題</th>
		<th>案件描述</th>
		<th>案件回覆</th>
		<th>案件狀態</th>
		<th>回覆</th>

	</tr>
</thead>
<%-- 	<%@ include file="page1.file" %>  --%>
<c:forEach var="serviceCasesVO" items="${list}">
<%-- 	<c:forEach var="serviceCasesVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"> --%>
		
		<tr>
			<td>${serviceCasesVO.case_no}</td>
			<td>${serviceCasesVO.mem_no}</td>
			<td>${serviceCasesVO.case_title}</td>
			<td>${serviceCasesVO.case_des}</td>
			<td>${serviceCasesVO.case_reply}</td> 
			<td>
			<c:if test="${serviceCasesVO.case_state == 2}">
   			<span class="badge badge-primary light">done</span>
			</c:if>
			<c:if test="${serviceCasesVO.case_state == 1}">
   			<span class="badge badge-danger">未處理</span>
			</c:if>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/serviceCases/ServiceCases.do" style="margin-bottom: 0px;">
<!-- 			     <input type="submit" value="回覆"> -->
			     <button type="submit" class="btn btn-primary btn-lg"" value="回覆">Reply</button>
			     <input type="hidden" name="case_no" value="${serviceCasesVO.case_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update">
			  </FORM>
			</td>

</tr>
	</c:forEach>
</table>
<ul>
  <li><a href="<%=request.getContextPath()%>/back_end/serviceCases/addCase.jsp">Add</a> a new Case.</li>
</ul>
</div>


<%@ include file="/back_end/commonJS.file" %>
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("案件列表");
</script>
</body>
</html>