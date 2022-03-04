<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import ="com.activitySession.model.*" %>
<%@ page import ="com.activity.model.*" %>
<%@ page import ="java.time.LocalTime" %>

<!DOCTYPE html>
<html>
<head>
<title>Feliz-後台</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/back_end/activity/css/act/addAct.css" rel="stylesheet">

<style>
	.select{
		width:100%;
		position: relative;
    	top: 15px;
	}
	.form-control{
		position: relative;
    	top: 10px;
    	width: 60%;
	}
	div.twoBtn div.col-lg-2 .updateSureBtn{
		margin-left:-500px;
		width:100px;
		height:50px;
		font-size:20px;
	}
</style>
</head>
<body>
	<%-- 	<%@ include file="/back_end/loading.file"%> --%>
	<!-- loading -->
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->

	<jsp:useBean id="actService" class="com.activity.model.ActivityService" />
	<jsp:useBean id="actSessionService" class="com.activitySession.model.ActivitySessionService" />
	<jsp:useBean id="actOrderDetailService" class="com.activityOrderDetail.model.ActivityOrderDetailService" />

	<div class="main-content">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color:red;font-size:25px;">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red;font-size:18px;">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<h1 class="card-title" style="font-size:28px;color:green;">修改活動訂單明細-表單</h1>
					</div>
					<div class="card-body">
						<div class="form-validation">
							<form class="needs-validation" method="post" action="<%=request.getContextPath()%>/activity/ActivityOrderDetail">
								<div class="row">
									<div class="col-xl-6">
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="actName">活動名稱
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<div class="mb-3 row">
													<select name="actNoSelect" class="select">
														<c:forEach var="actVO" items="${actService.all}"> 
															<c:if test="${actNo == actVO.act_no}">
																<option value="${actNo}" selected>${actVO.act_name}</option>
															</c:if>														
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="act_session_price">活動場次單價
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actSessionPrice"
													id="act_session_price" readonly value="${actOrderDetailVO.act_order_price}">
											</div>
										</div>
										
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="act_real_join_number">實際報名人數
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actRealJoinNumber"
													id="act_real_join_number" readonly value="${actOrderDetailVO.act_real_join_number}">
											</div>
										</div>									
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="act_price_total">明細總金額
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actPriceTotal"
													id="act_price_total" readonly value="${actOrderDetailVO.act_price_total}">
											</div>
										</div>									
																			
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="act_session_start_date">活動場次開始日期
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actSessionStartDate" readonly
													id="act_session_start_date" value="${actSessionVO.act_session_start_date}">
											</div>
										</div>
										
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="actPrice">活動場次開始時間
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<div class="mb-3 row">
													<select name="actSessionTimeSelect" class="select" id="actSessionTimeSelect">
														<c:forEach var="actSessionVOByActNo" items="${actSessionListByActNo}">
															<option value="${actSessionVOByActNo.act_session_no}" ${actSessionVO.act_session_start_time == actSessionVOByActNo.act_session_start_time ? 'selected' : ''} >
																<fmt:parseDate  value="${actSessionVOByActNo.act_session_start_time}" type="time" pattern="H:mm" var="parsedTime" />
																<fmt:formatDate value="${parsedTime}" type="time" pattern="H:mm"/>						
															</option>
														</c:forEach>												
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="mb-3 row twoBtn" >
									<div class="col-lg-2">
										<input type="hidden" name="action" value="updateActOrderDetailSure">
										<input type="hidden" name="whichPage" value="${param.whichPage}">
										<input type="hidden" name="updateActOrderDetailNo" value="${actOrderDetailVO.act_order_detail_no}">
										<input type="hidden" name="updateActSessionNo" value="${actOrderDetailVO.act_session_no}">										
										<input type="hidden" name="updateOrderNo" value="${actOrderDetailVO.act_order_no}">
										<button type="submit" id="sureBtn" class="btn btn-primary updateSureBtn">確定</button>
									</div>
									<div class="col-lg-2">
										<button type="reset" class="btn btn-secondary" style="margin-left:-450px;">重填</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/back_end/commonJS.file"%>
	
<script>
	let currentRequest = null;
		$('#actSessionTimeSelect').on('change',function(){
			currentRequest = $.ajax({
				url:"<%=request.getContextPath()%>/activity/ActivityOrderDetail",
				type:"POST",
				data:{
					action:'checkChangeSession',
					oldSessionNo:'${actSessionVO.act_session_no}',
					changeSessionNo:$('#actSessionTimeSelect').val(),
					orderNo:'${actOrderDetailVO.act_order_no}',
					changeActPeopleSelect:$('#act_real_join_number').val()
				},
				success:function(response){
			console.log(response);
					if(response === "true"){
 						currentRequest.abort();
					}else{
						alert("場次人數已達上限，無法更換場次");
 						$('#actSessionTimeSelect').val("${actSessionVO.act_session_no}");
						currentRequest.abort();
					}
				}
			});
		});
	
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
</script>
</body>
</html>