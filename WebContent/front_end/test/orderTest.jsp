<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomOrder.model.*"%>
<%@ page import="com.roomOrderDetail.model.*"%>
<%@ page import="java.time.LocalDate"%>

<jsp:useBean id="roomTypeSvc" class="com.roomType.model.RoomTypeService" />
<%
	LocalDate today = LocalDate.now();

	RoomOrderService orderSvc = new RoomOrderService();
	List<RoomOrderVO> ordList = orderSvc.getAllRoomOrder();
	pageContext.setAttribute("ordList", ordList);

	RoomOrderDetailService detailSvc = new RoomOrderDetailService();
	List<RoomOrderDetailVO> detailList = detailSvc.getAll();
	pageContext.setAttribute("detailList", detailList);
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
		</style>
	</head>

	<body>
		
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
		<%=LocalDate.now()%>
		<table class="table fold-table">
			<thead>
				<tr>
					<th>訂單編號</th>
					<th>會員編號</th>
					<th>房型</th>
					<th>間數</th>
					<th>預計入住日期</th>
					<th>預計退房日期</th>
					<th>訂單狀態</th>
					<th>取消</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="orderVO" items="${ordList}">
				<tr class="view">
					<td>${orderVO.ord_no}</td>
					<td>${orderVO.mem_no}</td>
					<td>${roomTypeSvc.getOneRoomType(orderVO.type_no).type_name}</td>
					<td>${orderVO.rm_num}</td>
					<td>${orderVO.start_date}</td>
					<td>${orderVO.end_date}</td>
					<td>
						<c:choose>
							<c:when test="${orderVO.ord_state==1}"><i class='bx bxs-circle' style='color:green'></i>已付款</c:when>
							<c:when test="${orderVO.ord_state==2}"><i class='bx bxs-circle' style='color:orange'></i>已改期</c:when>
							<c:when test="${orderVO.ord_state==3}"><i class='bx bxs-circle' style='color:red'></i>已取消</c:when>
							<c:when test="${orderVO.ord_state==4}"><i class='bx bxs-circle' style='color:gray'></i>已完成</c:when>
						</c:choose>
					</td>
					<td>
<!-- 						今天小於等於開始日 今天會出現 且 狀態是1-->	
						<c:if test="${LocalDate.now()<=orderVO.start_date && orderVO.ord_state == 1 || orderVO.ord_state == 2}">				
							<button type="submit" class="btn btn-secondary btn-sm">取消</button>
						</c:if>
					</td>
				</tr> 
				<tr class="fold">
					<td colspan="8">
						<div class="row d-flex justify-content-around my-2">
							<div class="col-4 order-data">
								<h4><i class='bx bxs-user-voice'></i> 訂購人資料</h4>
								<div>付款人：${orderVO.name} ${orderVO.title}</div>
								<div>電話：${orderVO.phone}</div>
								<div>Email: ${orderVO.email}</div>
							</div>
							<div class="col-4 order-data">
								<h4><i class='bx bx-credit-card'></i> 付款資料</h4>
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
									<c:forEach var="detailVO" items="${detailList}">
<%-- 									<c:forEach var="detailVO" items="${detailSvc.getAllByOrdno(ord_no)}"> --%>
<%-- 									<c:if test="${detailVO.ord_no}==1"> --%>
									<tr>
										<td>${detailVO.ord_no}</td>
										<td>${detailVO.detail_no}</td>
										<td>${detailVO.checkin_date}</td>
										<td>${detailVO.checkout_date}</td>
										<td>
										<c:choose>
											<c:when test="${detailVO.detail_state==1}"><i class='bx bxs-circle' style='color:green'></i>待入住</c:when>
											<c:when test="${detailVO.detail_state==2}"><i class='bx bxs-circle' style='color:orange'></i>入住中 (${detailVO.rm_no})</c:when>
											<c:when test="${detailVO.detail_state==3}"><i class='bx bxs-circle' style='color:gray'></i>已退房</c:when>
										</c:choose>
										</td>
									</tr>
<%-- 									</c:if> --%>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td>單價 <fmt:formatNumber value="${orderVO.price}" pattern="$###,###,###"/> X ${orderVO.rm_num}間</td>
										<td>總金額   <fmt:formatNumber value="${orderVO.total_price}" pattern="$###,###,###"/>
											<c:if test="${orderVO.ord_state==3}">
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
				$("#pagename").text("測試");
// 			    $(".fold-table tr.view").on("click", function () {
// 		            $(this).toggleClass("open").next(".fold").toggleClass("open");
// 		        });
			    $(".fold-table tr.view").on("click", function () {
		            $(this).toggleClass("open").next(".fold").toggleClass("open");
		        });
			} );
		</script>
	</body>
</html>