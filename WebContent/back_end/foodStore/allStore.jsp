<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodStore.model.*"%>

<%
    FoodStoreService fdStoreSvc = new FoodStoreService();

    List<FoodStoreVO> list = fdStoreSvc.getAllFoodStore();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="fdclassSvc" scope="page" class="com.foodClass.model.FoodClassService" />
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<style>
 
</style>
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
		<th>店家編號</th>
		<th>店家類別</th>
		<th>店家名稱</th>
		<th>店家地址</th>
		<th>店家經度</th>
		<th>店家緯度</th>
		<th>店家服務</th>
		<th>店家狀態</th>
		<th>修改</th>
		<th>查看照片</th>
		<th>新增照片</th>

	</tr>
</thead>
	<c:forEach var="FoodStoreVO" items="${list}">
		
		<tr>
			<td>${FoodStoreVO.fd_no}</td>
			<td>
				${fdclassSvc.getClassPK(FoodStoreVO.fd_class_no).fd_class_name}
			</td>
			<td>${FoodStoreVO.fd_name}</td>
			<td>${FoodStoreVO.fd_address}</td>
			<td>${FoodStoreVO.fd_longitude}</td>
			<td>${FoodStoreVO.fd_latitude}</td>
			<td>${FoodStoreVO.fd_service}</td>
			<td>${FoodStoreVO.fd_state}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodStoreServlet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" class="btn btn-primary btn-sm">
			     <input type="hidden" name="fd_no" value="${FoodStoreVO.fd_no}">
			     <input type="hidden" name="action"	value="Update_One"></FORM>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodImg.do" style="margin-bottom: 0px;">
			     <input type="submit" value="查看照片" class="btn btn-primary btn-sm">
			     <input type="hidden" name="fd_no" value="${FoodStoreVO.fd_no}">
			     <input type="hidden" name="action"	value="getOneFoodStoreImg"></FORM>
			</td>
			<td>
		<a href="<%=request.getContextPath()%>/back_end/foodImg/addImg.jsp?fd_no=${FoodStoreVO.fd_no}" class="btn btn-primary btn-sm">新增照片</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("店家列表");
		</script>
		
</body>
</html>