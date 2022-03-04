<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
li{
	font-size:20px;
	color:blue;
	margin-top: 1%;
	
  }
</style>


<body>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<ul>
	
  <li><a href='<%=request.getContextPath()%>/member/member.do?action=getAll'>List</a> all Member.</li>
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" >
        <b>輸入mail :</b>
        <input type="text" name="mem_mail">
        <input type="hidden" name="action" value="getOneBymail">
        <input type="submit" value="送出">
    </FORM>
  </li>
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" >
   		<button type="submit" value="getAllByState" name="action">正常會員列表</button>
    </FORM>
  </li>
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" >
   		<button type="submit" value="getAllByState" name="action">停權會員列表</button>
    </FORM>
  </li>
  <li><a href='<%=request.getContextPath()%>/front_end/member/addMember.jsp'>Add</a></li>
</ul>

</body>
</html>