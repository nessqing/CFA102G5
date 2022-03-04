<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>活動場次-新增</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/activity/datetimepicker/jquery.datetimepicker.css" />
<link href="<%=request.getContextPath()%>/back_end/activity/css/act/addAct.css" rel="stylesheet">

<style>
	.form-control{
		width:20rem;
	}
	.col-form-label{
		line-height:5rem;
	}
	div.xdsoft_datetimepicker xdsoft_noselect xdsoft_{
		top:20rem !important;	
	}
	.actSession{
		top:0rem;
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
	
	<div class="main-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<h1 class="card-title" style="font-size: 2rem; color: green;">新增活動場次-表單</h1>
					</div>
					<div class="card-body">
						<div class="form-validation">
							<form class="needs-validation" method="post"
								action="<%=request.getContextPath()%>/activity/ActivitySession">
								<div class="row">
									<div class="col-xl-6">
										<div class="mb-3 row">
											<select name="actNoSelect" class="select">
												<c:forEach var="actVO" items="${actService.all}">
													<option value="${actVO.act_no}">${actVO.act_name}</option>												
												</c:forEach>											
											</select>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="act_start_date">可開始報名日
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actStartDate" id="act_start_date">
												<span style="color:red;font-size:20px;">${errorMap["error_act_start_date"]}</span>	
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="act_end_date">報名最後截止日
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="text" class="form-control" name="actEndDate" id="act_end_date">
												<span style="color:red;font-size:20px;">${errorMap["error_act_end_date"]}</span>	
											</div>
										</div>
									</div>
									<div class="mb-3 row">
										<label class="col-lg-4 col-form-label actInstructionLabel" for="act_session_start_date">
										活動場次開始日期 <span class="text-danger">*</span>
										</label>
										<div class="col-lg-6">
											<input type="text" class="form-control" name="actSessionStartDate" id="act_session_start_date">
											<span style="color:red;font-size:20px;">${errorMap["error_act_session_start_date"]}</span>	
										</div>
									</div>
								</div>
								<div class="col-xl-6">
									<div class="mb-3 row">
										<label class="col-lg-4 col-form-label" for="act_session_start_time">活動場次開始時間
											<span class="text-danger">*</span>
										</label>
										<div class="col-lg-6">
											<input type="text" class="form-control" name="actSessionStartTime" id="act_session_start_time">
											<span style="color:red;font-size:20px;">${errorMap["error_act_session_start_time"]}</span>
										</div>
									</div>
									<div class="mb-3 row twoBtn">
										<div class="col-lg-2">
											<input type="hidden" name="action" value="addActSession">
											<button type="submit" class="btn btn-primary">確定</button>
										</div>
										<div class="col-lg-2">
											<button type="reset" class="btn btn-secondary btn">重填</button>
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

	<!-- 	datatimepicker -->
	<script src="<%=request.getContextPath()%>/back_end/activity/datetimepicker/jquery.datetimepicker.full.js"></script>

	
	<script>
		$.datetimepicker.setLocale('zh');
		$('#act_start_date').datetimepicker({
			theme : '',
			timepicker : false,
			step : 1,
			format : 'Y-m-d',
			value : new Date(),
			minDate:new Date(),
			maxDate:'2021-12-31',
		});
	
		$('#act_end_date').datetimepicker({
			theme : '',
			timepicker : false,
			step : 1,
			format : 'Y-m-d',
			value : '',
			minDate:new Date(),
			maxDate:'2021-12-31',
		});
		
		$('#act_session_start_date').datetimepicker({
			theme : '',
			timepicker : false,
			step : 1,
			format : 'Y-m-d',
			value : '',
			minDate:new Date(),
			maxDate:'2021-12-31',
		});
		
		$('#act_session_start_time').datetimepicker({
			theme : '', //theme: 'dark',
			timepicker : true, //timepicker:true,
			step : 60, //step: 60 (這是timepicker的預設間隔60分鐘)
			format : 'H:i', //format:'Y-m-d H:i:s',
			value : '', // value:   new Date(),
			minDate:'2021-12-31',
			maxDate:'2021-12-31',
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});
		

		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("說明");
	</script>
</body>
</html>