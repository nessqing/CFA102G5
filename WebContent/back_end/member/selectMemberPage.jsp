<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<!DOCTYPE html>

<%
		MemberService memSvc = new MemberService();
		List<MemberClassVO> list = memSvc.getAll();
		pageContext.setAttribute("list",list);
//         List<MemberClassVO> list = (List<MemberClassVO>)session.getAttribute("list"); 
%>
<html lang="en">
<head>
	<%@ include file="/back_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
</head>
<body>
		<%@ include file="/back_end/loading.file" %> <!-- loading -->
		<%@ include file="/back_end/header.file" %> <!-- Header -->
		<%@ include file="/back_end/sidebar.file" %> <!-- sidebar -->

		<div class="main-content card card-body table-responsive">
			<table id="example4" class="display" style="min-width: 845px">
				<thead>
					<tr>
						<th>會員編號</th>
						<th>會員名稱</th>
						<th>會員性別</th>
						<th>會員信箱</th>
						<th>會員密碼</th>									
						<th>會員電話</th>									
						<th>會員地址</th>									
						<th>會員狀態</th>
						<th>狀態管理</th>
						<th>資料管理</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="memVO" items="${list}">
					<tr>
							<td>${memVO.mem_no}</td>
							<td>${memVO.mem_name}</td>
							<td>${memVO.mem_sex == 1 ? '男' : '女'}</td>
							<td>${memVO.mem_mail}</td>	
	   				    	<td>${memVO.mem_password}</td>
							<td>${memVO.mem_mobile}</td>
							<td>${memVO.mem_add}</td>
							<td><span class="badge light badge-success">${memVO.mem_state == false ? '停權' : '正常'}</span></td>
						<td>
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
						 <input type="submit" class="btn btn-outline-secondary" value="${memVO.mem_state == true ? '停權' : '復權'}">
						 <input type="hidden" name="mem_no"  value="${memVO.mem_no}">
						 <input type="hidden" name="mem_state"  value="${memVO.mem_state}">
						 <input type="hidden" name="action"	value="updateMemberstate"></FORM>
					    </td>
						<td>
						 <FORM METHOD="post"  ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
						 <input type="submit" class="btn btn-outline-secondary" value="修改">
						 <input type="hidden" name="mem_no"  value="${memVO.mem_no}">
						 <input type="hidden" name="action" value="getOneForUpdate"></FORM>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/back_end/commonJS.file" %> <!-- 基本JS檔案 -->
		<script>
// 			● 可在這更改這一頁header的標題，不寫也可以，但請變成空字串 
			$("#pagename").text("會員資料");
		</script>
	</body>
</html>