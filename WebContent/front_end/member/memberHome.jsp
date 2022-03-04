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
	        <style>
        .banner-form {
        	margin-top: 0px;
        }
        .banner-form label {
        	padding-left: 12px;
        }
        .banner-form .nice-select {
        	background: #fff;
        }
        .banner-form .nice-select::after {
		    height: 8px;
		    width: 8px;
		    border-color: #555555;
		    top: 50%;
		    left: 80%;
		}		
		.banner-form {
			width: 100%;
		}


.close-calendar {
	position:absolute;
	padding:10px;
	background-color:#a4a4a4;
	right:0;
	top:0;
}
.close-calendar img {
	width:35px;
}
.arrow {
    position: absolute;
    color: #bbbfca;
    font-size: 36px;
    padding: 0px 10px;
    z-index: 10;
}
.aval-title {
    width: 90%;
    margin: 0 auto;
}
.calendar-forward {
    right: 5%;
    top: -1%;
    cursor: pointer;
}
.calendar-backward {
    left: 5%;
    top: -1%;
    cursor: pointer;
}
.condition {
    border: 1px solid black;
    border-radius: 10px;
    padding: 20px 40px;
    width: 90%;
    margin: 0 auto 20px auto;
}
.condition p {
    color: #acacac;
}
.condition h3 {
    width: fit-content;
}
.arrow:hover {
    color: #495464;
}
.view {
	position:relative;
    width: 1100px;
    overflow: hidden;
    margin: 0 auto;
}
#display {
    display: flex;
    flex-direction: row;
    width: fit-content;
    transition: 0.3s ease-in-out;
    margin: 0 auto;
}
.calendar-wrapper {
    min-width: 550px;
    opacity: 0;
    transition: 0.3s ease-in-out;
    padding: 0px 10px;
}
.title {
	margin: 5px 0;
    display: flex;
    height: min-content;
    text-align: center;
    letter-spacing: 1px;
    font-size: 20px;
    justify-content: center;
}
.title b {
    height: fit-content;
}
.title p {
    margin: 0px;
    color: #996A4D;
    font-weight: 700;
}
.calendar-td {
/*     padding: 1px 1px; */
}
.week-title th {
    text-align: center;
    padding: 10px 0px;
    font-size: 18px;
    color: #996A4D;
    font-weight: 700;
}
a.calendar-box {
    position: relative;
    display: block;
    background-color: #d3d3d3;
    height: 70px;
    width: 70px;
    margin: 0 auto;
}
a.calendar-default {
    display: flexbox;
    background-color: white;
    color:grey;
}
a.calendar-default:hover {
    color: #30504F;
    background-color: #E4D6C4;
}
a div.calendar-date {
	width:fit-content;
    position: absolute;
    right: 5px;
    top: 0px;
    font-size: 18px;
    text-align: right;
    align-self: flex-end;
}
a.calendar-isEmpty .calendar-price {
    position: absolute;
    bottom: 2px;
    right: 2px;
    font-size: 12px;
    font-weight: 500;
}
a.calendar-isFull {
	background-color: #e8e8e8;
	cursor: no-drop;
	pointer-events: none;
	color:grey;
}
a.calendar-isFull::before {
	position: absolute;
    left: 5px;
    top: -7px;
	font-size:30px;
	content: "\0002DF";
	color: grey;
}
a.calendar-isEmpty {
	cursor: pointer;
    color: black;
    border-bottom: 1.5px solid #969696 ;
}
a.calendar-isEmpty::before {
	position: absolute;
    left: 5px;
    top: 2px;
    font-family: "Font Awesome 5 Free";
    content: "\f111";
    color: rgb(16, 161, 16);
    font-size: 10px;
}
a.calendar-today {
    background: #edebe2;
}
@media screen and (max-width: 1140px) {
    .view {
        width: 860px;
        overflow: hidden;
        margin: 0 auto;
    }
    a.calendar-box {
        height: 100px;
        width: 100%;
    }
    .calendar-wrapper {
        width: 860px;
    }
    .calendar-wrapper table {
        width: 100%;
    }
    .calendar .calendar-date {	
        font-size: 16px;
    }
    .calendar .calendar-date::before {
        font-size: 16px;
    }
    .calendar div.calendar-price {
        font-size: 18px;
    }
}

@media screen and (max-width: 840px) {
    a.calendar-box {
        min-height: 70px;
        min-width: 70px;
    }
    .calendar-wrapper {
        width: 660px;
    }
    .view {
        width: 660px;
    }
     .calendar div.calendar-price {
        font-size: 16px;
    }
}

</style>
<style>


body{
  margin: 0;
}

header.header, footer.footer{

  width: 1200px;
  margin: 0 auto;
  box-sizing: border-box;
}
header.header{
  margin-bottom: 20px;
  margin-top: 20px;
}
footer.footer{
  margin-top: 20px;
}


div.parent_container{
  width: 1200px;
  margin: 0 auto;
  font-size: 0;
  box-sizing: border-box;
}
div.parent_container aside.aside, div.parent_container main.main, div.parent_container div.sub_aside{
  display: inline-block;
  vertical-align: top;
  font-size: 1rem; /* 16px */
  box-sizing: border-box;
}
div.parent_container aside.aside{

  width: 200px;
  margin-right: 20px;
}

div.parent_container main.main{

  width: calc(100% - 200px - 20px - 200px - 20px);
}

div.parent_container div.sub_aside{
  width: 200px;

  margin-left: 20px;
}
/*  footer.footer2{  */
/*  width: 100%;  */
/*  position: absolute;  */
/*  bottom: 0  */
/*  }  */

</style>

<header class="header">
<div>
<h2>${memberSvc.getOneBymail(mem_mail).mem_name}</h2>您好
</div>
 
</header>

<div class="parent_container">
  <aside class="aside">
  
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do">
<input type="hidden" name="action" value="getOneBymail">
<input type="hidden" name="mem_mail" value="${mem_mail}">
<input type="submit" class="default-btn btn-bg-one" value="個人資訊"></FORM><br>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/member/memberOrder.jsp">
<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}">
<input type="submit" class="default-btn btn-bg-one" value="住宿管理"></FORM><br>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/member/memberActivity.jsp">
<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}">
<input type="submit" class="default-btn btn-bg-one" value="活動管理"></FORM><br>

<FORM METHOD="post" ACTION="">
<input type="submit" class="default-btn btn-bg-one" value="客服資訊"></FORM><br>
  </aside>

  <main class="main">

    
  </main>
  
</div>

<footer class="footer">

</footer>
 <footer class="footer2">
 		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
 
 </footer>



</body>

</html>
