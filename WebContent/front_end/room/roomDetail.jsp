<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.roomImg.model.*"%>

<jsp:useBean id="memberSvc" class="com.member.model.MemberService" />
<jsp:useBean id="roomTypeSvc" scope="page" class="com.roomType.model.RoomTypeService" />

<!doctype html>
<html>
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
        <style>
            .services-item {
                padding: 0;
                background: transparent;
            }
            .room-text-area {
            	background-color: #fff;
            }
            .room-icon {
                display: flex;
                flex-wrap: no-wrap;
                justify-content: space-between;
                margin: 2% 2% 0 2%;
            }
            .room-icon>div {
                padding-top: 3%;
                width: 25%;
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
            }
            @media screen and (max-width:768px) {
            	.room-icon {
            		flex-wrap: wrap;
            	}
                .room-icon>div {
                width: 50%;
                }
            }
            .room-icon>div i {
                width: 100%;
                font-size: 30px;
                border-radius: 50%;
                border: 0.5px solid #cda274;
                color: #cda274;
                padding: 10px;
                text-align: center;
            }
            .room-icon>div>div {
                margin: 0 auto;
            }
            .room-icon>div h5 {
                padding: 10px;
                width: 100%;
                color: #996A4D;
                text-align: center;
            }
            img {
            	width: 100%;
            }
            .room-facility-content, .room-info-content {
            	margin: 2% 2% 0 2%;
             	border-top: 0.5px solid #cda274;
            	padding: 2%;
            }
          	.room-facility-content h5, .room-facility-content p, .type-title-area h2, .type-title-area>div {
				display: inline-block;
			}
			.room-facility-content h5 {
				margin-right: 4%;
			}
			.room-facility-content>p:last-child {
				padding: 2% 3%;
				color: rgba(236, 100, 75, 1);
			}
			.side-bar-form .type-title-area h2 {
				font-size: 26px;
				color: #a3785e;
			}
			span.price {
            	font-size: 22px;
			    letter-spacing: 0.5px;
			    color: #30504F;
			    font-weight: 700;
			    padding-left: 15px;
            }
			.room-facility-content p {
				font-size: 18px;
				color: #00694C;
			}
            .room-facility-content, .room-info-content p {
            	padding: 2%;
            	font-size: 20px;
            	letter-spacing: 0.5px;
            }
            .line-btn:hover .line {
			    transition: width .5s;
			    width: 35px;
			}
			.line-btn {
				padding-left: 22px;
				margin-left: 30%;
			}
			.line-btn i {
				font-size: 14px;
			}
			.line {
			    width: 20px;
			    height: 1px;
			    background: #fff;
			    display: inline-block;
			    position: relative;
 			    top: -6px;
    			right: -7px; 
			    transition: width .5s;
			}
			.room-area {
				padding-top: 1%;
			}
			.room-facility-content ul li i {
	            padding: 0 8px;
	           	color: #B38C61;
			}
			.room-facility-content ul li.exclusive {
	           	color: #00694C;
			}
			.room-facility-content ul li.exclusive i {
				color: #00694C;
			}
			.room-facility-content ul {
	            list-style-type: none;
	            padding: 0;
            }
            .room-facility-content ul li {
            	font-size: 20px;
	            display: inline;
	            padding: 10px;
          		float: left;
				display: block;
				color: #996A4D;
            }
            .room-facility-content div:last-child {
            	clear:left;
            }
			.room-details-side {
				position: sticky;
				top: 50PX;
			}
