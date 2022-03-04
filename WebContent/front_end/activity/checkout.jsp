<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
	int finalTotal = 0;
	List<Map<String,String>> shoppingList = (List<Map<String,String>>)session.getAttribute("shoppingCar");
	
	if(shoppingList != null){
		for(Map<String,String> map : shoppingList){
			finalTotal += Integer.parseInt(map.get("act_price")) * 
					Integer.parseInt(map.get("act_people_number"));		
		}
		pageContext.setAttribute("finalTotal", finalTotal);
	}
%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/front_end/activity/css/act/addAct.css" rel="stylesheet">

<style>
.col-lg-6 {
	position: relative;
	left: -10px;
	margin-top: 10px;
}
#sameAsMember{
	position: relative;
    left: -80px;
    top: -54px;
    height: 20px;
}
.totalPriceDiv{
	margin-left: 380px;
}
input{
	border:1px solid gray !important;
}
.table tr th{
	font-size:20px;
}
input:focus{
	border:1px solid red !important;
}
</style>
</head>
<body>
<%-- 	<%@ include file="/front_end/loading.file" %> <!-- loading --> --%>
        <%@ include file="/front_end/header.file" %> <!-- Header -->
	
	<jsp:useBean id="actOrderService" class="com.activityOrder.model.ActivityOrderService" />
	<jsp:useBean id="memberService" class="com.member.model.MemberService" />
	
	<div class="mt-5 mb-5 ptb-70 container" style="padding-top: 20px; padding-bottom:150px;">

		<div class="row">
			<div class="col-lg-12">
				<div class="card allForm">
					<div class="card-header">
						<h1 class="card-title" style="font-size:30px; color: green;">訂購人資料</h1>
					</div>
					<div class="col-lg-4" style="height:70px;">
						<label class="col-form-label" for="sameAsMember">同會員</label>
						<input type="checkbox" id="sameAsMember">
					</div>
					<div class="card-body">
						<div class="form-validation">
							<form id="checkoutForm" class="needs-validation" method="post" action="<%=request.getContextPath()%>/activity/ActivityOrder">
								<div class="row">
									<div class="col-xl-6">
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" style="top:-15px;">稱謂 <span class="text-danger">*</span></label>
											<select name="actOrderTitleSelect" class="select" id="actOrderTitleSelect" style="position: relative; top: 15px;">
												<option value="先生">先生</option>							
												<option value="女士">女士</option>							
											</select>
										</div>

										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="orderName">姓名
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="orderName"
													id="orderName" maxlength="4" placeholder="請輸入中文名" value="${addAct_actVO.act_name}">
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="orderPhone">電話
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="tel" name="orderPhone" id="orderPhone" class="form-control" maxlength="10" value="${addAct_actVO.act_price}">
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="orderEmail">Email
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" name="orderEmail" id="orderEmail"
													class="form-control">
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="orderCreditCard">信用卡卡號
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="tel" id="orderCreditCard" placeholder="請輸入16位數字" maxlength="16" name="orderCreditCard" class="form-control">
											</div>
										</div>
									</div>
									
									<div class="col-xl-6" style="border:1px solid gray">
										<h1 style="color:#007979">結帳明細</h1>
										<div class="table-responsive">
											<table class="table">
												<tr>
													<th>活動名稱</th>
													<th>開始日期</th>
													<th>開始時間</th>
													<th>人數</th>
													<th>金額</th>
												</tr>
												<c:choose>		
													<c:when test="${not empty requestScope.checkout_act_name}">
														<tbody>
															<tr class="top-class">
																<td class="product-name">${checkout_act_name}</td>
																<td class="product-price"><span class="unit-amount">${checkout_act_date}</span></td>
																<td class="product-quantity">
																	<span class="minus-btn">${checkout_act_time}</span>											
																</td>
																<td class="product-subtotal"><span class="subtotal-amount">${checkout_poepleNumber}</span></td>
																<td class="product-subtotal"><span class="subtotal-amount actPrice">${checkout_oneOrderPrice}</span></td>															
															</tr>
														</tbody>
														<input type="hidden" name="checkoutAction" value="immediateCheckout">
														<input type="hidden" name="actSessionDate" value="${checkout_act_date}">
														<input type="hidden" name="actSessionTime" value="${checkout_act_time}">
														<input type="hidden" name="actRealJoinNumber" value="${checkout_poepleNumber}">
														<input type="hidden" name="actOrderPrice" value="${checkout_oneOrderPrice}">
													</c:when>
													<c:otherwise>
														<c:forEach var="map" items="${shoppingCar}">		
															<tr class="top-class">
																<td class="product-name">${map["act_name"]}</td>
																<td class="product-price"><span class="unit-amount">${map["act_date"]}</span></td>
																<td class="product-quantity">
																	<span class="minus-btn">${map["act_session_start_time"]}</span>											
																</td>
																<td class="product-subtotal"><span class="subtotal-amount">${map["act_people_number"]}</span></td>
																<td class="product-subtotal"><span class="subtotal-amount actPrice totalPrice">${map["act_price"] * map["act_people_number"]}</span></td>
															</tr>
														</c:forEach>
														<input type="hidden" name="checkoutAction" value="shoppingCarCheckout">
													</c:otherwise>
												</c:choose>
											</table>
										</div>
										<c:choose>
											<c:when test="${not empty requestScope.checkout_act_name}">
												<div class="totalPriceDiv">
													<h2>總金額：${checkout_oneOrderPrice + totalPrice}</h2>
												</div>										
											</c:when>
											<c:otherwise>
												<div class="totalPriceDiv">
													<h2>總金額：${finalTotal}</h2>
												</div>	
											</c:otherwise>
										</c:choose>					
									</div>
								</div>
								<input type="hidden" name="action" value="checkout">
								<input type="hidden" name="orderTotalPrice" value="${checkout_oneOrderPrice + totalPrice}">
								
								<div class="mb-3 row">
									<div class="col-lg-12" style="left:170px;">
										<button type="button" class="btn btn-primary" onclick="check();">結帳</button>
										<button type="reset" class="btn btn-secondary btn" style="margin-left:20px;font-size:20px">重填</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/front_end/message.file" %> <!-- Message --> 
    <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
    <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 --> 
    
	<script>
	
	let currentRequest = null;
		$("#sameAsMember").change(function(){
			if(this.checked){
				currentRequest = $.ajax({
					url:"<%=request.getContextPath()%>/activity/ActivityOrder",
					type:"POST",
					data:{
						action:'sameAsMember',		
					},
					success:function(response){
						
						let obj = JSON.parse(response);
						
						if(obj.mem_title === '先生'){
							$("#actOrderTitleSelect option:first").attr("selected",true);
							$("span.current").text(obj.mem_title);
						}else{
							$("#actOrderTitleSelect option:last").attr("selected",true);
							$("span.current").text(obj.mem_title);
						}
						
						$("#orderName").val(obj.mem_name);
						$("#orderPhone").val(obj.mem_phone);
						$("#orderEmail").val(obj.mem_email);
						$("#orderCreditCard").val(obj.mem_creditCard);
					}
				});
			}else{
				$("#actOrderTitleSelect select").val('');
				$("#orderName").val('');
				$("#orderPhone").val('');
				$("#orderEmail").val('');
				$("#orderCreditCard").val('');
			}
		});
		
		function check(){
			let pattern = "[\u4e00-\u9fa5]{3,4}";
			let mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			let name = document.getElementById('orderName');
			let phone = document.getElementById('orderPhone');
			let email = document.getElementById('orderEmail');
			let creditCard = document.getElementById('orderCreditCard');
			let form = document.getElementById('checkoutForm');
			
			if(name.value === ''){
				alert("名稱請勿空白");
				name.focus();
				return false;
			}
			if(!(name.value.match(pattern))){
				alert("請輸入3-4個中文作為名稱");
				name.focus();
				return false;
			}
			if(phone.value === ''){
				alert("電話請勿空白");
				phone.focus();
				return false;
			}
			if(!phone.value.match("^09[0-9]{8}")){
				alert("電話不符合格式");
				phone.focus();
				return false;
			}
			if(email.value === ''){
				alert("Email請勿空白");
				email.focus();
				return false;
			}
			if(!email.value.match(mailformat)){
				alert("Email不符合格式");
				email.focus();
				return false;
			}
			if(!creditCard.value.match("[0-9]{16}")){
				alert("請輸入16位信用卡卡號");
				creditCard.focus();
				return false;
			}
			if(creditCard.value === ''){
				alert("信用卡卡號請勿空白");
				creditCard.focus();
				return false;
			}
			successCheckout();
			form.submit(() =>{
				setTimeout(() => {},1000);
			});
		}
// 		function submitForm(){
// 			let form = document.getElementById('checkoutForm');
// 			form.submit();
// 		}
		function autoClose() {
			swal.fire({
				icon : 'error', //常用的還有'error'
				title : '該欄位請勿空白',
				showConfirmButton : false, //因為會自動關閉，所以就不顯示ok按鈕
				timer : 1000
			// 單位毫秒，1秒後自動關閉跳窗
			})
		}
		function successCheckout() {
			swal.fire({
				icon : 'success', //常用的還有'error'
				title : '付款成功',
				showConfirmButton : false, //因為會自動關閉，所以就不顯示ok按鈕
				timer : 500
			// 單位毫秒，1秒後自動關閉跳窗
			})
		}
		
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
	</script>
</body>
</html>
