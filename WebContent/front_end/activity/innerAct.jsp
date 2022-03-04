<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Comparator" %>

<!DOCTYPE html>
<html>
<head>
<!-- 基本CSS檔案 -->
<%@ include file="/front_end/commonCSS.file"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/front_end/activity/css/style2.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front_end/activity/css/fotorama.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front_end/activity/datetimepicker/jquery.datetimepicker.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/front_end/activity/css/act/innerAct.css" />

<script src="http://maps.google.com/maps/api/js?key=AIzaSyAJNpDkwnyNLU6eh829XsUUYdFdTx2YjJ"></script>

</head>
<body>
	<%-- <%@ include file="/front_end/loading.file" %> <!-- loading --> --%>
	<%@ include file="/front_end/header.file"%>
	<!-- Header -->

	<jsp:useBean id="actImgService"
		class="com.activityImage.model.ActivityImageService" />


	<div class="mt-5 mb-5 ptb-70 container" style="padding-top:20px; padding-bottom: 150px;">
		<div id="wrapper">
			<div class="tour-details-main">
				<div class="container">
					<div class="row">
						<div class="col-lg-9 col-md-8 col-sm-12 col-xs-12">
							<!-- Tour Slider Start  輪播  -->
							<div class="tour-gallery actCarousel">
								<div class="fotorama" data-width="100%" data-fit="scaledown" data-nav="thumbs" data-thumbwidth="128" data-thumbheight="128"
									data-allowfullscreen="true" data-loop="true" data-autoplay="7000" data-keyboard="true">
									<c:forEach var="actImgVO" items="${actImgService.getActImageByActNo(actVO.getAct_no())}">
										<img src="<%=request.getContextPath()%>/activity/ActivityImage?action=innerAct&actImgNo=${actImgVO.act_img_no}" id="googleMapActImg">
									</c:forEach>
								</div>
							</div>
							<!-- Tour Slider End -->

							<!--輪播下的內容 -->
							<div class="read-more collapsed" id="actSessionDiv">
								<h1 class="read-more-title">
									臺灣花蓮 | <span style="color: blue;" id="actName">${actVO.act_name}</span>
								</h1>
								<div>
									<i class='bx bx-map' style="color: #F00078; font-size: 18px;">${actVO.act_location}</i>
								</div>

								<div style="margin: 20px 0;">
									<i class='bx bx-globe'>中文 導覽</i><i class='bx bx-bell'>2天前可免費取消</i>
								</div>

								<div class="read-more__content">
									<p><h5>${actVO.act_instruction}</h5></p>
								</div>
							</div>
							<!--輪播下內容結束  -->
							<!--右側開始 -->
							<div class="col-lg-3 col-md-4 col-sm-12 col-xs-12">
								<div class="tour-booking" id="tourbooking">
									<!-- Tour Booking Start -->
									<div class="actPrice">
										<div>
											<h3>TWD ${actVO.act_price}</h3>
										</div>
										<div>
											<a href="#actSessionDiv" class="chooseSession">選擇場次</a>
										</div>
									</div>
						<!--GoogleMap -->
									<div id="map-canvas"></div>
								</div>
							</div>

							<!--右側結束 -->
							<div class="col-lg-3 col-md-4 col-sm-12 col-xs-12">
								<div class="actSession">
									<form id="immediateCheckoutForm" action="<%=request.getContextPath()%>/activity/ActivityOrder" method="post">
										<div>
											<label id="actSessionLabel"><h3>選擇日期</h3></label>
											<p>
												<b style="margin-left: 5px;">請選擇出發日期</b>
											</p>
											<input type="text" name="act_date" id="act_date">
										</div>

										<div class="actSessionStartTime">
											<b style="position: relative; left: -170px; top: -5px;">場次時間:</b>
											<select name="actSessionStartTimeSelect" id="actSessionStartTimeSelect">
												<c:forEach var="actSessionVO" items="${actSessionByActNoList}">
													<option value="${actSessionVO.act_session_no}">${actSessionVO.act_session_start_time}</option>
												</c:forEach>
											</select> 
											<label for="actPeopleNumber" class="actPeopleNumberLabel"><b>人數:</b></label>
											<input type="text" readonly id="actPeopleNumber" name="actPeopleNumber" value="1"> 
											<input type="hidden" id="totalPeople" value="${actPeopleNumber}"> 
												<i class='bx bx-plus-circle plusIcon' id="actPricePlusBtn" onclick="plus();"></i> 
												<i class='bx bx-minus-circle minusIcon' id="actPriceMinusBtn" onclick="minus();"></i>
										</div>
										<div>
											<b style="font-size:20px;position:relative;bottom:40px;left:365px;">總金額</b>
											<span style="position:relative;left:580px;bottom:40px;font-size:22px;color:#46A3FF" id="actTotalPrice">${actVO.act_price}</span>
										</div>
										<div>
											<input type="hidden" name="action" value="immediateCheckout">
											<input type="hidden" name="act_name" value="${actVO.act_name}">
											<input type="hidden" name="act_price" value="${actVO.act_price}">
											<button type="button" id="addActToCarBtn" class="btn btn-success" style="left:300px;bottom:5px">
												<span class="btn-icon-start addCar"><i class='bx bxs-cart'></i>加入購物車</span>
											</button>
											<button type="button" id="immediateCheckout" onclick="check();" class="btn btn-info" style="left:320px;bottom: 5px">
												<span class="btn-icon-start"><i class='bx bxs-cart'></i>立即結帳</span>
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/front_end/message.file"%>
	<!-- Message -->
	<%@ include file="/front_end/footer.file"%>
	<!-- Footer -->
	<%@ include file="/front_end/commonJS.file"%>
	<!-- 基本JS檔案 -->

	<script src="<%=request.getContextPath()%>/front_end/activity/js/fotorama.js"></script>
	<script src="<%=request.getContextPath()%>/front_end/activity/datetimepicker/jquery.datetimepicker.full.js"></script>


	<script>
	$.datetimepicker.setLocale('zh');
	$('#act_date').datetimepicker({
		theme : '',
		timepicker : false,
		step : 1,
		format : 'Y-m-d',
		value : '${actSessionByActNoList.stream().findFirst().get().getAct_session_start_date()}',
		minDate:'${actSessionByActNoList.stream().findFirst().get().getAct_session_start_date()}',
		maxDate:'${actSessionByActNoList.stream().findFirst().get().getAct_session_start_date()}',
	});
