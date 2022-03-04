package com.activityOrderDetail.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.activityOrderDetail.model.ActivityOrderDetailService;
import com.activityOrderDetail.model.ActivityOrderDetailVO;
import com.activitySession.model.ActivitySessionService;
import com.activitySession.model.ActivitySessionVO;
import com.google.gson.Gson;


public class ActivityOrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		String action = request.getParameter("action");
		ActivityOrderDetailService actOrderDetailService = new ActivityOrderDetailService();
		ActivitySessionService actSessionService = new ActivitySessionService();
System.out.println("Action:"+action);
		
//		來自前台人數檢查
		if("checkSessionPeopleNumber".equals(action)) {
			Integer act_session_no = new Integer(request.getParameter("sessionNo"));

			Integer act_people_number = actOrderDetailService
								 .getActOrderDetailByActSessionNo(act_session_no)
								 .stream().mapToInt(detail -> detail.getAct_real_join_number())
								 .sum();
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(act_people_number));
		}
		
		
//	後台	
		if("getAll".equals(action)) {
			
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/selectActOrderDetail.jsp")
			.forward(request, response);
			return;
		}
		
		if("switchActOrderDetailState".equals(action)) {
			Integer act_order_detail_no = new Integer(request.getParameter("updateActOrderDetailNo").trim());
			Integer act_order_detail_state = new Integer(request.getParameter("switchActOrderDetailStateSelect").trim());
			
			actOrderDetailService.switchOrderDetailState(act_order_detail_no, act_order_detail_state);
			
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/selectActOrderDetail.jsp")
			.forward(request, response);
			return;
		}
		
		
		
		if("updateActOrderDetail".equals(action)) {
			//要修改的明細PK
			Integer act_order_detail_no = new Integer(request.getParameter("updateActOrderDetailNo"));
			ActivityOrderDetailVO actOrderDetailVO = actOrderDetailService.getActOrderDetailByPk(act_order_detail_no);
			//該筆明細的場次PK
			Integer act_session_no = new Integer(request.getParameter("actSessionNo"));
			//findByPk -> 活動場次VO
			ActivitySessionVO actSessionVO = actSessionService.getActSessionByPk(act_session_no);
			//找到該場次下的活動 會有多筆
			List<ActivitySessionVO> actSessionListByActNo = actSessionService.getActSessionByActNo(actSessionVO.getAct_no());
			
			Integer actNo = actSessionListByActNo.stream()
					.mapToInt(session -> session.getAct_no()).distinct()
					.findFirst().getAsInt();
						
			request.setAttribute("actNo", actNo);
			request.setAttribute("actSessionVO", actSessionVO);
			request.setAttribute("actOrderDetailVO", actOrderDetailVO);	
			request.setAttribute("actSessionListByActNo", actSessionListByActNo);	
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/updateActOrderDetail.jsp")
			.forward(request, response);
			return;
		}
		
		if("checkChangeSession".equals(action)) {
			//要更換的場次編號
			Integer change_act_session_no = new Integer(request.getParameter("changeSessionNo"));
			//哪筆訂單  訂單編號與場次編號 形成該筆的訂單明細編號
			Integer act_order_no = new Integer(request.getParameter("orderNo"));
		
			//可選擇要改場次的人數，確認人數(要更換的) 但有可能明細沒有該場次 這時候就直接動場次
			Integer change_act_people_number = new Integer(request.getParameter("changeActPeopleSelect"));
			
			Integer change_session_act_real_join_number = null;
			try {
				change_session_act_real_join_number = actOrderDetailService.getAll().stream()
						.filter(detail -> detail.getAct_session_no() == change_act_session_no.intValue())
						.mapToInt(detail -> detail.getAct_real_join_number())
						.sum();
			}catch(NoSuchElementException ex) {
				change_session_act_real_join_number = 0;

			}		
		
			Gson gson = new Gson();
			
			if(change_act_people_number + change_session_act_real_join_number <= 10) {
				response.getWriter().write(gson.toJson(true));
			}else {
				response.getWriter().write(gson.toJson(false));
			}
			
		}
		
		if("updateActOrderDetailSure".equals(action)) {			
			//活動場次單價
			Integer act_session_price = new Integer(request.getParameter("actSessionPrice"));
			
			//實際報名人數(原本的場次)
			Integer act_session_people_number = new Integer(request.getParameter("actRealJoinNumber"));			
				
			//原本的場次編號
			Integer old_act_session_no = new Integer(request.getParameter("updateActSessionNo"));
			
			//要更換的場次(不存在的情況要注意)
			Integer change_act_session_no = new Integer(request.getParameter("actSessionTimeSelect"));

			//訂單編號
			Integer act_order_no = new Integer(request.getParameter("updateOrderNo"));
			
			//要換過去的場次的人數
			Integer change_act_people_number = null;
			//總人數
			Integer totalPeople = null;
			//總金額
			Integer act_price_total = null;
			try {
			
			change_act_people_number = actOrderDetailService.getAll()
									.stream().filter(detail -> detail.getAct_order_no() == act_order_no.intValue() && detail.getAct_session_no() == change_act_session_no.intValue())
								 	.mapToInt(detail -> detail.getAct_real_join_number())
								 	.findFirst()
								 	.getAsInt();
			
			totalPeople = act_session_people_number+change_act_people_number;

			act_price_total = act_session_price * totalPeople;
			
			actOrderDetailService.orderDetailUpdate(totalPeople, act_price_total, act_order_no, change_act_session_no);
			
			LocalDate old_session_date = actSessionService.getActSessionByPk(old_act_session_no).getAct_session_start_date();
			
			LocalDate change_session_date = actSessionService.getActSessionByPk(change_act_session_no).getAct_session_start_date();
			
			//已改期的判斷
			if(old_act_session_no != change_act_session_no && (!old_session_date.equals(change_session_date))) {
				actOrderDetailService.switchOrderDetailState(act_order_no, old_act_session_no, 3);
			}
			
			}catch(NoSuchElementException ex) {

				act_price_total = act_session_people_number * act_session_price;
				actOrderDetailService.switchOrderDetailState(act_order_no, old_act_session_no, 3);
				actOrderDetailService.addActOrderDetail(act_order_no, change_act_session_no, act_session_people_number, act_session_price, 0, act_price_total, 1);
			}
//同時也要扣除相對應明細的人數以及總金額
			totalPeople = actOrderDetailService.getAll().stream()
					 .filter(detail -> detail.getAct_order_no() == act_order_no.intValue() && detail.getAct_session_no() == old_act_session_no.intValue())
					 .mapToInt(detail -> detail.getAct_real_join_number())
					 .findFirst()
					 .getAsInt();
			
			totalPeople -= act_session_people_number;
	
			Integer old_act_price_total = totalPeople * act_session_price;
			actOrderDetailService.orderDetailUpdate(totalPeople, old_act_price_total, act_order_no, old_act_session_no);
			
//重新計算 現有明細的人數 以及 場次 活動等
			
			List<ActivityOrderDetailVO> list = actOrderDetailService.getAll()
																	.stream()
																	.filter(detail -> detail.getAct_order_detail_state() != 2)
																	.collect(Collectors.toList());
			
			Integer oldSessionNumberTotal = list.stream()
											.filter(vo -> vo.getAct_session_no() == old_act_session_no.intValue())
											.mapToInt(people -> people.getAct_real_join_number())
											.sum();
		
			Integer changeSessionNumberTotal = list.stream()
					.filter(vo -> vo.getAct_session_no() == change_act_session_no.intValue())
					.mapToInt(people -> people.getAct_real_join_number())
					.sum();


			actOrderDetailService.switchOrderDetailState(act_order_no, old_act_session_no, 3);
			actSessionService.updateActSessionRealJoinNumber(old_act_session_no, oldSessionNumberTotal);
			actSessionService.updateActSessionRealJoinNumber(change_act_session_no, changeSessionNumberTotal);
			
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/selectActOrderDetail.jsp")
			.forward(request, response);
			return;
		}
		
		//根據分類查詢 (已付款、已取消、已改期)
			
		if("paid".equals(action)) {
			List<ActivityOrderDetailVO> selectByState = actOrderDetailService.getActOrderDetailByState(1);
			
			request.setAttribute("selectByState",selectByState);
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/selectActOrderDetail.jsp")
			.forward(request, response);
			return;
		}
		
		if("canceled".equals(action)) {
			List<ActivityOrderDetailVO> selectByState = actOrderDetailService.getActOrderDetailByState(2);
			
			request.setAttribute("selectByState",selectByState);
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/selectActOrderDetailByCancel.jsp")
			.forward(request, response);
			return;
		}
		
		if("changeDate".equals(action)) {
			List<ActivityOrderDetailVO> selectByState = actOrderDetailService.getActOrderDetailByState(3);
			
			request.setAttribute("selectByState",selectByState);
			request.getRequestDispatcher("/back_end/activity/actOrderDetail/selectActOrderDetailByChangeDate.jsp")
			.forward(request, response);
			return;
		}

		//申請取消
		if("cancelState".equals(action)) {
			Integer act_order_detail_no = new Integer(request.getParameter("cancelStateNo"));
			Integer act_session_no = new Integer(request.getParameter("actSessionNo"));
			
			LocalDate now = LocalDate.now();
			LocalDate start_date = actSessionService.getActSessionByPk(act_session_no)
											  		.getAct_session_start_date();
		
			Gson gson = new Gson();
			
			Period period = Period.between(now, start_date);

			if(period.getMonths() < 1 && period.getDays() >= 2) {
				actOrderDetailService.switchOrderDetailState(act_order_detail_no, 2);
				response.getWriter().write(gson.toJson(true));				
			}else {	
				response.getWriter().write(gson.toJson(false));
			}
		}
	}

}
