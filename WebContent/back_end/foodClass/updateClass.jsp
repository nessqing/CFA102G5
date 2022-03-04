<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodClass.model.*"%>

<%
FoodClassVO ClassVO = (FoodClassVO) request.getAttribute("foodClassVO");
%>
<!DOCTYPE html>
<html>
<head>
<style>
#example4{
 display: inline-block;
 width:85%!important;
 margin:0;
}
</style>
<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<title>Insert title here</title>
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
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodClassServlet.do" name="form1">
<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td>類別編號:</td>
		<td><%=ClassVO.getFd_class_no()%></td>
	</tr>
	<tr>
		<td>類別名稱:</td>
		<td><input type="text" name="fd_class_name" size="20" value="<%=ClassVO.getFd_class_name()%>" /></td>
	</tr>
	<tr>
		<td>類別狀態</td>
		<td><input type="radio" id="true" name="fd_class_state"  value="true" ${(foodClassVO.fd_class_state==true)?'checked':'' } >使用</td>
		<td><input type="radio" id="false" name="fd_class_state"  value="false" ${(foodClassVO.fd_class_state==false)?'checked':'' } >不使用</td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="fd_class_no" value="<%=ClassVO.getFd_class_no()%>">
<input type="submit" value="送出修改" class="btn btn-primary"></FORM>
</div>

<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("修改類別");
		</script>		
</body>
</html>