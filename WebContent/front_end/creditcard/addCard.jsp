<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.creditcard.model.*"%>
 
<%
  CreditcardClassVO crdVO = (CreditcardClassVO)request.getAttribute("crdVO");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>

  table#table-1 {
	background-color: black;
    border: 2px solid black;

  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
    <style type="text/css">
        .container {
            padding: 10px;
            border: 1px black solid;
            width: 600px;
        }
        .row {
            padding: 4px
        }
        .row label {
            font-weight: 500;
        }
        .row textarea {
            width: 97%;
            height: 500px;
            color: blue;
        }
        .row span {
            color: blue;
        }
        .hidden {
            display: none;
        }
        img {
            height: 100px;
            width: auto;
        }
    </style>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/creditcard/creditcard.do">
<table>

	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="crd_name"placeholder="!!!!!!" size="45" value="<%= (crdVO==null)? "urjeoedk"  : crdVO.getCrd_name()%>"/></td>
	</tr>
	<tr>
		<td>卡號:</td>
		<td><input type="TEXT" name="crd_num"placeholder="!!!!!!" size="45" value="<%= (crdVO==null)? "1234567890987654"  : crdVO.getCrd_num()%>"/></td>
	</tr>
	<tr>
		<td>到期日:</td>
		<td><input type="TEXT" name="crd_expiry"placeholder="!!!!!!" size="45" value="<%= (crdVO==null)? "123"  : crdVO.getCrd_expiry()%>"/></td>
	</tr>
	<tr>
		<td>安全碼:</td>
		<td><input type="TEXT" name="crd_security_code"placeholder="!!!!!!" size="45" value="<%= (crdVO==null)? "584"  : crdVO.getCrd_security_code()%>"/></td>
	</tr>
	<tr>
		<td>手機條碼:</td>
		<td><input type="TEXT" name="crd_barcode"placeholder="!!!!!!" size="45" value="<%= (crdVO==null)? "39485"  : crdVO.getCrd_barcode()%>"/></td>
	</tr>
	

</table>

<br>
<%String req = request.getParameter("mem_no");%>
<input type="hidden" name="action" value="addCard">
<input type="hidden" name="mem_no" value="<%=req%>">
<input type="submit" value="送出新增"></FORM>
  
    
<h1><%=req%></h1>

</body>
</html>