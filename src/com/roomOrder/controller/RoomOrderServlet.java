package com.roomOrder.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roomOrder.model.RoomOrderService;
import com.roomOrder.model.RoomOrderVO;

public class RoomOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getAll".equals(action)) { // 切換訂單狀態list
			try {
				/*************************** 開始查詢資料 ****************************************/
				RoomOrderService roomOrderSvc = new RoomOrderService();
				List<RoomOrderVO> ordList = roomOrderSvc.getAllRoomOrder();

				/*************************** 查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("ordList", ordList); // 資料庫取出的VO物件,存入req
				String url = "/back_end/roomOrder/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				req.getRequestDispatcher("/back_end/roomOrder/listAllOrder.jsp").forward(req, res);
			}
		}

		if ("getAllByOrdState".equals(action)) { // 切換訂單狀態list

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer ord_state = new Integer(req.getParameter("ord_state"));

				/*************************** 2.開始查詢資料 ****************************************/
				RoomOrderService roomOrderSvc = new RoomOrderService();
				List<RoomOrderVO> ordList = roomOrderSvc.getAllByOrdState(ord_state);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("ord_state", ord_state);
				req.setAttribute("ordList", ordList); // 資料庫取出的VO物件,存入req
				String url = "/back_end/roomOrder/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				req.getRequestDispatcher("/back_end/roomOrder/listAllOrder.jsp").forward(req, res);
			}
		}

		if ("getAllByType".equals(action)) { // 切換訂單房型分類list

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer type_no = new Integer(req.getParameter("type_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				RoomOrderService roomOrderSvc = new RoomOrderService();
				List<RoomOrderVO> ordList = roomOrderSvc.getAllByType(type_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("type_no", type_no);
				req.setAttribute("ordList", ordList); // 資料庫取出的VO物件,存入req
				String url = "/back_end/roomOrder/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				req.getRequestDispatcher("/back_end/roomOrder/listAllOrder.jsp").forward(req, res);
			}
		}
	}
}
