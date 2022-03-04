package com.member.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.activityOrderDetail.model.ActivityOrderDetailService;
import com.activitySession.model.ActivitySessionService;
import com.google.gson.Gson;
import com.member.model.*;
import com.roomOrder.model.RoomOrderService;
import com.roomOrder.model.RoomOrderVO;
import com.roomRsv.model.RoomRsvService;
import com.roomRsv.model.RoomRsvVO;

import mail.MailService;

@MultipartConfig

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberServlet() {
        super();
    }
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
        if ("addMember".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			InputStream is = null;
			byte[] mem_img = null;
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,50}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("姓名: 只能是中、英文字母, 且長度必需在2到10之間");
	            }
				String[] strRadio =  req.getParameterValues("mem_sex");
				if(strRadio != null) {
					for(int i = 0; i<strRadio.length; i++) {	
					}
				}
				Integer mem_sex =  Integer.parseInt(strRadio[0]);
				
				String mem_mail = req.getParameter("mem_mail");
//				if (mem_mail == null || mem_mail.trim().length() == 0) {
//					errorMsgs.add("請填入信箱");
//				}
//				byte[] Preset = getPictureByteArray("C:\\CFA102G5_workspace\\CFA102G5\\WebContent\\front_end\\member\\images\\peter1.jpg");
//				Part part = req.getPart("mem_img");
//				if(part.getSize() != 0) {
//					is = part.getInputStream();
//					mem_img = new byte[is.available()];
//					is.read(mem_img);
//				}else {
//					mem_img = Preset;
//				}
				String mem_password = req.getParameter("mem_password");
//				String mem_passwordReg = "^[(a-zA-Z0-9)]{6,20}$";
//				if (mem_password == null || mem_password.trim().length() == 0) {
//					errorMsgs.add("請填入密碼");
//				}else if(!mem_password.trim().matches(mem_passwordReg)) { 
//					errorMsgs.add("英文字母、數字, 且長度必需在6到20之間");
//	            }
				
				String mem_mobile = req.getParameter("mem_mobile");
//				if (mem_mobile == null || mem_mobile.trim().length() < 10 ) {
//					errorMsgs.add("請填入正確電話");
//				}
				
				String mem_add = req.getParameter("mem_add");
