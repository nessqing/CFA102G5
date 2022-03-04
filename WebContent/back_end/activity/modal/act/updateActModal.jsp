<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="actSvc" class="com.activity.model.ActivityService" />

<div class="modal fade mar" id="staticBackdropUpdateAct"
	data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title" id="staticBackdropLabel">選擇欲修改的活動</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
		<form method="post" action="<%=request.getContextPath()%>/activity/Activity">
			<div class="modal-body">
				<select name="updatePk" style="margin-left:120px;font-size:20px;" autofocus>
					<c:forEach var="actVO" items="${actSvc.all}">
						<option value="${actVO.act_no}">${actVO.act_no} - ${actVO.act_name}</option>						
					</c:forEach>
				</select>
			</div>
			<div class="modal-footer">
				<input type="hidden" name="action" value="updateAct">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">離開</button>
				<button type="submit" class="btn btn-primary">確定</button>
			</div>
		</form>
		</div>
	</div>
</div>