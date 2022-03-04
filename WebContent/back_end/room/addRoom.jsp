<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.room.model.*"%>
<%@ page import="com.roomType.model.*"%>

<%
  RoomVO roomVO = (RoomVO) request.getAttribute("roomVO");
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
			<a class="btn btn-secondary light" href="<%=request.getContextPath()%>/back_end/room/listAllRoom.jsp">&lt; 回房間列表</a>
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="card col-xl-9">  
				<form method="post" action="<%=request.getContextPath()%>/room/Room" name="addRoom">
					<div class="card-body d-flex justify-content-center">
						<div class="col-xl-8">
							<div class="row mb-2">
							    <label for="rm_no" class="col-sm-3 col-form-label">房間編號</label>
							    <div class="col-sm-8">
							    	<input type="text" name="rm_no" maxlength="3" class="form-control" id="rm_no" value="<%=(roomVO == null) ? "" : roomVO.getRm_no()%>">
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="type_no" class="col-sm-3 col-form-label">房型</label>
							    <div class="col-sm-8">
							    	<select class="mt-2 form-select" name="type_no">
							    		<jsp:useBean id="roomTypeSvc" scope="page" class="com.roomType.model.RoomTypeService" />
										<c:forEach var="roomTypeVO" items="${roomTypeSvc.allRoomType}">
											<option value="${roomTypeVO.type_no}" ${(roomVO.type_no==roomTypeVO.type_no)?'selected':'' }>${roomTypeVO.type_name}</option>
										</c:forEach>
									</select>
							    </div>
							</div>
							<div class="row mb-2">
							    <label for="rm_info" class="col-sm-3 col-form-label">房間介紹</label>
							    <div class="col-sm-8">
							    	<textarea name="rm_info" rows="2" class="form-control" id="rm_info"><%=(roomVO == null) ? "" : roomVO.getRm_info()%></textarea>
								    <div class="mt-1">ex. 3F，禁菸房，山景，出電梯右轉第一間</div>
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
				$("#pagename").text("新增房間");
			} );
		</script>
	</body>
</html>