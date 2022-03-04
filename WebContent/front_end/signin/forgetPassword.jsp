<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<!doctype html>
<html>
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
    </head>
<style>
body{
margin:0px auto;
}
div.main{
margin:0px;
padding:0px;
}
 footer.footer2{ 
 width: 100%; 
 position: absolute; 
 bottom: 0 
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
				<h3>會員中心</h3>
			</div>
		</div>
	</div>
	<!-- Inner Banner End -->
<header class="header">
<div>
</div>
</header>

<div class="main">
   <div><a href="<%=request.getContextPath()%>/front_end/index/index.jsp">回首頁</a></div>
    <div><a href="<%=request.getContextPath()%>/front_end/member/Member.jsp">回會員中心</a></div>
    <h2>請輸入註冊信箱</h2>
    <c:if test="${not empty errorMsgs}">
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<div style="color:red">${message}</div>
		</c:forEach>
	</ul>
	</c:if>
    <form method="post" action="<%=request.getContextPath()%>/member/member.do" role="form">
      <div class="col-md-9 col-sm-12">
        <div class="form-group form-group-lg">
          <input type="text" class="form-control col-md-6 col-sm-6 col-sm-offset-2" name="mem_mail" required>
          <input class="btn btn-primary btn-lg col-md-2 col-sm-2" type="submit" value="驗證">
          <input type="hidden" name="action" value="forgetPassword">
        </div>
      </div>
    </form>
</div>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
 <footer class="footer2">
 		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
 
 </footer>

	
	

</body>

</html>
