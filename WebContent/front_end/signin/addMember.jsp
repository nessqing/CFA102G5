<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
    

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


<h1>加入會員:</h1>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="mem_name"placeholder="!!!!!!" size="45" value="老王"/></td>
	</tr>
	<tr>
		<td>性別:</td>
		<td><input type="TEXT" name="mem_sex" size="45" value="1" /></td>
	</tr>
	<tr>
		<td>信箱:</td>
		<td><input type="TEXT" name="mem_mail" placeholder="!!!!!!" size="45"value="kk123@yahoo.com"></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="password" name="mem_password" placeholder="英文字母、數字, 且長度必需在2到10之間" size="45" value="123123" /></td>
	</tr>
	<tr>
		<td>mobile:</td>
		<td><input type="TEXT" name="mem_mobile" placeholder="電話" size="45" value="0912312312" /></td>
	</tr>
	<tr>
        <td>請上傳照片:</td>
        <td><input type="file" name="mem_img"value="" ></td>
	</tr>
	<tr>
		<td>地址:</td>
		<td><input type="TEXT" name="mem_add" placeholder="地址"  size="45" value="花蓮縣花蓮市中正路1號" /></td>
	</tr>
</table>

<br>
   
<input type="hidden" name="action" value="addMember">
<input type="submit" value="送出新增"></FORM>

    <script type="text/javascript">
    function readURL(input){
    	  if(input.files && input.files[0]){
    	    var reader = new FileReader();
    	    reader.onload = function (e) {
    	       $("#preview_progressbarTW_img").attr('src', e.target.result);
    	    }
    	    reader.readAsDataURL(input.files[0]);
    	  }
    	}
    </script>
   

</body>
</html>