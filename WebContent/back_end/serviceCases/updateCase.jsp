<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.serviceCases.model.*"%>
<%
  ServiceCasesVO serviceCasesVO = (ServiceCasesVO) request.getAttribute("serviceCasesVO");
%>

<html>

<head>
<%@ include file="/back_end/commonCSS.file"%>
</head>

<body>
	<%@ include file="/back_end/header.file"%><!-- Header -->	
	<%@ include file="/back_end/sidebar.file"%><!-- sidebar -->		
	
	<div class="main-content card card-body table-responsive">
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/serviceCases/ServiceCases.do" name="form1">
	<table id="example4" class="display" style="min-width: 845px">
	<tr>
		<td>
		 	<h3><a class="btn btn-secondary light" href="<%=request.getContextPath()%>/back_end/serviceCases/listAllCase.jsp">上一頁-案件列表</a></h3>
		</td>
	</tr>
	
	<tr>
		<td>案件編號:<font color=red><b>*</b></font></td>
		<td><%=serviceCasesVO.getCase_no()%></td>
	</tr>
	<tr>
		<td>會員編號:</td>
		<td><%=serviceCasesVO.getMem_no()%></td>
	</tr>
	<tr>
		<td>案件標題:</td>
		<td><%=serviceCasesVO.getCase_title()%></td>
	</tr>
	<tr>
		<td>案件描述:</td>
		<td><%=serviceCasesVO.getCase_des()%></td>
	</tr>
	<tr>
		<td>案件回覆:</td>
		<td><textarea name="case_reply" rows="7" cols="48" maxlength="200"></textarea>
		</td>
	
	</tr>
	<tr>
		<td>案件狀態:</td>
		<td>
		<select name="case_state" size="1">
  		<option value=1>待處理</option>
  		<option value=2>處理完畢</option>
		</select></td>
	</tr>
	<tr>
	
	
	    <td>
	<input type="submit" class="btn btn-primary" value="送出修改">
	   </td>
		<td>
		<%-- 錯誤表列 --%> 
		<c:if test="${not empty errorMsgs}">						
			<font style="color: red"></font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>		
		 </td>		
	</tr>
</table>

<!-- 隱藏的東西 -->
<input type="hidden" name="action" value="update">
<input type="hidden" name="case_no" value="<%=serviceCasesVO.getCase_no()%>">
<input type="hidden" name="mem_no" value="<%=serviceCasesVO.getMem_no()%>">
<input type="hidden" name="case_title" value="<%=serviceCasesVO.getCase_title()%>">
<input type="hidden" name="case_des" value="<%=serviceCasesVO.getCase_des()%>">


		</FORM>
	</div>
</body>

<%@ include file="/back_end/commonJS.file"%>

<script>
	// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
	$("#pagename").text("回覆案件");
</script>

</html>