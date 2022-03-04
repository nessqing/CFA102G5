<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.serviceCases.model.*"%>
<%@ page import="com.member.model.*"%>
<%
	ServiceCasesVO serviceCasesVO = (ServiceCasesVO) request.getAttribute("serviceCasesVO");
%>

<%
	MemberClassVO memberClassVO = (MemberClassVO) request.getAttribute("memberClassVO");
%>
<html>
<head>
 <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
</head>

<body>
<%-- 	<%@ include file="/front_end/loading.file" %> <!-- loading --> --%>
    <%@ include file="/front_end/header.file" %> <!-- Header -->
	
	<div class="mt-5 mb-5 ptb-70 container">
		<jsp:useBean id="scSvc"	class="com.serviceCases.model.ServiceCasesService" />
		<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
		<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/serviceCases/ServiceCases.do"	name="form1">
			<table id="example4" class="display" style="min-width: 845px">
				<tr>
					<td>
						<h3>
							<a class="btn btn-secondary light" href="<%=request.getContextPath()%>/front_end/member/memberHome.jsp">上一頁-會員中心</a>
						</h3>
					</td>
				</tr>
				<!-- 	抓取mem_no fk -->
				<tr>
					<td>會員編號:</td>
					<td>${memberSvc.getOneBymail(mem_mail).mem_no}</td>
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

					<td><input type="hidden" name="action" value="userinsert"></td>

				</tr>

			</table>
			<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}" />
		</FORM>
	</div>
	
	
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
</body>
<script>alert(新增成功);</script>
</html>