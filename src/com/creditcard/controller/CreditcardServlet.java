package com.creditcard.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.creditcard.model.*;
import com.member.model.MemberClassVO;
import com.member.model.MemberService;
import com.util.JDBCUtil;

@WebServlet("/CreditcardServlet")
public class CreditcardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreditcardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("addCard".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer mem_no = new Integer(req.getParameter("mem_no"));
				
				String crd_name = req.getParameter("crd_name");
				String crd_nameReg = "^[(a-zA-Z)]{3,30}$";
				if (crd_name == null || crd_name.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if(!(crd_name.trim().matches(crd_nameReg))) { 
					errorMsgs.add("姓名為信用卡英文名稱");
	            }
				String crd_num = req.getParameter("crd_num");
				if(crd_num == null || crd_num.replace(" ","").length() < 16 || crd_num.replace(" ","").length()>16) {
					errorMsgs.add("輸入正確卡號");
				}
				String crd_expiry = req.getParameter("crd_expiry");
				
				String crd_security_code = req.getParameter("crd_security_code");
				
				String crd_barcode = req.getParameter("crd_barcode");
				
				CreditcardClassVO crdVO = new CreditcardClassVO();	
				
				crdVO.setCrd_name(crd_name);
				crdVO.setCrd_num(crd_num);
				crdVO.setCrd_expiry(crd_expiry);
				crdVO.setCrd_security_code(crd_security_code);
				crdVO.setCrd_barcode(crd_barcode);

		if (!errorMsgs.isEmpty()) {
				req.setAttribute("crdVO", crdVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/creditcard/addCard.jsp");
				failureView.forward(req, res);
				return;
				}
				/***************************2.開始新增資料***************************************/
				CreditcardService crdSvc = new CreditcardService();
				crdVO = crdSvc.addCard(mem_no,crd_name,crd_num,crd_expiry,crd_security_code,crd_barcode);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				System.out.println(crdVO);
				
				req.setAttribute("list", crdSvc.getallByMem_no(mem_no));
				String url = "/front_end/creditcard/creditcard.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/creditcard/homePage.jsp");
				failureView.forward(req, res);
			}
			
		}
		if("getallByMem_no".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				//接收請求
				Integer mem_no = new Integer(req.getParameter("mem_no"));
				//查詢
				CreditcardService crdSvc = new CreditcardService();
				List<CreditcardClassVO> list = crdSvc.getallByMem_no(mem_no);
				//轉交
				HttpSession session = req.getSession();
				session.setAttribute("list", list);
				String url = "/front_end/creditcard/creditcard.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req,res);
	
			}catch(Exception e) {
				String url = "/front_end/creditcard/homePage.jsp";
				RequestDispatcher fail = req.getRequestDispatcher(url);
				fail.forward(req,res);
			}
		}
	  if("deleteCard".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				//接收參數
				Integer crd_no = new Integer(req.getParameter("crd_no"));
				Integer mem_no = new Integer(req.getParameter("mem_no"));
				//刪除資料
				CreditcardService crdSvc = new CreditcardService();
				crdSvc.deleteCard(crd_no);;
				//轉交
//				System.out.println(requestURL);
				if(requestURL.equals("/front_end/creditcard/creditcard.jsp")) {
					req.setAttribute("list",crdSvc.getallByMem_no(mem_no));
				}
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req,res);
				
			}catch(Exception e) {
				
			}
	  	}
	}
	

}
