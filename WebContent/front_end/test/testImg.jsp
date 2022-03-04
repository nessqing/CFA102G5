<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>


<head>
	<style>
	/* CSS只有這5行 */
	.preview div {
		display: inline-block;
		width: 45%;
		margin: 5px;
	}
	</style>
</head>


<body>
		<!-- 改成你自己的controller -->
		<form method="post" action="<%=request.getContextPath()%>/room/RoomImg" enctype="multipart/form-data">
			<div class="pic-upload">
				<input type="file" class="form-control upload-pic" id="formFile" accept="image/*" multiple name="imageFile">
			</div>
			<div class="preview"></div>
			<div class="mb-3 d-flex justify-content-center align-items-center">
		<!-- 改成你自己的action和PK -->
				<input type="hidden" name="type_no" value="${type_no}">
				<input type="hidden" name="action" value="insert">
				<button type="submit" class="btn">新增</button>
			</div>
		</form>
		<!-- controller裡面也要改收多張圖片 -->


<!-- JS要引入jquery和下面這些 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
			$(document).ready(function() {
// 				這裡加原本你組員寫的js
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
			});
			
		</script>
		
		
</body>
</html>