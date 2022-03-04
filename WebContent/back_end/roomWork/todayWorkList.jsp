<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomOrder.model.*"%>
<%@ page import="com.roomOrderDetail.model.*"%>
<%@ page import="java.time.LocalDate"%>

<jsp:useBean id="roomTypeSvc" class="com.roomType.model.RoomTypeService" />
<jsp:useBean id="roomSvc" class="com.room.model.RoomService" />
<jsp:useBean id="orderSvc" class="com.roomOrder.model.RoomOrderService" />
<jsp:useBean id="detailSvc" class="com.roomOrderDetail.model.RoomOrderDetailService" />
<jsp:useBean id="memberSvc" class="com.member.model.MemberService" />

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.9/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.dataTables.min.css" />
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<%@ include file="/back_end/commonCSS.file"%>
<!-- 基本CSS檔案 -->
<%@ include file="/back_end/commonJS.file"%>
<!-- 基本JS檔案 -->
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<style>
.bxs {
	font-size: 50px;
	color: #aaa;
}

input[type="text"] {
	width: 100%;
	margin-right: 10px;
	font-size: 20px;
	font-weight: 600;
}

div.room_name {
	margin: 10px 20px;
	width: 25%;
}

.modal-content {
	background: #f5f3ee;
}

li.select2-results__option {
	font-size: 18px;
}

.select2-selection {
	font-size: 18px;
	padding: 10px;
}

h4 {
	font-weight: 600;
	color: #30504F;
}

h3.card-title {
	color: #996A4D;
}

.to-jump {
	padding: 15px 30px;
	background: #D1E6E6;
}

.to-jump:last-child {
	padding: 10px 30px 5px 30px;
	cursor: default;
}

.to-jump:last-child h4 {
	margin-bottom: 0px;
}

.to-jump h3 {
	font-size: 30px;
	font-weight: 600;
	color: #996A4D;
	padding-top: 10px;
}

.to-jump p {
	font-size: 14px;
	color: #996A4D;
	padding: 0;
	margin: 0;
}

div.main-content {
	margin-top: 0px;
}

div.main-content>h3 {
	/* 			margin: 40px 10px 0px 10px; */
	font-size: 26px;
}

a {
	cursor: pointer;
}

table.dataTable tbody td {
	padding: 5px;
	font-size: 20px;
	font-weight: 500;
	border-bottom: 0;
	letter-spacing: 0.5px;
}

h3.checkInTitle {
	color: #fff;
	background: #a2bfb9;
	padding: 10px;
	font-weight: 600;
	position: relative;
	bottom: -55px;
}

table#checkInTable tr.odd {
	background: #edf2f1 !important;
}

h3.checkOutTitle {
	color: #fff;
	background: #C7B8A1;
	padding: 10px;
	font-weight: 600;
	position: relative;
	bottom: -55px;
}

table#checkOutTable tr.odd {
	background: #f5f5f0 !important;
}

h3.stayTitle {
	color: #fff;
	background: #c0c4d3;
	padding: 10px;
	font-weight: 600;
	position: relative;
	bottom: -55px;
}

table#stayTable tr.odd {
	background: #f5f6f8 !important;
}

td.sorting_1 {
	border-bottom: #ddd !important;
	padding-left: 35px !important;
}

.dataTables_filter>label {
	font-size: 20px;
	color: #fff;
}

