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
						<h1 class="card-title" style="font-size:28px;color:green;">新增活動圖片-表單</h1>
					</div>
					<div class="card-body">
						<div class="form-validation">
							<form class="needs-validation" method="post" action="<%=request.getContextPath()%>/activity/ActivityImage" id="addActImgForm" enctype="multipart/form-data">
								<div class="row">
									<div class="col-xl-8">
										<div class="mb-3 row">
											<label class="col-lg-3 col-form-label" for="actName">活動名稱
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-6">
												<div class="mb-3 row">
													<select name="actNoSelect" class="select" style="width:80%;margin-top: 10px;">
														<c:forEach var="actVO" items="${actService.all}">
															<option value="${actVO.act_no}">${actVO.act_name}</option>												
														</c:forEach>													
													</select>
												</div>
											</div>
										</div>
										<div class="mb-3 row">
											<label class="col-lg-3 col-form-label" for="actPrice">活動圖片
												<span class="text-danger">*</span>
											</label>
											<div class="col-lg-5 pic-upload">
												<input type="file" multiple class="form-control upload-pic" name="imageFile" id="formFile" style="margin-top:20px;">																		
											</div>
											<div class="preview"></div>
										</div>
									</div>
								</div>
								<div class="mb-3 row twoBtn" >
									<div class="col-lg-2">
										<input type="hidden" name="action" value="addActImg">
										<button type="button" class="btn btn-primary" style="margin-left:-500px;width:100px;height:50px;font-size:20px" onclick="check_file();">確定</button>
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
	$(document).ready(function() {
	    let upload = $(".upload-pic");
	    upload.change(function(){
	    	if (this.files) {
	            files = this.files;
	            let preview = $(this).parents(".pic-upload").next();
	            for (let i = 0; i < files.length; i++) {
	                if (files[i].type.indexOf("image") >= 0) {
	                    let reader = new FileReader();
	                    reader.addEventListener("load", (ex) => {
	                        let div = document.createElement("div");
	                        div.setAttribute("style","display:inline-block;");
	                        let img = document.createElement("img");
	                        img.src = ex.target.result;
		                  	img.setAttribute("style","display:inline-block;margin-top:30px;width:300px;height:200px;");
	                        img.classList.add("previewImg");
	                        div.append(img);
	                        preview.append(div);
	                    });
	                    reader.readAsDataURL(files[i]);
	                } else {
	                    window.close();
	                }
	            }
	        }
	    })
	});
	
	function check_file(){
		let file = document.getElementById('formFile').value;
		let type = file.substring(file.lastIndexOf('.')).toLowerCase();
		let myForm = document.getElementById('addActImgForm');

		if(file ===''){
			alert("請選擇檔案");
			return false;
		}
		
		if(type === '.jpg' || type === '.png' || type === '.gif'){
			myForm.submit();
		}else{
			alert('圖片不符合格式: .jpg .png .gif')
			return false;
		}
		
	}
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
</script>
</body>
</html>