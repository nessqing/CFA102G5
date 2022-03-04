package com.foodImg.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.foodImg.model.FoodImgService;
import com.foodImg.model.FoodImgVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class FoodImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		res.setContentType("image/jpeg");
		Integer fd_no = new Integer(req.getParameter("fd_no").trim());
		FoodImgService vic = new FoodImgService();
		
		ServletOutputStream out = res.getOutputStream();
	    out.write(vic.findImgOne(fd_no).getFd_img());
	    out.close();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getStoreImg".equals(action)) {	// 來自select_page.jsp的請求

			Integer fd_no = new Integer(req.getParameter("fd_no").trim());
			/*************************** 2.開始查詢資料 *****************************************/
			FoodImgService imgSvc = new FoodImgService();
			List<FoodImgVO> imgVO = imgSvc.foodImg(fd_no);
			
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("imgVO", imgVO);
			String url = "/front_end/storeMap/storeImg.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
			successView.forward(req, res);
			return;
			/*************************** 其他可能的錯誤處理 *************************************/
			
		}
		
		if("getOneFoodStoreImg".equals(action)) {	// 來自select_page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			Integer fd_no = new Integer(req.getParameter("fd_no").trim());
			/*************************** 2.開始查詢資料 *****************************************/
			FoodImgService imgSvc = new FoodImgService();
			List<FoodImgVO> imgVO = imgSvc.foodImg(fd_no);
			
			if(imgVO.size()==0) {
				errorMsgs.add("此店家無照片請先新增照片。");
				req.setAttribute("fd_no", fd_no);
				String url = "/back_end/foodImg/addImg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
				return;
			}
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("imgVO", imgVO);
			String url = "/back_end/foodImg/getOneFoodStoreImg.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
			successView.forward(req, res);
			return;
			/*************************** 其他可能的錯誤處理 *************************************/
			}catch (Exception e) {
				errorMsgs.add("查無此店家或圖片或輸入錯誤"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
		if("addImg".equals(action)) {				// 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			byte[] img = null;
			/***************************1.接收請求參數 -**********************/
			Integer fd_no = new Integer(req.getParameter("fd_no").trim());
			
			Part fd_img = req.getPart("fd_img");
			/***************************2.圖片處理**********************/
			InputStream in = fd_img.getInputStream();
			img = new byte[in.available()];
			in.read(img);
			in.close();
			/***************************3.開始新增資料***************************************/
			FoodImgVO foodImgVO = new FoodImgVO();
			foodImgVO.setFd_no(fd_no);
			foodImgVO.setFd_img(img);
			FoodImgService imgSvc = new FoodImgService();
			foodImgVO = imgSvc.addImg(fd_no, img);
			
			List<FoodImgVO> imgVO = imgSvc.foodImg(fd_no);
			/***************************4.新增完成,準備轉交(Send the Success view)***********/
			req.setAttribute("imgVO", imgVO);
			String url ="/back_end/foodImg/getOneFoodStoreImg.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}catch (Exception e) {
			errorMsgs.add("新增失敗"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/foodImg/addImg.jsp");
			failureView.forward(req, res);
		}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數***************************************/
				Integer fd_img_no = new Integer(req.getParameter("fd_img_no"));
				
				/***************************2.開始刪除資料***************************************/
				FoodImgService Svc = new FoodImgService();
				FoodImgVO imgVO1 = Svc.findByPk(fd_img_no);
				Svc.deleteAcc(fd_img_no);
				List<FoodImgVO> imgVO = Svc.foodImg(imgVO1.getFd_no());
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/		
				req.setAttribute("imgVO", imgVO);
				String url ="/back_end/foodImg/getOneFoodStoreImg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("One_Img_Update".equals(action)) { 
		
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			/***************************1.接收請求參數 -**********************/
			try {
				Integer fd_img_no = new Integer(req.getParameter("fd_img_no"));

				FoodImgService Svc = new FoodImgService();
				FoodImgVO imgvo = Svc.findByPk(fd_img_no);
			
				req.setAttribute("imgvo", imgvo);
				
				String url = "/back_end/foodImg/updateFoodImg.jsp";
  				RequestDispatcher successView = req.getRequestDispatcher(url); 
  				successView.forward(req, res);
  				/***************************其他可能的錯誤處理**********************************/
				}catch (Exception e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
						e.printStackTrace();
							RequestDispatcher failureView = req
									.getRequestDispatcher("back_end/foodimg/allStoreImg.jsp");
							failureView.forward(req, res);
							}
		}
		
		
		if ("update".equals(action)) {
			
		
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			/***************************1.接收請求參數 -**********************/
		try {	
			Integer fd_img_no = new Integer(req.getParameter("fd_img_no"));
			Integer fd_no = new Integer(req.getParameter("fd_no"));
			Part fd_img = req.getPart("fd_img");
			InputStream img = fd_img.getInputStream();
			if(img.available()==0) {
				errorMsgs.add("沒有選擇照片");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
				failureView.forward(req, res);
				return;
			}
			byte[] Pic = new byte[img.available()];
			img.read(Pic);
			img.close();

			FoodImgVO vo = new FoodImgVO();
			vo.setFd_img_no(fd_img_no);
			vo.setFd_no(fd_no);
			vo.setFd_img(Pic);
		
			FoodImgService Svc = new FoodImgService();
			vo = Svc.updateImg(fd_no, Pic, fd_img_no);
			List<FoodImgVO> imgVO = Svc.foodImg(fd_no);
			
			req.setAttribute("imgVO", imgVO); 
			String url = "/back_end/foodImg/getOneFoodStoreImg.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
					e.printStackTrace();
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
						failureView.forward(req, res);
			}
		
	
		
	}
}

}
