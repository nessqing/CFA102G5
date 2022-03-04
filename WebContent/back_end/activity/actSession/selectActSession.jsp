<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import ="com.activitySession.model.*" %>
<%@ page import ="java.util.List" %>

<%
	ActivitySessionService actSessionSvc = new ActivitySessionService();
	List<ActivitySessionVO> list = actSessionSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<title>Feliz-後台</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/back_end/activity/css/actImg/selectActImg.css" rel="stylesheet">
<style>
	div.queryByPage{
		position: relative;
    	right: 50px;
    	bottom: 600px
    }
    table tr th{
    	font-size:18px;
    }
    table tr td.upstate{
		color:#28FF28;
	}	
	table tr td.downstate{
		color:#FF2D2D;
	}
</style>
</head>
<body>
	<%-- 	<%@ include file="/back_end/loading.file"%> --%>
	<!-- loading -->
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->
	
	<jsp:useBean id="actService" class="com.activity.model.ActivityService" />
	
<div class="main-content">
	<div class="table-responsive">
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
					<th>修改</th>				
				</tr>
				
			<%@ include file="/back_end/activity/pages/actSession/page1.file" %> 
				<c:forEach var="actSessionVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<tr ${(actSessionVO.act_session_no == param.updateActSessionNo) ? 'style="background-color:#FFE6FF;"':''}>
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
					<td>
						<form method="post" action="<%=request.getContextPath()%>/activity/ActivitySession">
							<input type="hidden" name="action" value="updateActSession">
							<input type="hidden" name="updateActSessionNo" value="${actSessionVO.act_session_no}">
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    			<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			     			<button type="submit" class="btn btn-primary">修改</button>
						</form>
					</td>
				</tr>
				</c:forEach>
			</table>
		<%@ include file="/back_end/activity/pages/actSession/page2.file" %> 
	</div>
</div>
	
	<%@ include file="/back_end/commonJS.file"%>
	
	<script>
		
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
	</script>
</body>
</html>