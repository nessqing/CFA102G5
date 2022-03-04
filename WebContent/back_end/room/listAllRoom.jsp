<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.room.model.*"%>

<jsp:useBean id="roomTypeSvc" class="com.roomType.model.RoomTypeService" />

<%
	// 用來計算各個狀態的有幾筆資料
	RoomService roomSvc = new RoomService();
	pageContext.setAttribute("roomSvc", roomSvc);
	
	// 第一次進來執行if裡面，list是getAll
	// 不是第一次進來(點擊狀態分類從controller過來的)，table中就用forward過來的list
	if (request.getAttribute("list") == null) {
		List<RoomVO> list = roomSvc.getAllRoom();
		pageContext.setAttribute("list", list);
	}

	// 切換分類的下底線，第一次進來分類0，第一個li加底線
	if (request.getAttribute("rm_state") == null) {
		pageContext.setAttribute("rm_state", 0);
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/back_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
		<style>
		table.dataTable thead th, td {
			color: #30504F;
		}
		table.dataTable tbody tr td:first-child {
			padding-left: 40px;
			font-weight: 700;
		}
		#roomTypeTable_filter {
			color: #30504F;
		}
		
		#roomTypeTable_filter input {
			border: .5px solid #8FC2C2;
		}
		
		.btn-icon-start {
			background-color: transparent;
			margin-right: 0;
		}
		
		i.bxs-pencil, i.bxs-image {
			color: #30504F;
			font-size: 16px;
			padding-right: 2px;
		}
		
		.state label {
			position: relative;
			display: inline-block;
			width: 42px;
			height: 22px;
			background-color: #E4D6C4;
			border-radius: 25px;
			cursor: pointer;
			box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.3);
			margin-top: 16px;
		}

		.state label:before {
			content: '';
			position: absolute;
			top: 3px;
			left: 4px;
			height: 15px;
			width: 15px;
			background-color: white;
			border-radius: 24px;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
		}
		
		.state input {
			display: none;
		}
		
		.state input:checked+label {
			background-color: #996A4D;
		}
		
		.state input:checked+label:before {
			transform: translateX(20px);
			box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
		}
		h3>span {
			color: #30504F;
			font-weight: 700;
		}
		.img-container {
			width: 250px;
			border-radius: 0 30px 0 30px;
			overflow: hidden;
			background-color: #F7F6F2;
		}
		img.no-img {
			padding-left: 20px;
		}
		img {
			max-width: 100%;
		}
		</style>
	</head>

	<body>
<%-- 		<%@ include file="/back_end/loading.file" %> <!-- loading --> --%>
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->
		
		<div class="main-content">
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color:red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="d-flex mb-4 justify-content-start align-items-center flex-wrap add">
				<a href='<%=request.getContextPath()%>/back_end/room/addRoom.jsp'>
					<button type="button" class="btn btn-rounded btn-primary"><span
						class="btn-icon-start text-primary"><i class='bx bx-plus' style='color:#fff' ></i>
					</span>新增</button>				
				</a>
				<div class="card-tabs mt-3 mt-sm-0">
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link" href="<%=request.getContextPath()%>/room/Room?action=getAll">所有房間 (${roomSvc.getAllRoom().size()})</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="<%=request.getContextPath()%>/room/Room?rm_state=1&action=getAllByRmState">空房 (${roomSvc.getAllByRmState(1).size()})</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="<%=request.getContextPath()%>/room/Room?rm_state=2&action=getAllByRmState">入住中 (${roomSvc.getAllByRmState(2).size()})</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="<%=request.getContextPath()%>/room/Room?rm_state=3&action=getAllByRmState">待清潔 (${roomSvc.getAllByRmState(3).size()})</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="<%=request.getContextPath()%>/room/Room?rm_state=4&action=getAllByRmState">已停用 (${roomSvc.getAllByRmState(4).size()})</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="table-responsive">
				<table id="roomTypeTable" class="display default-table" style="min-width: 845px;">
					<thead>
						<tr>
							<th>房間編號</th>
							<th>房型</th>
							<th>房間介紹</th>
							<th>房間狀態</th>
							<th>入住人姓名</th>
							<th>修改</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="roomVO" items="${list}">
						<tr>
							<td>${roomVO.rm_no}</td>
							<td>${roomTypeSvc.getOneRoomType(roomVO.type_no).type_name}</td>
							<td>${roomVO.rm_info}</td>
							<td>
								<c:choose>
									<c:when test="${roomVO.rm_state==1}">空房</c:when>
									<c:when test="${roomVO.rm_state==2}">入住中</c:when>
									<c:when test="${roomVO.rm_state==3}">待清潔</c:when>
									<c:when test="${roomVO.rm_state==4}">已停用</c:when>
								</c:choose>
							</td>
							<td>${roomVO.name_title}</td>
							<td>
<%-- 								<form method="post" action="<%=request.getContextPath()%>/room/Room"> --%>
<%-- 									<input type="hidden" name="rm_no"  value="${roomVO.rm_no}"> --%>
<!-- 			     					<input type="hidden" name="action"	value="getOneForUpdate"> -->
<!-- 									<button type="submit" class="btn btn-secondary btn-sm"><i class='bx bxs-pencil'></i>修改</button> -->
<!-- 			     				</form> -->
			     				<a class="btn btn-secondary btn-sm" href="<%=request.getContextPath()%>/room/Room?rm_no=${roomVO.rm_no}&action=getOneForUpdate">修改</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>


		<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
		<script>
			$(document).ready(function() {
				$("#pagename").text("房間列表");
			    $("#roomTypeTable").DataTable( {
			    	"lengthMenu": [5, 10, 20],
			        "language": {
			        	"processing": "處理中...",
			            "loadingRecords": "載入中...",
			            "lengthMenu": "顯示 _MENU_ 項結果",
			            "zeroRecords": "沒有符合的結果",
			            "info": "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
			            "infoEmpty": "顯示第 0 至 0 項結果，共 0 項",
			            "infoFiltered": "(從 _MAX_ 項結果中過濾)",
			            "infoPostFix": "",
			            "search": "搜尋",
			            "searchPlaceholder": "...",
			            "paginate": {
			                "first": "第一頁",
			                "previous": "上一頁",
			                "next": "下一頁",
			                "last": "最後一頁"
			            },
			        }
			    } );
			    $("li.nav-item:eq(${rm_state+1})").children().addClass("nav-link active");
			} );
		</script>
	</body>
</html>