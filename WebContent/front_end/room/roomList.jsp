<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomImg.model.*"%>

<jsp:useBean id="roomTypeSvc" scope="page" class="com.roomType.model.RoomTypeService" />
<jsp:useBean id="roomImgSvc" scope="page" class="com.roomImg.model.RoomImgService" />
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />

<%
	// 沒有搜尋過進來就顯示全部allList，有沒有搜尋過用rangedate判斷
	if (session.getAttribute("rangedate") == null) {
		List<RoomTypeVO> allList = roomTypeSvc.getAllRoomFront();
		pageContext.setAttribute("allList", allList);
	}
		
	// rangedate字串分割成start_date、end_date，也可以放controller裡
	if (session.getAttribute("rangedate") != null) {
		String rangedate = (String) session.getAttribute("rangedate");
		List<String> dateList = new LinkedList<String>();
		dateList = Arrays.asList(rangedate.split(" to "));
		String start_date = dateList.get(0);
		String end_date = dateList.get(1);
		pageContext.setAttribute("start_date", start_date);
		pageContext.setAttribute("end_date", end_date);
	}
	
	
%>

<!doctype html>
<html>
<head>
<%@ include file="/front_end/commonCSS.file"%>
<!-- 基本CSS檔案 -->
<style>
.room-text {
	border-left: 3px solid #d0af6d;
}

.room-card-area {
	background-color: #fff;
	padding: 2% 5%;
}

.room-card-two {
	margin: 4% 0;
	box-shadow: 5px 5px #f0e9df;
	border: 1px solid #f0e8df;
	background: #fcfbf9;
}

.room-card-two .room-card-content i {
	margin-right: 5px;
	position: relative;
	top: 3px;
	padding-right: 5px;
}

.room-card-content .line {
	height: 2px;
	background: #B38C61;
	top: -6px;
	right: -17px;
}

.room-card-content .line-btn {
	padding: 0;
	font-weight: 700;
}

.room-img {
	margin: 0 20px;
}

.room-card-two .room-card-img {
	padding: 0;
	border-radius: 0 20px 0 20px;
	overflow: hidden;
}

.room-card-two .room-card-img a img {
	border-radius: 0px;
}

.room-price {
	height: 100%;
	padding: 20px;
}

.room-price span:nth-child(2) {
	font-size: 16px;
}

.room-card-content {
	font-size: 20px;
	color: #70778b;
}

.room-card-content>div {
	padding: 3px;
}

.room-card-content a {
	color: #B38C61;
}

.room-card-content div:last-child {
	margin-top: 10px;
}

.line-btn:hover .line {
	transition: width .5s;
	width: 35px;
}

.line-btn {
	padding-left: 22px;
}

.line-btn i {
	font-size: 14px;
}

.line {
	width: 20px;
	height: 1.5px;
	background: #fff;
	display: inline-block;
	position: relative;
	top: -5px;
	right: -7px;
	transition: width .5s;
}

.room-card-two .room-card-content i.arrow {
	font-size: 22px;
	margin: 0;
}

span.price {
	font-size: 24px;
	letter-spacing: 0.5px;
	color: #30504F;
	font-weight: 700;
	padding-right: 5px;
}

.room-price>div:first-child {
	padding: 10px 0;
}

ul.check {
	list-style-type: none;
}

ul.check li {
	font-size: 16px;
	display: inline;
	padding: 0 20px;
}

i.bx-check-circle {
	padding: 0 5px;
	color: #B38C61;
}

.outer_circle_step {
	background-color: #fff;
	width: 38px;
	height: 38px;
	border-radius: 50%;
	border: 1px solid #666;
	color: #adadad;
	margin-right: 6px;
}

.step-item {
	font-weight: 700;
	margin: 1% 2%;
}

.step_item_label {
	color: #555;
	padding-right: 5px;
	padding-left: 5px;
	letter-spacing: .7px;
	max-width: 130px;
}

.step-item:hover .step_item_label {
	color: #996A4D;
}

.step_item_label_not_select {
	color: #adadad;
}

.step-item:hover .step_item_label_not_select {
	color: #adadad;
}

.flex_center {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
}

.inner_circle_step {
	font-size: 16px;
	width: 32px;
	height: 32px;
	border-radius: 50%;
}

