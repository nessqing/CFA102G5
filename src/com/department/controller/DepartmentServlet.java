package com.department.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.department.model.*;

public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DepartmentServlet() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("Update_One".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Integer dep_no = new Integer(req.getParameter("dep_no"));
			DepService ser = new DepService();
			DepartmentVO DepVO = ser.getOnePK(dep_no);
			req.setAttribute("DepVO", DepVO);
			
			String url = "back_end/department/updateDep.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
		}
		
		if("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			Integer dep_no = new Integer(req.getParameter("dep_no"));
			String dep_name = req.getParameter("dep_name");
			if (dep_name == null || dep_name.trim().length() == 0) {
				errorMsgs.add("部門名稱: 請勿空白");	}
			Boolean dep_state = new Boolean(req.getParameter("dep_state"));

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/department/allDep.jsp");
				failureView.forward(req, res);
				return;
			}
			DepService ser = new DepService();
			DepartmentVO vo = ser.updateDep(dep_name, dep_state, dep_no);
			
			req.setAttribute("vo", vo); // 資料庫update成功後,正確的的vo物件,存入req
			String url = "/back_end/department/allDep.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/department/allDep.jsp");
			failureView.forward(req, res);
		}
		}
		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			String dep_name = req.getParameter("dep_name");
			if (dep_name == null || dep_name.trim().length() == 0) {
				errorMsgs.add("部門名稱: 請勿空白");	}
			Boolean dep_state = new Boolean(req.getParameter("dep_state"));

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/department/allDep.jsp");
				failureView.forward(req, res);
				return;
			}
			DepartmentVO vo = new DepartmentVO();
			DepService ser = new DepService();
			vo = ser.addDep(dep_name, dep_state);
			String url = "/back_end/department/allDep.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);				
			
			/***************************其他可能的錯誤處理**********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/department/allDep.jsp");
			failureView.forward(req, res);
		}
		}
	}

}
