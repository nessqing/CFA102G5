<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import ="com.activity.model.*" %>
<%@ page import ="java.util.List" %>

<%
 	request.setCharacterEncoding("UTF-8"); 
	ActivityService actSvc = new ActivityService();
	List<ActivityVO> list = actSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<title>Feliz-後台</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/back_end/activity/css/act/selectAct.css" rel="stylesheet">

</head>
<body>
	<%-- 	<%@ include file="/back_end/loading.file"%> --%>
	<!-- loading -->
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->
	
	
	<jsp:useBean id="actService" class="com.activity.model.ActivityService" />
	<jsp:useBean id="actClassService" class="com.activityClass.model.ActivityClassService" />
	
<div class="main-content">
	<div class="table-responsive">
		<div class="updateAndSwitch">
			<button type="button" class="btn btn-primary btn-xs updateBtn" data-bs-toggle="modal" data-bs-target="#staticBackdropUpdateAct">選擇修改活動</button>
				<!-- 修改的modal -->
			<jsp:include page="/back_end/activity/modal/act/updateActModal.jsp"/>
						
			<button type="button" class="btn btn-info btn-xs switchBtn" data-bs-toggle="modal" data-bs-target="#staticBackdropSwitchActState">切換上下架</button>
				<!-- 切換上下架的modal -->
			<jsp:include page="/back_end/activity/modal/act/switchActStateModal.jsp"/>			
		</div>
		
		<div class="importExcel">
			<form id="importExcelForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/activity/UploadExcel">
			  <div class="form-group">
			    <label for="importExcelFile" style="color:#2828FF;font-size:18px;">匯入Excel：</label>
			    <input type="file" class="form-control-file" name="importExcelFile" id="importExcelFile" style="color:#F75000;">
			    <input type="hidden" name="action" value="importExcel">
			    <button type="button" class="btn btn-primary btn-xs" onclick="importExcel();">送出</button>
			  </div>
			</form>
		</div>
		
		<div class="query">
			 <form method="post" action="<%=request.getContextPath()%>/activity/Activity">
				<select name="queryActClass" class="querySelect">
					<c:forEach var="actClassVO" items="${actClassService.all}">
						<option value="${actClassVO.act_class_no}">${actClassVO.act_class_name}</option>
					</c:forEach>			
				</select>
					<input type="hidden" name="action" value="queryByActClassBackEnd">
					<button class="btn light btn-secondary btn-xs">查詢</button>
			 </form>
		</div>
			<table class="table">
				<tr>
					<th>編號</th>
					<th>活動類別名稱</th>
					<th>活動名稱</th>
					<th>價格</th>
					<th>地點</th>
					<th>行程時間</th>
					<th>集合地點</th>
					<th>經度</th>
					<th>緯度</th>
					<th>累積銷售人數</th>
					<th>可參與人數</th>
					<th>評價總人數</th>
					<th>平均星數</th>
					<th>狀態</th>
					<th>活動說明</th>
				</tr>
			<%@ include file="/back_end/activity/pages/act/page1.file" %> 
				<c:forEach var="actVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<tr ${(actVO.act_no == param.updateActNo) ? 'style="background-color:#FFE6FF;"':''}>
					<th>${actVO.act_no}</th>
					<td>
						<c:forEach var="actClassVO" items="${actClassService.all}">
							<c:if test="${actVO.act_class_no == actClassVO.act_class_no}">
								${actClassVO.act_class_name}
							</c:if>
						</c:forEach>
					</td>
					<td>${actVO.act_name}</td>
					<td>${actVO.act_price}</td>
					<td>${actVO.act_location}</td>
					<td>${actVO.act_schedule_time}</td>
					<td>${actVO.act_gather_location}</td>
					<td>${actVO.act_location_longitude}</td>
					<td>${actVO.act_location_latitude}</td>
					<td>${actVO.act_sell_number}</td>
					<td>${actVO.act_join_number}</td>
					<td>${actVO.act_evaluation_number}</td>
					<td>${actVO.act_average_star_number}</td>
					<td class="${actVO.act_state == true ? 'upstate' : 'downstate' }">${actVO.act_state == true ? "上架" : "下架"}</td>
					<td>
						<button type="button" class="btn btn-danger btn-xxs" data-bs-toggle="modal" data-bs-target="#staticBackdropActInstruction${actVO.act_no}">查看</button>
							<!--查看說明的modal 因為很多文字 -->
						<jsp:include page="/back_end/activity/modal/act/actInstructionModal.jsp">
							<jsp:param name="actNo" value="${actVO.act_no}"/>
							<jsp:param name="actInstruction" value="${actVO.act_instruction}"/>
						</jsp:include>
					</td>
				</tr>
				</c:forEach>
			</table>
		<%@ include file="/back_end/activity/pages/act/page2.file" %> 
	</div>
</div>
	
	<%@ include file="/back_end/commonJS.file"%>
	
	<script>
		$(document).ready(function(){
			$(document).on('shown.bs.modal',function(){
				 $(this).find('[autofocus]').focus();
			});
		});
		
		function importExcel(){
			let file = document.getElementById('importExcelFile').value;
			let type = file.substring(file.lastIndexOf('.')).toLowerCase();
			let myForm = document.getElementById('importExcelForm');

			if(file ===''){
				alert("請選擇檔案");
				return false;
			}		
			if(type === '.xlsx'){
				myForm.submit();
			}else{
				alert("只接受.xlsx檔案");
				return false;
			}
		}
			
		function createWhichPage(){
			let select = document.getElementById('switchActStateSelect');
			let value = select.options[select.selectedIndex].value; //option value
			let myForm = document.getElementById('switchActStateForm');
			let input = document.createElement('input');
			let goBackPage = 0;
			if(value % 5 == 0){
				goBackPage = value/5;
			}else{
				goBackPage = (value/5)+1;
			}
			input.setAttribute("type","hidden");
			input.setAttribute("name","whichPage");
			input.setAttribute("value",parseInt(goBackPage));
			myForm.appendChild(input);
			
			myForm.submit();
		}
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
	</script>
</body>
</html>