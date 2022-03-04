<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>

<!DOCTYPE html>

<%
		MemberService memSvc = new MemberService();
		List<MemberClassVO> list = memSvc.getAll();
		pageContext.setAttribute("list",list);
//         List<MemberClassVO> list = (List<MemberClassVO>)session.getAttribute("list"); 
%> 

<html>
<head>
<title>listAllEmp.jsp</title>

<style>
  table {
	width: 1200px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>
</head>
<a href = '<%=request.getContextPath()%>/front_end/member/SelectPage.jsp'>首頁</a><br>
<body bgcolor='white'>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<table>
	<tr>
		<th>會員編號</th>
		<th>會員姓名</th>
		<th>性別</th>
		<th>信箱</th>
		<th>密碼</th>
		<th>電話</th>
		<th>照片</th>
		<th>地址</th>
		<th>會員狀態</th>
		<th>停復權</th>
		<th>修改</th>
		
	</tr>
	<%@ include file="page/page1.file" %> 
	<c:forEach var="memVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<tr>
		<td>${memVO.mem_no}</td>
		<td>${memVO.mem_name}</td>
		<td>${memVO.mem_sex}</td>
		<td>${memVO.mem_mail}</td>	
		<td>${memVO.mem_password}</td>
		<td>${memVO.mem_mobile}</td>
		<td>${memVO.mem_img}</td>
		<td>${memVO.mem_add}</td>
		<td>${memVO.mem_state}</td>

		<td>
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
		
		     <input type="submit" value="停復權">
			 <input type="hidden" name="mem_no"  value="${memVO.mem_no}">
			 <input type="hidden" name="mem_state"  value="${memVO.mem_state}">
			 <input type="hidden" name="action"	value="updateMemberstate"></FORM>
		</td>
		<td>
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			 <input type="submit" value="修改">
			 <input type="hidden" name="mem_no"  value="${memVO.mem_no}">
			 <input type="hidden" name="action" value="getOneForUpdate"></FORM>
		</td>
	</tr>
	</c:forEach>
	<%@ include file="page/page2.file" %> 
</table>


</body>
</html>