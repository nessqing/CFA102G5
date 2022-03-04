<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>


<%
    Object account = session.getAttribute("empVO");                  // 從 session內取出 (key) account的值
    if (account == null) {                                             // 如為 null, 代表此user未登入過 , 才做以下工作
      session.setAttribute("location", request.getRequestURI());       //*工作1 : 同時記下目前位置 , 以便於login.html登入成功後 , 能夠直接導至此網頁(須配合LoginHandler.java)
      response.sendRedirect(request.getContextPath()+"/backLogin.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
      return;
    }
%>
<html>
<head>
<title>員工首頁</title>

<style>
.emp{
background: #CDBCA9;
width: 200px;
font-size:20px;
}
.logo_name{
font-size:20px;


}
</style>

</head>
<body bgcolor='white'>

	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
            <div class="logo">
                <div class="logo_name">FeLiz BACKSTAGE</div>
            </div>

<ul class="emp">
  <li>${empVO.emp_name} : 您好</li>
  <li><a href='listAllEmp.jsp'>查詢全部員工</a></li>
  <li><a href='addEmp.jsp'>新增員工</a></li>
  
    <jsp:useBean id="depSvc" scope="page" class="com.department.model.DepService" />
   <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmployeeServlet.do" >
       <b>您要搜尋哪個部門員工 </b>
       <select size="1" name="dep_no">
         <c:forEach var="DepartmentVO" items="${depSvc.all}" > 
          <option value="${DepartmentVO.dep_no}">${DepartmentVO.dep_name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getDepInEmp">
       <input type="submit" value="送出">
    </FORM>
  </li>
</ul>


</body>
</html>