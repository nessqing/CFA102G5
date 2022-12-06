package com.activityImage.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import com.activityImage.model.ActivityImageService;
import com.activityImage.model.ActivityImageVO;
import com.google.gson.Gson;


@MultipartConfig(maxFileSize=5*1024*1024,maxRequestSize=5*5*1024*1024)
public class ActivityImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		ActivityImageService actImgService = null;
		//overUsing
		
		if("getAll".equals(action)) {
			doPost(request,response);
			
		}else if("frontImg".equals(action)) {
			actImgService = new ActivityImageService();
			Integer act_no = new Integer(request.getParameter("actNo").trim());
			List<ActivityImageVO> list = actImgService.getActImageByActNo(act_no);
			
			byte[] imgArray = list.stream().findFirst().get().getAct_img();
			ServletOutputStream out = response.getOutputStream();
			out.write(imgArray);
			out.close();
			
		}else if("actList".equals(action)) {
			actImgService = new ActivityImageService();
			Integer act_no = new Integer(request.getParameter("actNo").trim());
			byte[] imgArray = actImgService.getActImageByActNo(act_no)
						      .stream().findFirst().get().getAct_img();
			ServletOutputStream out = response.getOutputStream();
			out.write(imgArray);
			out.close();
						  
		}else if("innerAct".equals(action)){
			actImgService = new ActivityImageService();
			Integer act_img_no = new Integer(request.getParameter("actImgNo").trim());
			byte[] imgArray = actImgService.getActImageByPk(act_img_no).getAct_img();
			ServletOutputStream out = response.getOutputStream();
			out.write(imgArray);
			out.close();
		}else {
			response.setContentType("img/jpeg");
			String act_img_no = request.getParameter("act_img_no");
			ActivityImageService actImageService = new ActivityImageService();
			byte[] imgArray = actImageService.getActImageByPk(new Integer(act_img_no)).getAct_img();
			ServletOutputStream out = response.getOutputStream();
			out.write(imgArray);
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		ActivityImageService actImageService = new ActivityImageService();

//		依照活動類別查詢
		if("queryByAct".equals(action)) {
			Integer act_no = new Integer(request.getParameter("queryAct"));
			List<ActivityImageVO> list = actImageService.getActImageByActNo(act_no);
			int whichPage = 0;
			if((act_no%5) == 0) {
				whichPage = act_no/5;
			}else {
				whichPage = (act_no/5)+1;
			}
			
			request.setAttribute("whichPage",whichPage);
			request.setAttribute("queryActImg_List", list);
			request.getRequestDispatcher("/back_end/activity/actImg/queryActImg.jsp").forward(request, response);

			return;
		}
		//新增圖片
		if("addActImg".equals(action)) {
			Collection<Part> parts = request.getParts();
			Integer act_no = new Integer(request.getParameter("actNoSelect"));
			
			for(Part part : parts) {
				InputStream in = part.getInputStream();
				
				if(part.getContentType() != null) {
					byte[] imgArray = new byte[in.available()];
					in.read(imgArray);
					in.close();
					actImageService.addActImage(act_no, imgArray);			
				}
			}
			
			request.getRequestDispatcher("/back_end/activity/actImg/selectActImg.jsp")
			.forward(request, response);
			
			return;
		}
		
		//刪除圖片
		if ("deleteActImg".equals(action)) {
			Integer act_img_no = new Integer(request.getParameter("act_img_no"));
			actImageService.deleteActImage(act_img_no);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(act_img_no));

			return;
		}
		
		//欲更新圖片的ID
		if("updateActImg".equals(action)) {
			Integer act_img_no = new Integer(request.getParameter("updateActImgNo"));
			
			ActivityImageVO actImageVO = actImageService.getActImageByPk(act_img_no);
			request.setAttribute("actImageVO", actImageVO);
			request.getRequestDispatcher("/back_end/activity/actImg/updateActImg.jsp")
			.forward(request, response);
			
			return;
		}
		
		//將該ID做修改
		if("updateActImgSure".equals(action)) {
			Part part = request.getPart("actImg");
			String act_img_no = request.getParameter("updateActImgNo");
			Integer act_no = new Integer(request.getParameter("actNoSelect"));
			byte[] imgArray = null;
				// 修改時 檢查有無選擇圖片，若沒有保持原圖
			if (part.getInputStream().available() > 0) {
				InputStream in = part.getInputStream();
				imgArray = new byte[in.available()];
				in.read(imgArray);
			} else {
				imgArray = actImageService.getActImageByPk(new Integer(act_img_no)).getAct_img();
			}

			actImageService.updateActImage(new Integer(act_img_no), act_no, imgArray);

			request.getRequestDispatcher("/back_end/activity/actImg/selectActImg.jsp")
			.forward(request, response);

			return;
			
		}
		
		//查看全部
		if("getAll".equals(action)) {
			
			request.getRequestDispatcher("/back_end/activity/actImg/selectActImg.jsp")
			.forward(request, response);
			
			return;
		}

	}

}
