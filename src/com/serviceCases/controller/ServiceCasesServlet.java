package com.serviceCases.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.serviceCases.model.*;

public class ServiceCasesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ServiceCasesServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		//新增案件
		if ("insert".equals(action)) { // 來自addCase.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer mem_no = new Integer(req.getParameter("mem_no").trim());
				String case_title = req.getParameter("case_title");
				String case_des = req.getParameter("case_des");
				ServiceCasesVO serviceCasesVO = new ServiceCasesVO();
				String case_reply = null;
				Integer case_state = 1;
				
				if (case_title == null || case_title.length() == 0) {
					errorMsgs.add("案件標題請勿空白");
					
				} else if(case_title.trim().length() > 20) { 
					errorMsgs.add("案件標題要20個字以內");
	            } 

				if (case_des == null || case_des.trim().length() == 0) {
					errorMsgs.add("案件描述請勿空白");
					
				} else if (case_des.trim().length() > 200) {
					errorMsgs.add("案件描述要200個字以內");
				}
		
				serviceCasesVO.setMem_no(mem_no);
				serviceCasesVO.setCase_title(case_title);
				serviceCasesVO.setCase_des(case_des);
				serviceCasesVO.getCase_reply();
				serviceCasesVO.setCase_state(case_state);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("serviceCasesVO", serviceCasesVO); // 含有輸入格式錯誤的serviceCasesVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/serviceCases/addCase.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ServiceCasesService scSvc = new ServiceCasesService();
				serviceCasesVO = scSvc.addServiceCases(mem_no, case_title, case_des, case_reply,case_state);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/serviceCases/listAllCase.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllCase.jsp
				successView.forward(req, res);
				
				

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/serviceCases/addCase.jsp");
				failureView.forward(req, res);
				
			}
		}
		
		if ("userinsert".equals(action)) { // 來自addCase.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				Integer mem_no = new Integer(req.getParameter("mem_no").trim());
				
				String case_title = req.getParameter("case_title");
				
				
				String case_des = req.getParameter("case_des");
//				String case_desReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{10,200}$";
				if (case_title == null || case_title.length() == 0) {
					errorMsgs.add("案件標題請勿空白");
					
				} else if(case_title.trim().length() > 20) { 
					errorMsgs.add("案件標題要20個字以內");
	            } 

				if (case_des == null || case_des.trim().length() == 0) {
					errorMsgs.add("案件描述請勿空白");
					
				} else if (case_des.trim().length() > 200) {
					errorMsgs.add("案件描述要200個字以內");
				}

				String case_reply = null;
				Integer case_state = 1;

				ServiceCasesVO serviceCasesVO = new ServiceCasesVO();
				serviceCasesVO.setMem_no(mem_no);
				serviceCasesVO.setCase_title(case_title);
				serviceCasesVO.setCase_des(case_des);
				serviceCasesVO.getCase_reply();
				serviceCasesVO.setCase_state(case_state);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("serviceCasesVO", serviceCasesVO); // 含有輸入格式錯誤的serviceCasesVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/serviceCases/userAddCase.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ServiceCasesService scSvc = new ServiceCasesService();
				serviceCasesVO = scSvc.addServiceCases(mem_no, case_title, case_des, case_reply,case_state);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/member/memberHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/serviceCases/userAddCase.jsp");
				failureView.forward(req, res);
				
			}
		}
		
				//更新案件
				if ("update".equals(action)) { // 來自addCase.jsp的請求

					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					try {
						/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
						Integer case_no = new Integer(req.getParameter("case_no").trim());
						Integer mem_no = new Integer(req.getParameter("mem_no").trim());
						String case_title = req.getParameter("case_title").trim();
						String case_des = req.getParameter("case_des").trim();	
						String case_reply = req.getParameter("case_reply");

						if (case_reply == null || case_reply.trim().length() == 0) {
							errorMsgs.add("案件描述請勿空白");
							
						} else if (case_reply.trim().length() > 200) {
							errorMsgs.add("案件描述要200個字以內");
						}
						
						Integer case_state = new Integer(req.getParameter("case_state").trim());
						if (case_state == 1) {
							errorMsgs.add("請更改案件回覆狀態");
						}

						ServiceCasesVO serviceCasesVO = new ServiceCasesVO();
						serviceCasesVO.setCase_no(case_no);
						serviceCasesVO.setMem_no(mem_no);
						serviceCasesVO.setCase_title(case_title);
						serviceCasesVO.setCase_des(case_des);
						serviceCasesVO.setCase_reply(case_reply);
						serviceCasesVO.setCase_state(case_state);

						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("serviceCasesVO", serviceCasesVO); // 含有輸入格式錯誤的serviceCasesVO物件,也存入req
							RequestDispatcher failureView = req.getRequestDispatcher("/back_end/serviceCases/updateCase.jsp");
							failureView.forward(req, res);
							return;
						}

						/*************************** 2.開始修改資料 ***************************************/
						ServiceCasesService scSvc = new ServiceCasesService();
						serviceCasesVO = scSvc.updateServiceCases(case_no,mem_no,case_title,case_des,case_reply,case_state);

						/*************************** 3.修改完成,準備轉交(Send the Success view) ***********/
						req.setAttribute("serviceCasesVO", serviceCasesVO);
						String url = "/back_end/serviceCases/listOneCase.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listOneCase.jsp
						successView.forward(req, res);

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/back_end/serviceCases/updateCase.jsp");
						failureView.forward(req, res);
					}
				}
				
				if ("getOne_For_Update".equals(action)) { // 來自listOneCase.jsp的請求

					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					try {
						/***************************1.接收請求參數****************************************/
						Integer case_no = new Integer(req.getParameter("case_no"));
						
						/***************************2.開始查詢資料****************************************/
						ServiceCasesService scSvc = new ServiceCasesService();
						ServiceCasesVO scVO = scSvc.getOneServiceCases(case_no);
										
						/***************************3.查詢完成,準備轉交(Send the Success view)************/
						req.setAttribute("serviceCasesVO", scVO);         // 資料庫取出的serviceCasesVO物件,存入req
						String url = "/back_end/serviceCases/updateCase.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updateCase.jsp
						successView.forward(req, res);

						/***************************其他可能的錯誤處理**********************************/
					} catch (Exception e) {
						errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back_end/serviceCases/listAllCase.jsp");
						failureView.forward(req, res);
					}
				}
				
	}

}
