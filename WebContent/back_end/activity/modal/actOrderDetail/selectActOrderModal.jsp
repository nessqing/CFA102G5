<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="actOrderService" class="com.activityOrder.model.ActivityOrderService" />
<jsp:useBean id="memberService" class="com.member.model.MemberService" />
<c:set scope="page" value="${actOrderService.getActOrderByPk(param.actOrderNo)}" var="actOrderVO" />

<div class="modal fade mar" id="staticBackdropActOrder${param.actOrderNo}"
	data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title" id="staticBackdropLabel">查看活動訂單-${param.actOrderNo}</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<table class="table">
				<tr>
					<th>訂單編號</th>
					<th>會員名稱</th>
					<th>活動訂購日期</th>
					<th>訂單總金額</th>
					<th>訂購人稱謂</th>				
					<th>訂購人名稱</th>				
					<th>訂購人電話</th>				
					<th>訂購人信箱</th>				
					<th>訂購人信用卡卡號</th>	
				</tr>
				<tr>
					<th>${actOrderVO.act_order_no}</th>
					<td>
						<c:forEach var="memberVO" items="${memberService.all}">
							<c:if test="${actOrderVO.mem_no == memberVO.mem_no}">
								${memberVO.mem_name}
							</c:if>
						</c:forEach>
					</td>
					<td>
						<fmt:parseDate  value="${actOrderVO.act_booking_date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
						<fmt:formatDate value="${parsedDate}" type="date" pattern="yyyy-MM-dd"/>
					</td>
					<td>${actOrderVO.act_order_total_price}</td>
					<td>${actOrderVO.act_order_title}</td>
					<td>${actOrderVO.act_order_name}</td>
					<td>${actOrderVO.act_order_phone}</td>
					<td>${actOrderVO.act_order_email}</td>
					<td>${actOrderVO.act_order_credit_card}</td>				
				</tr>
			</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
			</div>
		</div>
	</div>
</div>