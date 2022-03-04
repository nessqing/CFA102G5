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
		<jsp:useBean id="scSvc"	class="com.serviceCases.model.ServiceCasesService" />
		<jsp:useBean id="memSvc" class="com.member.model.MemberService" />
		<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/serviceCases/ServiceCases.do"	name="form1">
			<table id="example4" class="display" style="min-width: 845px">
				<tr>
					<td>
						<h3>
							<a class="btn btn-secondary light" href="<%=request.getContextPath()%>/back_end/serviceCases/listAllCase.jsp">上一頁-案件列表</a>
						</h3>
					</td>
				</tr>
				<!-- 	抓取mem_no fk -->
				<tr>
					<td>會員編號:</td>
					<td><select name="mem_no" size="1">
							<c:forEach var="memVO" items="${memSvc.all}">
								<option value="${memVO.mem_no}">${memVO.mem_no}-${memVO.mem_name}
							</c:forEach>
					</select>
					</td>

				</tr>

				<tr>
					<td>案件標題:</td>
					<td><input type="TEXT" name="case_title" maxlength="20" size="45"
						value="<%=(serviceCasesVO == null) ? "" : serviceCasesVO.getCase_title()%>" />

					</td>
				</tr>

				<tr>
					<td>案件描述:</td>
					<td>
						<textarea name="case_des" rows="7" cols="48" maxlength="200"><%=(serviceCasesVO == null) ? "" : serviceCasesVO.getCase_des()%></textarea>
					</td>
				</tr>

				<tr>
					<td><button type="submit" class="btn btn-primary" value="送出新增">送出新增</button></td>

					<td>
						<%-- 錯誤表列 --%> <c:if test="${not empty errorMsgs}">
							<font style="color: red"></font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
					</td>

					<td><input type="hidden" name="action" value="insert"></td>

				</tr>

			</table>
		</FORM>
	</div>
</body>


<%@ include file="/back_end/commonJS.file"%>

<script>
	// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
	$("#pagename").text("新增案件");
</script>
</html>