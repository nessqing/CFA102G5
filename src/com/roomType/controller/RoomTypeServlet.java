package com.roomType.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.roomImg.model.RoomImgService;
import com.roomImg.model.RoomImgVO;
import com.roomRsv.model.RoomRsvService;
import com.roomRsv.model.RoomRsvVO;
import com.roomType.model.RoomTypeService;
import com.roomType.model.RoomTypeVO;

public class RoomTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String type_name = req.getParameter("type_name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
				if (type_name == null || type_name.trim().length() == 0) {
					errorMsgs.add("房型名稱 請勿空白");
				} else if (!type_name.trim().matches(nameReg)) {
					errorMsgs.add("房型名稱 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
				}

				Integer type_qty = null;
				try {
					type_qty = new Integer(req.getParameter("type_qty").trim());
				} catch (NumberFormatException e) {
					type_qty = 2;
					errorMsgs.add("容納人數請填數字");
				}

				Integer type_price = null;
				try {
					type_price = new Integer(req.getParameter("type_price").trim());
				} catch (NumberFormatException e) {
					type_price = 2000;
					errorMsgs.add("金額請填數字");
				}

				Integer type_size = null;
				try {
					type_size = new Integer(req.getParameter("type_size").trim());
				} catch (NumberFormatException e) {
					type_size = 10;
					errorMsgs.add("房型大小請填數字");
				}

				String bed_size = req.getParameter("bed_size");
				if (bed_size == null || bed_size.trim().length() == 0) {
					errorMsgs.add("床型 請勿空白");
				} else if (bed_size.trim().length() > 20) {
					errorMsgs.add("床型 長度必需在20字以內");
				}

				String type_info = req.getParameter("type_info");
				if (type_info == null || type_info.trim().length() == 0) {
					errorMsgs.add("房型資訊 請勿空白");
				}

				String type_facility = req.getParameter("type_facility");
				if (type_facility.trim().length() > 200) {
					errorMsgs.add("房型設施 長度必需在200字以內");
				}

				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setType_name(type_name);
				roomTypeVO.setType_qty(type_qty);
				roomTypeVO.setType_price(type_price);
				roomTypeVO.setType_size(type_size);
				roomTypeVO.setBed_size(bed_size);
				roomTypeVO.setType_info(type_info);
				roomTypeVO.setType_facility(type_facility);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomTypeVO", roomTypeVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomType/addRoomType.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				RoomTypeService roomTypeSvc = new RoomTypeService();
				roomTypeVO = roomTypeSvc.addRoomType(type_name, type_qty, type_price, type_size, bed_size, type_info,
						type_facility);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/roomType/listAllRoomType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAll頁面
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomType/addRoomType.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneForShow".equals(action)) { // 前台房型詳情roomDetail.jsp
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer type_no = new Integer(req.getParameter("type_no"));
				System.out.print("==============" + type_no);
				HttpSession session = req.getSession();
				Integer qty = (Integer) session.getAttribute("qty");
				if (qty == null) {
					qty = 1;
				}

				/*************************** 2.開始查詢資料 ****************************************/
				// 房型資料
				RoomTypeService roomTypeSvc = new RoomTypeService();
				RoomTypeVO roomTypeVO = roomTypeSvc.getOneRoomType(type_no);
				// 如果該房型已被下架，就回到房型列表
				if (roomTypeVO.getType_state() == false) {
					req.getRequestDispatcher("/front_end/room/roomList.jsp").forward(req, res);
					return;
				}

				// 房型資料的設施字串切割存成list
				List<String> facilityList = new LinkedList<String>();
				String data = roomTypeVO.getType_facility();
				String[] split = data.split(",");
				for (int i = 0; i < split.length; i++) {
					facilityList.add(split[i]);
				}

				// 房型圖片list
				RoomImgService roomImgSvc = new RoomImgService();
				List<RoomImgVO> images = roomImgSvc.getAllByType(type_no);

				// 不可預訂的日期
				RoomRsvService RoomRsvSvc = new RoomRsvService();
				List<RoomRsvVO> list = RoomRsvSvc.getNotRsv(qty, type_no);

				// 把list裡的日期變成字串
				String result = "";
				for (int i = 0; i < list.size(); i++) {
					result += "\"" + list.get(i).getRsv_date().toString() + "\",";
				}

				/*************************** 3.查詢完成,準備轉交 ************/
				req.setAttribute("roomTypeVO", roomTypeVO); // 資料庫取出的VO物件,存入req
				req.setAttribute("facilityList", facilityList); // 分割完的設施list,存入req
				req.setAttribute("images", images); // 資料庫取出的VO物件,存入req
				req.setAttribute("result", result); // 不可預訂的日期
				req.setAttribute("qty", qty); // 間數，沒搜尋過session就沒有存，帶入1傳回前台，不存session是怕被汙染，這些參數都要互相符合
				String url = "/front_end/room/roomDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交前台的roomDetail.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				// 若錯誤 導回房型列表
				req.getRequestDispatcher("/front_end/room/roomList.jsp").forward(req, res);
			}
		}

		if ("getOneForUpdate".equals(action)) { // 來自listAll的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer type_no = new Integer(req.getParameter("type_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				// 房型資料
				RoomTypeService roomTypeSvc = new RoomTypeService();
				RoomTypeVO roomTypeVO = roomTypeSvc.getOneRoomType(type_no);

				// 房型資料的設施字串切割存成list
				List<String> facilityList = new LinkedList<String>();
				String data = roomTypeVO.getType_facility();
				String[] split = data.split(",");
				for (int i = 0; i < split.length; i++) {
					facilityList.add(split[i]);
				}

				// 房型圖片list
				RoomImgService roomImgSvc = new RoomImgService();
				List<RoomImgVO> images = roomImgSvc.getAllByType(type_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("roomTypeVO", roomTypeVO); // 資料庫取出的VO物件,存入req
				req.setAttribute("facilityList", facilityList); // 分割完的設施list,存入req
				req.setAttribute("images", images); // 資料庫取出的VO物件,存入req

				String url = (action.equals("getOneForUpdate")) ? "/back_end/roomType/updateRoomType.jsp"
						: "/front_end/room/roomDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交後台update_emp_input.jsp或前台的roomDetail.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("失敗");
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer type_no = new Integer(req.getParameter("type_no").trim());

				String type_name = req.getParameter("type_name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
				if (type_name == null || type_name.trim().length() == 0) {
					errorMsgs.add("房型名稱 請勿空白");
				} else if (!type_name.trim().matches(nameReg)) {
					errorMsgs.add("房型名稱 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
				}

				Integer type_qty = null;
				try {
					type_qty = new Integer(req.getParameter("type_qty").trim());
				} catch (NumberFormatException e) {
					type_qty = 2;
					errorMsgs.add("容納人數請填數字");
				}

				Integer type_price = null;
				try {
					type_price = new Integer(req.getParameter("type_price").trim());
				} catch (NumberFormatException e) {
					type_price = 2000;
					errorMsgs.add("金額請填數字");
				}

				Integer type_size = null;
				try {
					type_size = new Integer(req.getParameter("type_size").trim());
				} catch (NumberFormatException e) {
					type_size = 10;
					errorMsgs.add("房型大小請填數字");
				}

				String bed_size = req.getParameter("bed_size");
				if (bed_size == null || bed_size.trim().length() == 0) {
					errorMsgs.add("床型 請勿空白");
				} else if (bed_size.trim().length() > 20) {
					errorMsgs.add("床型 長度必需在20字以內");
				}

				String type_info = req.getParameter("type_info");
				if (type_info == null || type_info.trim().length() == 0) {
					errorMsgs.add("房型資訊 請勿空白");
				}

				String type_facility = req.getParameter("type_facility");
				if (type_facility.trim().length() > 200) {
					errorMsgs.add("房型設施 長度必需在200字以內");
				}

				Boolean type_state = new Boolean(req.getParameter("type_state"));

				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setType_no(type_no);
				roomTypeVO.setType_name(type_name);
				roomTypeVO.setType_qty(type_qty);
				roomTypeVO.setType_price(type_price);
				roomTypeVO.setType_size(type_size);
				roomTypeVO.setBed_size(bed_size);
				roomTypeVO.setType_info(type_info);
				roomTypeVO.setType_facility(type_facility);
				roomTypeVO.setType_state(type_state);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomTypeVO", roomTypeVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomType/updateRoomType.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 ***************************************/
				RoomTypeService roomTypeSvc = new RoomTypeService();
				roomTypeVO = roomTypeSvc.updateRoomType(type_no, type_name, type_qty, type_price, type_size, bed_size,
						type_info, type_facility, type_state);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/roomType/listAllRoomType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAll頁面
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/roomType/addRoomType.jsp");
				failureView.forward(req, res);
			}
		}

		if ("change_state".equals(action)) {

			Integer type_no = new Integer(req.getParameter("type_no").trim());
			Boolean type_state = new Boolean(req.getParameter("type_state"));
			type_state = (type_state == true ? false : true);

			RoomTypeService roomTypeSvc = new RoomTypeService();
			roomTypeSvc.changeState(type_no, type_state);

			String url = "/back_end/roomType/listAllRoomType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAll頁面
			successView.forward(req, res);
		}

	}
}
