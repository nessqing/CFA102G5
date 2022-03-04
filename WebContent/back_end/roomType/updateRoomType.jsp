<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>

<%
  RoomTypeVO roomTypeVO = (RoomTypeVO) request.getAttribute("roomTypeVO");
%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/back_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
		<style>
		.card {
			background-color: #f7f6f2;
			margin: 0 auto;
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
		.pk {
			font-weight: 600;
			color: #30504F;
			font-size: 20px;
			padding: 13px 0 0 30px;
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
		</style>
	</head>

	<body>
		<%@ include file="/back_end/loading.file" %> <!-- loading -->
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
		
		<div class="main-content">
			<a class="btn btn-secondary light" href="<%=request.getContextPath()%>/back_end/roomType/listAllRoomType.jsp">&lt; 回房型列表</a>
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
<!-- 				<font style="color:red">請修正以下錯誤:</font> -->
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message}</li>
						</c:forEach>
					</ul>
			</c:if>
			<div class="card col-xl-9">
<!-- 				<div class="card-header"></div>                     -->
				<form method="post" action="<%=request.getContextPath()%>/room/RoomType" name="updateRoomType">
					<div class="card-body d-flex justify-content-center">
						<div class="col-xl-8">
							<div class="row mb-2">
							    <label for="type_name" class="col-sm-3 col-form-label">房型編號</label>
							    <div class="pk col-sm-8">${roomTypeVO.type_no}</div>
							</div>
							<div class="row mb-2">
							    <label for="type_name" class="col-sm-3 col-form-label">房型名稱</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_name" maxlength="10" class="form-control" id="type_name" value="${roomTypeVO.type_name}">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_qty" class="col-sm-3 col-form-label">容納人數</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_qty" class="form-control" id="type_qty" value="${roomTypeVO.type_qty}">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_price" class="col-sm-3 col-form-label">金額</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_price" class="form-control" id="type_price" value="${roomTypeVO.type_price}">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_size" class="col-sm-3 col-form-label">房型大小</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_size" class="form-control" id="type_size" value="${roomTypeVO.type_size}">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="bed_size" class="col-sm-3 col-form-label">床型</label>
							    <div class="col-sm-8">
							    	<input type="text" name="bed_size" class="form-control" id="bed_size" value="${roomTypeVO.bed_size}">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_info" class="col-sm-3 col-form-label">房型資訊</label>
							    <div class="col-sm-8">
							    	<textarea name="type_info" rows="3" class="form-control" id="type_info">${roomTypeVO.type_info}</textarea>
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_facility" class="col-sm-3 col-form-label">房型設施</label>
							    <div class="col-sm-8">
							    	<textarea name="type_facility" rows="2" class="form-control" id="type_facility">${roomTypeVO.type_facility}</textarea>
							    </div>
							</div>
							<div class="row mb-2">
							    <label class="col-sm-3 col-form-label">房型狀態</label>
							    <div class="col-sm-8">
								    <select class="mt-2 form-select" id="type_facility" name="type_state">
	                                    <option value = true ${roomTypeVO.type_state == true ? 'selected' : '' }>上架</option>
	                                    <option value = false ${roomTypeVO.type_state == false ? 'selected' : '' }>下架</option>
	                                </select>
                                </div>	
							</div>
						</div>
					</div>
					<div class="mb-3 d-flex justify-content-center align-items-center">
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="type_no" value="${roomTypeVO.type_no}">
						<button type="submit" class="btn btn-primary col-lg-3">修改</button>
                	</div>
				</form>
			</div>
		</div>
		
		<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
		<script>
			$(document).ready(function() {
				$("#pagename").text("修改房型【房型 No. ${roomTypeVO.type_no}】");
			} );
		</script>
	</body>
</html>