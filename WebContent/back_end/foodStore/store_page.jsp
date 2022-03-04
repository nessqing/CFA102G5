<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodStore.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul class="Store">
  <li><a href='allStore.jsp'>查詢全部店家</a></li>
  <li><a href='addStore.jsp'>新增店家</a></li>
  
  
  
    <jsp:useBean id="storeSvc" scope="page" class="com.foodClass.model.FoodClassService" />
   <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FoodStoreServlet.do" >
       <b>您要搜尋哪個類別 </b>
       <select size="1" name="fd_class_no">
         <c:forEach var="stroeVO" items="${storeSvc.all}" > 
          <option value="${stroeVO.fd_class_no}">${stroeVO.fd_class_name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getStoreFK">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  
</ul>

</body>
</html>