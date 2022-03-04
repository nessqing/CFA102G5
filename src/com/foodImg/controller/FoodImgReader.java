package com.foodImg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodImg.model.FoodImgService;


public class FoodImgReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/jpeg");

		FoodImgService vic = new FoodImgService();
		ServletOutputStream out = res.getOutputStream();
		
		Integer fd_img_no = new Integer(req.getParameter("fd_img_no").trim());
	    out.write(vic.findByPk(fd_img_no).getFd_img());
	    out.close();
		  	
	}


}


