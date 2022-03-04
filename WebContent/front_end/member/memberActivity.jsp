<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.activityOrder.model.*"%>
<%@ page import="com.activityOrderDetail.model.*"%>
<%@ page import ="java.util.List" %>


<%
ActivityOrderService actOrderSvc = new ActivityOrderService();

Integer mem_no = new Integer(request.getParameter("mem_no"));
List<ActivityOrderVO> list = actOrderSvc.getActOrderByMemberNo(mem_no);
pageContext.setAttribute("list",list);

%>
<!doctype html>
<html>
<jsp:useBean id="actOrderDetailService" class="com.activityOrderDetail.model.ActivityOrderDetailService" />
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
<div class=info>

<table class="table table-striped ">
	<tr>
		<th>訂購人姓名</th>
		<th>訂購人信箱</th>
		<th>訂購日期</th>
		<th>電話</th>	
		<th>金額</th>
		<th></th>	
	</tr>
<c:forEach var="actSvc" items="${list}">	
	<tr>
		<td>${actSvc.act_order_name}</td>
		<td>${actSvc.act_order_email}</td>
		<td>${actSvc.act_booking_date}</td>
		<td>${actSvc.act_order_phone}</td>
		<td>${actSvc.act_order_total_price}</td>
<td>
	 <div class="d-grid gap-2 d-flex justify-content-center">
	 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do">
		 <input type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal${actSvc.act_order_no}" value="明細">
			
		
	
		<!-- Modal -->
		<div class="modal fade " id="exampleModal${actSvc.act_order_no}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title" id="exampleModalLabel">活動明細</h3>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<table class="table table-striped">
							<tr>
								<td>場次編號:</td>
								<td>報名人數:</td>
								<td>活動單價:</td>
								<td>總金額:</td>
								<td>訂單狀態:</td>	
								<td>取消:</td>	
							</tr>
						<c:forEach var="detail" items="${actOrderDetailService.getActOrderDetailByActOrderNo(actSvc.act_order_no)}">	
							<tr>
								<td>${detail.act_session_no}</td>
								<td>${detail.act_real_join_number}</td>
								<td>${detail.act_order_price}</td>
								<td>${detail.act_price_total}</td>
								<td>
									<c:if test="${detail.act_order_detail_state == 1}">
										<span class="paid">已付款</span>
									</c:if>
									<c:if test="${detail.act_order_detail_state == 2}">
										<span class="cancel">已取消</span>
									</c:if>
									<c:if test="${detail.act_order_detail_state == 3}">
										<span class="change">已改期</span>
									</c:if>
								</td>
								<td><c:if test="${detail.act_order_detail_state == 1}">
									<button type="button" class="btn btn-danger" onclick="cancel(${detail.act_order_detail_no},${detail.act_session_no});">取消</button>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</table>
					</div>
					<div class="modal-footer  d-flex justify-content-center">
						<input type="button" class="btn btn-secondary " data-bs-dismiss="modal"value="取消">
					</div>
				</div>
			</div>
		</div>
	 </FORM>
	 </div>	
</td>
	</tr>
</c:forEach>
</table>
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
	<script>
	let currentRequest = null;
	function cancel(cancelStateNo,actSessionNo){
		
		currentRequest = $.ajax({
			url:"<%=request.getContextPath()%>/member/member.do",
			type:"POST",
			data:{
				action:'cancelState',
				cancelStateNo:cancelStateNo,
				actSessionNo:actSessionNo
			},
			success:function(response){
				console.log(response);
				if(response === "true"){
					alert('取消成功');
					currentRequest.abort();
				}else{
					alert('距離活動開始少於兩天，無法取消');
					currentRequest.abort();
				}
			}
		});
		
	}
	

	</script>
	

</body>

</html>
