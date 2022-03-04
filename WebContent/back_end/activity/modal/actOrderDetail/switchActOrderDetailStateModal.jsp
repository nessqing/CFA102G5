<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="actOrderDetailSvcForSwitch" class="com.activityOrderDetail.model.ActivityOrderDetailService" />

<div class="modal fade mar" id="staticBackdropSwitchActOrderDetailState"
	data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="left:-8rem;width:80rem;">
			<div class="modal-header">
				<h2 class="modal-title" id="staticBackdropLabel">選擇欲修改的活動明細</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
		<form id="switchActOrderDetailStateForm" method="post" action="<%=request.getContextPath()%>/activity/ActivityOrderDetail">
			<div class="modal-body" style="width:800px;">
				<select name="updateActOrderDetailNo" id="switchActStateSelect" style="margin-left:120px;font-size:20px;" autofocus>
					<c:forEach var="actOrderDetailVO" items="${actOrderDetailSvcForSwitch.all}">
						<option value="${actOrderDetailVO.act_order_detail_no}">明細:${actOrderDetailVO.act_order_detail_no} - 訂單:${actOrderDetailVO.act_order_no} - 場次: ${actOrderDetailVO.act_session_no}</option>						
					</c:forEach>
				</select>
				
				<select id="switchActOrderDetailStateSelect" name="switchActOrderDetailStateSelect" style="margin-left:120px;font-size:20px;" autofocus>
					<c:forEach begin="${actOrderDetailStateNumber.count}" end="3" varStatus="actOrderDetailStateNumber">
						<c:if test="${actOrderDetailStateNumber.count == 1 }">
							<option value="${actOrderDetailStateNumber.count}">已付款</option>
						</c:if>
						<c:if test="${actOrderDetailStateNumber.count == 2 }">
							<option value="${actOrderDetailStateNumber.count}">已取消</option>
						</c:if>
						<c:if test="${actOrderDetailStateNumber.count == 3 }">
							<option value="${actOrderDetailStateNumber.count}">已改期</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<div class="modal-footer">
				<input type="hidden" name="action" value="switchActOrderDetailState">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">離開</button>
				<button type="button" class="btn btn-primary" onclick="createWhichPage();">確定</button>
			</div>
		</form>
		</div>
	</div>
</div>