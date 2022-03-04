<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.serviceCases.model.*"%>
<%
  ServiceCasesVO serviceCasesVO = (ServiceCasesVO) request.getAttribute("serviceCasesVO");
%>

<html>
<head>
<%@ include file="/back_end/commonCSS.file"%>

<style></style>


</head>
<body>
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->
<div class="main-content card card-body table-responsive">
<table id="example4" class="display" style="min-width: 845px">
	<tr><td>
		 <h3><a href="<%=request.getContextPath()%>/back_end/serviceCases/listAllCase.jsp">上一頁-案件列表</a></h3>
	</td></tr>
</table>

<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<th>案件編號</th>
		<th>會員編號</th>
		<th>案件標題</th>
		<th>案件描述</th>
		<th>案件回覆</th>
		<th>案件狀態</th>
		
	</tr>
	<tr>
<!-- 	<td>1</td> -->
		<td><%=serviceCasesVO.getCase_no()%></td>
		<td><%=serviceCasesVO.getMem_no()%></td>
		<td><%=serviceCasesVO.getCase_title()%></td>
		<td><%=serviceCasesVO.getCase_des()%></td>
		<td><%=serviceCasesVO.getCase_reply()%></td>
		<td><%=serviceCasesVO.getCase_state()%></td>

	</tr>
</table>
</div>
</body>
<%@ include file="/back_end/commonJS.file"%>
<script>
	// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
	$("#pagename").text("新增案件成功");
</script>
</html>