.hidden {
	display: none;
}
.card {
    margin-bottom: 0;
}
</style>
</head>
<body>
	<%-- 		<%@ include file="/back_end/loading.file" %> <!-- loading --> --%>
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->

	<div class="main-content">
		<div class="row d-flex justify-content-around">
			<div class="col-xl-2 card to-jump">
				<a href="#checkInTable">
					<div class="text-center row">
	
						<div class="col">
							<h4>今日待入住訂單</h4>
							<h3>${orderSvc.checkInList().size()}</h3>
						</div>
	
						<div class="col-4 bxs d-flex justify-content-center align-items-center">
							<i class='bx bx-log-in'></i>
						</div>
	
					</div>
				</a>
			</div>

			<div class="col-xl-2 card to-jump">
				<a href="#checkOutTable">
					<div class="text-center row">
					
						<div class="col">
							<h4>今日待退房房間</h4>
							<h3>${detailSvc.checkoutList().size()}</h3>
						</div>
	
						<div class="col-4 bxs d-flex justify-content-center align-items-center">
							<i class='bx bx-log-out'></i>
						</div>
	
					</div>
				</a>
			</div>

			<div class="col-xl-2 card to-jump">
				<a href="#stayTable">
					<div class="text-center row">
	
						<div class="col">
							<h4>入住中房間</h4>
							<h3>${detailSvc.stayList().size()}</h3>
						</div>
	
						<div class="col-4 bxs d-flex justify-content-center align-items-center">
							<i class='bx bx-home-smile'></i>
						</div>
	
					</div>
				</a>
			</div>

			<div class="col-xl-2 card to-jump">
				<div class="text-center row">

					<div class="col">
						<h4>房間使用率</h4>
						<p>預計入住房間 / 可使用房間</p>
						<h3>${orderSvc.getRoomStayRate()} %</h3>
					</div>

				</div>
			</div>
		</div>

		<h3 class="checkInTitle text-center"><i class='bx bx-log-in-circle' ></i>
			今日待入住
			<h3>
				<table id="checkInTable" class="display" style="min-width: 800px;">
					<thead>
						<tr>
							<th>訂單編號</th>
							<th>會員資料</th>
							<th class="hidden">會員帳號</th>
							<th class="hidden">會員電話</th>
							<th>預計入住日</th>
							<th>預計退房日</th>
							<th>訂購人姓名</th>
							<th>訂購人電話</th>
							<th>入住</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="checkInVO" items="${orderSvc.checkInList()}">
							<tr>
								<td>${checkInVO.ord_no}</td>
								<td>${checkInVO.mem_no} - ${memberSvc.getOne(checkInVO.mem_no).mem_name}</td>
								<td class="hidden">${memberSvc.getOne(checkInVO.mem_no).mem_mail}</td>
								<td class="hidden">${memberSvc.getOne(checkInVO.mem_no).mem_mobile}</td>
								<td>${checkInVO.start_date}</td>
								<td>${checkInVO.end_date}</td>
								<td>${checkInVO.name}</td>
								<td>${checkInVO.phone}</td>
								<td><a class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal${checkInVO.ord_no}">CHECK IN</a> <!-- 						<td> --> <!-- ****Modal**** -->
									<div class="modal fade " id="exampleModal${checkInVO.ord_no}" tabindex="-1" aria-labelledby="exampleModalLabel${checkInVO.ord_no}" aria-hidden="true">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
											<form method="post" action="<%=request.getContextPath()%>/room/RoomRsv">
												<div class="modal-body">

													<div class="card-body">
														<h3>訂單編號${checkInVO.ord_no} - ${roomTypeSvc.getOneRoomType(checkInVO.type_no).type_name}   共${detailSvc.getAllByOrdno(checkInVO.ord_no).size()}間房間</h3>
														<c:set var="len" value="${detailSvc.getAllByOrdno(checkInVO.ord_no).size()}" scope="page"></c:set>
														
														<div class="mt-4">
															<h3 class="card-title">
																<i class='bx bx-home-alt'></i> 來選房間
															</h3>
														</div>
														<h4>備註： ${checkInVO.note}</h4>
														<!-- 房號select -->
														<select id="limit-select${checkInVO.ord_no}" name="rooms[]" multiple="multiple">
															<c:forEach var="room" items="${roomSvc.getAllByTypeState(checkInVO.type_no)}">
																<option value="${room.rm_no}">${room.rm_no}-${room.rm_info}</option>
															</c:forEach>
														</select>
														
														<div class="mt-4">
															<h3 class="card-title">
																<i class='bx bx-user'></i> 填寫房間入住人
															</h3>
														</div>
														<% int i = 1; %>
														<!-- 入住人姓名 -->
														<div class="d-flex flex-wrap">
															<c:forEach var="detail" items="${detailSvc.getAllByOrdno(checkInVO.ord_no)}">
																<div class="room_name">
																	<label>第<%= i++ %>間</label> <input class="form-control room_name" type="text" name="names[]" maxlength="10">
																</div>
															</c:forEach>
														</div>

													</div>

												</div>

												<div class="modal-footer  d-flex justify-content-center">
													<input type="hidden" name="ord_no"  value="${checkInVO.ord_no}"> 
 			     									<input type="hidden" name="action"	value="checkIn"> 
													<input type="button" class="btn btn-secondary mr-4" data-bs-dismiss="modal" value="取消">
													<button type="submit" class="btn btn-primary col-xl-3 col-sm-3">送出</button>
												</div>
											</form>
											</div>
										</div>
									</div> <!-- ****Modal結束**** --></td>
							</tr>
							<script>	
					$('#limit-select${checkInVO.ord_no}').select2({
						maximumSelectionLength: ${len}
					});
					</script>
						</c:forEach>
					</tbody>
				</table>

				<h3 class="checkOutTitle text-center"><i class='bx bx-log-out-circle' ></i>
					今日待退房
					<h3>
						<table id="checkOutTable" class="display" style="min-width: 800px;">
							<thead>
								<tr>
									<th>明細編號</th>
									<th>會員資料</th>
									<th class="hidden">會員帳號</th>
									<th class="hidden">會員電話</th>
									<th>實際入住日</th>
									<th>預計退房日</th>
									<th>入住人姓名</th>
									<th>房間號碼</th>
									<th>退房</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="checkOutVO" items="${detailSvc.checkoutList()}">
									<tr>
										<td>${checkOutVO.detail_no}</td>
										<td>${orderSvc.getOneRoomOrder(checkOutVO.ord_no).mem_no} - ${memberSvc.getOne(orderSvc.getOneRoomOrder(checkOutVO.ord_no).mem_no).mem_name}</td>
										<td class="hidden">${memberSvc.getOne(orderSvc.getOneRoomOrder(checkOutVO.ord_no).mem_no).mem_mail}</td>
										<td class="hidden">${memberSvc.getOne(orderSvc.getOneRoomOrder(checkOutVO.ord_no).mem_no).mem_mobile}</td>
										<td>${checkOutVO.checkin_date}</td>
										<td>${orderSvc.getOneRoomOrder(checkOutVO.ord_no).end_date}</td>
										<td>${roomSvc.getOneRoom(checkOutVO.rm_no).name_title}</td>
										<td>${checkOutVO.rm_no}</td>
										<td>
										<form method="post" action="<%=request.getContextPath()%>/room/RoomRsv">
											<input type="hidden" name="detail_no"  value="${checkOutVO.detail_no}">
											<input type="hidden" name="rm_no"  value="${checkOutVO.rm_no}">
											<input type="hidden" name="action"	value="checkOut">
											<button type="submit" class="btn btn-secondary btn-sm">CHECK OUT</button>
										</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<h3 class="stayTitle text-center"><i class='bx bx-down-arrow-circle' ></i>
							入住中清單
							<h3>
								<table id="stayTable" class="display" style="min-width: 800px;">
									<thead>
										<tr>
											<th>明細編號</th>
											<th>會員資料</th>
											<th class="hidden">會員帳號</th>
											<th class="hidden">會員電話</th>
											<th>實際入住日</th>
											<th>預計退房日</th>
											<th>入住人姓名</th>
											<th>房間號碼</th>
											<th>提前退房</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="stayVO" items="${detailSvc.stayList()}">
											<tr>
												<td>${stayVO.detail_no}</td>
												<td>${orderSvc.getOneRoomOrder(stayVO.ord_no).mem_no} - ${memberSvc.getOne(orderSvc.getOneRoomOrder(stayVO.ord_no).mem_no).mem_name}</td>
												<td class="hidden">${memberSvc.getOne(orderSvc.getOneRoomOrder(stayVO.ord_no).mem_no).mem_mail}</td>
												<td class="hidden">${memberSvc.getOne(orderSvc.getOneRoomOrder(stayVO.ord_no).mem_no).mem_mobile}</td>
												<td>${stayVO.checkin_date}</td>
												<td>${orderSvc.getOneRoomOrder(stayVO.ord_no).end_date}</td>
												<td>${roomSvc.getOneRoom(stayVO.rm_no).name_title}</td>
												<td>${stayVO.rm_no}</td>
												<td>
												<form method="post" action="<%=request.getContextPath()%>/room/RoomRsv">
													<input type="hidden" name="detail_no"  value="${stayVO.detail_no}">
													<input type="hidden" name="rm_no"  value="${stayVO.rm_no}">
													<input type="hidden" name="type_no"  value="${orderSvc.getOneRoomOrder(stayVO.ord_no).type_no}">
													<input type="hidden" name="end_date"  value="${orderSvc.getOneRoomOrder(stayVO.ord_no).end_date}">
	 			     								<input type="hidden" name="action"	value="checkOutEarly">
													<button type="submit" class="btn btn-secondary btn-sm">提前退房</button>
												</form>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
	</div>




	<script>
		$(document).ready(
			function() {
				$("#pagename").text("當日房務");
				$("#checkInTable").DataTable( {
			    	"responsive": true,
			    	"paging": false,
			    	"lengthMenu": false,
			    	"info": false,
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
			            "searchPlaceholder": "會員帳號/電話也能搜",
			            "paginate": {
			                "first": "第一頁",
			                "previous": "上一頁",
			                "next": "下一頁",
			                "last": "最後一頁"
			            },
			        }
			    } );
				$("#checkOutTable").DataTable( {
			    	"responsive": true,
			    	"paging": false,
			    	"lengthMenu": false,
			    	"info": false,
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
			            "searchPlaceholder": "(๑•̀ㅂ•́)و",
			            "paginate": {
			                "first": "第一頁",
			                "previous": "上一頁",
			                "next": "下一頁",
			                "last": "最後一頁"
			            },
			        }
			    } );
				$("#stayTable").DataTable( {
			    	"responsive": true,
			    	"paging": false,
			    	"lengthMenu": false,
			    	"info": false,
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
			            "searchPlaceholder": "ก็ ʕ•͡ᴥ•ʔ ก้",
			            "paginate": {
			                "first": "第一頁",
			                "previous": "上一頁",
			                "next": "下一頁",
			                "last": "最後一頁"
			            },
			        }
			    } );
// 				$('#limit-select${checkInVO.ord_no}').select2({
// 					maximumSelectionLength: ${len}
// 				});
		});
	</script>
</body>
</html>