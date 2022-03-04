<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodImg.model.*"%>

    <%
    FoodImgVO foodImgVO = (FoodImgVO)request.getAttribute("imgvo"); 
    
    %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
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
	
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodImg.do" name="form1" enctype="multipart/form-data">
<table id="example4" class="display" style="min-width: 845px">
	
	<tr>
		<td>照片編號:<font color=red><b><%=foodImgVO.getFd_img_no()%></b></font></td>
		<td><input type="hidden" readonly="readonly" name="fd_img_no" value="<%=foodImgVO.getFd_img_no()%>"></td>
	</tr>
	<tr>
		<td>店家編號:<font color=red><b><%=foodImgVO.getFd_no()%></b></font></td>
		<td><input type="hidden" readonly="readonly" name="fd_no" value="<%=foodImgVO.getFd_no()%>"></td>
	</tr>
</table>
	<div>
		<a>原照片:</a>
		<a><img src="${pageContext.request.contextPath}/FoodImgReader.do?fd_img_no=<%=foodImgVO.getFd_img_no()%>" style="width:250px;max-height:250px"></a>
	</div>	
	
	<div>
		<a><input type="file" name="fd_img" size="45" value="<%=foodImgVO.getFd_img()%>" onchange="loadImageFile(event)"></a>
	</div>
		<a>欲修改成 :<img id="image" src="" style="width:250px;max-height:250px"></a>
		

<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="fd_img_no" value="<%=foodImgVO.getFd_img_no()%>">
<input type="submit" value="送出修改" class="btn btn-primary">

<script>
function loadImageFile(event){ 
	let image = document.getElementById('image');
	image.src = URL.createObjectURL(event.target.files[0]); };
</script>
</FORM>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("修改照片");
		</script>

</body>
</html>