/*             上方步驟區域 */
            .inner-title {
				padding: 1%;
				background-image: linear-gradient(to left, transparent, #ededed 80%);
			}
			.inner-title>div.row {
				margin-left: 3%;
				margin-bottom: 1%;
			}            
			ul.check {
	            list-style-type: none;
            }
            ul.check li {
            	font-size: 16px;
	            display: inline;
	            padding: 0 20px;
            }
            .check i.bx-check-circle {
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
			.not_select {
				cursor: default;
			}
            body {
/*  			  	background-image: linear-gradient(rgba(228, 214, 196, 0.1), rgba(228, 214, 196, 0.3)),  */
/*                    url(../assets/img/color_bg.png);  */
                background-image: linear-gradient(170deg, transparent, rgba(228, 214, 196, 0.5));
/*             	background-color: #f7f9fa; */

			}
			.side-bar-form .form-group label, .side-bar-form .form-group i {
 	    		color: #30504F;
			}
			.side-bar-form .form-group i.bx-calendar {
			    position: relative;
			    top: 0;
	    		left: 0;
			}
        </style>
	</head>
    <body>
		<%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->
        
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
	                        <div class="flex_center inner_circle_step shapeborder_selected_in">
	                            1</div>
	                    </div>
	                    <a href="<%=request.getContextPath()%>/front_end/room/roomList.jsp" class="step_item_label">搜尋</a>
	                </div>
                
	                <div class="step-item flex_center">
	                    <div class="outer_circle_step flex_center shapeborder_selected_out">
	                        <div class="flex_center inner_circle_step shapeborder_selected_in">
	                            2</div>
	                    </div>
	                    <a class="step_item_label">選擇日期</a>
	                </div>
                
	                <div class="step-item flex_center not_select">
	                    <div class="outer_circle_step flex_center">
	                        <div class="flex_center inner_circle_step">
	                            3</div>
	                    </div>
	                    <a class="step_item_label step_item_label_not_select">填寫資料/付款</a>
	                </div>
                
	                <div class="step-item flex_center not_select">
	                    <div class="outer_circle_step flex_center">
	                        <div class="flex_center inner_circle_step">
	                            4</div>
	                    </div>
	                    <a class="step_item_label step_item_label_not_select">確認</a>
	                </div>
				</div>
			</div>
		<div class="row room-area">
			<div class="col-lg-8">
				<div class="room-details-article">
					<div class="room-details-slider owl-carousel owl-theme">
<!-- 					大圖 -->
						<c:forEach var="img" items="${images}">
						<div class="room-details-item" data-hash="${img.img_no}">
							<img src="<%=request.getContextPath()%>/room/RoomImg?img_no=${img.img_no}&action=showImages">
						</div>
						</c:forEach>
					</div>
					<div id="services-slider" class="owl-carousel owl-theme">
<!-- 					小圖 -->
						<c:forEach var="img" items="${images}">
						<div class="services-item">
							<a href="#${img.img_no}"><img src="<%=request.getContextPath()%>/room/RoomImg?img_no=${img.img_no}&action=showImages""></a>
						</div>
						</c:forEach>
					</div>

					<div class="type-title-area">
						<h2>${roomTypeVO.type_name}</h2>
						<div>
							<span class="price"><fmt:formatNumber value="${roomTypeVO.type_price}" pattern="NT$ ###,###" /></span><span> / 一晚</span>
						</div>
					</div>
					<div class="room-text-area">
						<div class="room-icon">
							<div>
								<div>
									<i class='bx bx-user'></i>
								</div>
								<h5>最多 ${roomTypeVO.type_qty} 人入住</h5>
							</div>
							<div>
								<div>
									<i class='bx bx-fullscreen'></i>
								</div>
								<h5>${roomTypeVO.type_size} m<sup>2</sup></h5>
							</div>
							<div>
								<div>
									<i class='bx bx-bed'></i>
								</div>
								<h5>${roomTypeVO.bed_size}</h5>
							</div>
							<div>
								<div>
									<i class='bx bx-wifi'></i>
								</div>
								<h5>超快的wifi</h5>
							</div>
						</div>
						
						<div class="room-info-content">
							<h5>房型介紹</h5>
							<p>${roomTypeVO.type_info}</p>
						</div>
						
						<div class="room-facility-content">
							<h5>房型設施</h5>
							<p>*<i class='bx bx-check-circle'></i>為該房型獨有</p>
							<ul>
								<li><i class='bx bx-hotel' ></i>舒眠級睡床及寢具</li>
								<li><i class='bx bxs-drink' ></i>免費飲料</li>
								<li><i class='bx bx-coffee'></i>煮水壺</li>
								<li><i class='bx bxs-cube'></i>保險箱</li>
								<li><i class='bx bx-male'></i>淋浴間</li>
								<li><i class='bx bx-bath' ></i>獨立浴缸</li>
								<li><i class='bx bx-wind'></i>吹風機</li>
								<c:forEach var="facility" items="${facilityList}">
									<li class="exclusive"><i class='bx bx-check-circle'></i>${facility}</li>
								</c:forEach>
							</ul>
							<p>※每間房間裝潢與設備因樓層或位置不同而稍有差異，依實際入住客房為準。</p>
						</div>
					</div>
				</div>
				
			</div>

			<div class="col-lg-4">
				<div class="room-details-side">
					<div class="side-bar-form">
						<div class="type-title-area">
							<h2>${roomTypeVO.type_name} x ${qty}間</h2>
							<div>
								<span class="price"><fmt:formatNumber value="${roomTypeVO.type_price}" pattern="$###,###" /></span><span> / 一晚</span>
							</div>
						<form method="post" action="<%=request.getContextPath()%>/room/RoomRsv" id="immediateCheckoutForm">
							<div class="row align-items-center">
								<div class="col-lg-12">
	                                <div class="form-group">
	                                    <label><i class='bx bx-calendar'></i> 入住期間</label>
	                                    <div class="input-group">
	                                    	<input type="text" id="rangeDate" name="rangedate" placeholder="請選擇入住期間" class="form-control" data-input>
                                        	<span class="input-group-addon"></span>
	                                    </div>
	                                    <i class='bx bxs-chevron-down'></i>	
	                                </div>
								</div>
								<div class="col-lg-12 col-md-12">
									<input type="hidden" name="type_no" value="${roomTypeVO.type_no}">
									<input type="hidden" name="qty" value="${qty}">
									<input type="hidden" name="action" value="payment">
									<button type="button" class="btn btn-primary line-btn" onclick="check();"><div class="line"></div><i class='bx bx-chevron-right'></i>預訂</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
        <script>
        	// header房型介紹加border-bottom
	        $(`.nav-item:nth-child(1)>a`).attr('class', 'active');
	        // 大圖跟小圖的carousel
	        $('#services-slider').owlCarousel({
                items: 5,
                loop: false,
                margin: 10,
                nav: true,
                dots: false,
                autoplayHoverPause: true,
            })
            $('.owl-carousel').owlCarousel({
                URLhashListener:true,
            });
	        // 房型設施 電視icon
	        $(".room-facility-content li:nth-child(8)>i").removeClass().addClass("bx bx-tv");
	        // calendar
	        $("#rangeDate").flatpickr({
	            mode: 'range',
	            dateFormat: "Y-m-d",
	            defaultDate: ["${start_date}", "${end_date}"],
	            minDate: "today",
	            maxDate: new Date().fp_incr(90),
	            disable: [${result}],
	        });
	        
	      	// 預訂時有無登入會員，有登入就驗證(qty是null就是session消失了，住宿期間錯誤可能是 重選選錯||session消失)
	    	function check(){
	    		let duringStay = document.getElementById('rangeDate');
	      		
	    		if('${mem_mail}' === ''){
	    			notLogin();
	    			return false;
	    		} else if ("${qty}" === ''){
	    			qtyIsNull();
	    			return false;
	    		} else if (duringStay.value.length != 24){
	    			duringStay.focus();
	    			rangeDateIsNull();
	    			return false;
	    		} else {
	    			document.getElementById('immediateCheckoutForm').submit();
	    		}
	    	}
	      	// alert樣式
	        function notLogin() {
	    		swal.fire({
	    			icon : 'error',
	    			title : '請先登入',
	    			showConfirmButton : false,
	    			timer : 1000
	    		}).then(function () {
	     	        window.location.href = "<%=request.getContextPath()%>/front_end/signin/signin.jsp";
	     	    })
	    	}
	        function qtyIsNull() {
	    		swal.fire({
	    			icon : 'error',
	    			title : '請選擇房間數量',
	    			showConfirmButton : false,
	    			timer : 1000
	    		}).then(function () {
	     	        window.location.href = "<%=request.getContextPath()%>/front_end/index/index.jsp";
	     	    })		
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