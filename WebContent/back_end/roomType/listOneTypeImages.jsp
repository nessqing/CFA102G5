<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomImg.model.*"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/back_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
		<style>
		.card {
			background-color: #f7f6f2;
		}
		.imgs {
			background-color: #fff;
		}
		.col-form-label {
			font-size: 20px;
			color: #30504F;
			text-align: right;
			padding-top: 13px;
		}
		ul {
			display: inline-block;
		}
		h3 {
 			font-weight: 600; 
			color: #30504F;
  			padding: 5px; 
		}
		h4 {
			color: #996A4D;
			padding-bottom: 10px;
		}
		.form-select {
			color: #30504F;
			font-size: 16px;
 			height: 50px;  
		}
		textarea.form-control {
			height: auto;
			font-size: 16px;
		}
		.img-container {
			width: 45%;
			cursor: pointer;
			position: relative;
			margin: 0 auto;
			display: inline-block;
		}
		img {
			transition: 0.5s;
			max-width: 100%;
		}
		.delete{
		    transition: 0.5s;
		    opacity: 0;
		    visibility: hidden;
		    position: absolute;
		    bottom: 0;
		    width: 100%;
		    text-align: center;
		}
		.img-container:hover .delete {
			bottom: 30px;
			opacity: 1;
			visibility: visible;
		}
		.img-container:hover img {
			transition: 0.5s;
			opacity: 0.6;
		}
		.btn-delete {
			background-color: white;
			border: 1px solid #aaa;
			padding: 10px;
			border-radius: 30px;
		}
		.btn-delete img {
			width: 36px;
		}
 		.btn-delete:hover img {
 			transition: 0.5s;
 		} 
		.preview div {
			display: inline-block;
			width: 45%;
			margin: 5px;
		}
		</style>
	</head>

	<body>
		<%@ include file="/back_end/loading.file" %> <!-- loading -->
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->

	<div class="main-content">
		<a class="btn btn-secondary light"
			href="<%=request.getContextPath()%>/back_end/roomType/listAllRoomType.jsp">&lt;
			回房型列表</a>
<!-- 		錯誤表列 -->
					<c:if test="${not empty errorMsgs}">
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
			<div class="card col-xl-11">
				<div class="card-body d-flex justify-content-around">
					<div class="col-xl-6">
						<h3 class="title d-flex justify-content-center">圖片列表</h3>
						<c:choose>
							<c:when test="${images.size() != 0 }">
								<h4 class="title d-flex justify-content-center">共${images.size()}張圖片</h4>
							</c:when>
							<c:otherwise>
								<h4 class="title d-flex justify-content-center">還沒有圖片喔</h4>
							</c:otherwise>
						</c:choose>
						<c:forEach var="img" items="${images}">
							<div class="mb-3 img-container">
								<img
									src="<%=request.getContextPath()%>/room/RoomImg?img_no=${img.img_no}&action=showImages">
	
								<div class="delete">
									<form method="post"
										action="<%=request.getContextPath()%>/room/RoomImg">
										<input type="hidden" name="img_no" value="${img.img_no}">
										<input type="hidden" name="type_no" value="${img.type_no}">
										<input type="hidden" name="action" value="delete">
										<button type="submit" class="btn-delete">
											<img class="img-delete" 
												src="<%=request.getContextPath()%>/back_end/assets/img/backend_trash.png">
										</button>
									</form>
								</div>
	
							</div>
						</c:forEach>
					</div>
	
					<div class="col-xl-6 imgs">
						<h3 class="title d-flex justify-content-center">新增圖片</h3>
						<form method="post" action="<%=request.getContextPath()%>/room/RoomImg" enctype="multipart/form-data">
							<div class="pic-upload">
								<input type="file" class="form-control upload-pic" id="formFile" accept="image/*" multiple name="imageFile">
<!-- 								<input type="file" accept="image/*" name="rm_pic" class="upload-pic" multiple /> -->
							</div>
							<div class="preview"></div>
							
							<div class="mb-3 d-flex justify-content-center align-items-center">
<!-- 								insert_rm_type -->
								<input type="hidden" name="type_no" value="${type_no}">
								<input type="hidden" name="action" value="insert"> 
								<button type="submit" class="btn btn-primary col-lg-4">新增</button>
							</div>
						</form>

					</div>
					
				</div>
			</div>
		</div>

	<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
		<script>
			$(document).ready(function() {
				$("#pagename").text("房型圖片【房型 No. ${type_no}】");
				$(".img-delete").hover(function(){
			        $(this).attr('src', '<%=request.getContextPath()%>/back_end/assets/img/backend_trashopen.png');
			    },function(){
			        $(this).attr('src', '<%=request.getContextPath()%>/back_end/assets/img/backend_trash.png');
			    });
				
				//General Upload Pic Preview js
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
	                                let img = document.createElement("img");
	                                img.src = ex.target.result;
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
			} );
			
		</script>
	</body>
</html>