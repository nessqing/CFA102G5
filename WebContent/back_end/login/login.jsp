<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>


<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/back_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
		<style>
		h3 {
			font-size: 30px;
			color: #996A4D;
			font-weight: 600;
		}
		label {
			font-size: 18px;
			color: #30504F;
			letter-spacing: 1px;
		}
		</style>
	</head>
	
	<body class="vh-100 h-100 authincation container">
    	<div class="container row justify-content-center align-items-center">
        	<div class="authincation-content col-md-8">
            	<div class="col-xl-12">
                	<div class="auth-form">
						<div class="mb-2 row justify-content-around">
                        	<img src="<%=request.getContextPath()%>/back_end/assets/img/logo.png" class="w-50">
						</div>
                        <h3 class="text-center mb-4">後台管理登入</h3>
                        <form action="<%=request.getContextPath()%>/EmployeeServlet.do" method="post">
                        	<div class="mb-4">
                        		<label class="mb-2">Email</label>
                                <input type="email" class="form-control" name="emp_mail" placeholder="Email" required>
                            </div>
                            <div class="mb-5">
                            	<label class="mb-2">Password</label>
                            	<input type="password" class="form-control" name="emp_password" placeholder="Password" required>
                            </div>
                            <div>
                            	<button type="submit" class="btn btn-primary btn-block" name="action" value="Login">登入</button>
                        	</div>
                    	</form>
                	</div>
                	<c:if test="${not empty errorMsgs}">
							<ul>
	  					  <c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
						</c:forEach>
							</ul>
					</c:if>
            	</div>
        	</div>
    	</div>

		<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
	</body>
</html>