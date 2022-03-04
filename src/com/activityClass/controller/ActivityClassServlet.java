package com.activityClass.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.activityClass.model.ActivityClassService;
import com.activityClass.model.ActivityClassVO;

public class ActivityClassServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		ActivityClassService actClassService = new ActivityClassService();

		//新增活動類別
		if ("addActClass".equals(action)) {

			String act_class_name = request.getParameter("actClassName");

			ActivityClassVO actClassVO = actClassService.addActClass(act_class_name);
			List<ActivityClassVO> list = actClassService.getAll();
			request.setAttribute("selectActClassList", list);
			request.getRequestDispatcher("/back_end/activity/actClass/selectActClass.jsp")
			.forward(request, response);

			return;
			
		}
		//修改活動類別
		if("updateActClass".equals(action)) {
			String act_class_no = request.getParameter("actClassNo");
			String act_class_name = request.getParameter("actClassName");
			String act_class_state = request.getParameter("actClassState");
			actClassService.updateActClass(new Integer(act_class_no),act_class_name, new Boolean(act_class_state));

			List<ActivityClassVO> list = actClassService.getAll();
			request.setAttribute("selectActClassList", list);
			request.getRequestDispatcher("/back_end/activity/actClass/selectActClass.jsp")
			.forward(request, response);

			return;
		}
		//切換活動類別狀態
		if("switchActClassState".equals(action)) {
			String act_class_no = request.getParameter("actClassNo");
			String act_class_state = request.getParameter("actClassState");			
			actClassService.switchActClassState(new Integer(act_class_no), new Boolean(act_class_state.equals("true") ? "false" : "true"));

			List<ActivityClassVO> list = actClassService.getAll();
			request.setAttribute("selectActClassList",list);
			
			request.getRequestDispatcher("/back_end/activity/actClass/selectActClass.jsp")
			.forward(request, response);
			
			return;
		}
		//查全部
		if("getAllActClass".equals(action)) {
			
			List<ActivityClassVO> list = actClassService.getAll();
			request.setAttribute("selectActClassList",list);
			
			request.getRequestDispatcher("/back_end/activity/actClass/selectActClass.jsp")
			.forward(request, response);
			return;
		}		
	}	
}
