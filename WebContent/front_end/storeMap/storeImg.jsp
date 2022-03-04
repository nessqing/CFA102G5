<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodImg.model.*"%>


<!DOCTYPE html>
<html>
<head>
       <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
       
<style>
.showbox {
	width: 700px;
	height: 460px;
	vertical-align: middle;
}
.abgne {
	float:left;
	margin-top: 10px;
	overflow: hidden;
}
.abgne a {
	margin-right: 10px;
}
.abgne a img {
	width: 140px;
	height: 92px;
	vertical-align: middle;
}

</style>
</head>
<body>
<div>
<c:forEach var="FoodImgVO" items="${imgVO}" >

	<div class="abgne">
		<a href="<%=request.getContextPath()%>/FoodImgReader.do?fd_img_no=${FoodImgVO.fd_img_no}"><img src="<%=request.getContextPath()%>/FoodImgReader.do?fd_img_no=${FoodImgVO.fd_img_no}" title="" /></a>
	</div>
	
	</c:forEach>

<div class="showbox"><img id="show-image" src="<%=request.getContextPath()%>/FoodImgReader.do?fd_img_no=${imgVO[0].fd_img_no}" /></div>
</div>
<script src='https://code.jquery.com/jquery-1.12.4.min.js'></script>
<script>

$(function(){
	var $showImage = $('#show-image');
	$('.abgne a').click(function(){
		$showImage.attr('src', $(this).attr('href'));
	}).click(function(){
		return false;
	});
});
</script>
</body>
</html>