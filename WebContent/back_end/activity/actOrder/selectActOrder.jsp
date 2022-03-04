<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import ="com.activityOrder.model.*" %>
<%@ page import ="java.util.List" %>

<%
	ActivityOrderService actOrderSvc = new ActivityOrderService();
	List<ActivityOrderVO> list = actOrderSvc.getAll();
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
		position: absolute;
   		right: 50px;
    }
    table.table{
    	height:50px;
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
	
	<jsp:useBean id="memberService" class="com.member.model.MemberService" />
	
<div class="main-content">
	<div class="table-responsive">
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
				
			<%@ include file="/back_end/activity/pages/actOrder/page1.file" %> 
				<c:forEach var="actOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
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
				</c:forEach>
			</table>
		<%@ include file="/back_end/activity/pages/actOrder/page2.file" %> 
	</div>
</div>
	
	<%@ include file="/back_end/commonJS.file"%>
	
	<script>
		
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
	</script>
</body>
</html>