.shapeborder_selected_out {
	border: 1px solid #d0af6d;
	color: #fff;
}

.shapeborder_selected_in {
	background-color: #d0af6d;
}

.guest_duration {
	margin-left: 50px;
	padding: 2%;
	background-color: #fff;
	font-size: 20px;
	letter-spacing: 1.5px;
}

.guest_duration p {
	padding: 5px 15px 0 15px;
	font-size: 16px;
	color: #996A4D;
	font-weight: 700;
	cursor: pointer;
}

.guest_duration p:hover {
	color: #30504F;
}

.booking_information_date {
	font-weight: 700;
	padding-right: 15px;
	color: #30504F;
}

.booking_information_people>span {
	padding: 0 5px 0 15px;
	border-left: 1px solid #ccc;
}

body {
	background-color: #f7f9fa;
}

.inner-title {
	padding: 1% 1% 0 1%;
	background-image: linear-gradient(to left, #fcfcfc 0, #ededed 80%);
}

.inner-title>div.row {
	margin-left: 3%;
	margin-bottom: 1%;
}

.form-group label, .side-bar-form .form-group i {
	color: #30504F;
}

.form-group i.bx-calendar {
	position: relative;
	top: 0;
	left: 0;
}
.btn-secondary {
	padding: 12px 32px;
}
</style>
</head>
<body>
	<%@ include file="/front_end/loading.file"%>
	<!-- loading -->
	<%@ include file="/front_end/header.file"%>
	<!-- Header -->

	<div class="mt-5 mb-5 pt-20 container">
		<div class="inner-title">
			<div>
				<ul class="check">
					<li><i class='bx bx-check-circle'></i>入住當天前免費取消</li>
					<li><i class='bx bx-check-circle'></i>訂房皆含早餐</li>
					<li><i class='bx bx-check-circle'></i>細緻用心的服務</li>
				</ul>
			</div>
			<div class="row">
				<div class="step-item flex_center">
					<div class="outer_circle_step flex_center shapeborder_selected_out">
						<div class="flex_center inner_circle_step shapeborder_selected_in">1</div>
					</div>
					<a href="<%=request.getContextPath()%>/front_end/index/index.jsp" class="step_item_label">搜尋</a>
				</div>

				<div class="step-item flex_center">
					<div class="outer_circle_step flex_center shapeborder_selected_out">
						<div class="flex_center inner_circle_step shapeborder_selected_in">2</div>
					</div>
					<a class="step_item_label">選擇房型</a>
				</div>

				<div class="step-item flex_center not_select">
					<div class="outer_circle_step flex_center">
						<div class="flex_center inner_circle_step">3</div>
					</div>
					<a class="step_item_label step_item_label_not_select">填寫資料/付款</a>
				</div>

				<div class="step-item flex_center not_select">
					<div class="outer_circle_step flex_center">
						<div class="flex_center inner_circle_step">4</div>
					</div>
					<a class="step_item_label step_item_label_not_select">確認</a>
				</div>
			</div>
			<div class="guest_duration d-flex">
				<c:choose>
					<c:when test="${rangedate != null}">
						<div class="booking_information d-flex">
							<div class="booking_information_date">
								<span id="guest_checkinDate" class="mr-10">${start_date}</span> <i class='bx bx-right-arrow-alt'></i> <span id="guest_checkoutDate">${end_date}</span>
							</div>
							<div class="booking_information_people">
								<span id="guest_los" class="booking_information_people_adults">${qty}</span>間 <span id="guest_adults">${guest}</span>人
							</div>
						</div>
						<a href="<%=request.getContextPath()%>/front_end/index/index.jsp"><p>
								修改<i class='bx bxs-edit'></i>
							</p></a>
					</c:when>
					<c:otherwise>
						<div class="booking_information d-flex">
							<div class="booking_information_date">
								<span id="guest_checkinDate" class="mr-10">尚未選擇搜尋條件</span>
							</div>
						</div>
						<a href="<%=request.getContextPath()%>/front_end/index/index.jsp"><p>
								選擇<i class='bx bxs-edit'></i>
							</p></a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="col-lg-12 room-card-area">
			<c:choose>
				<c:when test="${rangedate == null}">
					<h5>所有房型 共 ${allList.size()} 種</h5>
				</c:when>
				<c:when test="${ableList.size() == 0}">
					<h5>(ಥ_ಥ) 沒有符合您需求的房型，建議您修改搜尋條件</h5>
				</c:when>
				<c:otherwise>
					<h5>在這期間能訂購的房型(每天的空房數量都夠)</h5>
				</c:otherwise>
			</c:choose>
	
<%-- 			<c:if test="${rangedate != null && notList.size() > 0}"> --%>
<!-- 				<div class="custom-control custom-switch"> -->
<%-- 					<input type="checkbox" class="custom-control-input" id="switch" ${viewAll == 1 ? 'checked' : '' } onclick="notListShow()"> <label class="custom-control-label" for="switch">顯示不符合條件的房型</label> --%>
<!-- 				</div> -->
<%-- 			</c:if> --%>

			<!-- allList單個 -->
			<c:forEach var="roomTypeVO" items="${allList}">
				<div class="room-card-two">
					<div class="row align-items-center">
						<div class="col-sm-12 col-lg-4 p-0 room-img">
							<div class="room-card-img">
								<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow">
									<c:choose>
										<c:when test="${roomImgSvc.getAllByType(roomTypeVO.type_no).size() > 0}">
											<img src="<%=request.getContextPath()%>/room/RoomImg?type_no=${roomTypeVO.type_no}&action=showFirstImages">
										</c:when>
										<c:otherwise>
											<img src="<%=request.getContextPath()%>/front_end/assets/img/no-img.jpg" class="no-img">
										</c:otherwise>
									</c:choose>
								</a>
							</div>
						</div>

						<div class="col-sm-6 col-lg-5 p-0 room-text">
							<div class="room-card-content">
								<h3>${roomTypeVO.type_name}</h3>
								<div>
									<i class='bx bx-user'></i>${roomTypeVO.type_qty} 人
								</div>
								<div>
									<i class='bx bx-expand'></i>${roomTypeVO.type_size} m<sup>2</sup>
								</div>
								<div>
									<i class='bx bxs-hotel'></i>${roomTypeVO.bed_size}</div>
								<div>
									<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow" class="line-btn"><div class="line"></div>
										<i class='bx bx-chevron-right arrow'></i>查看客房詳情</a>
								</div>
							</div>
						</div>

						<div class="col-sm-4 col-lg-2 p-0 room-price">
							<div>
								<span class="price"><fmt:formatNumber value="${roomTypeVO.type_price}" pattern="NT$ ###,###" /></span><span>/ 一晚</span>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>

			<!-- ableList單個 -->
			<c:forEach var="roomTypeVO" items="${ableList}">
				<div class="room-card-two">
					<div class="row align-items-center">
						<div class="col-sm-12 col-lg-4 p-0 room-img">
							<div class="room-card-img">
								<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow"> <c:choose>
										<c:when test="${roomImgSvc.getAllByType(roomTypeVO.type_no).size() > 0}">
											<img src="<%=request.getContextPath()%>/room/RoomImg?type_no=${roomTypeVO.type_no}&action=showFirstImages">
										</c:when>
										<c:otherwise>
											<img src="<%=request.getContextPath()%>/front_end/assets/img/no-img.jpg" class="no-img">
										</c:otherwise>
									</c:choose>
								</a>
							</div>
						</div>

						<div class="col-sm-6 col-lg-5 p-0 room-text">
							<div class="room-card-content">
								<h3>${roomTypeVO.type_name}</h3>
								<div>
									<i class='bx bx-user'></i>${roomTypeVO.type_qty} 人
								</div>
								<div>
									<i class='bx bx-expand'></i>${roomTypeVO.type_size} m<sup>2</sup>
								</div>
								<div>
									<i class='bx bxs-hotel'></i>${roomTypeVO.bed_size}</div>
								<div>
									<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow" class="line-btn"><div class="line"></div>
										<i class='bx bx-chevron-right arrow'></i>查看客房詳情</a>
								</div>
							</div>
						</div>

						<div class="col-sm-4 col-lg-2 p-0 room-price">
							<div>
								<span class="price"><fmt:formatNumber value="${roomTypeVO.type_price}" pattern="NT$ ###,###" /></span><span>/ 一晚</span>
							</div>
							<form method="post" action="<%=request.getContextPath()%>/room/RoomRsv" class="toPayment">
								<input type="hidden" name="rangedate" value="${rangedate}">
								<input type="hidden" name="type_no" value="${roomTypeVO.type_no}">
								<input type="hidden" name="qty" value="${qty}">
								<input type="hidden" name="action" value="payment">
								<button type="button" class="btn btn-primary line-btn" onclick="checkout();"><div class="line"></div><i class='bx bx-chevron-right'></i>預訂</button>
							</form>
						</div>
					</div>
				</div>
			</c:forEach>


			<!-- notList單個 -->
			<c:forEach var="roomTypeVO" items="${notList}">
				<div class="room-card-two" id="notListArea">
					<div class="row align-items-center">
						<div class="col-sm-12 col-lg-4 p-0 room-img">
							<div class="room-card-img">
								<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow"> <c:choose>
										<c:when test="${roomImgSvc.getAllByType(roomTypeVO.type_no).size() > 0}">
											<img src="<%=request.getContextPath()%>/room/RoomImg?type_no=${roomTypeVO.type_no}&action=showFirstImages">
										</c:when>
										<c:otherwise>
											<img src="<%=request.getContextPath()%>/front_end/assets/img/no-img.jpg" class="no-img">
										</c:otherwise>
									</c:choose>
								</a>
							</div>
						</div>

						<div class="col-sm-6 col-lg-5 p-0 room-text">
							<div class="room-card-content">
								<h3>${roomTypeVO.type_name}</h3>
								<div>
									<i class='bx bx-user'></i>${roomTypeVO.type_qty} 人
								</div>
								<div>
									<i class='bx bx-expand'></i>${roomTypeVO.type_size} m<sup>2</sup>
								</div>
								<div>
									<i class='bx bxs-hotel'></i>${roomTypeVO.bed_size}</div>
								<div>
									<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow" class="line-btn"><div class="line"></div>
										<i class='bx bx-chevron-right arrow'></i>查看客房詳情</a>
								</div>
							</div>
						</div>

						<div class="col-sm-4 col-lg-2 p-0 room-price">
							<div>
								<span class="price"><fmt:formatNumber value="${roomTypeVO.type_price}" pattern="NT$ ###,###" /></span><span>/ 一晚</span>
							</div>
							<button type="button" class="btn btn-secondary" disable><i class='bx bx-x'></i>
								不符合</button>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>


		<p id="text" style="display: none">Checkbox is CHECKED!</p>

	</div>
	<%@ include file="/front_end/message.file"%>
	<!-- Message -->
	<%@ include file="/front_end/footer.file"%>
	<!-- Footer -->
	<%@ include file="/front_end/commonJS.file"%>
	<!-- 基本JS檔案 -->
	<script>
	        $(`.nav-item:nth-child(1)>a`).attr('class', 'active');
	     	// calendar
	        $("#rangeDate").flatpickr({
	            mode: 'range',
	            dateFormat: "Y-m-d",
	            defaultDate: ["${start_date}", "${end_date}"],
	            minDate: "today",
	            maxDate: new Date().fp_incr(90),
	        });
	        function checkout(){
	    		if('${mem_mail}' === ''){
	    			notLogin();
	    			window.setTimeout(() => location.href="<%=request.getContextPath()%>/front_end/signin/signin.jsp",800);
	    			return false;
	    		} else {
	    			// event: click event, event.target: click point; closet: 向上找元素
	    			let form = event.target.closest('.toPayment');
	    			form.submit();
// 	    			document.getElementById('toPayment').submit();
	    		}
	    	}
	       	<% if(request.getAttribute("notEnough")!=null){%>
	        	notEnough();
    		<%}%>
	      	// alert樣式
	        function notEnough() {
	    		swal.fire({
	    			icon : 'error',
	    			title : '您訂的太慢了，房間被別人搶走了',
	    			showConfirmButton : false,
	    			timer : 2500
	    		})		
	    	}
	        function notLogin() {
	    		swal.fire({
	    			icon : 'error',
	    			title : '請先登入',
	    			showConfirmButton : false,
	    			timer : 1000
	    		})		
	    	}
		</script>
</body>
</html>