//檢查立即結帳時有無登入
	function check(){
		if('${mem_mail}' === ''){
			notLogin();
			window.setTimeout(() => location.href="<%=request.getContextPath()%>/front_end/signin/signin.jsp",800);
			return false;
		}
		document.getElementById('immediateCheckoutForm').submit();
	}

//場次時間	
	let currentRequest = null;
	$('#actSessionStartTimeSelect').on('change',function(){
		currentRequest = $.ajax({
			url:"<%=request.getContextPath()%>/activity/ActivityOrderDetail",
			type:"POST",
			data:{
				action:'checkSessionPeopleNumber',
				sessionNo:$("#actSessionStartTimeSelect option:selected").val(),			
			},
			success:function(response){
				$("#totalPeople").val(response);//帶回來該場次目前有多少人
				$("#actPeopleNumber").val(1);//當change事情發生 要把該人數設回1
				$("#actTotalPrice").text($("#actPeopleNumber").val() * ${actVO.act_price});//總價
// 				一定要先設定值
				
				let myInput = document.getElementById('actPeopleNumber');
				let plusBtn = document.getElementById('actPricePlusBtn');
				let minus	= document.getElementById('actPriceMinusBtn');
				
				if(parseInt(response) >= 10){
					alert("不好意思，該場次人數已達上限!");
					plusBtn.disabled = true;
					plusBtn.setAttribute("style","color:gray;");
				}
				if(parseInt(myInput.value) + parseInt(response) < 10){
										
					plusBtn.disabled = false;
					plusBtn.removeAttribute("style","color:gray;");
				}
				minus.setAttribute("style","color:gray;");
				minus.disabled = true;
				
				currentRequest.abort();
			}
		});
	});
	
