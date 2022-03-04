<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
    </head>
    <body>
		<%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->
		
		<div class="mt-5 mb-5 ptb-70 container">
		
<!-- 		● 以下到83行刪除  -->

			<h2>★ 外層容器的class</h2>
			<p>● <strong>class="mt-5"</strong>，最外層或是第一個div class要加上，不然會被擋在header後面，margin-top: 3rem的意思 <br>
			● <strong>class="mb-5"</strong>，最外層或是最後一個div建議加上，跟footer留些距離，margin-bottom: 3rem的意思  <br>
			● <strong>class="ptb-70"</strong>，如果覺得還是太上面了，可再加這個，padding-top：20px、padding-bottom:50px的意思  <br>
			● <strong>class="container"</strong>，最大寬是1140px而且置中，如果螢幕寬度小於1140px會跟著縮小</p>

			<hr><br>
			
			<h2>★ icon</h2>
			<a href="https://boxicons.com/"><h3 style="color:red">boxicons</h3></a>
			<p>使用方式：點選icon 複製Font裡的i標籤<br>
			
			<hr><br>

			<h2>★ button</h2>
			<p>前台的主要兩個button是咖啡色系，與後台的綠色系不同。<br>但是class名稱相同，同樣class="btn btn-primary" 在前台會變成咖啡色按鈕，在後台會變成綠色按鈕。<br>因前台使用按鈕的地方比較少，所以按鈕預設比較大顆(左右的padding比較大)</p>
			<button type="button" class="btn btn-secondary">取消</button>
			<button type="button" class="btn btn-primary">確定</button>

			<hr><br>

			<h3>普通版</h3>
			<button type="button" class="btn btn-primary">primary</button>
			<button type="button" class="btn btn-secondary">secondary</button>
			<button type="button" class="btn btn-success">success</button>
			<button type="button" class="btn btn-danger">danger</button>
			<button type="button" class="btn btn-warning">warning</button>
			<button type="button" class="btn btn-info">info</button>
			<button type="button" class="btn btn-light">light</button>
			<button type="button" class="btn btn-dark">dark</button>

			<hr><br>

			<h3>按鈕尺寸：加尺寸的class</h3>
			<button type="button" class="btn btn-primary btn-lg">btn-lg</button>
			<button type="button" class="btn btn-primary">預設</button>
			<button type="button" class="btn btn-primary btn-sm">btn-sm</button>
			<button type="button" class="btn btn-primary btn-xs">btn-xs</button>
			<button type="button" class="btn btn-primary btn-xxs">btn-xxs</button>

			<hr><br>

			<h3>方按鈕：加btn-square</h3>
			<button type="button" class="btn btn-square btn-primary">primary</button>
			<button type="button" class="btn btn-square btn-secondary">secondary</button>
			<button type="button" class="btn btn-square btn-success">success</button>
			<button type="button" class="btn btn-square btn-danger">danger</button>
			<button type="button" class="btn btn-square btn-warning">warning</button>
			<button type="button" class="btn btn-square btn-info">info</button>
			<button type="button" class="btn btn-square btn-light">light</button>
			<button type="button" class="btn btn-square btn-dark">dark</button>

			<hr><h2>★ 色碼和跳窗看後台的test.jsp</h2><hr><br><br>

			<h2>★ SweetAlert2 </h2>
			<h3>用法就是取代alert，常用於提示使用者按了button後發生的事，例如修改完成、新增完成、錯誤、已加入購物車</h3>
			<a href="https://sweetalert2.github.io/"><h3 style="color:red">英文官網</h3></a>
			<a href="http://mishengqiang.com/sweetalert2/"><h3 style="color:red">中文官網</h3></a>
			<br>
			<button type="button" class="btn btn-info" onclick="addToCart()">
				簡易版- 加入購物車
			</button>
			<button type="button" class="btn btn-success" onclick="autoClose()">
				自動關閉版- 加入購物車
			</button>

<!-- 		刪到這裡		 -->

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
        </script>
        
    </body>
</html>
