<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
MemberClassVO memVO = (MemberClassVO) request.getAttribute("memVO");
%>
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
	<div>
		<form method="post" action="<%=request.getContextPath()%>/member/member.do" name="front1" >
			<div class="card-body d-flex justify-content-center">
				<div class="col-xl-8">
					<div class="row mb-2">
						<label for="type_name" class="col-sm-3 col-form-label">Name</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_name}</div>
						</div>	
					</div>
					<div class="row mb-2">
						<label for="type_name" class="col-sm-3 col-form-label">Sex</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_sex == 1 ? '男' : '女'}</div>
						</div>
					</div>
					<div class="row mb-2">
						<label for="type_qty" class="col-sm-3 col-form-label">Email</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_mail}</div>
						</div>
					</div>
					<div class="row mb-2">
						<label for="type_price" class="col-sm-3 col-form-label">Password</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_password}</div>
						</div>
					</div>
					<div class="row mb-2">
						<label for="type_size" class="col-sm-3 col-form-label">Mobile</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_mobile}</div>
						</div>
					</div>
					<div class="row mb-2">
						<label for="bed_size" class="col-sm-3 col-form-label">Address</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_add}</div>
						</div>
					</div>
					<div class="row mb-2">
						<label for="type_info" class="col-sm-3 col-form-label">狀態</label>
						<div class="col-sm-8">
							<div class="pk col-sm-8">${memVO.mem_state == true ? '正常' : '未啟用'}</div>
						</div>
					</div>
				</div>
			</div>  
	   </form>
	   
 <div class="d-grid gap-2 d-flex justify-content-center">
 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do">
	 <input type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" value="修改資料">
		
	

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">修改資料</h3>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				
	<form method="post" action="<%=request.getContextPath()%>/member/member.do" name="front1">
				<div class="modal-body">
					<table>
						<tr>
							<td>姓名:</td>
							<td><input type="TEXT" name="mem_name" size="35" value="<%=memVO.getMem_name()%>" /></td>
						</tr>	
						<tr>
							<td>sex:</td>
							<td><input type="TEXT" name="mem_sex" size="35" value="<%=memVO.getMem_sex()%>" /></td>
						</tr>	
						<tr>
							<td>密碼:</td>
							<td><input type="TEXT" name="mem_password" size="30" value="<%=memVO.getMem_password()%>" /></td>
						</tr>
						<tr>
							<td>電話:</td>
							<td><input type="TEXT" name="mem_mobile" size="30" value="<%=memVO.getMem_mobile()%>" /></td>
						</tr>
						<tr>
							<td>地址:</td>
							<td><input type="TEXT" name="mem_add" size="30" value="<%=memVO.getMem_add()%>" /></td>
						</tr>
					</table>
				</div>
				<div class="modal-footer  d-flex justify-content-center">
					<input type="button" class="btn btn-secondary " data-bs-dismiss="modal"value="取消">
<!-- 							<input type="button" class="btn btn-primary"> -->
					<input type="hidden" name="action" value="updateMember">
					<input type="hidden" name="mem_no" value="<%=memVO.getMem_no()%>">
					<input type="submit" class="btn btn-primary" value="送出修改">
				</div>
				</form>
			</div>
		</div>
	</div>

 </FORM>
	
 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/creditcard/creditcard.do" style="margin-bottom: 0px;">
	<input class="btn btn-primary" type="submit" value="付款資訊">
	<input type="hidden" name="mem_no"  value="${memVO.mem_no}">
	<input type="hidden" name="action" value="getallByMem_no">
 </FORM>
</div>
    
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
