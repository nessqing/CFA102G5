<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Feliz-後台</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/back_end/activity/css/act/addAct.css" rel="stylesheet">
</head>
<body>
	<%-- 	<%@ include file="/back_end/loading.file"%> --%>
	<!-- loading -->
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->

	<jsp:useBean id="actImgService" class="com.activityImage.model.ActivityImageService" />
	<jsp:useBean id="actService" class="com.activity.model.ActivityService" />

	<div class="main-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<h1 class="card-title" style="font-size:28px;color:green;">修改活動圖片-表單</h1>
					</div>
					<div class="card-body">
						<div class="form-validation">
							<form class="needs-validation" method="post" action="<%=request.getContextPath()%>/activity/ActivityImage" enctype="multipart/form-data">
								<div class="row">
									<div class="col-xl-6">
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="actName">活動名稱
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<div class="mb-3 row">
													<select name="actNoSelect" class="select" style="width:80%;">
														<c:forEach var="actNo" items="${actImgService.getAll().stream().map(act -> act.getAct_no()).distinct().toList()}"> 
															<c:forEach var="actVO" items="${actService.all}">
																<c:if test="${actNo == actVO.act_no}">
																	<option value="${actNo}" ${actNo == actImageVO.act_no ? 'selected':''}>${actVO.act_name}</option>
																</c:if>
															</c:forEach>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-4 col-form-label" for="actPrice">活動圖片
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<input type="file" class="form-control" name="actImg"
													id="uploadImg" onchange="loadImg(event);">
												<img src="<%=request.getContextPath()%>/activity/ActivityImage?act_img_no=${actImageVO.act_img_no}" id="showImg" style="width:300px;height:200px;margin-top:30px;">
											</div>
										</div>
									</div>
								</div>
								<div class="mb-3 row twoBtn" >
									<div class="col-lg-2">
										<input type="hidden" name="action" value="updateActImgSure">
										<input type="hidden" name="whichPage" value="${param.whichPage}">
										<input type="hidden" name="updateActImgNo" value="${actImageVO.act_img_no}">
										<button type="submit" class="btn btn-primary" style="margin-left:-500px;width:100px;height:50px;font-size:20px">確定</button>
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
	function loadImg(e){
		let output = document.getElementById('showImg');
	    output.src = URL.createObjectURL(e.target.files[0]);
	    output.onload = function() {
	      URL.revokeObjectURL(output.src);
	    }
	}
	
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
</script>
</body>
</html>