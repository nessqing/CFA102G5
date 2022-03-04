package com.room.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.room.model.RoomService;
import com.room.model.RoomVO;

public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getAll".equals(action)) { // 切換房間狀態list

			/*************************** 開始查詢資料 ****************************************/
			RoomService roomSvc = new RoomService();
			List<RoomVO> list = roomSvc.getAllRoom();

			/*************************** 查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("list", list); // 資料庫取出的VO物件,存入req
			String url = "/back_end/room/listAllRoom.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("getAllByRmState".equals(action)) { // 切換房間狀態list

			/*************************** 1.接收請求參數 ****************************************/
			Integer rm_state = new Integer(req.getParameter("rm_state"));

			/*************************** 2.開始查詢資料 ****************************************/
			RoomService roomSvc = new RoomService();
			List<RoomVO> list = roomSvc.getAllByRmState(rm_state);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("rm_state", rm_state);
			req.setAttribute("list", list); // 資料庫取出的VO物件,存入req
			String url = "/back_end/room/listAllRoom.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				RoomService roomSvc = new RoomService();

				String rm_no = req.getParameter("rm_no");

				String rmReg = "^[(0-9)]{3}$";
				if (roomSvc.getOneRoom(rm_no) != null) {
					errorMsgs.add("已存在相同的房間編號，請用修改功能");
					RoomVO roomVO = roomSvc.getOneRoom(rm_no);
					req.setAttribute("roomVO", roomVO); // 撈出該房間VO,存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/room/updateRoom.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				} else if (rm_no == null || rm_no.trim().length() == 0) {
					errorMsgs.add("房間編號 請勿空白");
				} else if (!rm_no.trim().matches(rmReg)) {
					errorMsgs.add("房型編號 只能是數字 , 且長度必需是3個字");
				}

				Integer type_no = new Integer(req.getParameter("type_no"));

				String rm_info = req.getParameter("rm_info");
				if (rm_info == null || rm_info.trim().length() == 0) {
					errorMsgs.add("房間介紹 請勿空白");
				} else if (rm_info.trim().length() > 20) {
					errorMsgs.add("房間介紹長度必需在1到20之間");
				}

				RoomVO roomVO = new RoomVO();
				roomVO.setRm_no(rm_no);
				roomVO.setType_no(type_no);
				roomVO.setRm_info(rm_info);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomVO", roomVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/room/addRoom.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 ****************************************/
				roomVO = roomSvc.addRoom(rm_no, type_no, rm_info);

				/*************************** 3.修改完成,準備轉交(Send the Success view) ************/
				req.setAttribute("roomVO", roomVO); // 資料庫取出的VO物件,存入req
				String url = "/back_end/room/listAllRoom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("修改房間資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/room/addRoom.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneForUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String rm_no = req.getParameter("rm_no");

				/*************************** 2.開始查詢資料 ****************************************/
				RoomService roomSvc = new RoomService();
				RoomVO roomVO = roomSvc.getOneRoom(rm_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("roomVO", roomVO); // 資料庫取出的VO物件,存入req
				String url = "/back_end/room/updateRoom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("連到房間詳情失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/room/listAllRoom.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String rm_no = req.getParameter("rm_no");

				Integer type_no = new Integer(req.getParameter("type_no"));

				String rm_info = req.getParameter("rm_info");
				if (rm_info == null || rm_info.trim().length() == 0) {
					errorMsgs.add("房間介紹 請勿空白");
				} else if (rm_info.trim().length() > 20) {
					errorMsgs.add("房間介紹長度必需在1到20之間");
				}

				Integer rm_state = new Integer(req.getParameter("rm_state"));

				String name_title = req.getParameter("name_title");
				if (name_title.trim().length() > 10) {
					errorMsgs.add("入住人姓名長度必需在10字以內");
				}

				RoomVO roomVO = new RoomVO();
				roomVO.setRm_no(rm_no);
				roomVO.setType_no(type_no);
				roomVO.setRm_info(rm_info);
				roomVO.setRm_state(rm_state);
				roomVO.setName_title(name_title);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomVO", roomVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/room/updateRoom.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 ****************************************/
				RoomService roomSvc = new RoomService();
				roomVO = roomSvc.updateRoom(rm_no, type_no, rm_info, rm_state, name_title);

				/*************************** 3.修改完成,準備轉交(Send the Success view) ************/
				req.setAttribute("roomVO", roomVO); // 資料庫取出的VO物件,存入req
				String url = "/back_end/room/listAllRoom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("修改房間資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/room/updateRoom.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
