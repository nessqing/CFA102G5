<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodStore.model.*"%>
<%
	FoodStoreVO vo = (FoodStoreVO)request.getAttribute("vo");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../commonCSS.file" %> <!-- 基本CSS檔案 -->
<meta charset="UTF-8">
<style>
.child{
    float: left;
    padding: 20px 15px 20px 15px;
    box-sizing: border-box;
    background-clip: content-box;
    font-size:16px;
    display:inline-block
}
#example4{
 display: inline-block;
 width:70%!important;
 margin:0;
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodStoreServlet.do" name="form1">
<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td>店家編號:</td>
		<td><%=vo.getFd_no()%></td>
	</tr>
	
	<jsp:useBean id="fdsvc" scope="page" class="com.foodClass.model.FoodClassService" />
	<tr>
		<td>店家類別:</td>
		<td><select size="1" name="fd_class_no" >
			<c:forEach var="fdsvcVO" items="${fdsvc.all}">
				<option value="${fdsvcVO.fd_class_no}" ${(vo.fd_class_no==fdsvcVO.fd_class_no)?'selected':'' } >${fdsvcVO.fd_class_name}
			</c:forEach>
		</select></td>
	</tr>
	
	<tr>
		<td>店名:</td>
		<td><input type="TEXT" name="fd_name" size="20" maxlength="10" value="<%=vo.getFd_name()%>" /></td>
	</tr>
	<tr>
		<td>店家地址:</td>
		<td><input type="TEXT" name="fd_address" size="50" maxlength="50" value="<%=vo.getFd_address()%>" /></td>
	</tr>
	<tr>
		<td>店家經度:</td>
		<td><input type="TEXT" name="fd_longitude" size="50" value="<%=vo.getFd_longitude()%>" /></td>
	</tr>
	<tr>
		<td>店家緯度:</td>
		<td><input type="TEXT" name="fd_latitude" size="50" value="<%=vo.getFd_latitude()%>" /></td>
	</tr>
	</table>
	
	<div style="float: left">
		<p style="padding:20px 15px 20px 15px;font-size:16px;float:left">服務:</p>
		<div class="child"><input type="checkbox"  name="fd_service" value="內用" <%if(vo.getFd_service().contains("內用")==true) out.println("checked");%>>內用</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="外帶" <%if(vo.getFd_service().contains("外帶")==true) out.println("checked");%>>外帶</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="外送" <%if(vo.getFd_service().contains("外送")==true) out.println("checked");%>>外送</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="Free WiFi" <%if(vo.getFd_service().contains("Free WiFi")==true) out.println("checked");%>>Free WiFi</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="提供廁所" <%if(vo.getFd_service().contains("提供廁所")==true) out.println("checked");%>>提供廁所</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="適合親子" <%if(vo.getFd_service().contains("適合親子")==true) out.println("checked");%>>適合親子</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="便利支付" <%if(vo.getFd_service().contains("便利支付")==true) out.println("checked");%>>便利支付</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="提供素食" <%if(vo.getFd_service().contains("提供素食")==true) out.println("checked");%>>提供素食</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="哺(集)乳室" <%if(vo.getFd_service().contains("哺(集)乳室")==true) out.println("checked");%>>哺(集)乳室</div>
		<div class="child"><input type="checkbox"  name="fd_service" value="停車場" <%if(vo.getFd_service().contains("停車場")==true) out.println("checked");%>>停車場</div>
	</div>
	
	<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td style="padding:20px 15px 20px 15px;font-size:16px">店家狀態:</td>
		<td style="font-size:16px"><input type="radio" name="fd_state"  value="true" ${(vo.fd_state==true)?'checked':'' }>上架店家</td>
		<td style="font-size:16px"><input type="radio" name="fd_state"  value="false" ${(vo.fd_state==false)?'checked':'' }>下架店家</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="fd_no" value="<%=vo.getFd_no()%>">
<input type="submit" value="送出修改" class="btn btn-primary"></FORM>
</div>
<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("修改店家資料");
			
		</script>
		
</body>
</html>