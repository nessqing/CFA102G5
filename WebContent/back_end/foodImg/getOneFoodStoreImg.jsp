<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodImg.model.*"%>

<jsp:useBean id="storeSvc" scope="page" class="com.foodStore.model.FoodStoreService" />

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->


</head>
<body>
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
<div class="main-content card card-body table-responsive">
     <h4><a href="<%=request.getContextPath()%>/back_end/foodStore/allStore.jsp">回店家列表</a></h4>
     <h4><a href="<%=request.getContextPath()%>/back_end/foodImg/addImg.jsp?fd_no=${imgVO[0].fd_no}">新增照片</a></h4>
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
		<th>照片編號</th>
		<th>照片店家</th>
		<th>店家照片</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
</thead>
	<c:forEach var="FoodImgVO" items="${imgVO}" >
		<tr>
			<td>${FoodImgVO.fd_img_no}</td>
			<td>${storeSvc.getOneStore(FoodImgVO.fd_no).fd_name}</td>
			<td><img src="<%=request.getContextPath()%>/FoodImgReader.do?fd_img_no=${FoodImgVO.fd_img_no}" style="width:200px;height:200px;"></td>
		
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodImg.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" class="btn btn-secondary">
			     <input type="hidden" name="fd_img_no" value="${FoodImgVO.fd_img_no}">
			     <input type="hidden" name="action"	value="One_Img_Update"></FORM>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodImg.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除" class="btn btn-danger">
			     <input type="hidden" name="fd_img_no"  value="${FoodImgVO.fd_img_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>

		</tr>
	</c:forEach>
	
</table>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("店家照片");
		</script>
</body>
</html>