<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
        <style>
        input[type=checkbox], input[type=radio] {
			width: 20px;
		}
        </style>
    </head>
    <body>
		<%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->
		
		<div class="mt-5 mb-5 ptb-70 container">

            <div class="row">
                <!-- **左邊 -->
                <div class="col-lg-6 col-12">

                    <!-- Checkbox Form Start -->
                    <form action="#">
                        <div class="checkbox-form">

                            <!-- Checkbox Form Title Start -->
                            <h3 class="title">訂購人資料</h3>
                            <p>已帶入您的會員資料，若訂購人與會員不同請再修改</p>
                            <!-- Checkbox Form Title End -->

                            <div class="row">
                                <div class="col-md-6">
                                    <label>稱謂 <span class="required">*</span></label>
                                    <select class="myniceselect nice-select wide rounded-0">
                                        <option value="Mr.">Mr.</option>
                                        <option value="Ms.">Ms.</option>
                                        <option value="Mrs.">Mrs.</option>
                                        <option value="Miss.">Miss.</option>
                                    </select>
                                </div>

                                <div class="col-md-12">
                                    <div class="checkout-form-list">
                                        <label>姓名 <span class="required">*</span></label>
                                        <input type="text">
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="checkout-form-list">
                                        <label>電話 <span class="required">*</span></label>
                                        <input type="text" maxlength="10" placeholder="請輸入10碼行動電話">
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="checkout-form-list">
                                        <label>e-mail <span class="required">*</span></label>
                                        <input type="email" placeholder="請輸入有效的email">
                                    </div>
                                </div>
                            </div>

                            <!-- Checkbox Form Title Start -->
                            <h3 class="title">付款資料</h3>
                            <!-- Checkbox Form Title End -->

                            <div class="col-12">
                                    <div>
                                        <input type="radio" name="creditcard"
                                            id="1">
                                        <label for="1">
                                            3569636012341234
                                        </label>
                                    </div>
                                    <div>
                                        <input type="radio" name="creditcard"
                                            id="2">
                                        <label for="2">
                                            5157118883672345 
                                        </label>
                                    </div>
                              
                                <a class="btn btn-info btn-sm">新增信用卡</a>
                            </div>

                            <div class="order-notes">
                                <div class="checkout-form-list checkout-form-list-2">
                                    <label>備註</label>
                                    <textarea id="checkout-mess" cols="60" rows="3"
                                        placeholder="房間偏好需視實際住房情況而定。我們將會竭盡所能滿足您的要求"></textarea>
                                </div>
                            </div>

						<div >
							<div class="form-group">
								<input type="checkbox" id="chb2">
								<p>
									Accept <a href="terms-condition.html">Terms & Conditions</a> And <a href="privacy-policy.html">Privacy Policy.</a>
								</p>
							</div>
						</div>
					</div>
                    </form>
                </div>
                <!-- Checkbox Form End -->

                <!-- **右邊 -->
                <div class="col-lg-6 col-12">
                    <!-- Your Order Area Start -->
                    <div class="your-order-area border">

                        <!-- Title Start -->
                        <h3 class="title">訂購明細</h3>
                        <!-- Title End -->

                        <!-- Your Order Table Start -->
                        <div class="your-order-table table-responsive">
                            <div>2021年 9月 25日 - 2021年 9月 27日</div>
                            <div>2 晚 | 2 客房</div>

                            <div>
                                <div>豪華雙人房 </div>
                                <div>單價 $3200</div>
                            </div>
                            <div>
                                <div></div>
                                <div>總金額 $ 12,800</div>
                            </div>
                        </div>
                        <!-- Your Order Table End -->
                        <div class="col-12">
	                        <button type="submit" class="btn btn-primary col-5">付款</button>
                        </div>
                    </div>
                    <!-- Your Order Area End -->
                </div>
            </div>

    <!-- Checkout Section End -->

		</div>
		
		<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
        
        <script>
		// ● header顯示目前在哪個區塊，"活動"的頁面請將nth-child(1)改成2，"美食"的頁面改成3，其他人這行可刪掉
        $(`.nav-item:nth-child(1)>a`).attr('class', 'active');
        
		// ● 以下是sweetalert2的範例也可以刪除
        // 簡易版
        function addToCart() {
					// 簡易版；標題,內文,圖示
        	swal.fire('已加入購物車','快到購物車內結帳吧！','success');
        }
     	// 自動關閉版
        function autoClose() {
			swal.fire({
			  icon: 'success',  //常用的還有'error'
			  title: '已加入購物車',
			  showConfirmButton: false, //因為會自動關閉，所以就不顯示ok按鈕
			  timer: 1000 // 單位毫秒，1秒後自動關閉跳窗
			})
		}
     	
        $("option:nth-child(4)").attr("selected",true);
//         $("span.current").text("先生");
        
        $("select.myniceselect>option").change(function() {
        	alert("option change");
        });

        $("div.myniceselect").click(function() {
        	alert("div click");
        });
        </script>
        
    </body>
</html>
