package com.activitySession.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.activityOrderDetail.model.ActivityOrderDetailService;
import com.activitySession.model.ActivitySessionService;
import com.activitySession.model.ActivitySessionVO;



public class ActivitySessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		String action = request.getParameter("action");
		ActivitySessionService actSessionService = new ActivitySessionService();
		
		final Integer act_session_upper_limit = 10; //活動場次上限人數
		final Integer act_session_lower_limit = 3; //活動場次下限人數
		final Integer act_session_real_number = 0; //活動場次實際參與人數  => 有訂單一產生 去抓該場次實際人數 在更新此欄位
		//預設舉辦狀態為false 之後要搭配排程器 去抓 場次人數有超過下限人數 才變成true(舉辦)
		
//		第一次點擊新增時
		if("beginActSessionAdd".equals(action)) {
			
			request.getRequestDispatcher("/back_end/activity/actSession/addActSession.jsp")
			.forward(request, response);

			return;
		}
		
		//切換舉辦/不舉辦 狀態
		if("switchActSessionState".equals(action)) {
			final Integer act_session_no = new Integer(request.getParameter("updateActSessionNo").trim());
			Boolean act_session_hold_state = new Boolean(request.getParameter("updateActSessionState").trim());
		
			actSessionService.switchActSessionState(act_session_no, act_session_hold_state == true ? false : true);

			
			request.getRequestDispatcher("/back_end/activity/actSession/selectActSession.jsp")
			.forward(request, response);
	
			return;
		}
		
		
		//修改頁面	
		if("updateActSession".equals(action)) {
			final Integer act_session_no = new Integer(request.getParameter("updateActSessionNo").trim());
			final ActivitySessionVO actSessionVO = actSessionService.getActSessionByPk(act_session_no);
			
			request.setAttribute("updateActSession_actSessionVO",actSessionVO);
			request.getRequestDispatcher("/back_end/activity/actSession/updateActSession.jsp")
			.forward(request, response);

			return;
		}
		
	//修改表單提交後	
		if("updateActSessionSure".equals(action)) {
			//活動場次編號
			final Integer act_session_no = new Integer(request.getParameter("updateActSessionNo").trim());
			//活動編號
			final Integer act_no = new Integer(request.getParameter("actNoSelect").trim());
			//活動場次可報名日期
			final LocalDate act_start_date = LocalDate.parse(request.getParameter("actStartDate"));
			//活動場次最後報名日期
			final LocalDate act_end_date =LocalDate.parse(request.getParameter("actEndDate"));
			//活動場次開始日期
			final LocalDate act_session_start_date = LocalDate.parse(request.getParameter("actSessionStartDate"));
			//活動場次開始時間
			final LocalTime act_session_start_time = LocalTime.parse(request.getParameter("actSessionStartTime"));
			
			final Boolean act_session_hold_state = actSessionService.getActSessionByPk(act_session_no).getAct_session_hold_state();
		
			final Integer real_number = actSessionService.getActSessionByPk(act_session_no).getAct_session_real_number();
			actSessionService.updateActSession(act_session_no, act_no, act_start_date, act_end_date, 
					real_number, act_session_start_date, act_session_start_time, act_session_upper_limit, 
				act_session_lower_limit, act_session_hold_state);
			

			request.getRequestDispatcher("/back_end/activity/actSession/selectActSession.jsp")
			.forward(request, response);

			return;
		}
		
		//新增開始		
		if("addActSession".equals(action)) {
			Map<String,String> errorMap = new LinkedHashMap<>();
			request.setAttribute("errorMap", errorMap);
			final Integer act_no = new Integer(request.getParameter("actNoSelect"));
			//活動場次可報名日期
			String actStartDate = request.getParameter("actStartDate");
			//活動場次最後報名日期
			String actEndDate = request.getParameter("actEndDate");
			//活動場次開始日期
			String actSessionStartDate = request.getParameter("actSessionStartDate");
			//活動場次開始時間
			String actSessionStartTime = request.getParameter("actSessionStartTime");
			
			final Boolean act_session_hold_state = false;
			
			if(actStartDate == null || actStartDate.length() == 0) {
				errorMap.put("error_act_start_date","活動場次可報名日期:請勿空白");
			}
			
			if(actEndDate == null || actEndDate.length() == 0) {
				errorMap.put("error_act_end_date","活動場次最後報名日期:請勿空白");
			}
			
			if(actSessionStartDate == null || actSessionStartDate.length() == 0) {
				errorMap.put("error_act_session_start_date","活動場次開始日期:請勿空白");
			}
			
			if(actSessionStartTime == null || actSessionStartTime.length() == 0) {
				errorMap.put("error_act_session_start_time","活動場次開始時間:請勿空白");
			}
			
			if(!errorMap.isEmpty()) {
				request.getRequestDispatcher("/back_end/activity/actSession/addActSession.jsp")
				.forward(request, response);

				return;
			}
			
			LocalDate act_start_date = LocalDate.parse(actStartDate);
			LocalDate act_end_date = LocalDate.parse(actEndDate);
			LocalDate act_session_start_date = LocalDate.parse(actSessionStartDate);
			LocalTime act_session_start_time = LocalTime.parse(actSessionStartTime);
			
			
			final ActivitySessionVO vo = actSessionService.addActSession(act_no, act_start_date, act_end_date, 
					act_session_real_number, act_session_start_date,act_session_start_time, 
					act_session_upper_limit, act_session_lower_limit, act_session_hold_state);

			
			request.getRequestDispatcher("/back_end/activity/actSession/selectActSession.jsp")
					.forward(request, response);
			
			return;
		}
		
		//查活動場次列表
		if("getAll".equals(action)) {
			
			request.getRequestDispatcher("/back_end/activity/actSession/selectActSession.jsp")
			.forward(request, response);
	
			return;
		}
	}

}
