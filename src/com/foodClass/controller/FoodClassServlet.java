package com.foodClass.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodClass.model.*;

public class FoodClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodClassServlet() {
        super();
    }
	protected void doGet(HttpServletRequest re, HttpServletResponse res) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("Update_One".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Integer fd_class_no = new Integer(req.getParameter("fd_class_no"));
			FoodClassService ser = new FoodClassService();
			FoodClassVO foodClassVO = ser.getClassPK(fd_class_no);
			req.setAttribute("foodClassVO", foodClassVO);
			
			String url = "back_end/foodClass/updateClass.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
		
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			Integer fd_class_no = new Integer(req.getParameter("fd_class_no"));
			String fd_class_name = req.getParameter("fd_class_name");
			if(fd_class_name == null || fd_class_name.trim().length() == 0) {
				errorMsgs.add("類別名稱不得為空");
			}
			Boolean fd_class_state = new Boolean(req.getParameter("fd_class_state"));
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodClass/allClass.jsp");
				failureView.forward(req, res);
				return;
			}
			FoodClassService ser = new FoodClassService();
			FoodClassVO foodClassVO = ser.updateFoodClass(fd_class_name, fd_class_state, fd_class_no);
			
			req.setAttribute("foodClassVO", foodClassVO); // 資料庫update成功後,正確的的vo物件,存入req
			String url = "/back_end/foodClass/allClass.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交allClass.jsp
			successView.forward(req, res);
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/foodClass/allClass.jsp");
			failureView.forward(req, res);
		}
		}
		
		
		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			String fd_class_name = req.getParameter("fd_class_name");
			if(fd_class_name == null || fd_class_name.trim().length() == 0) {
				errorMsgs.add("類別名稱不得為空");
			}
			Boolean fd_class_state = new Boolean(req.getParameter("fd_class_state"));
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodClass/allClass.jsp");
				failureView.forward(req, res);
				return;
			}
			FoodClassService ser = new FoodClassService();
			FoodClassVO foodClassVO = ser.addFoodClass(fd_class_name, fd_class_state);
			
			req.setAttribute("foodClassVO", foodClassVO); // 資料庫update成功後,正確的的vo物件,存入req
			String url = "/back_end/foodClass/allClass.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交allClass.jsp
			successView.forward(req, res);
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/foodClass/allClass.jsp");
			failureView.forward(req, res);
		}
			
		}
	}

}