//				if (mem_add == null || mem_add.trim().length() == 0) {
//					errorMsgs.add("請填入地址");
//				}
				MemberClassVO memVO = new MemberClassVO();	
				memVO.setMem_name(mem_name);
				memVO.setMem_sex(mem_sex);
				memVO.setMem_mail(mem_mail);
				memVO.setMem_password(mem_password);
				memVO.setMem_mobile(mem_mobile);
				memVO.setMem_img(mem_img);
				memVO.setMem_add(mem_add);
				//mail 重複驗證
				MemberService memSvc = new MemberService();
				List<MemberClassVO> listall = memSvc.getAll();
				for (MemberClassVO memVOList : listall) {
					if (memVOList.getMem_mail().equals(mem_mail)) {
						errorMsgs.add("信箱已被註冊，請重新輸入");
					}
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/signin/signup.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始新增資料***************************************/
				MemberService memberSvc = new MemberService();
				memVO = memberSvc.addMember(mem_name, mem_sex, mem_mail, mem_password,mem_mobile,mem_img,mem_add,false);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				HttpSession session  = req.getSession();
				
				Integer checkMem = memberSvc.getOneBymail(mem_mail).getMem_no();
				MailService mail = new MailService();
				String authCode = mail.getRandom();
				String subject = "會員驗證碼";
				String message = "感謝您註冊本網站會員，請輸入以下驗證碼完成註冊:" + authCode;
				
				
				try {
					mail.sendMail(mem_mail, subject, message);
					session.setAttribute("checkMem", checkMem);
		            session.setAttribute("authCode", authCode);
		            res.sendRedirect(req.getContextPath() + "/front_end/signin/checkMember.jsp");
				}catch(Exception e) {
					e.printStackTrace();
				}
				
//				req.setAttribute("memVO", memVO);
//				String url = "/front_end/member/memberHome.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/signin/signup.jsp");
				failureView.forward(req, res);
			}
		}
        if("getOneForUpdate".equals(action)) {
           	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
        	try {
        		Integer mem_no = new Integer(req.getParameter("mem_no"));
        		
        		MemberService  memSvc = new MemberService();
        		MemberClassVO memVO = memSvc.getOne(mem_no);
        		
        		req.setAttribute("memVO", memVO);
        		String url = "/front_end/member/updateMember.jsp";
        		RequestDispatcher successView = req.getRequestDispatcher(url);
        		successView.forward(req, res);
       		
        	}catch(Exception e){
				errorMsgs.add("無法取得:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/member/listMember.jsp");
				failureView.forward(req, res);
        	}
        }
        if("updateMember".equals(action)) {
        	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer mem_no = new Integer(req.getParameter("mem_no"));
				
				String mem_name = req.getParameter("mem_name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if(!mem_name.trim().matches(nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("姓名: 只能是中、英文字母, 且長度必需在2到10之間");
	            }
				Integer mem_sex = new Integer(req.getParameter("mem_sex"));
				
				String mem_password = req.getParameter("mem_password");
				String mem_passwordReg = "^[(a-zA-Z0-9)]{2,10}$";
				if (mem_password == null || mem_password.trim().length() == 0) {
					errorMsgs.add("請填入密碼");
				}else if(!mem_password.trim().matches(mem_passwordReg)) { 
					errorMsgs.add("英文字母、數字, 且長度必需在2到10之間");
	            }
				String mem_mobile = req.getParameter("mem_mobile");
				if (mem_mobile == null || mem_mobile.trim().length() < 10  ) {
					errorMsgs.add("請填入正確電話");
				}
				String mem_add = req.getParameter("mem_add");
//				if (mem_add == null || mem_add.trim().length() == 0) {
//					errorMsgs.add("請填入地址");
//				}
				
				MemberClassVO memVO = new MemberClassVO();
				
				memVO.setMem_no(mem_no);
				memVO.setMem_name(mem_name);
				memVO.setMem_sex(mem_sex);
				memVO.setMem_password(mem_password);
				memVO.setMem_mobile(mem_mobile);
				memVO.setMem_add(mem_add);
			
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/member/updateMember.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MemberService memSvc = new MemberService();
				memVO = memSvc.updateMember(mem_name,mem_sex,mem_password,mem_mobile,null,mem_add,mem_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memVO", memVO); 
				String url = "/front_end/member/listone.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/member/updateMember.jsp");
				failureView.forward(req, res);
			}
			
        }
        if ("getAll".equals(action)) {
			/***************************開始查詢資料 ****************************************/
			MemberClassJDBCDAO dao = new MemberClassJDBCDAO();
			List<MemberClassVO> list = dao.getAll();

			/***************************查詢完成,準備轉交(Send the Success view)*************/
			HttpSession session = req.getSession();
			session.setAttribute("list", list);    // 資料庫取出的list物件,存入session
			// Send the Success view
			String url = "/front_end/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req,res);
			return;
		}
        if("getOneBymail".equals(action)) {
        	
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				//請求
				String format = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
				
				String mem_mail = req.getParameter("mem_mail");
				if(mem_mail == null || (mem_mail.trim()).length() == 0) {
					errorMsgs.add("輸入mail");
				}else if(!mem_mail.matches(format)){
					errorMsgs.add("輸入正確mail");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failview = req.getRequestDispatcher("/front_end/member/SelectPage.jsp");
					failview.forward(req, res);
					return;
				}
				//查詢
				MemberService memSvc = new MemberService();
				MemberClassVO memVO = memSvc.getOneBymail(mem_mail);
				
				//查詢完成轉交
				req.setAttribute("memVO", memVO);
				String url = "/front_end/member/listone.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
				
			}catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/SelectPage.jsp");
				failureView.forward(req, res);
			}
			

        }
        if("forgetPassword".equals(action)) {
        	
        	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
        	try {
        		//請求
        		String mem_mail = req.getParameter("mem_mail");
				MemberService memSvc = new MemberService();
				
				List<MemberClassVO> listall = memSvc.getAll();
				for (MemberClassVO memVOList : listall) {
					if (memVOList.getMem_mail() != mem_mail) {
						errorMsgs.add("信箱無註冊資料，請重新輸入");
					}break;
				}
				MemberClassVO memVO = memSvc.getOneBymail(mem_mail);
				MailService mail = new MailService();
				String authCode = mail.getRandom();
				
				memSvc.updatePassword(authCode, memVO.getMem_no());
				
				String subject = "臨時密碼";
				String message = "臨時密碼:" + authCode + "請登入後修改密碼";
			

				try {
					mail.sendMail(mem_mail, subject, message);
		            res.sendRedirect(req.getContextPath() + "/front_end/signin/signin.jsp");
				}catch(Exception e) {
					e.printStackTrace();
				}
        	}catch(Exception e) {
        		errorMsgs.add(e.getMessage());
        		RequestDispatcher failureView = req.getRequestDispatcher("/front_end/signin/forgetPassword.jsp");
        		failureView.forward(req, res);
        	}
        	
        	
        }
        if("updateMemberstate".equals(action)) {
        	List<String> errorMsgs = new LinkedList<String>();
        	req.setAttribute("errorMsgs", errorMsgs);
        	try {
        		Integer mem_no = new Integer(req.getParameter("mem_no"));
        		Boolean mem_state = new Boolean(req.getParameter("mem_state"));
        		
        		MemberClassVO memVO = new MemberClassVO();
        		MemberService memSvc = new MemberService();
        		
        		memVO = memSvc.updateMemberstate(mem_state, mem_no);
        		
        		req.setAttribute("memVO", memVO);
        		String url = "/back_end/member/selectMemberPage.jsp";
        		RequestDispatcher success = req.getRequestDispatcher(url);
        		success.forward(req, res);
        		
        	}catch(Exception e) {
        		
        	}
    	
        }
        if("log_out".equals(action)) {
        	HttpSession session = req.getSession();
        	session.invalidate();
        	//導回登入頁面-------------
        	String url = "/front_end/index/index.jsp";
        	RequestDispatcher successView = req.getRequestDispatcher(url);
        	successView.forward(req, res);
        }
        

		//取消活動
        
		ActivityOrderDetailService actOrderDetailService = new ActivityOrderDetailService();
		ActivitySessionService actSessionService = new ActivitySessionService();
        
		if("cancelState".equals(action)) {
			Integer act_order_detail_no = new Integer(req.getParameter("cancelStateNo"));
			Integer act_session_no = new Integer(req.getParameter("actSessionNo"));
			
			LocalDate now = LocalDate.now();
			LocalDate start_date = actSessionService.getActSessionByPk(act_session_no)
											  		.getAct_session_start_date();
		
			Gson gson = new Gson();
			
			Period period = Period.between(now, start_date);

			if(period.getMonths() <1 && period.getDays() >= 2) {
				System.out.println("更改成功");
				actOrderDetailService.switchOrderDetailState(act_order_detail_no, 2);
				res.getWriter().write(gson.toJson(true));				
			}else {	
				res.getWriter().write(gson.toJson(false));
			}

		}
		
		
		//取消訂房
		
		RoomRsvService RoomRsvService = new RoomRsvService();
		RoomOrderService RoomOrderService = new RoomOrderService();
		
		if("cancelroom".equals(action)) {
			try {
			Integer mem_no = new Integer(req.getParameter("mem_no"));
			Integer orderNO = new Integer(req.getParameter("ord_no"));
			RoomOrderService.cancel(orderNO);
			
			Integer qty = new Integer(req.getParameter("qty"));
			Integer type_no = new Integer(req.getParameter("type_no"));
			String start_date = req.getParameter("start_date");
			String end_date = req.getParameter("end_date");
			
			
			RoomRsvService.canceleRoomRsv(qty, type_no, java.sql.Date.valueOf(start_date), Date.valueOf(end_date));
			
	       	String url = "/front_end/member/memberOrder.jsp";
        	RequestDispatcher successView = req.getRequestDispatcher(url);
        	successView.forward(req, res);
			
        	return;
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
		}
 
	}
	public static InputStream getPictureStream(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		return fis;
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	

}
