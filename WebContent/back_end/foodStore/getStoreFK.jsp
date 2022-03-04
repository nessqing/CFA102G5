<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodStore.model.*"%>

<jsp:useBean id="storeSvc" scope="page" class="com.foodClass.model.FoodClassService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 1000px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>
</head>
<body>
<table id="table-1">
	<tr><td>
		 <h4><a href="back_end/foodStore/store_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

	<tr>
		<th>店家編號</th>
		<th>店家名稱</th>
		<th>店址</th>
		<th>經度</th>
		<th>緯度</th>
		<th>店家服務</th>
		<th>店家狀態</th>
		<th>店家類別</th>
		<th>修改</th>
	</tr>

	<c:forEach var="storeVO" items="${storeVO}">
		
		<tr>
			<td>${storeVO.fd_no}</td>
			<td>${storeVO.fd_name}</td>
			<td>${storeVO.fd_address}</td>
			<td>${storeVO.fd_longitude}</td>
			<td>${storeVO.fd_latitude}</td>
			<td>${storeVO.fd_service}</td>
			<td>${storeVO.fd_state}</td>
			<td>
				${storeSvc.getClassPK(storeVO.fd_class_no).fd_class_name}
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodStoreServlet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="fd_no" value="${storeVO.fd_no}">
			     <input type="hidden" name="action"	value="Update_One"></FORM>
			</td>
		</tr>
	</c:forEach>
	
</table>
</body>
</html>