// 	購物車
	let shoppingCarRequest = null;
	$("#addActToCarBtn").on('click',function(){
		shoppingCarRequest = $.ajax({
			url:"<%=request.getContextPath()%>/activity/ActivityOrder",
			type:"POST",
			data:{
				action:'actShoppingCart',
				carAction:'add',
				actNo:${actVO.act_no},
				actNameInCar:$("#actName").text(),
				actDateInCar:$("#act_date").val(),
				actTime:$("#actSessionStartTimeSelect option:selected").text(),
				actPeopleNumber:$("#actPeopleNumber").val(),
			},
			success:function(response){				
				if(response.includes("update")){					
					autoCloseUpdate();
					$("#carCount").text(response.charAt(response.length-1));
					shoppingCarRequest.abort();
				}else{
					autoCloseForShoppingCar();
					$("#carCount").text(response);
					shoppingCarRequest.abort();
				}
			}
		});
	});
	
	
	document.getElementById('actPriceMinusBtn').disabled = true;
	document.getElementById('actPriceMinusBtn').setAttribute("style", "color:gray;");
	
	
	function plus(){
		let myInput = document.getElementById('actPeopleNumber');
		let totalPeople = parseInt(document.getElementById('totalPeople').value);
		let val = parseInt(myInput.value);
		let total = totalPeople + val;
		let totalPrice = document.getElementById('actTotalPrice');
		
		let plusBtn = document.getElementById('actPricePlusBtn');
		let minusBtn = document.getElementById('actPriceMinusBtn');
		
		minusBtn.disabled = false;
		minusBtn.removeAttribute("style", "color:gray;");
		
		if(total < 10){
			myInput.value = ++val;
			totalPrice.textContent = val * ${actVO.act_price};
		}else{
			autoClose();
			plusBtn.disabled = true;
			plusBtn.setAttribute("style", "color:gray;");
			return false;
		}		
	}
	
	function minus(){
		let myInput = document.getElementById('actPeopleNumber');
		let val = parseInt(myInput.value);
		let plusBtn = document.getElementById('actPricePlusBtn');
		let minusBtn = document.getElementById('actPriceMinusBtn');
		
		let totalPrice = document.getElementById('actTotalPrice');
		
		plusBtn.disabled = false;
		plusBtn.removeAttribute("style", "color:gray;");
		--val;
		minusBtn.disabled = val <= 1;
		if(val >= 1){
			myInput.value = val;
			totalPrice.textContent = myInput.value * ${actVO.act_price};
			if (val === 1) {minusBtn.style.color = 'gray';}
		}else{
			if (val == 0)
				return false;
			
			minusBtn.disabled = true;
			minusBtn.setAttribute("style", "color:gray;");
			myInput.value = val;
			return false;
		}		
	}

	var map;
	function initMap() { // Main
		
		this.lat1 = parseFloat(${actVO.act_location_latitude});
		this.lng1 = parseFloat(${actVO.act_location_longitude});

	  // 初始化地圖
	  map = new google.maps.Map(document.getElementById('map-canvas'), {
	    zoom: 14,
	    center: { lat: 23.99483, lng: 121.630453 }			//初始化地點
	  });
	  
	  // 載入路線服務與路線顯示圖層
	  var directionsService = new google.maps.DirectionsService();
	  var directionsDisplay = new google.maps.DirectionsRenderer();
	  var destination = {lat: lat1, lng: lng1};				//宣告物件加入經緯度	
	  // 放置路線圖層
	  directionsDisplay.setMap(map);
	  // 路線相關設定
	  var request = {
	    origin: { lat: 23.99483, lng: 121.630453 },				// 起始地點
	    destination,					// 加入destination 物件
	    travelMode: 'DRIVING',									//預設就是DRIVING
	  };
	  // 繪製路線
	  directionsService.route(request, function (result, status) {
	      if (status == 'OK') {
	          // 回傳路線上每個步驟的細節
	          console.log(result.routes[0].legs[0].steps);
	          directionsDisplay.setDirections(result);
	      } else {
	          console.log(status);
	      }
	  });
	}
// 	google.maps.event.addDomListener(window, 'load', initMap);
	window.onload = initMap;
	
	$("#queryBtn").click(function(){
		$("#queryForm").submit();
	});
	
	
	// ● header顯示目前在哪個區塊，"活動"的頁面請將nth-child(1)改成2，"美食"的頁面改成3，其他人這行可刪掉
	$(`.nav-item:nth-child(2)>a`).attr('class', 'active');
	
	// ● 以下是sweetalert2的範例也可以刪除
	// 簡易版
	function addToCart() {
		// 簡易版；標題,內文,圖示
		swal.fire('已加入購物車', '快到購物車內結帳吧！', 'success');
	}
	// 自動關閉版
	function autoClose() {
		swal.fire({
			icon : 'error', //常用的還有'error'
			title : '人數已達上限',
			showConfirmButton : false, //因為會自動關閉，所以就不顯示ok按鈕
			timer : 1000
		// 單位毫秒，1秒後自動關閉跳窗
		})
	}
	function autoCloseForShoppingCar() {
		swal.fire({
			icon : 'success', //常用的還有'error'
			title : '成功加入活動',
			showConfirmButton : false, //因為會自動關閉，所以就不顯示ok按鈕
			timer : 1000
		// 單位毫秒，1秒後自動關閉跳窗
		})		
	}
	function autoCloseUpdate() {
		swal.fire({
			icon : 'success', //常用的還有'error'
			title : '購物車項目已更新',
			showConfirmButton : false, //因為會自動關閉，所以就不顯示ok按鈕
			timer : 1000
		// 單位毫秒，1秒後自動關閉跳窗
		})		
	}
	function notLogin() {
		swal.fire({
			icon : 'error', //常用的還有'error'
			title : '請先登入',
			showConfirmButton : false, //因為會自動關閉，所以就不顯示ok按鈕
			timer : 1000
		// 單位毫秒，1秒後自動關閉跳窗
		})		
	}
</script>

</body>
</html>
