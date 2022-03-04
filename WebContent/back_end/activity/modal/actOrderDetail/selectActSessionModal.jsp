<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="actSessionService" class="com.activitySession.model.ActivitySessionService" />
<jsp:useBean id="actService" class="com.activity.model.ActivityService" />
<c:set scope="page" value="${actSessionService.getActSessionByPk(param.actSessionNo)}" var="actSessionVO" />

<div class="modal fade mar" id="staticBackdropActSession${param.actSessionNo}"
	data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title" id="staticBackdropLabel">查看活動場次-${param.actSessionNo}</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<table class="table">
				<tr>
					<th>場次編號</th>
					<th>活動名稱</th>
					<th>活動報名開始日</th>
					<th>活動報名截止日</th>
					<th>實際人數</th>				
					<th>開始日</th>				
					<th>開始時間</th>				
					<th>最高上限人數</th>				
					<th>最低下限人數</th>				
					<th>舉辦狀態</th>				
				</tr>
			
				<tr>
					<th>${actSessionVO.act_session_no}</th>
					<td>
						<c:forEach var="actVO" items="${actService.all}">
							<c:if test="${actSessionVO.act_no == actVO.act_no}">
								${actVO.act_name}
							</c:if>
						</c:forEach>
					</td>
					<td>${actSessionVO.act_start_date}</td>
					<td>${actSessionVO.act_end_date}</td>
					<td>${actSessionVO.act_session_real_number}</td>
					<td>${actSessionVO.act_session_start_date}</td>
					<td>
						<fmt:parseDate  value="${actSessionVO.act_session_start_time}"  type="time" pattern="H:mm" var="parsedTime" />
						<fmt:formatDate value="${parsedTime}" type="time" pattern="H:mm"/>						
					</td>
					<td>${actSessionVO.act_session_upper_limit}</td>
					<td>${actSessionVO.act_session_lower_limit}</td>
					<td class="${actSessionVO.act_session_hold_state == true ? 'upstate' : 'downstate' }">${actSessionVO.act_session_hold_state == true ? "舉辦" : "不舉辦"}</td>					
				</tr>
			</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
			</div>
		</div>
	</div>
</div>