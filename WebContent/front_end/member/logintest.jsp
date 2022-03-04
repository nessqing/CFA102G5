<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<div class = "text-center ptb-70">
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
								<div class="col-lg-11 col-sm-11 text-right">
							<a class="forget" href="#">忘記密碼</a>
						</div>
		<button type="submit" class="default-btn btn-bg-one" value="isuser" name="action">登入</button>
<!--         <input type="hidden" name="action" value="isuser"> -->
<!--         <input type="submit" value="送出"> -->
						<div class="col-12">
							<p class="account-desc">
								還不是會員嗎? <a href="<%=request.getContextPath()%>/front_end/member/addMember.jsp">註冊</a>
							</p>
						</div>			
</form>
</div>
</body>
</html>