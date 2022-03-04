<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="com.roomType.model.*" %>
<%@ page import="com.room.model.*" %>
<%@ page import="com.roomRsv.model.*"%>

<%
	RoomTypeService roomTypeSvc = new RoomTypeService();
	List<RoomTypeVO> rtList = roomTypeSvc.getAllRoomType();
	pageContext.setAttribute("rtList", rtList);
%>

<%
	RoomRsvService rsvService = new RoomRsvService();
	List<RoomRsvVO> rsvList = rsvService.getAllRoomRsv();
	pageContext.setAttribute("rsvList", rsvList);
%>

<!doctype html>
<html>
    <head>
        <%@ include file="/front_end/commonCSS.file" %> <!-- 基本CSS檔案 -->
        <style>
        .banner-form {
        	margin-top: 0px;
        }
        .banner-form label {
        	padding-left: 12px;
        }
        .banner-form .nice-select {
        	background: #fff;
        }
        .banner-form .nice-select::after {
		    height: 8px;
		    width: 8px;
		    border-color: #555555;
		    top: 50%;
		    left: 80%;
		}		
		.banner-form {
			width: 100%;
		}


.close-calendar {
	position:absolute;
	padding:10px;
	background-color:#a4a4a4;
	right:0;
	top:0;
}
.close-calendar img {
	width:35px;
}
.arrow {
    position: absolute;
    color: #bbbfca;
    font-size: 36px;
    padding: 0px 10px;
    z-index: 10;
}
.aval-title {
    width: 90%;
    margin: 0 auto;
}
.calendar-forward {
    right: 5%;
    top: -1%;
    cursor: pointer;
}
.calendar-backward {
    left: 5%;
    top: -1%;
    cursor: pointer;
}
.condition {
    border: 1px solid black;
    border-radius: 10px;
    padding: 20px 40px;
    width: 90%;
    margin: 0 auto 20px auto;
}
.condition p {
    color: #acacac;
}
.condition h3 {
    width: fit-content;
}
.arrow:hover {
    color: #495464;
}
.view {
	position:relative;
    width: 1100px;
    overflow: hidden;
    margin: 0 auto;
}
#display {
    display: flex;
    flex-direction: row;
    width: fit-content;
    transition: 0.3s ease-in-out;
    margin: 0 auto;
}
.calendar-wrapper {
    min-width: 550px;
    opacity: 0;
    transition: 0.3s ease-in-out;
    padding: 0px 10px;
}
.title {
	margin: 5px 0;
    display: flex;
    height: min-content;
    text-align: center;
    letter-spacing: 1px;
    font-size: 20px;
    justify-content: center;
}
.title b {
    height: fit-content;
}
.title p {
    margin: 0px;
    color: #996A4D;
    font-weight: 700;
}
.calendar-td {
/*     padding: 1px 1px; */
}
.week-title th {
    text-align: center;
    padding: 10px 0px;
    font-size: 18px;
    color: #996A4D;
    font-weight: 700;
}
a.calendar-box {
    position: relative;
    display: block;
    background-color: #d3d3d3;
    height: 70px;
    width: 70px;
    margin: 0 auto;
}
a.calendar-default {
    display: flexbox;
    background-color: white;
    color:grey;
}
a.calendar-default:hover {
    color: #30504F;
    background-color: #E4D6C4;
}
a div.calendar-date {
	width:fit-content;
    position: absolute;
    right: 5px;
    top: 0px;
    font-size: 18px;
    text-align: right;
    align-self: flex-end;
}
a.calendar-isEmpty .calendar-price {
    position: absolute;
    bottom: 2px;
    right: 2px;
    font-size: 12px;
    font-weight: 500;
}
a.calendar-isFull {
	background-color: #e8e8e8;
	cursor: no-drop;
	pointer-events: none;
	color:grey;
}
a.calendar-isFull::before {
	position: absolute;
    left: 5px;
    top: -7px;
	font-size:30px;
	content: "\0002DF";
	color: grey;
}
a.calendar-isEmpty {
	cursor: pointer;
    color: black;
    border-bottom: 1.5px solid #969696 ;
}
a.calendar-isEmpty::before {
	position: absolute;
    left: 5px;
    top: 2px;
    font-family: "Font Awesome 5 Free";
    content: "\f111";
    color: rgb(16, 161, 16);
    font-size: 10px;
}
a.calendar-today {
    background: #edebe2;
}
@media screen and (max-width: 1140px) {
    .view {
        width: 860px;
        overflow: hidden;
        margin: 0 auto;
    }
    a.calendar-box {
        height: 100px;
        width: 100%;
    }
    .calendar-wrapper {
        width: 860px;
    }
    .calendar-wrapper table {
        width: 100%;
    }
    .calendar .calendar-date {
        font-size: 16px;
    }
    .calendar .calendar-date::before {
        font-size: 16px;
    }
    .calendar div.calendar-price {
        font-size: 18px;
    }
}

