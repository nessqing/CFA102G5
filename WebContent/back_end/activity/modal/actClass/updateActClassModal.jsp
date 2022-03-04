<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Modal -->
<div class="modal fade mar"
	id="staticBackdrop${param.actClassNo}" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title" id="staticBackdropLabel">活動類別-修改</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">

				<form id="updateActClassForm${param.actClassNo}" method="post" action="<%=request.getContextPath()%>/activity/ActivityClass">
					<label for="updateActClassNo">活動類別編號</label> 
					<input class="form-control form-control-lg" type="text"
						id="updateActClassNo" name="actClassNo" readonly
						value="${param.actClassNo}"> 
					<label id="actClassNameLabel" for="actClassName">活動類別名稱</label> 
					<input class="form-control form-control-lg" type="text" id="updateActClassName" name="actClassName" value="${param.actClassName}"
						autofocus>
					<input type="hidden" name="actClassState" value="${param.actClassState}">
					<input type="hidden" name="action" value="updateActClass">
				</form>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary"
					onclick="sendRequest(get('updateActClassForm${param.actClassNo}'));">確定</button>
			</div>
		</div>
	</div>
</div>