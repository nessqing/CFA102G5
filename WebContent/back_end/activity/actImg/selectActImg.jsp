<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import ="com.activityImage.model.*" %>
<%@ page import ="java.util.List" %>

<%
	ActivityImageService actImgSvc = new ActivityImageService();
	List<ActivityImageVO> list = actImgSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<title>Feliz-後台</title>
<%@ include file="/back_end/commonCSS.file"%><!-- 基本CSS檔案 -->
<link href="<%=request.getContextPath()%>/back_end/activity/css/actImg/selectActImg.css" rel="stylesheet">
</head>
<body>
	<%-- 	<%@ include file="/back_end/loading.file"%> --%>
	<!-- loading -->
	<%@ include file="/back_end/header.file"%>
	<!-- Header -->
	<%@ include file="/back_end/sidebar.file"%>
	<!-- sidebar -->
	
	<jsp:useBean id="actImgService" class="com.activityImage.model.ActivityImageService" />
	<jsp:useBean id="actService" class="com.activity.model.ActivityService" />
	
<div class="main-content">
	<div class="table-responsive">
		<div class="updateAndSwitch">
			<!-- 新增圖片 -->
			<form method="post" action="<%=request.getContextPath()%>/back_end/activity/actImg/addActImg.jsp">
				<input type="hidden" name="action" value="addActImg">
				<button type="submit" class="btn btn-primary">新增圖片</button>	
			</form>
		</div>
			<table class="table">
				<tr>
					<th>圖片編號</th>
					<th>活動名稱</th>
					<th>活動圖片</th>
					<th>修改圖片</th>
					<th>刪除圖片</th>				
				</tr>
			<%@ include file="/back_end/activity/pages/actImg/page1.file" %> 
				<c:forEach var="actImgVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<tr ${(actImgVO.act_img_no == param.updateActImgNo) ? 'style="background-color:#FFE6FF;"':''}>
					<th>${actImgVO.act_img_no}</th>
					<td>
						<c:forEach var="actVO" items="${actService.all}">
							<c:if test="${actImgVO.act_no == actVO.act_no}">
								${actVO.act_name}
							</c:if>
						</c:forEach>
					</td>
					<td>
						<img src="<%=request.getContextPath()%>/activity/ActivityImage?act_img_no=${actImgVO.act_img_no}">
					</td>
					<td>
						<form method="post" action="<%=request.getContextPath()%>/activity/ActivityImage">
							<input type="hidden" name="action" value="updateActImg">
							<input type="hidden" name="updateActImgNo" value="${actImgVO.act_img_no}">
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    			<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			     			<button type="submit" class="btn btn-primary">修改</button>
						</form>
					</td>
					<td>		<!--刪除圖片 -->
						<button  type="button" id="deleteActImg" class="btn btn-danger" onclick="deleteAjax(${actImgVO.act_img_no});">刪除</button>						
					</td>
				</tr>
				</c:forEach>
			</table>
		<%@ include file="/back_end/activity/pages/actImg/page2.file" %> 
	</div>
</div>
	
	<%@ include file="/back_end/commonJS.file"%>
	
	<script>
		function deleteAjax(ActImgNo){
			let xhr = new XMLHttpRequest();
			let action = "deleteActImg";
			let url= "<%=request.getContextPath()%>/activity/ActivityImage";
			
			xhr.onload = function (){
			   if(xhr.status == 200){
			      alert(xhr.responseText+"號圖片已刪除");
			      window.location.reload();
			   }//xhr.status == 200
			}
			
			xhr.open("POST",url,true); 
			xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			  //送出請求 
			xhr.send("action=deleteActImg"+"&act_img_no="+ActImgNo);
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
			myForm.appendChild(input)
			
			myForm.submit();
		}
		// ● 可在這更改header的標題，不寫也可以，但請變成空字串 
		$("#pagename").text("");
	</script>
</body>
</html>