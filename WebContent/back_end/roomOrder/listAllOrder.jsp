<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomOrder.model.*"%>
<%@ page import="com.roomOrderDetail.model.*"%>
<%@ page import="java.time.LocalDate"%>

<jsp:useBean id="roomTypeSvc" class="com.roomType.model.RoomTypeService" />
<jsp:useBean id="orderSvc" class="com.roomOrder.model.RoomOrderService" />
<jsp:useBean id="detailSvc" class="com.roomOrderDetail.model.RoomOrderDetailService" />

<%	
	//用來計算各個狀態的有幾筆資料
// 	pageContext.setAttribute("orderSvc", orderSvc);
// 	pageContext.setAttribute("detailSvc", detailSvc);
	
	// 第一次進來執行if裡面，list是getAll
	// 不是第一次進來(點擊狀態分類從controller過來的)，table中就用forward過來的list
	if (request.getAttribute("ordList") == null) {
		List<RoomOrderVO> ordList = orderSvc.getAllRoomOrder();
		pageContext.setAttribute("ordList", ordList);
	}

	// 切換分類的下底線，第一次進來分類0，第一個li加底線
	if (request.getAttribute("ord_state") == null && request.getAttribute("type_no") != null) {
		pageContext.setAttribute("ord_state", 9);
	} else if (request.getAttribute("ord_state") == null && request.getAttribute("type_no") == null) {
		pageContext.setAttribute("ord_state", 0);
	}

	// 切換房型分類的下底線，第一次進來分類0，第一個li加底線
	if (request.getAttribute("type_no") == null) {
		pageContext.setAttribute("type_no", 0);
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.9/css/jquery.dataTables.min.css"/>
    	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.dataTables.min.css"/>
		<%@ include file="/back_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
		<style>
		table.fold-table tbody tr.view {
            cursor: pointer;
        }
        table.fold-table tbody tr.view:hover {
            box-shadow: 0 0.125rem 1rem rgb(0 0 0 / 19%);
        }
		table.fold-table tbody tr.view.open {
            background: #8FC2C2;
        }
        table.fold-table tbody tr.view.open td {
            color: white;
        }
		table.fold-table tbody tr.fold {
            display: none;
        }
        table.fold-table tbody tr.fold.open {
            display: table-row;
        }
		table {
			width: 90%;
		}
		table.fold-table>thead>tr>th {
 			align: center;
		    font-size: 1.125rem;
		    text-transform: capitalize;
		    font-weight: 600;
		    padding: 1.25rem 0.9375rem;
		}
		thead {
			background: #F7F6F2;
		}
		td,div {
			font-size: 1rem;
			letter-spacing: 0.5px;
		}
		div.order-data>div {
			font-size: 1.1rem;
			padding: 1%;
		}
		table tbody tr td:nth-child(1),table tbody tr td:nth-child(2) {
			padding-left: 45px ;
		}
		table.table-striped {
			width: 90%;
			margin: auto;
			table-layout: fixed;
		}
		table.table-striped tbody tr:last-child {
			min-width: 400px;
		}
		h4 {
			color: #996A4D;
		}
		table.table-striped i {
			font-size: 12px;
		}
		table.table-striped th, table.table-striped td {
		    padding: 0.6rem 0.3rem;
		}
		table.table-striped tfoot td {
		    font-weight: 600;
		    font-size: 1.1rem;
		}
		.search-area {
			max-width: 20rem;
			margin-left: auto;
			margin-right: 3%;
		}
		.custom-dropdown {
			margin: 0 1%;
		}
		i.bxs-chevron-down {
			font-size: 18px;
			padding-left: 7px;
		}
		table i {
			padding: 5px;
		}
		.btn-sm, .btn-group-sm>.btn {
			font-size: 1rem !important;
		    padding: 0.625rem 1.6rem;
		}
		.dropdown-menu {
		    z-index: 3;
		    min-width: 9rem;
		    padding: .5rem 0;
		    text-align: center;
		    border-radius: .5rem;
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
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="d-flex mb-4 align-items-center flex-wrap">
			<div class="dropdown custom-dropdown">
				<button type="button" class="btn btn-sm btn-secondary" data-bs-toggle="dropdown">
					<c:choose>
						<c:when test="${type_no==0}">依房型篩選<i class='bx bxs-chevron-down'></i></c:when>
						<c:when test="${type_no!=0}">${roomTypeSvc.getOneRoomType(type_no).type_name}<i class='bx bxs-chevron-down'></i></c:when>
					</c:choose>
				</button>
				<div class="dropdown-menu dropdown-menu-end">
					<c:forEach var="roomTypeVO" items="${roomTypeSvc.allRoomType}">
						<a class="dropdown-item" href="<%=request.getContextPath()%>/room/RoomOrder?type_no=${roomTypeVO.type_no}&action=getAllByType">${roomTypeVO.type_name}</a>
					</c:forEach>
				</div>
			</div>
			<div class="card-tabs mt-3 mt-sm-0">
				<ul class="nav nav-tabs" role="tablist">
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/room/RoomOrder?action=getAll">所有訂單 (${orderSvc.getAllRoomOrder().size()})</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/room/RoomOrder?ord_state=1&action=getAllByOrdState">已付款 (${orderSvc.getAllByOrdState(1).size()})</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/room/RoomOrder?ord_state=2&action=getAllByOrdState">已改期 (${orderSvc.getAllByOrdState(2).size()})</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/room/RoomOrder?ord_state=3&action=getAllByOrdState">已取消 (${orderSvc.getAllByOrdState(3).size()})</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/room/RoomOrder?ord_state=4&action=getAllByOrdState">已完成 (${orderSvc.getAllByOrdState(4).size()})</a></li>
				</ul>
			</div>
			<div class="input-group search-area">
				<input type="text" class="form-control" placeholder="Search here"> <span class="input-group-text"><a href="javascript:void(0)"> <i class="flaticon-381-search-2"></i></a></span>
			</div>
		</div>
		<div>
<%-- 			${roomTypeSvc.getOneRoomType(type_no).type_name} --%>
<%-- 			${orderSvc.getAllByOrdState(ord_state)} 要轉中文 --%>
<%-- 			搜尋結果0筆時要出現什麼 --%>
<!-- 			else -->
		</div>
		<table class="table fold-table">
			<thead>
				<tr>
					<th>訂單編號</th>
					<th>會員編號</th>
					<th>預計入住日期</th>
					<th>預計退房日期</th>
					<th>房型</th>
					<th>間數</th>
					<th>訂單狀態</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="orderVO" items="${ordList}">
					<tr class="view">
						<td>${orderVO.ord_no}</td>
						<td>${orderVO.mem_no}</td>
						<td>${orderVO.start_date}</td>
						<td>${orderVO.end_date}</td>
						<td>${roomTypeSvc.getOneRoomType(orderVO.type_no).type_name}</td>
						<td>${orderVO.rm_num}</td>
						<td><c:choose>
								<c:when test="${orderVO.ord_state==1}">
									<i class='bx bxs-circle' style='color: green'></i>已付款</c:when>
								<c:when test="${orderVO.ord_state==2}">
									<i class='bx bxs-circle' style='color: orange'></i>已改期</c:when>
								<c:when test="${orderVO.ord_state==3}">
									<i class='bx bxs-circle' style='color: red'></i>已取消</c:when>
								<c:when test="${orderVO.ord_state==4}">
									<i class='bx bxs-circle' style='color: #aaa'></i>已完成</c:when>
							</c:choose></td>
					</tr>
					<tr class="fold">
						<td colspan="8">
							<div class="row d-flex justify-content-around my-2">
								<div class="col-4 order-data">
									<h4>
										<i class='bx bxs-user-voice'></i> 訂購人資料
									</h4>
									<div>付款人：${orderVO.name} ${orderVO.title}</div>
									<div>電話：${orderVO.phone}</div>
									<div>Email: ${orderVO.email}</div>
								</div>
								<div class="col-4 order-data">
									<h4>
										<i class='bx bx-credit-card'></i> 付款資料
									</h4>
<%-- 									<div>訂單成立日期： <fmt:formatDate value="${orderVO.ord_date}" pattern="yyyy-MM-dd HH:mm:ss" /></div> --%>
									<div>訂單成立日期： ${orderVO.ord_date}</div>
									<div>信用卡號碼： ${orderVO.payment}</div>
									<div>備註： ${orderVO.note}</div>
								</div>
							</div>
							<table class="table table-striped">
								<thead>
									<tr>
										<th>訂單編號</th>
										<th>明細編號</th>
										<th>實際入住日期</th>
										<th>實際退房日期</th>
										<th>明細狀態(房間編號)</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="detailVO" items="${detailSvc.getAllByOrdno(orderVO.ord_no)}">
										<tr>
											<td>${detailVO.ord_no}</td>
											<td>${detailVO.detail_no}</td>
											<td>${detailVO.checkin_date}</td>
											<td>${detailVO.checkout_date}</td>
											<td>							
												<c:choose>
													<c:when test="${orderSvc.getOneRoomOrder(detailVO.ord_no).ord_state==3}">
														<i class='bx bxs-square' style='color: red'></i>已取消</c:when>
													<c:when test="${detailVO.detail_state==1}">
														<i class='bx bxs-square' style='color: green'></i>待入住</c:when>
													<c:when test="${detailVO.detail_state==2}">
														<i class='bx bxs-square' style='color: orange'></i>入住中 (${detailVO.rm_no})</c:when>
													<c:when test="${detailVO.detail_state==3}">
														<i class='bx bxs-square' style='color: gray'></i>已退房</c:when>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td>單價 <fmt:formatNumber value="${orderVO.price}" pattern="$###,###,###" /> X ${orderVO.rm_num}間
										</td>
										<td>總金額 <fmt:formatNumber value="${orderVO.total_price}" pattern="$###,###,###" /> <c:if test="${orderVO.ord_state==3}">
												<div>(已扣掉取消後退款金額)</div>
											</c:if>
										</td>
									</tr>
								</tfoot>
							</table>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->		]
<!-- 		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script> -->
<!-- 		<script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script> -->
	
		<script>
			$(document).ready(function() {
				$("#pagename").text("訂單管理");
			    $(".fold-table tr.view").on("click", function () {
		            $(this).toggleClass("open").next(".fold").toggleClass("open");
		        });
			    $("li.nav-item:eq(${ord_state+1})").children().addClass("nav-link active");
// 			    $(".btn-secondary").text("${roomTypeSvc.getOneRoomType(type_no).type_name}");
			} );
		</script>
	</body>
</html>