@media screen and (max-width: 840px) {
    a.calendar-box {
        min-height: 70px;
        min-width: 70px;
    }
    .calendar-wrapper {
        width: 660px;
    }
    .view {
        width: 660px;
    }
     .calendar div.calendar-price {
        font-size: 16px;
    }
}

        </style>
    </head>
    <body>
		<%@ include file="/front_end/loading.file" %> <!-- loading -->
        <%@ include file="/front_end/header.file" %> <!-- Header -->

	<div class="mt-5 mb-5 ptb-70 container">

		<div class="banner-form">
			<div class="d-flex justify-content-space-between align-items-center">
				<div class="col-lg-3 col-md-3">
					<div class="form-group">
						<label>房型</label> 
						<select id="room-type">
							<c:forEach var="rt" items="${rtList}">
								<c:if test="${rt.type_state == 'true'}">
									<option value="${rt.type_no}">${rt.type_name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-lg-3 col-md-3">
					<div class="form-group">
						<label class="col-sm-12 control-label">天數</label> 
						<select name="stay" id="stay" class="change-room">
							<option value="1" selected>1晚</option>
							<option value="2">2晚</option>
							<option value="3">3晚</option>
							<option value="4">4晚</option>
							<option value="5">5晚</option>
							<option value="6">6晚</option>
							<option value="7">7晚</option>
						</select>
					</div>
				</div>
				<div class="col-lg-3 col-md-3">
					<div class="form-group">
						<label class="col-sm-12 control-label">間數</label>
						<select name="qty" id="qty" class="change-room">
							<option value="1">1間</option>
							<option value="2">2間</option>
							<option value="3">3間</option>
							<option value="4">4間</option>
							<option value="5">5間</option>
							<option value="6">6間</option>
							<option value="7">7間</option>
						</select>
					</div>
				</div>

			</div>
		</div>


		<!-- 			日曆 -->
		<div class="view">
			<div id="display"></div>
			<div class="calendar-backward arrow">
				<i class='bx bx-chevron-left'></i>
			</div>
			<div class="calendar-forward arrow">
				<i class='bx bx-chevron-right'></i>
			</div>
		</div>


	</div>

	<%@ include file="/front_end/message.file" %> <!-- Message --> 
        <%@ include file="/front_end/footer.file" %> <!-- Footer -->      
        <%@ include file="/front_end/commonJS.file" %> <!-- 基本JS檔案 -->
        
        <script>
        $(`.nav-item:nth-child(1)>a`).attr('class', 'active');
		
        $(document).ready(function () {
            let display = document.getElementById("display");
            let weeks = ["一", "二", "三", "四", "五", "六", "日"];
            let today = new Date();
            let thisYear = today.getFullYear();
            let thisMonth = today.getMonth();
            let todayDate = today.getDate();
            let todayStr = thisYear + "-" + (thisMonth+1) + "-" + todayDate
            console.log(todayStr);
            let current = 0;
            var loaded = [0, 1]
            getCalendars(12); //拿一年份的月曆！
            fetchAvalibility(current);
            fetchAvalibility(current+1);
            
            function createCalendar(year, month) {
                let feb = leapYear(year);
                let monthOfDay = [31, feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
                let wrapper = document.createElement("div"); //包住個別日曆
                wrapper.classList.add("calendar-wrapper");
                let title = document.createElement("div"); //產生日曆標頭
                title.classList.add("title");
                title.innerHTML = "<p>" + year + "年  " + (month + 1) + "月</p>";
                let table = document.createElement("table"); //產生日曆表格
                table.classList.add("calendar");
                table.classList.add("table-bordered")
                let firstTr = document.createElement("tr"); //產生標頭列
                firstTr.classList.add("week-title");
                table.append(firstTr);
                wrapper.append(title);
                wrapper.append(table);
                //建立抬頭
                for (let i = 0; i < 7; i++) {
                    let th = document.createElement("th");
                    th.innerText = weeks[i];
                    firstTr.append(th);
                }
                //找出該月第一天是禮拜幾
                let firstDayOfWeek = new Date(year, month, 1).getDay();
                if (firstDayOfWeek == 0) firstDayOfWeek = 7;
                //確認月曆行數
                let rows = (monthOfDay[month] + firstDayOfWeek - 1) / 7;
                //產生月曆行數
                for (let i = 0; i < rows; i++) {
                    let tr = document.createElement("tr");
                    for (let j = 1; j <= 7; j++) {
                        let td = document.createElement("td");
                        let a = document.createElement("a");
//                         let img = document.createElement("img");
<%--                         img.setAttribute("src", "<%=request.getContextPath()%>/css/ajax-loader.gif"); --%>
//                         img.setAttribute("style", "display:none; width:100%")
                        td.classList.add("calendar-td");
                        a.classList.add("calendar-box");
                        let id =
                            year.toString() + "-"
                            + (month + 1).toString().padStart(2, "0") + "-"
                            + (i * 7 + j - firstDayOfWeek + 1).toString().padStart(2, "0");
                        if (i === 0 && j >= firstDayOfWeek) {
                            a.setAttribute("data-year", year);
                            a.setAttribute("data-month", month + 1);
                            a.setAttribute("data-date", j + i * 7 - firstDayOfWeek + 1);
                            a.setAttribute("id", id);
                        } else if (i * 7 + j - firstDayOfWeek + 1 <= monthOfDay[month]) {
                            a.setAttribute("data-year", year);
                            a.setAttribute("data-month", month + 1);
                            a.setAttribute("data-date", j + i * 7 - firstDayOfWeek + 1);
                            a.setAttribute("id", id);
                        }
//                         a.append(img);s
                        td.append(a);
                        tr.append(td);
                    }
                    table.append(tr);
                }
                return wrapper;
            }
            
            function leapYear(year) {
                let feb = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0 ? 29 : 28;
                return feb;
            }
            //填充日期資訊
            function fillUpDates(year, month, thisMonthDate) {
                let feb = leapYear(year);
                let monthOfDay = [31, feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
                for (let i = thisMonthDate; i <= monthOfDay[month]; i++) {
                    let celldate = document.createElement("div");
                    let cellqty = document.createElement("div");
                    let cellprice = document.createElement("div");
                    cellqty.classList.add("calendar-qty"); //剩餘房間數
                    cellprice.classList.add("calendar-price");
                    celldate.classList.add("calendar-date");
                    let id = year.toString() + "-" + (month + 1).toString().padStart(2, "0") + "-" + i.toString().padStart(2, "0");
                    let hrefstr =
                        "<%=request.getContextPath()%>/room/RoomRsv?action=booking" 
                        + "&date=" + id
                        + "&stay=0";
                    let box = document.getElementById(id);
                    box.setAttribute("href", hrefstr);
                    box.classList.add("calendar-default");
                    celldate.innerText = i;
                    box.append(celldate);
                    box.append(cellqty);  //剩餘房間數
                    box.append(cellprice);
                }
            }
            
            function getCalendars(number) {
                for (i = 0; i < number; i++) {
                    let thisMonthDate = 1;
                    let year = thisYear + Math.floor(thisMonth / 12);
                    let month = thisMonth % 12;
                    let calendar = createCalendar(year, month);
                    display.append(calendar);
                    if (i == 0){
                        thisMonthDate = todayDate;
                    } 
                    fillUpDates(year, month, thisMonthDate);
                    thisMonth++;
                }
                thisMonth = today.getMonth();
                let todaybox = document.getElementById(
                           thisYear.toString() + "-" 
                        + (thisMonth + 1).toString().padStart(2, "0") + "-"
                        + today.getDate().toString().padStart(2, "0")
                );
                todaybox.classList.add("calendar-today");
                let position = 0;
                let forward = $(".calendar-forward");
                let backward = $(".calendar-backward");
                let calendars = $(".calendar-wrapper");
                calendars.eq(0).css("opacity", "1");
                calendars.eq(1).css("opacity", "1");
                backward.fadeOut();
                //導覽
                forward.click(function () {
                    current += 1;
                    let calendarWidth = parseInt($(".calendar-wrapper").css("width").split("px")[0]);
                    calendars.css("opacity", "0");
                    if (0 < number - current) {
                        backward.fadeIn(0);
                        $("#display").css("transform", "translateX(-" + (position += calendarWidth) + "px)");
                    }
                    if (1 === number - current) {
                        forward.fadeOut(0);
                    }
                    calendars.eq(current).css("opacity", "1");
                    calendars.eq(current + 1).css("opacity", "1");
                    if (loaded.indexOf(current+1) < 0){
                    	fetchAvalibility(current + 1);
                        loaded.push(current+1);
                    }
                });
                backward.click(function () {
                    current -= 1;
                    let calendarWidth = parseInt($(".calendar-wrapper").css("width").split("px")[0]);
                    calendars.css("opacity", "0");
                    if (number - current > 0) {
                        forward.fadeIn(0);
                        $("#display").css("transform", "translateX(-" + (position -= calendarWidth) + "px)");
                    }
                    if (number - current === 12) {
                        backward.fadeOut(0);
                    }
                    calendars.eq(current).css("opacity", "1");
                    calendars.eq(current + 1).css("opacity", "1");
                    if (loaded.indexOf(current) < 0){
                    	fetchAvalibility(current);
                        loaded.push(current+1);
                    }
                });
                $(window).resize(function () {
                    let CalendarWidth = parseInt($(".calendar-wrapper").css("width").split("px")[0]);
                    let reposition = CalendarWidth * current;
                    position = reposition;
                    $("#display").css("transform", "translateX(-" + reposition + "px)");
                });
            }
            var rm_price = {
            		<c:forEach var="roomtypevo" items="${rtList}">
            			${roomtypevo.type_no}:${roomtypevo.type_name},
            		</c:forEach>
            };
            
            function fetchAvalibility(currentCal){
                let allDays = $(".calendar-wrapper").eq(currentCal).find(".calendar-default");
                let stayDays = $("#stay").val();
                let type_no = $("#room-type").val();
                let qty = $("#qty").val();
                for (let i = 0 ; i < allDays.length; i++){
                	let currentDate = allDays.eq(i);
                    $.ajax({
                         url: "<%=request.getContextPath()%>/room/RoomRsv",
                         data:{
                             date: currentDate.attr("id"),
                             stay: stayDays,
                             type_no: type_no,
                             qty: qty,
                             action:"roomCheck"
                         },
                         type: 'POST',
                         beforeSend: function() {
                        	 currentDate.children("img").show();
                          },
                         success: function(str){
                            var data = JSON.parse(str)
                            let type_no = Object.keys(data)[0];
                            let roomLeft = Object.values(data)[0]; //剩餘房間數
                
        					if(data.isFull == "true"){
        						currentDate.addClass("calendar-isFull");
        						/* currentDate.attr("href",""); */
        					} else if(data.isMam == "true"){
        						currentDate.addClass("calendar-isMam"); //房間滿
        					} else { 
        						
       							if (data != null){
       								currentDate.children(".calendar-price").text("$" + rm_price[type_no]);
       								currentDate.children(".calendar-qty").text("剩 " + roomLeft); //可以訂 空房>=選的房間數
       							}
        				
        						currentDate.addClass("calendar-isEmpty");
        				
        						let href = currentDate.attr("href").split("stay")[0] 
        									+ "stay=" + $("#stay").val() 
        									+ "&qty=" + $("#qty").val()
        									+ "&type_no=" + type_no;
        					   						
        						currentDate.attr("href", href);
        					}
//                             currentDate.children("img").hide();
                         }
                    })
                } 
            }
            $("#stay").change(function(){
            	loaded = [current, current+1];
            	fetchAvalibility(current)
            	fetchAvalibility(current+1)
            	 $(".calendar-price").text("");
            	$(".calendar-qty").text("");
            	 $(".calendar-default").removeClass("calendar-isFull calendar-isMam calendar-isEmpty");
            })
            
            $("#qty").change(function(){
            	loaded = [current, current+1];
            	fetchAvalibility(current)
            	fetchAvalibility(current+1)
            	 $(".calendar-price").text("");
            	$(".calendar-qty").text("");
            	 $(".calendar-default").removeClass("calendar-isFull calendar-isMam calendar-isEmpty");
            })
            
            $("#room-type").change(function(){
            	loaded = [current, current+1];
            	fetchAvalibility(current)
            	fetchAvalibility(current+1)
            	 $(".calendar-price").text("");
            	$(".calendar-qty").text("");
            	 $(".calendar-default").removeClass("calendar-isFull calendar-isMam calendar-isEmpty");
            })
        });
        
        </script>
        
    </body>
</html>
