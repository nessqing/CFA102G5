<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomImg.model.*"%>
<%@ page import="com.activity.model.*"%>

<jsp:useBean id="roomTypeSvc" scope="page" class="com.roomType.model.RoomTypeService"/>
<jsp:useBean id="roomImgSvc" scope="page" class="com.roomImg.model.RoomImgService" />
<jsp:useBean id="actSvc" scope="page" class="com.activity.model.ActivityService" />
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />

<% 
	// 如果session中沒有間數跟人數
	if (session.getAttribute("qty") == null) {
		pageContext.setAttribute("qty", 1);
	}
	if (session.getAttribute("guest") == null) {
		pageContext.setAttribute("guest", 2);
	}

	// 日曆的日期，字串分割成start_date、end_date
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
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
        <style>
        .testimonials-content>span {
        	font-size: 20px;
        	letter-spacing: 1px;
        	margin-left: 25px;
        	color: #996A4D;
        }
        p.act_instruction, p.type_info {
			overflow: hidden;
			display: -webkit-box;
			-webkit-line-clamp: 2;
			-webkit-box-orient: vertical;
		}
		p.type_info {
			-webkit-line-clamp: 5;
		}
		.banner-form .form-group label>i {
		    position: relative;
		    top: 0;
    		left: 0;
		}
		.banner-form .btn-primary {
			padding: 12px;
		}
		.main-nav {
    		background-color: rgba(255,255,255,0.8);
    	}
    	.no-js .owl-carousel, .owl-carousel.owl-loaded {
    		z-index: 0;
    	}
    	.banner-form .form-group .form-control:focus {
    		z-index: 2;
    	}
        </style>
    </head>
    <body>
		<%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->

        <!-- Banner Area -->
        <div class="banner-area">
            <div class="container">
                <div class="banner-content">
                    <h1>Feliz 提供您愉快的住宿體驗</h1>                        
                    <h1>開始您的旅程</h1>
                </div>
            </div>
        </div>
        <!-- Banner Area End -->

        <!-- Banner Form Area -->
        <div class="banner-form-area">
            <div class="container">
                <div class="banner-form">
                    <form method="post" action="<%=request.getContextPath()%>/room/RoomRsv" id="getEnoughType">
                        <div class="row align-items-center d-flex justify-content-between">
                            <div class="col-lg-4 col-md-4">
                                <div class="form-group">
                                    <label><i class='bx bx-calendar'></i> 入住期間</label>
                                    <div class="input-group">
                                    	<input type="text" id="rangeDate" name="rangedate" placeholder="請選擇入住期間" class="form-control" data-input>
                                        <span class="input-group-addon"></span>
                                    </div>
                                    <i class='bx bxs-chevron-down'></i>	
                                </div>
                            </div>

                            <div class="col-lg-2 col-md-2">
                                <div class="form-group">
                                    <label><i class='bx bx-home-alt' ></i> 間數</label>
                                    <select id="qty" name="qty" class="form-control" required>
                                        <option value="1" ${qty == 1 ? 'selected' : '' }>01</option>
                                        <option value="2" ${qty == 2 ? 'selected' : '' }>02</option>
                                        <option value="3" ${qty == 3 ? 'selected' : '' }>03</option>
                                        <option value="4" ${qty == 4 ? 'selected' : '' }>04</option>
                                        <option value="5" ${qty == 5 ? 'selected' : '' }>05</option>
                                        <option value="6" ${qty == 6 ? 'selected' : '' }>06</option>
                                        <option value="7" ${qty == 7 ? 'selected' : '' }>07</option>
                                        <option value="8" ${qty == 8 ? 'selected' : '' }>08</option>
                                    </select>	
                                </div>
                            </div>

                            <div class="col-lg-2 col-md-2">
                                <div class="form-group">
                                    <label><i class='bx bx-user' ></i> 人數</label>
                                    <select id="guest" name="guest" class="form-control" required>
                                        <option value="1" ${guest == 1 ? 'selected' : '' }>01</option>
                                        <option value="2" ${guest == 2 ? 'selected' : '' }>02</option>
                                        <option value="3" ${guest == 3 ? 'selected' : '' }>03</option>
                                        <option value="4" ${guest == 4 ? 'selected' : '' }>04</option>
                                    </select>	
                                </div>
                            </div>

                            <div class="col-lg-3 col-md-3 d-flex justify-content-end pt-70">
                                <input type="hidden" name="action" value="getEnoughType">
                            	<button type="button" class="btn btn-primary col-lg-8" onclick="check();">查看可訂房型</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Banner Form Area End -->
        
        <!-- Room Testimonials Area Two  -->
        <div class="testimonials-area-one pt-100 pb-70 room-area-bg">
            <div class="container">
                <div class="section-title text-center">
                    <h2 class="area-title">房型介紹</h2>
                    <hr>
                    <h3 class="area-subtitle">恬靜舒適的居住空間房與細緻用心的服務，提供您卓越的住宿體驗。</h3>
                </div>
                <div class="testimonials-slider-two owl-carousel owl-theme pt-45">
                	<c:forEach var="roomTypeVO" items="${roomTypeSvc.getAllRoomFront()}">
                    <div class="testimonials-style">
                        <div class="row align-items-center">
                            <div class="col-lg-7">
                                <div class="testimonials-img">
                                <c:choose>
									<c:when test="${roomImgSvc.getAllByType(roomTypeVO.type_no).size() > 0}">
										<img src="<%=request.getContextPath()%>/room/RoomImg?type_no=${roomTypeVO.type_no}&action=showFirstImages">
									</c:when>
									<c:otherwise>
										<img src="<%=request.getContextPath()%>/front_end/assets/img/no-img.jpg" class="no-img">
									</c:otherwise>
								</c:choose>
                                </div>
                            </div>
        
                            <div class="col-lg-5">
                                <div class="testimonials-content">
                                    <h2>${roomTypeVO.type_name}</h2>
                                    <span>NT<fmt:formatNumber value="${roomTypeVO.type_price}" pattern="$ ###,###"/></span>
                                    <span><i class='bx bxs-user' ></i> ${roomTypeVO.type_qty}人</span>
                                    <p class="type_info">${roomTypeVO.type_info}</p>
                                    <div class="d-flex justify-content-center">
                                    	<a class="btn btn-primary" href="<%=request.getContextPath()%>/room/RoomType?type_no=${roomTypeVO.type_no}&action=getOneForShow">瞭解更多</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
					</c:forEach>
                </div>
            </div>
        </div>
        <!-- Testimonials Area Two End -->
        
        <!-- Activity Area -->
        <div class="index-activity-bg room-area pt-100 pb-70">
            <div class="container">
                <div class="section-title text-left">
                    <h2 class="area-title ">熱門活動</h2>
                    <hr>
                    <h3 class="area-subtitle">探索花蓮的各種特色活動。<br>我們提供超值優惠、精選旅遊攻略與人氣玩樂。<br>推薦您大家都在參加的熱門活動！</h3>
                </div>
                <div class="row pt-45 justify-content-end">
					<c:forEach var="actVO" items="${actSvc.getPopularAct()}">
                    <div class="index-activity col-lg-3 col-md-5">
                        <div class="room-card">
                        	<a href="<%=request.getContextPath()%>/activity/Activity?action=frontAct&actNo=${actVO.act_no}">
                            	<img src="<%=request.getContextPath()%>/activity/ActivityImage?action=actList&actNo=${actVO.act_no}">
                                <div class="content">
                                    <i class='bx bxs-medal no2'></i>
                                    <h5>${actVO.act_name}</h5>
                                    <div class="index-activity">
                                        <p class="act_instruction">${actVO.act_instruction}</p>
                                        <p class="index-activity-price">NT<fmt:formatNumber value="${actVO.act_price}" pattern="$ ###,###"/></p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
					</c:forEach>
                </div>
            </div>
        </div>
        <!-- Activity Area End -->
        
        <!-- Food Area -->
        <div class="reservation-area pt-100 pb-70">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-6">
                        <div class="reservation-content">
                            <div class="section-title text-center">
                                <h2 class="area-title">美食地圖</h2>
                                <hr>
                                <h3 class="area-subtitle text-left">飯店周邊的美食店家，在地人推薦的特色花蓮美食！<br>好山好水又靠海的花蓮，除了豐富的名勝跟景點讓人玩不膩，花蓮美食更是多到數不清。</h3>
                            </div>
                            <a href="<%=request.getContextPath()%>/front_end/storeMap/storeMap.jsp" class="default-btn btn-bg-one">更多周邊美食</a>
                        </div>
                    </div>
                    
                    <div class="col-lg-5">
                        <div class="reservation-img">
                        	<img src="<%=request.getContextPath()%>/front_end/assets/img/index_food1.jpg">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Food Area End -->

        <%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->  
        <script>
        $("#rangeDate").flatpickr({
            mode: 'range',
            dateFormat: "Y-m-d",
            defaultDate: ["${start_date}", "${end_date}"],
            minDate: "today",
            maxDate: new Date().fp_incr(90),
            disable: [],
        });
        function check(){
    		let duringStay = document.getElementById('rangeDate');
      		
			if (duringStay.value.length != 24){
				duringStay.focus();
    			rangeDateIsNull();
    			return false;
    		} else {
    			document.getElementById('getEnoughType').submit();
    		}
    	}
        function rangeDateIsNull() {
    		swal.fire({
    			icon : 'error',
    			title : '請選擇 入住日 和 退房日',
    			showConfirmButton : false,
    			timer : 1000
    		})		
    	}
        </script>
    </body>
</html>
