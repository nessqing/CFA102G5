package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.member.model.MemberService;
import com.member.model.MemberClassVO;



public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req,res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		List<String> errorMsgs = new LinkedList<>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		HttpSession session = req.getSession();
		
		String mem_mail = req.getParameter("mem_mail");
		String mem_password= req.getParameter("mem_password");
		
		MemberService memSvc = new MemberService();
		MemberClassVO memVO = memSvc.isUser(mem_mail,mem_password);
		
		
		if(memVO == null) {
			errorMsgs.add("帳號密碼錯誤");
			if(!errorMsgs.isEmpty()){ 			
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/signin/signin.jsp");
				failureView.forward(req, res);
			   }
		}else{

					session.setAttribute("mem_mail", mem_mail);//登入標記
					try {
						
						String location = (String)session.getAttribute("location");//回來源網頁
						System.out.println(location);
						if(location != null) {
							//session.removeAttribute("location");
							res.sendRedirect(location);
							return;//重導
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				
					res.sendRedirect(req.getContextPath()+"/front_end/member/memberHome.jsp");
		}
		
	}

}
