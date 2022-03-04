<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>

<%
  RoomTypeVO roomTypeVO = (RoomTypeVO) request.getAttribute("roomTypeVO");
%>

<!DOCTYPE html>
<html lang="en">
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
				<form method="post" action="<%=request.getContextPath()%>/room/RoomType" name="addRoomType">
					<div class="card-body d-flex justify-content-center">
						<div class="col-xl-8">
							<div class="row mb-2">
							    <label for="type_name" class="col-sm-3 col-form-label">房型名稱</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_name" maxlength="10" class="form-control" id="type_name" value="<%=(roomTypeVO == null) ? "" : roomTypeVO.getType_name()%>">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_qty" class="col-sm-3 col-form-label">容納人數</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_qty" class="form-control" id="type_qty" value="<%=(roomTypeVO == null) ? "" : roomTypeVO.getType_qty()%>">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_price" class="col-sm-3 col-form-label">金額</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_price" class="form-control" id="type_price" value="<%=(roomTypeVO == null) ? "" : roomTypeVO.getType_price()%>">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_size" class="col-sm-3 col-form-label">房型大小</label>
							    <div class="col-sm-8">
							    	<input type="text" name="type_size" class="form-control" id="type_size" value="<%=(roomTypeVO == null) ? "" : roomTypeVO.getType_size()%>">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="bed_size" class="col-sm-3 col-form-label">床型</label>
							    <div class="col-sm-8">
							    	<input type="text" name="bed_size" class="form-control" id="bed_size" value="<%=(roomTypeVO == null) ? "" : roomTypeVO.getBed_size()%>">
							    	<div class="mt-1">ex. 一張超大雙人床或兩張單人床</div>
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_info" class="col-sm-3 col-form-label">房型資訊</label>
							    <div class="col-sm-8">
							    	<textarea name="type_info" rows="4" class="form-control" id="type_info"><%=(roomTypeVO == null) ? "" : roomTypeVO.getType_info()%></textarea>
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_facility" class="col-sm-3 col-form-label">房型設施</label>
							    <div class="col-sm-8">
							    	<textarea name="type_facility" rows="4" class="form-control" id="type_facility"><%=(roomTypeVO == null) ? "" : roomTypeVO.getType_facility()%></textarea>
							    	<div class="mt-1">ex. 42吋液晶電視,豪華吧台,專屬管家</div>
							    </div>
							</div>
						</div>
					</div>
					<div class="mb-3 d-flex justify-content-center align-items-center">
						<input type="hidden" name="action" value="insert">
						<input class="btn btn-primary col-lg-3" type="submit" value="新增">
                	</div>
				</form>
			</div>
		</div>

		<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
		<script>
			$(document).ready(function() {
				$("#pagename").text("新增房型");
			} );
		</script>
	</body>
</html>