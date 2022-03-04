<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>

<jsp:useBean id="roomTypeSvc" scope="page" class="com.roomType.model.RoomTypeService" />
<jsp:useBean id="roomImgSvc" scope="page" class="com.roomImg.model.RoomImgService" />
<jsp:useBean id="memberSvc" class="com.member.model.MemberService" />
<jsp:useBean id="crdSvc" class="com.creditcard.model.CreditcardService" />

<!doctype html>
<html>
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
        <style>
	input[type=checkbox], input[type=radio] {
		width: 30px;
	}
	/*             上方步驟區域 */
	.inner-title {
		padding: 1%;
		background-image: linear-gradient(to left, #fff, #ededed 80%);
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
		background-color: #f7f9fa;
	}
	
	p {
		display: inline;
	}
	.room-area {
		padding-top: 1%;
	}
	.room-card-area {
		background-color: #fff; 
 		padding: 3%;
	}
	.your-order-area {
		background-image: linear-gradient(to left, transparent, #ededed 90% ,#ededed 100%);
		position: sticky;
		top: 70PX;
		padding: 15px;
		font-size: 20px;
		letter-spacing: 1px;
	}
	.nice-select {
		font-size: 20px;
	}
	.nice-select.open, .nice-select:active, .nice-select:focus, .form-control:focus {
	    border: 1px solid #cda274;
	}
	input[type=text], input[type=search], input[type=password], input[type=number], input[type=email], textarea {
	    font-size: 20px;
	}
	label {
    	margin-bottom: 0;
	}
	.form-group {
		margin: 4% 0;
	}
	.creditcard-info .form-group {
		margin: 4%;
	}
	.form-group>label {
    	color: #30503f;
    	font-weight: 600;
    	letter-spacing: 0.5px;
	}
	.basic-info>p {
		margin: 2% 0;
		display: block;
		font-size: 20px;
		color: #d0af6d;
	}
	.basic-info>p>i {
		padding: 10px;
	}
	.basic-info, .creditcard-info {
		margin: 2% 0 5% 0;
		box-shadow: 5px 5px #f0e9df;
		border: 1px solid #f0e8df;
		background: #fcfbf9;
	}
	.checkout-form-list {
		margin-right: 40px;
	}
	.remind-info {
		margin: 2% 0 5% 0;
	}
	.remind-info>p {
		color: #a3785e;
		font-size: 18px;
		font-weight: 600;
	}
	@media screen and (max-width: 500px) {
		.form-group input[type=text], .nice-select, textarea {
			margin: 0 20px;
		}
	}
/* 	客製化radio */
	input[type='radio']:after {
        width: 18px;
        height: 18px;
        border-radius: 15px;
        top: -4px;
        left: 7px;
        position: relative;
        background-color: #ddd;
        content: '';
        display: inline-block;
        visibility: visible;
        border: 2px solid white;
    }
    input[type='radio']:checked:after {
        width: 18px;
        height: 18px;
        border-radius: 15px;
        top: -4px;
        left: 7px;
        position: relative;
        background-color: #a3785e;
        content: '';
        display: inline-block;
        visibility: visible;
        border: 2px solid white;
    }
 	#addCard-area {
 		display: none;
 		padding: 0 6%;
 	}
 	#addCard-area>input {
 		width: 70px;
 		height: 40px;
 		padding: 5px 10px;
 	}
	.room-card-img {
		margin: 4% 0;
	}
	hr {
		border: 1px solid #aaa;
		margin: 2% 0;
	}
	.booking_information>div {
		font-weight: 600;
		color: #30503f;
	}
	.booking_information_people span:first-child {
		border-right: 1px solid #30503F;
		padding-right: 10px;
	}
	.booking_information_people span:last-child {
		padding-left: 10px;
	}
	.roomtype_information>div:first-child {
		font-weight: 600;
		color: #a3785e;
	}
	.roomtype_information>div:last-child {
		font-weight: 600;
		color: #a3785e;
	}
	textarea {
		resize: none;
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
	                    <a href="<%=request.getContextPath()%>/front_end/index/index.jsp" class="step_item_label">搜尋</a>
	                </div>
                
	                <div class="step-item flex_center">
	                    <div class="outer_circle_step flex_center shapeborder_selected_out">
	                        <div class="flex_center inner_circle_step shapeborder_selected_in">
	                            2</div>
	                    </div>
	                	<a href="<%=request.getContextPath()%>/room/RoomType?type_no=${type_no}&action=getOneForShow" class="step_item_label">選擇日期</a>
	                </div>
                
	                <div class="step-item flex_center not_select">
	                    <div class="outer_circle_step flex_center shapeborder_selected_out">
	                        <div class="flex_center inner_circle_step shapeborder_selected_in">
	                            3</div>
	                    </div>
	                    <a class="step_item_label">填寫資料/付款</a>
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
			
			<div class="room-card-area">
				<form method="post" action="<%=request.getContextPath()%>/room/RoomRsv" id="checkoutForm" class="row">
				<!-- **左邊 -->
                <div class="col-lg-8 checkbox-form">

                	<h3 class="title">訂購人資料</h3>
					<div class="basic-info">
						<p><i class='bx bxs-user-check'></i>已帶入您的會員資料</p>
                        	<div class="form-group row col-12">
                        		<label class="col-sm-3 align-self-center">稱謂 <span class="required">*</span></label>
                       			<select class="myniceselect nice-select wide col-sm-3" name="title">
                                	<option value="先生" ${memberSvc.getOneBymail(mem_mail).mem_sex == 1 ? 'selected' : '' }>先生</option>
                                	<option value="女士" ${memberSvc.getOneBymail(mem_mail).mem_sex == 2 ? 'selected' : '' }>女士</option>                           
                        		</select>
                        	</div>

                            <div class="form-group row col-12">
	                        	<label class="col-sm-3 align-self-center">姓名 <span class="required">*</span></label>
	                        	<input class="col-sm-4 form-control" type="text" name="name" id="name" maxlength="10" value="${memberSvc.getOneBymail(mem_mail).mem_name}">
                            </div>

                            <div class="form-group row col-12">
                            	<label class="col-sm-3 align-self-center">電話 <span class="required">*</span></label>
                            	<input class="col-sm-4 form-control" type="text" name="phone" id="phone" maxlength="10" placeholder="請輸入10碼行動電話" value="${memberSvc.getOneBymail(mem_mail).mem_mobile}">
                            </div>

                            <div class="form-group row col-12">
                           		<label class="col-sm-3 align-self-center">e-mail <span class="required">*</span></label>
                            	<input class="col-sm-8 form-control" type="email" name="email" id="email" placeholder="請輸入有效的email" value="${mem_mail}">
                            </div>

                            <div class="order-notes form-group row col-12">     
                            	<label class="col-sm-3 align-self-center">偏好 <span class="required">*</span></label>                          
                                <div class="checkout-form-list">
                                	<input type="checkbox" id="note1" name="note1" value="禁菸房">
                                	<label for="note1"> 禁菸房</label>
                                </div>
                                <div class="checkout-form-list">
                                	<input type="checkbox" id="note2" name="note2" value="靠近電梯">
                                	<label for="note2"> 靠近電梯</label>
                                </div>
                            </div>
                            
                            <div class="form-group row col-12"> 
                            	<label class="col-sm-3 align-self-center">備註</label>
                            	<textarea class="col-sm-8 form-control" name="notearea" maxlength="90" cols="30" rows="4"
                                        placeholder="房間偏好需視實際住房情況而定。我們將會竭盡所能滿足您的要求。"></textarea>
                            </div>
						</div>

						<h3 class="title">選擇信用卡</h3>
						<div class="creditcard-info">
                            <div class="col-12">
                            	<c:forEach var="crdVO" items="${crdSvc.getallByMem_no(memberSvc.getOneBymail(mem_mail).mem_no)}">
                                    <div class="form-group card-data">
                                        <input type="radio" name="creditcard" id="${crdVO.crd_no}"
                                            value="${crdVO.crd_num}">
                                        <label for="${crdVO.crd_no}">
                                            ${crdVO.crd_num}
                                        </label>
                                    </div>
                              	</c:forEach>
                              	<div class="form-group addCard-button">
                                	<input type="radio" name="creditcard" id="addCard" value="addCard">
                                	<label for="addCard">使用別張信用卡</label>
                                </div>
                                <div class="form-group" id="addCard-area">
									<input type="text" name="card_no1" size="4" value="" maxlength="4"><i class='bx bx-minus'></i>
									<input type="text" name="card_no2" size="4" value="" maxlength="4"><i class='bx bx-minus'></i>
									<input type="text" name="card_no3" size="4" value="" maxlength="4"><i class='bx bx-minus'></i>
									<input type="text" name="card_no4" size="4" value="" maxlength="4">
                                </div>
                            </div>
						</div>
						
                        <div class="remind-info">
							<div class="form-group col-12">
								<input type="checkbox" id="cancelRules">
	                        	<label for="cancelRules">本人已閱讀和接受<a>條款及條件。</a></label>
							</div>
	                        <p>您可以在 ${start_date} 之前的任何時間取消預訂，且不會產生額外費用。</p>
						</div>
						
						<div class="col-12 d-flex justify-content-center">
							<input type="hidden" name="mem_no" value="${memberSvc.getOneBymail(mem_mail).mem_no}">
							<input type="hidden" name="type_no" value="${type_no}">
							<input type="hidden" name="start_date" value="${start_date}">
							<input type="hidden" name="end_date" value="${end_date}">
							<input type="hidden" name="days" value="${days}">
							<input type="hidden" name="qty" value="${qty}">
							<input type="hidden" name="action" value="paying">
	                        <button type="button" class="btn btn-primary col-xl-4 col-sm-5" onclick="check();">付款</button>
                        </div>
					</div>

                <!-- **右邊 -->
                <div class="col-lg-4">
                
                    <div class="your-order-area">
                        <h3 class="title">訂購明細</h3>
                        <div class="your-order-table table-responsive">
                        	<div class="booking_information">
								<div class="booking_information_date">
									<span id="guest_checkinDate" class="mr-10">${start_date}</span> <i class='bx bx-right-arrow-alt'></i> <span id="guest_checkoutDate">${end_date}</span>
								</div>
								<div class="booking_information_people">
									<span>${days}晚</span>
									<span id="qty" class="booking_information_people_adults">${qty}間房</span>
								</div>
							</div>
						
							<hr>
						
                            <div class="roomtype_information">
                                <div><i class='bx bx-purchase-tag-alt' ></i> ${roomTypeSvc.getOneRoomType(type_no).type_name}</div>
                            	<div class="room-card-img">
                            	<c:choose>
									<c:when test="${roomImgSvc.getAllByType(type_no).size() > 0}">
										<img src="<%=request.getContextPath()%>/room/RoomImg?type_no=${type_no}&action=showFirstImages">
									</c:when>
									<c:otherwise>
										<img src="<%=request.getContextPath()%>/front_end/assets/img/no-img.jpg" class="no-img">
									</c:otherwise>
								</c:choose>
								</div>
                                <div class="d-flex justify-content-between">
                                	<div>一間房一晚</div>
                                	<div><fmt:formatNumber value="${roomTypeSvc.getOneRoomType(type_no).type_price}" pattern="NT$ ###,###" /></div>
								</div>
								<hr>
                                <div class="d-flex justify-content-between">
                                	<div>總價</div>
                                	<div><fmt:formatNumber value="${roomTypeSvc.getOneRoomType(type_no).type_price*qty*days}" pattern="NT$ ###,###,###" /></div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
			</form>
			</div>
		</div>
		
		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
        
        <script>
		// header顯示在哪個區塊
        $(`.nav-item:nth-child(1)>a`).attr('class', 'active');
		
     	// 使用別張信用卡區塊顯示
        $('input[type=radio][name="creditcard"]').on('change', function() {
			if($(this).val() == "addCard") {
				$("#addCard-area").show();
				$('#addCard-area input:nth-child(1)').focus();
			} else {
				$("#addCard-area").hide();
			}     
        })
        
		// 信用卡卡號自動跳下欄位
		$('body').find('#addCard-area').children('input').keyup( function(e){
		// 限制只能輸入數字
		  if(!/^\d+$/.test(this.value)){
		    var newValue = /^\d+/.exec(this.value);
		    newValue != null ? $(this).val(newValue) : $(this).val('');
		    }
		  this.value.length == this.getAttribute('maxlength') && $(this).next().next().focus();
		});
     	
     	// 欄位驗證
     	function check(){
			let mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			let name = document.getElementById('name');
			let phone = document.getElementById('phone');
			let email = document.getElementById('email');
			let form = document.getElementById('checkoutForm');
			
			if(name.value.trim() === '' || name.value.trim().length > 10){
				name.focus();
				autoClose();
				return false;
			} else if (!phone.value.match("^09[0-9]{8}")){
				phone.focus();
				autoClose();
				return false;
			} else if (email.value.trim() === '' || !email.value.match(mailformat)){
				email.focus();
				autoClose();
				return false;
			} else if($("input:radio[name='creditcard']:checked").val() == null){
				creditcard();
				return false;
			} else if($("input:radio[name='creditcard']:checked").val() == "addCard" && ($("input[name='card_no1']").val().length != 4 || $("input[name='card_no2']").val().length != 4 || $("input[name='card_no3']").val().length != 4 || $("input[name='card_no4']").val().length != 4)){
				$('#addCard-area input:nth-child(1)').focus();
				cardNo();
				return false;
			} else if($("input[id=cancelRules]").is(':checked') === false){
				$("input[id=cancelRules]").focus();
				cancelRules();
				return false;
			} 

			form.submit();
		}
     	// alert樣式
     	function autoClose() {
			swal.fire({
				icon : 'error',
				title : '請完成必填欄位',
				showConfirmButton : false,
				timer : 1000
			})
		}
     	function cancelRules() {
			swal.fire({
				icon : 'warning',
				title : '請勾選同意取消規則',
				showConfirmButton : false,
				timer : 1000
			})
		}
     	function creditcard() {
			swal.fire({
				icon : 'error',
				title : '請選擇信用卡',
				showConfirmButton : false,
				timer : 1000
			})
		}
     	function cardNo() {
			swal.fire({
				icon : 'error',
				title : '信用卡共需16碼',
				showConfirmButton : false,
				timer : 1000
			})
		}
     	function paying() {
     		swal.fire({
     		  	title: '產生訂單中...',
     		  	html: '請勿重整或關閉畫面',
     		  	showConfirmButton : false,
     		  	timer: 1500,
     		  	timerProgressBar: true,
     		}).then(function () {
     	        window.location.href = "<%=request.getContextPath()%>/front_end/index/index.jsp";
     	    })
     	}
        </script>
        
    </body>
</html>
