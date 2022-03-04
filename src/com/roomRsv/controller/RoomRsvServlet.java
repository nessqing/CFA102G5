package com.roomRsv.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.room.model.RoomService;
import com.roomOrder.model.RoomOrderService;
import com.roomOrderDetail.model.RoomOrderDetailService;
import com.roomOrderDetail.model.RoomOrderDetailVO;
import com.roomRsv.model.RoomRsvService;
import com.roomType.model.RoomTypeService;
import com.roomType.model.RoomTypeVO;
import com.util.room.SendMail;

public class RoomRsvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("Action" + action);
		// 從首頁搜尋，房型列表顯示符合的結果
		if ("getEnoughType".equals(action)) {
			/*************************** 1.接收請求參數 ****************************************/
			String rangedate = req.getParameter("rangedate");
			Integer qty = new Integer(req.getParameter("qty"));
			Integer guest = new Integer(req.getParameter("guest"));

			// 將收到的住宿期間分割成 起始日 和 結束日
			List<String> dateList = new LinkedList<String>();
			dateList = Arrays.asList(rangedate.split(" to "));
			String start_date = dateList.get(0);
			String end_date = dateList.get(1);

			/*************************** 2.開始查詢資料 ****************************************/
			RoomTypeService roomTypeSvc = new RoomTypeService();
			// 符合條件的
			List<RoomTypeVO> ableList = roomTypeSvc.getEnoughType(java.sql.Date.valueOf(start_date),
					java.sql.Date.valueOf(end_date), qty, guest);
			// 不符合條件的
			List<RoomTypeVO> notList = roomTypeSvc.getNotEnoughType(java.sql.Date.valueOf(start_date),
					java.sql.Date.valueOf(end_date), qty, guest);

			/*************************** 3.查詢完成,準備轉交 ************/
			HttpSession session = req.getSession();
			session.setAttribute("rangedate", rangedate);
			session.setAttribute("start_date", start_date);
			session.setAttribute("end_date", end_date);
			session.setAttribute("qty", qty);
			session.setAttribute("guest", guest); // 人數只有搜尋時會用到，選完房型改記type_no
			session.setAttribute("ableList", ableList);
			session.setAttribute("notList", notList);
			String url = "/front_end/room/roomList.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交前台的roomList.jsp
			successView.forward(req, res);
		}

		// 到結帳頁
		if ("payment".equals(action)) {

			try {
				String rangedate = req.getParameter("rangedate");
				Integer type_no = new Integer(req.getParameter("type_no"));
				Integer qty = new Integer(req.getParameter("qty"));

				// 將收到的住宿期間分割成 起始日 和 結束日
				List<String> dateList = new LinkedList<String>();
				String[] split = rangedate.split(" to ");
				for (int i = 0; i < split.length; i++) {
					dateList.add(split[i]);
				}
				String start_date = split[0];
				String end_date = split[1];

				// 取得天數
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = df.parse(start_date);
				Date endDate = df.parse(end_date);
				long from = startDate.getTime();
				long to = endDate.getTime();
				int days = (int) ((to - from) / (1000 * 60 * 60 * 24));

				// 再確認一次是否有空房

				/*** 值存入session(跳出結帳畫面時還能抓的到值)，同時存入req(結帳頁用，存session會被汙染)，準備轉交 ***/
				/*** 未來要像購物車一樣存list ***/
				HttpSession session = req.getSession();
				session.setAttribute("rangedate", rangedate);
				session.setAttribute("start_date", start_date);
				session.setAttribute("end_date", end_date);
				session.setAttribute("qty", qty);

				req.setAttribute("type_no", type_no);
				req.setAttribute("rangedate", rangedate);
				req.setAttribute("start_date", start_date);
				req.setAttribute("end_date", end_date);
				req.setAttribute("qty", qty);
				req.setAttribute("days", days); // 只有結帳頁會用到

				String url = "/front_end/room/payment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交前台的結帳頁
				successView.forward(req, res);
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/room/roomList.jsp");
				failureView.forward(req, res);
			}
		}

		// 產生訂單
		if ("paying".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*** 1.接收請求參數 - 輸入格式的錯誤處理 ***/
				Integer mem_no = new Integer(req.getParameter("mem_no"));
				Integer type_no = new Integer(req.getParameter("type_no"));
				String start_date = req.getParameter("start_date");
				String end_date = req.getParameter("end_date");
				Integer days = new Integer(req.getParameter("days"));
				Integer qty = new Integer(req.getParameter("qty"));

				System.out.print(type_no);
				String title = req.getParameter("title");

				String name = req.getParameter("name");
				if (name == null || name.trim().length() == 0 || name.trim().length() > 10) {
					errorMsgs.add("姓名欄位錯誤，請重新檢查");
				}

				String phone = req.getParameter("phone");
				if (phone == null || phone.trim().length() != 10) {
					errorMsgs.add("電話欄位錯誤，請重新檢查");
				}

				String email = req.getParameter("email");
				
				// 備註欄串接
				String note1 = req.getParameter("note1");
				if (note1 != null) {
					note1 = note1 + ",";
				} else
					note1 = "";
				String note2 = req.getParameter("note2");
				if (note2 != null) {
					note2 = note2 + ",";
				} else
					note2 = "";
				String notearea = req.getParameter("notearea");
				String note = note1 + note2 + notearea;

				// 信用卡判斷+串接
				String creditcard = req.getParameter("creditcard");
				String card_no1 = req.getParameter("card_no1");
				String card_no2 = req.getParameter("card_no2");
				String card_no3 = req.getParameter("card_no3");
				String card_no4 = req.getParameter("card_no4");
				creditcard = card_no1 + card_no2 + card_no3 + card_no4;

				// 找房價，總金額
				RoomTypeService roomTypeSvc = new RoomTypeService();
				Integer price = roomTypeSvc.getOneRoomType(type_no).getType_price();
				Integer total_price = price * qty * days;

				// 確認預約表的空房是否足夠，不夠(沒有查到符合的資料)就回到房型列表
				if (roomTypeSvc
						.paymentCheck(java.sql.Date.valueOf(start_date), java.sql.Date.valueOf(end_date), qty, type_no)
						.size() == 0) {
					req.setAttribute("notEnough", 1);
					req.getRequestDispatcher("/front_end/index/index.jsp").forward(req, res);
					return;
				} else {

					/*** 2.開始新增訂單 ***/
					// 訂單明細VO
					List<RoomOrderDetailVO> list = new ArrayList<RoomOrderDetailVO>();
					RoomOrderDetailService detailSvc = new RoomOrderDetailService();
					RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
					for (int i = 0; i < qty; i++) {
						list.add(detailVO);
					}
					// 新增訂單明細，回傳ord_no
					RoomOrderService roomorderSvc = new RoomOrderService();
					Integer ord_no = roomorderSvc.insertAuto(mem_no, type_no, java.sql.Date.valueOf(start_date),
							java.sql.Date.valueOf(end_date), qty, price, total_price, note, title, name, phone, email,
							creditcard, list);

					// 預約表上的預約間數增加
					RoomRsvService roomrsvSvc = new RoomRsvService();
					roomrsvSvc.reserveRoomRsv(qty, type_no, java.sql.Date.valueOf(start_date),
							java.sql.Date.valueOf(end_date));

					// 寄送mail
					SendMail mail = new SendMail();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:m:s");
					String messageText = name + " " + title + "，您好：\n以下是您的訂單資料\n\n" + "📑 訂單編號：  " + ord_no
							+ "\n\n📆 入住期間：  " + start_date + " ➜ " + end_date
							+ "\n\n此郵件為自動生成，請勿回覆此郵件。如有任何疑問，可使用客服即時通與我們聯繫。";
					mail.sendMail(email, "🌴 Feliz Hotel  您的住宿預訂已確認💳", messageText);

					// 新增完訂單後記得把session的值清空
					HttpSession session = req.getSession();
					session.removeAttribute("rangedate");
					session.removeAttribute("start_date");
					session.removeAttribute("end_date");
					session.removeAttribute("qty");
					session.removeAttribute("guest");
					session.removeAttribute("ableList");
					session.removeAttribute("notList");

					/*** 3.查詢完成,準備轉交 ***/
					req.setAttribute("days", days);
					req.setAttribute("ord_no", ord_no);
					String url = "/front_end/room/confirmation.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交前台的結帳完成頁
					successView.forward(req, res);
				}
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/room/roomList.jsp");
				failureView.forward(req, res);
			}
		}

		if ("checkOut".equals(action)) {
			try {
				Integer detail_no = new Integer(req.getParameter("detail_no"));
				String rm_no = req.getParameter("rm_no");

				RoomOrderDetailService detailSvc = new RoomOrderDetailService();
				detailSvc.checkoutDetail(detail_no);

				RoomService roomSvc = new RoomService();
				roomSvc.checkoutRoom(rm_no);

				String url = "/back_end/roomWork/todayWorkList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomOrder/listAllOrder.jsp");
				failureView.forward(req, res);
			}
		}

		if ("checkOutEarly".equals(action)) {
			try {
				Integer detail_no = new Integer(req.getParameter("detail_no"));
				String rm_no = req.getParameter("rm_no");
				Integer type_no = new Integer(req.getParameter("type_no"));
				String end_date = req.getParameter("end_date");

				RoomOrderDetailService detailSvc = new RoomOrderDetailService();
				detailSvc.checkoutDetail(detail_no);

				RoomService roomSvc = new RoomService();
				roomSvc.checkoutRoom(rm_no);

				RoomRsvService roomRsvSvc = new RoomRsvService();
				roomRsvSvc.checkOutEarly(type_no, java.sql.Date.valueOf(end_date));

				String url = "/back_end/roomWork/todayWorkList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomOrder/listAllOrder.jsp");
				failureView.forward(req, res);
			}
		}

		if ("checkIn".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*** 1.接收請求參數 - 輸入格式的錯誤處理 ***/
				Integer ord_no = new Integer(req.getParameter("ord_no"));
				String names[] = req.getParameterValues("names[]");
				String rooms[] = req.getParameterValues("rooms[]");

				for (int i = 0; i < names.length; i++) {
					System.out.println("name== " + names[i] + ",");
				}
				for (int i = 0; i < rooms.length; i++) {
					System.out.println("room== " + rooms[i] + ",");
				}

				/*** 2.訂單改已完成、訂單明細填上房號、房間填上入住人姓名 ***/
				// 訂單
				RoomOrderService orderSvc = new RoomOrderService();
				orderSvc.updateRoomOrder(ord_no);

				// 訂單明細
				RoomOrderDetailService detailSvc = new RoomOrderDetailService();
				List<RoomOrderDetailVO> list = detailSvc.getAllByOrdno(ord_no);

				for (int i = 0; i < list.size(); i++) {
					detailSvc.checkinDetail(list.get(i).getDetail_no(), rooms[i]);
				}

				// 房間
				RoomService roomSvc = new RoomService();

				for (int i = 0; i < rooms.length; i++) {
					roomSvc.checkinRoom(rooms[i], names[i]);
				}

				/*** 3.查詢更新準備轉交 ***/
				String url = "/back_end/roomWork/todayWorkList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomOrder/listAllOrder.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
