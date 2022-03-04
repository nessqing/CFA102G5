<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.creditcard.model.*"%>



<%
List<CreditcardClassVO> list = (List<CreditcardClassVO>)session.getAttribute("list");
%>


<!doctype html>
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
<head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
</head>

<style>

 footer.footer2{ 
 width: 100%; 
 position: absolute; 
 bottom: 0 
 } 

div.info{
width:1500px;
margin: 50px auto;
display: table;
}

</style>
<body>
        <%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->
	<!-- Inner Banner -->
	<div class="inner-banner">
		<div class="container">	
			<div class="inner-title">
				<ul>
					<li><a href="<%=request.getContextPath()%>/front_end/index/index.jsp">Home</a></li>
					<li><i class='bx bx-chevron-right'></i></li>
					<li><a href="<%=request.getContextPath()%>/front_end/member/memberHome.jsp">singin</a></li>
					<li><i class='bx bx-chevron-right'></i></li>
					<li>MemberCentre</li>
				</ul>
				<h3>信用卡管理</h3>
			</div>
		</div>
	</div>
	<!-- Inner Banner End -->
<div class=info>

<form METHOD="post" action="<%=request.getContextPath()%>/front_end/creditcard/creditcard.jsp">
<input class="btn btn-outline-secondary" type="submit" value="新增信用卡">
<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}"></form><br>
<table class="table table-striped">
	<tr>
		<th>姓名</th>
		<th>卡號</th>
		<th >安全碼</th>
		<th>到期月/年</th>	
		<th>手機條碼</th>
		<th></th>	
	</tr>
<c:forEach var="crdVO" items="${list}">	


	<tr>
		<td>${crdVO.crd_name}</td>
		<td>${crdVO.crd_num}</td>
		<td>***</td>
		<td>${crdVO.crd_expiry}</td>
		<td>${crdVO.crd_barcode}</td>
		<td>
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/creditcard/creditcard.do" style="margin-bottom: 0px;">
			 <input class="btn btn-outline-secondary" type="submit" value="刪除">
			 <input type="hidden" name="crd_no"  value="${crdVO.crd_no}">
			 <input type="hidden" id="mem_no" name="mem_no"  value="${crdVO.mem_no}">
			 <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			 <input type="hidden" name="action" value="deleteCard"></FORM>
	</td>
	</tr>
</c:forEach>
</table>
</div>
 <footer class="footer2">
 		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
 
 </footer>
 
<!--  <script> -->
<!--   var A  = document.getElementById('A') -->
<!--   var b = document.getElementById('mem_no').value -->
<!--   A.value = b  -->
<!--   console.log("b",b) -->
<!--  </script> -->

</body>
</html>