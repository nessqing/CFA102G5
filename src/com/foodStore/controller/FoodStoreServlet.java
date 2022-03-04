package com.foodStore.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.foodStore.model.*;

public class FoodStoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodStoreServlet() {
        super();
    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("Update_One".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Integer fd_no = new Integer(req.getParameter("fd_no"));

			FoodStoreService ser = new FoodStoreService();
			FoodStoreVO vo = ser.getOneStore(fd_no);
			req.setAttribute("vo", vo);
			
			String url = "back_end/foodStore/updateStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		}
	
	
	
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			Integer fd_no = new Integer(req.getParameter("fd_no"));
			Integer fd_class_no = new Integer(req.getParameter("fd_class_no"));
			String fd_name = req.getParameter("fd_name");	
			if (fd_name == null || fd_name.trim().length() == 0) {
				errorMsgs.add("店家名稱: 請勿空白");
			}
			String fd_address = req.getParameter("fd_address");
			if (fd_address == null || fd_address.trim().length() == 0) {
				errorMsgs.add("店家名稱: 請勿空白");
			}
			String LONGITUDE = "^[\\-\\+]?(0(\\.\\d{1,6})?|([1-9](\\d)?)(\\.\\d{1,6})?|1[0-7]\\d{1}(\\.\\d{1,6})?|180(\\.0{1,6})?)$";
			String fd_longitude1 = req.getParameter("fd_longitude");
			if (fd_longitude1 == null || fd_longitude1.trim().length() == 0) {
				errorMsgs.add("經度: 請勿空白");
			} else if(!fd_longitude1.trim().matches(LONGITUDE)) { 
				errorMsgs.add("經度: 請依照格式輸入");
            }
			Double fd_longitude = new Double(fd_longitude1);    //正則表示後轉型成DOUBLE
			
		    String LATITUDE = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,6})?|90(\\.0{1,6})?)$";
		    String fd_latitude1 = req.getParameter("fd_latitude");
		    if (fd_latitude1 == null || fd_latitude1.trim().length() == 0) {
				errorMsgs.add("緯度: 請勿空白");
			} else if(!fd_latitude1.trim().matches(LATITUDE)) { 
				errorMsgs.add("緯度: 請依照格式輸入");
            }
			Double fd_latitude = new Double(fd_latitude1);		//正則表示後轉型成DOUBLE
			
			String[] fd_service1 = req.getParameterValues("fd_service");
			String fd_service = Arrays.toString(fd_service1);
			
			Boolean fd_state = new Boolean(req.getParameter("fd_state"));
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
				failureView.forward(req, res);
				return;
			}
			FoodStoreService ser = new FoodStoreService();
			FoodStoreVO vo = ser.updateFoodStore(fd_name, fd_address, fd_longitude, fd_latitude, fd_service, fd_state, fd_class_no, fd_no);
		
			req.setAttribute("vo", vo); // 資料庫update成功後,正確的的vo物件,存入req
			String url = "/back_end/foodStore/allStore.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
			failureView.forward(req, res);
		}
	
		}
		
		if("insert".equals(action))	{
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			Integer fd_class_no = new Integer(req.getParameter("fd_class_no"));
			String fd_name = req.getParameter("fd_name");
			if (fd_name == null || fd_name.trim().length() == 0) {
				errorMsgs.add("店家名稱: 請勿空白");
			}
			String fd_address = req.getParameter("fd_address");
			if (fd_address == null || fd_address.trim().length() == 0) {
				errorMsgs.add("店址: 請勿空白");
			}
			String LONGITUDE = "^[\\-\\+]?(0(\\.\\d{1,6})?|([1-9](\\d)?)(\\.\\d{1,6})?|1[0-7]\\d{1}(\\.\\d{1,6})?|180(\\.0{1,6})?)$";
			String fd_longitude1 = req.getParameter("fd_longitude");
			if (fd_longitude1 == null || fd_longitude1.trim().length() == 0) {
				errorMsgs.add("經度: 請勿空白");
			} else if(!fd_longitude1.trim().matches(LONGITUDE)) { 
				errorMsgs.add("經度: 請依照格式輸入");
            }
			Double fd_longitude = new Double(fd_longitude1);    //正則表示後轉型成DOUBLE
			
		    String LATITUDE = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,6})?|90(\\.0{1,6})?)$";
		    String fd_latitude1 = req.getParameter("fd_latitude");
		    if (fd_latitude1 == null || fd_latitude1.trim().length() == 0) {
				errorMsgs.add("緯度: 請勿空白");
			} else if(!fd_latitude1.trim().matches(LATITUDE)) { 
				errorMsgs.add("緯度: 請依照格式輸入");
            }
			Double fd_latitude = new Double(fd_latitude1);		//正則表示後轉型成DOUBLE
			
			String[] fd_service1 = req.getParameterValues("fd_service");
			String fd_service = Arrays.toString(fd_service1);
						
			Boolean fd_state = new Boolean(req.getParameter("fd_state"));
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
				failureView.forward(req, res);
				return;
			}
			FoodStoreService ser = new FoodStoreService();
			FoodStoreVO vo = ser.addFoodStore(fd_name, fd_address, fd_longitude, fd_latitude, fd_service, fd_state, fd_class_no);
			
			req.setAttribute("vo", vo); // 資料庫update成功後,正確的的vo物件,存入req
			String url = "/back_end/foodStore/allStore.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back_end/foodStore/allStore.jsp");
			failureView.forward(req, res);
		}
		}
	
		
		
		if("getStoreFK".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			Integer fd_class_no = new Integer(req.getParameter("fd_class_no"));
			List<FoodStoreVO> storeVO = null;
			FoodStoreService ser = new FoodStoreService();
			if(fd_class_no==1) {
				 storeVO = ser.getAllFoodStore();
			}else {
				 storeVO = ser.findfdByFK(fd_class_no);
				if(storeVO.size()==0) {
					errorMsgs.add("查無此類別店家");
					}
				}
			//刪除(下架)false店家
			for(int i =0;i<storeVO.size();i++) {
				if(storeVO.get(i).getFd_state()==false) {
					storeVO.remove(i);
				}
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/storeMap/storeMap.jsp");
				failureView.forward(req, res);
				return;
			}
			req.setAttribute("storeVO", storeVO);
			String url = "front_end/storeMap/storeMap.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
			successView.forward(req, res);
			return;
			
			}catch (Exception e) {
				errorMsgs.add("查詢資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/storeMap/storeMap.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
