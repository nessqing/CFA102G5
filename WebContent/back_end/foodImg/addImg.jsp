<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodImg.model.*"%>
<%
Integer fd_no = null;
if(request.getAttribute("fd_no")!=null){
fd_no = (Integer)request.getAttribute("fd_no");
}else{
fd_no = new Integer(request.getParameter("fd_no"));
}
%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<style type="text/css">
img{
width:300px;

}

</style>
</head>

<body>
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
  
     <div class="main-content card card-body table-responsive">
     <h4><a href="<%=request.getContextPath()%>/back_end/foodStore/allStore.jsp">回店家列表</a></h4>
     <%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

    <FORM METHOD="post" action="<%=request.getContextPath()%>/FoodImg.do" enctype="multipart/form-data">
    	<table id="example4" class="display" style="min-width: 845px">
    	<tr>
    		<td>店家編號:<font color=red><b><%=fd_no%></b></font></td>
        	<td><input type="hidden" name="fd_no" value="<%=fd_no%>"></td>
        </tr>
        <tr>
        <td><input type="file" accept="image/*" multiple name="fd_img" id="upload" onchange="loadImageFile(event)" required>
        <img id="image" src="" >
        </tr> 
        </table>
        <input type="submit" value="送出" class="btn btn-primary"> 
        <input type="hidden" name="action" value="addImg">
        
    </FORM>
       </div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("新增店家照片");


function loadImageFile(event){ 
	let image = document.getElementById('image');
	image.src = URL.createObjectURL(event.target.files[0]); };
</script>		
</body>


</html>