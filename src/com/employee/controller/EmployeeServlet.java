package com.employee.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employee.model.EmpService;
import com.employee.model.EmployeeVO;

public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOneEmp".equals(action)) {
			Integer emp_no = new Integer(req.getParameter("emp_no"));
			EmpService ser = new EmpService();
			EmployeeVO empVO = ser.getOneEmp(emp_no);
			
			req.setAttribute("empVO", empVO);
			
			String url = "back_end/emp/personal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
		if("log_out".equals(action)) {
			HttpSession session = req.getSession();
			session.invalidate();
			//導回登入頁面-------------
			String url = "/back_end/login/login.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("Login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
				/*************************** 1.接收請求參數 **********************/
			String emp_mail = req.getParameter("emp_mail");
			String emp_password = req.getParameter("emp_password");
			/*************************** 2.開始查詢資料 *****************************************/
			EmpService ser = new EmpService();
			EmployeeVO empVO1 = ser.Login(emp_mail, emp_password);
			if(empVO1==null) {
				errorMsgs.add("查無資料或輸入錯誤");
			}else if(empVO1.getEmp_state()==false) {
				errorMsgs.add("您已離職無法登入。");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/login/login.jsp");	//查無資料返回登入
				failureView.forward(req, res);
				return;// 程式中斷
			}else {
				HttpSession session = req.getSession();
			    session.setAttribute("empVO1", empVO1);
			    try {
			    	 String location = (String) session.getAttribute("location2");
			         if (location != null) {
			           session.removeAttribute("location2");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
			           res.sendRedirect(location);            
			           return;}
			    }catch (Exception ignored) { }
			      res.sendRedirect(req.getContextPath()+"/back_end/roomWork/todayWorkList.jsp");
			}
	}
		
		if("Update_One".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Integer emp_no = new Integer(req.getParameter("emp_no"));
			
			EmpService ser = new EmpService();
			EmployeeVO empVO = ser.getOneEmp(emp_no);
			
			req.setAttribute("empVO", empVO);
			
			String url = "back_end/emp/updateEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		try {	
			Integer emp_no = new Integer(req.getParameter("emp_no"));
			String emp_password = req.getParameter("emp_password").trim();
			if(emp_password == null || emp_password.length() < 5) {
				errorMsgs.add("密碼: 請勿空白、小於字數6");
			}
			String emp_name = req.getParameter("emp_name").trim();
			if(emp_name == null || emp_name.length() == 0) {
				errorMsgs.add("員工姓名: 請勿空白");
			}
			String emp_mail = req.getParameter("emp_mail").trim();
			String e_emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
			if (emp_mail == null || emp_mail.trim().length() == 0) {
				errorMsgs.add("電子信箱: 請勿空白");
			} else if(!emp_mail.trim().matches(e_emailReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("電子信箱: 請依照電子郵件格式輸入");
            }
			Boolean emp_state = new Boolean(req.getParameter("emp_state"));
			Integer dep_no = new Integer(req.getParameter("dep_no"));
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
				return;
			}
			EmpService ser = new EmpService();
			EmployeeVO empVO = ser.updateEmp(emp_password, emp_name, emp_mail, emp_state, dep_no, emp_no);
			
			req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/back_end/emp/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/listAllEmp.jsp");
			failureView.forward(req, res);
		}
		}
		
		if("insert".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String emp_name = req.getParameter("emp_name").trim();
				String e_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,5}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!emp_name.trim().matches(e_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母, 且長度必需在2到5之間");
	            }
				String emp_mail = req.getParameter("emp_mail").trim();
				String e_emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (emp_mail == null || emp_mail.trim().length() == 0) {
					errorMsgs.add("電子信箱: 請勿空白");
				} else if(!emp_mail.trim().matches(e_emailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("電子信箱: 請依照電子郵件格式輸入");
	            }
				String emp_password = req.getParameter("emp_password").trim();
				if(emp_password == null || emp_password.length() < 5) {
					errorMsgs.add("密碼: 請勿空白、小於字數6");
				}
				Boolean emp_state = new Boolean(req.getParameter("emp_state"));
				Integer dep_no = new Integer(req.getParameter("dep_no"));
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始查詢資料*****************************************/
				EmpService ser = new EmpService();
				EmployeeVO empVO = ser.addEmp(emp_password, emp_name, emp_mail, emp_state, dep_no);
				
				req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back_end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
			}
		 catch (Exception e) {
			errorMsgs.add("新增資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/emp/listAllEmp.jsp");
			failureView.forward(req, res);
		}
	}
		
//		if("getDepInEmp".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			try {
//				Integer dep_no = new Integer (req.getParameter("dep_no"));
//				EmpService ser = new EmpService();
//				List<EmployeeVO> empVO = ser.getEmpInDep(dep_no);
//				if(empVO==null) {
//					errorMsgs.add("查無此部門員工");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back_end/emp/listAllEmp.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				req.setAttribute("empVO", empVO);
//				String url = "back_end/emp/getAllEmpInDep.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
//				successView.forward(req, res);
//				return;
//			}
//		catch (Exception e) {
//			errorMsgs.add("查詢資料失敗:"+e.getMessage());
//			RequestDispatcher failureView = req
//					.getRequestDispatcher("/back_end/emp/selectPage.jsp");
//			failureView.forward(req, res);
//		}
//		
//		}
		
	}
}