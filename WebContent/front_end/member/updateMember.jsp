<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>


<%
  MemberClassVO memVO = (MemberClassVO) request.getAttribute("memVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員修改</title>
</head>
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<form method="post" action="<%=request.getContextPath()%>/member/member.do" name="front1">
<table>
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="mem_name" size="45" value="<%=memVO.getMem_name()%>" /></td>
	</tr>
	<tr>
		<td>性別:</td>
		<td><input type="TEXT" name="mem_sex" size="45" value="<%=memVO.getMem_sex()%>" /></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="TEXT" name="mem_password" size="45" value="<%=memVO.getMem_password()%>" /></td>
	</tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="mem_mobile" size="45" value="<%=memVO.getMem_mobile()%>" /></td>
	</tr>
	<tr>
		<td>照片:</td>
		<td><input type="TEXT" name="mem_img" size="45" value="<%=memVO.getMem_img()%>" /></td>
	</tr>
	<tr>
		<td>地址:</td>
		<td><input type="TEXT" name="mem_add" size="45" value="<%=memVO.getMem_add()%>" /></td>
	</tr>
</table>
<input type="hidden" name="action" value="updateMember">
<input type="hidden" name="mem_no" value="<%=memVO.getMem_no()%>">
<input type="submit" value="送出修改">
</form>


</body>
</html>