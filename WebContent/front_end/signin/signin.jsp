<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="google-signin-client_id" content="115626440645264975000">
<!doctype html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
    </head>
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
					<li>Sign In</li>
				</ul>
				<h3>Sign In</h3>
			</div>
		</div>
	</div>
	<!-- Inner Banner End -->

	<!-- 登入 -->
	<div class="sign-in-area ptb-70">
		<div class="container text-center user-all-form">
			<div class="contact-form">
				<div class="section-title text-center">
					<span class="sp-color">Sign In</span>
					<h2>登入</h2>
				</div>
				<form id="" METHOD="post" ACTION="<%=request.getContextPath()%>/member/LoginHandler" >
		<div class="col-lg-12">
			<div class="form-group">
				<input type="email" name="mem_mail" class="form-control" required
					data-error="請輸入正確的Email格式" placeholder="Email">
					<div class="help-block with-errors"></div>
					</div>
				</div>
		<div class="col-12">
			<div class="form-group">
				<input type="password" name="mem_password" class="form-control"
					required data-error="請輸入密碼" placeholder="Password">
				<div class="help-block with-errors"></div>
			</div>
		</div>
	<c:if test="${not empty errorMsgs}">
	<font style="color:red"></font>
	<ul>
	<c:forEach var="message" items="${errorMsgs}">
			<div style="color:red">${message}</div>
	</c:forEach>
	</ul>
    </c:if> 
			<div class="col-lg-11 col-sm-11 text-right">
				<a class="forget" href="<%=request.getContextPath()%>/front_end/signin/forgetPassword.jsp">忘記密碼</a>
				 
				   </div>
		<button type="submit" class="default-btn btn-bg-one" value="isuser" name="action">登入</button>
<!-- 		<div class="g-signin2" data-onsuccess="onSignIn"></div> -->
						<div class="col-12">
							<p class="account-desc">
								還不是會員嗎? <a href="<%=request.getContextPath()%>/front_end/signin/signup.jsp">註冊</a>
							</p>
						</div>			
  				</form>
			</div>
		</div>
	</div>
	<!-- Sign In Area End -->
		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script type="text/javascript">
        function onSignIn(googleUser) {
        	  var profile = googleUser.getBasicProfile();
        	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        	  console.log('Name: ' + profile.getName());
        	  console.log('Image URL: ' + profile.getImageUrl());
        	  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
        	}
        </script>
    </body>
</html>
