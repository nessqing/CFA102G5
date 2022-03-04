<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomOrder.model.*"%>
<%@ page import="com.roomOrderDetail.model.*"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.util.*"%>


<jsp:useBean id="roomTypeSvc" class="com.roomType.model.RoomTypeService" />
<jsp:useBean id="roomOrderDetailSvc" class="com.roomOrderDetail.model.RoomOrderDetailService" />
<%
	LocalDate today = LocalDate.now();

	Integer mem_no = new Integer(request.getParameter("mem_no"));
	RoomOrderService orderSvc = new RoomOrderService();
	List<RoomOrderVO> ordList = orderSvc.getAllByMemRoomOrder(mem_no);
	pageContext.setAttribute("ordList", ordList);
	
	
	
// 	RoomOrderDetailService detailSvc = new RoomOrderDetailService();
// 	List<RoomOrderDetailVO> detailList = detailSvc.getAll();
// 	pageContext.setAttribute("detailList", detailList);
%>



<!doctype html>
<html>
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="actSvc" scope="page" class="com.activityOrder.model.ActivityOrderService" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
    </head>
    <body>
		<%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->
	<!-- Inner Banner -->
	<div class="inner-banner">
		<div class="container">
			<div class="inner-title">
				<ul>
					<li><a href="<%=request.getContextPath()%>/front_end/index/index.jsp">Home</a></li>
					<li><i class='bx bx-chevron-right'></i></li>
					<li><a href="<%=request.getContextPath()%>/front_end/member/memberHome.jsp">singin</a></li>
					<li><i class='bx bx-chevron-right'></i></li>
					<li>MemberCentre</li>
				</ul>
				<h3>會員中心</h3>
			</div>
		</div>
	</div>
	<!-- Inner Banner End -->

<style>


body{
  margin: 0;
}

header.header, footer.footer{

  width: 1200px;
  margin: 0 auto;
  box-sizing: border-box;
}
header.header{
  margin-bottom: 20px;
  margin-top: 20px;
}
footer.footer{
  margin-top: 20px;
}


div.parent_container{
  width: 1200px;
  margin: 0 auto;
  font-size: 0;
  box-sizing: border-box;
}
div.parent_container aside.aside, div.parent_container main.main, div.parent_container div.sub_aside{
  display: inline-block;
  vertical-align: top;
  font-size: 1rem; /* 16px */
  box-sizing: border-box;
}
div.parent_container aside.aside{

  width: 200px;
  margin-right: 20px;
}

div.parent_container main.main{

  width: calc(100% - 200px - 20px - 200px - 20px);
}

div.parent_container div.sub_aside{
  width: 200px;

  margin-left: 20px;
}
/*  footer.footer2{  */
/*  width: 100%;  */
/*  position: absolute;  */
/*  bottom: 0  */
/*  }  */

</style>
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

<header class="header">
<div>
<h2>${memberSvc.getOneBymail(mem_mail).mem_name}</h2>您好
</div>
 
</header>

<div class="parent_container">
  <aside class="aside">
  
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do">
<input type="hidden" name="action" value="getOneBymail">
<input type="hidden" name="mem_mail" value="${mem_mail}">
<input type="submit" class="default-btn btn-bg-one" value="個人資訊"></FORM><br>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/member/memberOrder.jsp">
<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}">
<input type="submit" class="default-btn btn-bg-one" value="住宿管理"></FORM><br>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/member/memberActivity.jsp">
<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}">
<input type="submit" class="default-btn btn-bg-one" value="活動管理"></FORM><br>

<FORM METHOD="post" ACTION="">
<input type="submit" class="default-btn btn-bg-one" value="客服資訊"></FORM><br>
  </aside>

  <main class="main">
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
<!-- 					<th>訂單編號</th> -->
<!-- 					<th>會員編號</th> -->
					<th>房型</th>
					<th>間數</th>
					<th>預計入住日期</th>
					<th>預計退房日期</th>
					<th>訂單狀態</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="orderVO" items="${ordList}">
				<tr class="view">
<%-- 					<td>${orderVO.ord_no}</td> --%>
<%-- 					<td>${orderVO.mem_no}</td> --%>
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
<!-- 						今天小於等於開始日 今天會出現 且 狀態是1-->	
						<c:if test="${!LocalDate.now().isAfter(orderVO.start_date.toLocalDate()) && orderVO.ord_state == 1 || orderVO.ord_state == 2}">				
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do">
							<input type="submit" class="btn btn-secondary btn-sm" value="取消訂單">
							<input type="hidden" name="action" value="cancelroom">
							<input type="hidden" name=ord_no value="${orderVO.ord_no}">
							<input type="hidden" name=qty value="${orderVO.rm_num}">
							<input type="hidden" name=type_no value="${orderVO.type_no}">
							<input type="hidden" name=start_date value="${orderVO.start_date}">
							<input type="hidden" name=end_date value="${orderVO.end_date}">
							<input type="hidden" name=mem_no value="${memberSvc.getOneBymail(mem_mail).mem_no}">
							</FORM>
						</c:if>
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
<%-- 									<c:forEach var="detailVO" items="${detailList}"> --%>
									<c:forEach var="detailVO" items="${roomOrderDetailSvc.getAllByOrdno(orderVO.ord_no)}">
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
    
  </main>
  
</div>

<footer class="footer">
</footer>
 <footer class="footer2">
 		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
 
 </footer>

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
