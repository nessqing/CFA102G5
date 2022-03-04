<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Feliz-後台</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/back_end/activity/css/act/addAct.css" rel="stylesheet">

<style>
	#actInstruction{
		resize: none;
    	overflow: visible;
    	min-height:120px;
    	max-height:120px;
    	width:320px;
    	margin-left:5px;
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

	<jsp:useBean id="actClassService" class="com.activityClass.model.ActivityClassService" />
	<jsp:useBean id="actService" class="com.activity.model.ActivityService" />

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
						<h2 class="card-title" style="font-size:28px;color:blue;">修改活動-表單</h2>
					</div>
					<div class="card-body">
						<div class="form-validation">
							<form class="needs-validation" method="post" action="<%=request.getContextPath()%>/activity/Activity">
								<div class="row">
									<div class="col-xl-6">
										<div class="mb-3 row">
											<select name="actClassNoSelect" class="select">
												<c:forEach var="actClassVO" items="${actClassService.all}">
													<option value="${actClassVO.act_class_no}">${actClassVO.act_class_name}</option>												
												</c:forEach>										
											</select>
										</div>
										
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="actName">活動名稱
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actName" id="actName" value="${updateAct_actVO.act_name}">
											<span style="color:red;font-size:20px;">${errorMap["error_act_name"]}</span>
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="actPrice">活動價格
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actPrice" id="actPrice" value="${updateAct_actVO.act_price}">
											<span style="color:red;font-size:20px;">${errorMap["error_act_price"]}</span>
											</div>
										</div>
									</div>
									<div class="mb-3 row">
										<label class="col-lg-4 col-form-label actInstructionLabel" for="actInstruction">活動說明
											<span class="text-danger">*</span>
										</label>
										<div class="col-lg-6">
											<textarea class="form-control" name="actInstruction"
												id="actInstruction" oninput="autoGrow(this)">${updateAct_actVO.act_instruction}
											</textarea>
										<span style="color:red;font-size:20px;">${errorMap["error_act_instruction"]}</span>
										</div>
									</div>
								</div>
								<div class="col-xl-6">
									<div class="mb-3 row">
										<label class="col-lg-4 col-form-label" for="actScheduleTime">活動行程時間
											<span class="text-danger">*</span>
										</label>
										<div class="col-lg-6">
											<input type="text" class="form-control" name="actScheduleTime" id="actScheduleTime" value="${updateAct_actVO.act_schedule_time}">
										<span style="color:red;font-size:20px;">${errorMap["error_act_schedule_time"]}</span>
										</div>
									</div>
									<div class="mb-3 row">
										<label class="col-lg-4 col-form-label" for="actGatherLocation">活動集合地點<span
											class="text-danger">*</span>
										</label>
										<div class="col-lg-6">
											<input type="text" class="form-control" id="actGatherLocation" name="actGatherLocation"
												value="${updateAct_actVO.act_gather_location}">
										<span style="color:red;font-size:20px;">${errorMap["error_act_gather_location"]}</span>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label"
												for="actLocationLongitude">活動地點的經度<span
												class="text-danger">*</span>
											</label>
											<div class="col-lg-6" style="margin-left:10px">
												<input type="text" class="form-control" id="actLocationLongitude" name="actLocationLongitude"
													maxlength="11" value="${updateAct_actVO.act_location_longitude}">
											<span style="color:red;font-size:20px;">${errorMap["error_act_location_longitude"]}</span>
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label"
												for="actLocationLatitude">活動地點的緯度 <span
												class="text-danger">*</span>
											</label>
											<div class="col-lg-6" style="margin-left:10px">
												<input type="text" class="form-control" name="actLocationLatitude" maxlength="10"
													id="actLocationLatitude" value="${updateAct_actVO.act_location_latitude}">
											<span style="color:red;font-size:20px;">${errorMap["error_act_location_latitude"]}</span>
											</div>
											<div class="mb-3 row twoBtn" >
												<div class="col-lg-2">												
													<input type="hidden" name="action" value="updateActSure">
													<input type="hidden" name="updateActNo" value="${updateAct_actVO.act_no}">
													<input type="hidden" name="requestURL" value="${requestURL}">
													<input type="hidden" name="whichPage" value="${whichPage}">
													<button type="submit" class="btn btn-primary">確定</button>
												</div>
												<div class="col-lg-2">
													<button type="reset" class="btn btn-secondary btn">重填</button>
												</div>
											</div>
										</div>
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
		function autoGrow(element){
			element.style.height = "5px";
			element.style.height = (element.scrollHeight)+"px";
		}
		
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
	</script>
</body>
</html>