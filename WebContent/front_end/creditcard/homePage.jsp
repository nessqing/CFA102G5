<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.creditcard.model.*"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>信用卡</title>
</head>
<body>

  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/creditcard/creditcard.do" >
        <b>輸入:</b>
        <input type="text" name="mem_no">
        <input type="hidden" name="action" value="getallByMem_no">
        <input type="submit" value="送出">
    </FORM>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/creditcard/creditcard.do">
        <input type="hidden" name="action" value="addCard">
        <input type="submit" value="送出">
	</FORM>
</body>
</html>