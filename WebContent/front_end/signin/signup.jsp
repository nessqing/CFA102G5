<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
    

<!doctype html>
<html lang="en">
			<%-- 錯誤表列 --%>

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
					<li><a href="index.html">Home</a></li>
					<li><i class='bx bx-chevron-right'></i></li>
					<li>Sign Up</li>
				</ul>
				<h3>Sign Up</h3>
			</div>
		</div>
	</div>
	<!-- Inner Banner End -->

	<!-- 註冊 -->
	<div class="sign-up-area ptb-70">
		<div class="container text-center user-all-form">
			<div class="contact-form">
				<div class="section-title text-center">
					<span class="sp-color">Sign Up</span>
					<h2>註冊</h2>
				</div>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" name="form1" enctype="multipart/form-data" id="signupForm">
<div>
	<div class="col-lg-12">
		<div class="form-group">
		<input type="TEXT" class="form-control" name="mem_name" placeholder="name"  size="30" value="" required data-error="請輸入您的姓名"/></div>
	</div>
	<div class="row">
    <div class="form-group form-check col-lg-3 col-sm-3 pl-5">
		<input type="radio" class="form-check-input" 
					name="mem_sex" value="1"> <label
						class="form-check-label">男性</label>
						</div>
	<div class="form-group form-check col-lg-3 col-sm-3">
		<input type="radio" class="form-check-input"
			name="mem_sex" required value="2"> <label
				class="form-check-label">女性</label>
			</div>
	</div>
		<c:if test="${not empty errorMsgs}">
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
				<div class="help-block with-errors">${message}</div>
				</c:forEach>
			</ul>
		</c:if>
	<div class="col-lg-12">
		<div class="form-group">
		<input type="email" class="form-control" name="mem_mail" placeholder="E-mail"  size="30" value="" required data-error="請輸入正確的email格式"/></div>

	</div>
	<div class="col-lg-12">
		<div class="form-group">
		<input id="password" type="password" onblur="checkpas1();" class="form-control" name="mem_password" placeholder="password"  size="30" value="" /></div>
		 <div class="help-block with-errors"></div>
	</div>
	<div class="col-12">
		<div class="form-group">
			<input id="repassword" onChange="checkpas();" class="form-control" type="password" name="password" required
					placeholder="Password (確認密碼)" >
						<span class="tip" style="color: red">密碼不相同</span>
						<div class="help-block with-errors"></div>
					</div>
				</div>
	<div class="col-lg-12">
		<div class="form-group">
		<input type="TEXT" class="form-control" name="mem_mobile" placeholder="mobile"  size="30" value="" pattern="^09[0-9]{8}$" required data-error="請輸入手機號碼" /></div>
		 <div class="help-block with-errors"></div>
	</div>
	<div class="col-lg-12">
		<div class="form-group">
		<input type="TEXT" class="form-control" name="mem_add" placeholder="地址"  size="30" value="" /></div>
	</div>
</div>
 <input type="hidden" class="form-control" name="mem_img"value="" >			
 <input type="hidden" class="form-control" name="action"value="addMember" >			
<div class="col-lg-12 col-md-12 text-center"><br>
	<button type="button" class="default-btn btn-bg-one" onclick="checkpas2()">註冊</button>
		</div>
			</form>
				<div class="col-12">
					<p class="account-desc">
						已經有帳號? <a href="signin.jsp">登入</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- Sign Up Area End -->

		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
    <script>
    $(".tip").hide();
    function checkpas1(){
    var pas1=document.getElementById("password").value;
    var pas2=document.getElementById("repassword").value;
    if(pas1!=pas2&&pas2!="")
    $(".tip").show();
	    else
	    	$(".tip").hide();
	    }
    function checkpas(){
    var pas1=document.getElementById("password").value;
    var pas2=document.getElementById("repassword").value;
    if(pas1!=pas2){
    $(".tip").show();
	    }else{
	    $(".tip").hide();
	    }
    }
    function checkpas2(){
    var pas3=document.getElementById("password").value;
    var pas4=document.getElementById("repassword").value;
    if(pas3!=pas4){
	    alert("兩次輸入的密碼不一致！");
	    return false;
	    	}
    let myForm = document.getElementById('signupForm');
    myForm.submit();
    }
    </script>
        
    </body>
</html>
