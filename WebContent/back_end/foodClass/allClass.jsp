<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodClass.model.*"%>

<%
    FoodClassService classSvc = new FoodClassService();
    List<FoodClassVO> list = classSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<meta charset="BIG5">
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
<table id="example4" class="display" style="min-width: 845px">
<thead>
	<tr>
		<th>類別編號</th>
		<th>類別名稱</th>
		<th>類別狀態</th>
		<th>修改</th>

	</tr>
</thead>
	<c:forEach var="FoodClassVO" items="${list}" >
		
		<tr>
			<td>${FoodClassVO.fd_class_no}</td>
			<td>${FoodClassVO.fd_class_name}</td>
			<td>${FoodClassVO.fd_class_state}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodClassServlet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" class="btn btn-secondary">
			     <input type="hidden" name="fd_class_no" value="${FoodClassVO.fd_class_no}">
			     <input type="hidden" name="action"	value="Update_One"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("類別列表");
		</script>
</body>
</html>