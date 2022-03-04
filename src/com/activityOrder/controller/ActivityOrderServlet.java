package com.activityOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.activity.model.ActivityService;
import com.activityOrder.model.ActivityOrderService;
import com.activityOrder.model.OrderVO;
import com.activityOrderDetail.model.ActivityOrderDetailService;
import com.activityOrderDetail.model.ActivityOrderDetailVO;
import com.activitySession.model.ActivitySessionService;
import com.activitySession.model.ActivitySessionVO;
import com.creditcard.model.CreditcardService;
import com.google.gson.Gson;
import com.member.model.MemberClassVO;
import com.member.model.MemberService;
import com.util.activity.SendMail;


public class ActivityOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(ActivityOrderServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		String action = request.getParameter("action");
		ActivityOrderService actOrderService = new ActivityOrderService();
		ActivityOrderDetailService detailService = new ActivityOrderDetailService();
		ActivityService actService = new ActivityService();
		ActivitySessionService sessionService = new ActivitySessionService();
logger.info("action:"+action);		
		
		
//		付款流程
		if("checkout".equals(action)) {
			String checkoutAction = request.getParameter("checkoutAction");
logger.info("結帳動作:"+checkoutAction);
			HttpSession session = request.getSession();
			
			String mem_email = (String)session.getAttribute("mem_mail");
			MemberService memberService = new MemberService();
					
			Integer mem_no = memberService.getOneBymail(mem_email).getMem_no();//會員編號
			LocalDateTime act_booking_date = LocalDateTime.now();//當下訂購日期時間
			Integer act_order_total_price = new Integer(request.getParameter("orderTotalPrice"));//訂單總金額
			String act_order_title = request.getParameter("actOrderTitleSelect");//稱謂
			String act_order_name = request.getParameter("orderName");//姓名
			String act_order_phone = request.getParameter("orderPhone");//電話
			String act_order_email = request.getParameter("orderEmail");//Email
			String act_order_credit_card = request.getParameter("orderCreditCard");//信用卡卡號

//來自購物車結帳			
			if("shoppingCarCheckout".equals(checkoutAction)) {
				List<Map<String,String>> list = (List<Map<String,String>>) session.getAttribute("shoppingCar");
				List<ActivityOrderDetailVO> detailList = new ArrayList<>();
				
				for(Map<String,String> map : list) {
					ActivityOrderDetailVO vo = new ActivityOrderDetailVO();					
					LocalDate date = LocalDate.parse(map.get("act_date"));
					LocalTime time = LocalTime.parse(map.get("act_session_start_time"));

					Integer act_session_no = sessionService.getAll()
					.stream().filter(act -> act.getAct_session_start_date().equals(date) && act.getAct_session_start_time().equals(time))
							 .mapToInt(act -> act.getAct_session_no()).findFirst().getAsInt();
					Integer act_real_join_number = new Integer(map.get("act_people_number"));
					Integer act_order_price = new Integer(map.get("act_price"));
					
					vo.setAct_session_no(act_session_no);
					vo.setAct_real_join_number(act_real_join_number);
					vo.setAct_order_price(act_order_price);
					vo.setAct_coupon_price(0);
					vo.setAct_price_total(act_real_join_number * act_order_price );
					vo.setAct_order_detail_state(1);
					
					detailList.add(vo);

				}
				actOrderService.insertWithOrderDetails(mem_no, act_booking_date, 
						act_order_total_price, act_order_title, act_order_name, 
						act_order_phone, act_order_email, act_order_credit_card, detailList);

				//清空購物車
				list.clear();
				session.setAttribute("shoppingCar",list);
				
//更新場次的人數 不包含已取消 退款的人數
				int[] sessionNoArray = sessionService.getAll().stream().mapToInt(s->s.getAct_session_no()).toArray();
				List<ActivityOrderDetailVO> actOrderDetailList = detailService.getAll()
										.stream().filter(detail -> detail.getAct_order_detail_state() != 2)
										.collect(Collectors.toList());
				
				for(int i=0;i<sessionNoArray.length;i++) {
					Integer sessionNo = sessionNoArray[i];
					Integer total = 0;
					for(int j=0;j<actOrderDetailList.size();j++) {
						if(actOrderDetailList.get(j).getAct_session_no() == sessionNo) {
							total += actOrderDetailList.get(j).getAct_real_join_number();
						}
					}
					sessionService.updateActSessionRealJoinNumber(sessionNo,total);							
				}
				
//更新活動累積銷售的人數				
			int[] actNoArray = actService.getAll().stream().mapToInt(act->act.getAct_no()).toArray();
			List<ActivitySessionVO> actSessionList = sessionService.getAll();
				for(int i=0;i<actNoArray.length;i++) {
					Integer actNo = actNoArray[i];
					Integer total = 0;
					for(int j=0;j<actSessionList.size();j++) {
						if(actSessionList.get(j).getAct_no() == actNo) {
							total += actSessionList.get(j).getAct_session_real_number();
						}
					}
					actService.updateActSellNumber(actNo, total);						
				}
	

//寄信
				SendMail mail = new SendMail();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:m:s");
				
				String messageText = act_order_name + act_order_title + " 您好，您於 " + formatter.format(act_booking_date)
						+ " 在Feliz網站下訂活動，非常感謝，我們會在活動開始前一天通知您! ";
									
				mail.sendMail(act_order_email, "Feliz Hotel 訂購完成信件", messageText);
				
				//回首頁
				request.getRequestDispatcher("/front_end/index/index.jsp")
						.forward(request, response);
				return;
			}
//來自立即結帳			
			if("immediateCheckout".equals(checkoutAction)) {
				ActivityOrderDetailVO vo = new ActivityOrderDetailVO();
				List<ActivityOrderDetailVO> detailList = new ArrayList<>();
				LocalDate date = LocalDate.parse(request.getParameter("actSessionDate"));
				LocalTime time = LocalTime.parse(request.getParameter("actSessionTime"));
				
				
				Integer act_session_no = sessionService.getAll()
						.stream().filter(act -> act.getAct_session_start_date().equals(date) && act.getAct_session_start_time().equals(time))
						.mapToInt(act -> act.getAct_session_no()).findFirst().getAsInt();
				
				Integer act_real_join_number = new Integer(request.getParameter("actRealJoinNumber"));
				
				Integer act_order_price = new Integer(request.getParameter("actOrderPrice"));
				
				
				//act_order_price 是立即結帳該筆訂單的總價
				Integer act_session_price = act_order_price / act_real_join_number;
				
				vo.setAct_session_no(act_session_no);
				vo.setAct_real_join_number(act_real_join_number);
				vo.setAct_order_price(act_session_price);
				vo.setAct_coupon_price(0);
				vo.setAct_price_total(act_real_join_number * act_session_price);
				vo.setAct_order_detail_state(1);
				
				detailList.add(vo);
				
				actOrderService.insertWithOrderDetails(mem_no, act_booking_date,
						act_order_total_price, act_order_title, act_order_name, 
						act_order_phone, act_order_email, act_order_credit_card, detailList);
			
				
//更新場次的人數 不包含已取消 退款的人數
				List<ActivityOrderDetailVO> actOrderDetailList = detailService.getActOrderDetailByActSessionNo(act_session_no)
								.stream().filter(detail -> detail.getAct_order_detail_state() != 2)
								.collect(Collectors.toList());
				
				Integer total = actOrderDetailList.stream().mapToInt(detail -> detail.getAct_real_join_number())
								  .sum();
				
				sessionService.updateActSessionRealJoinNumber(act_session_no,total);							
				
				ActivitySessionVO sessionVO = sessionService.getActSessionByPk(act_session_no);
				Integer act_no = sessionVO.getAct_no();
				
				//目前活動的累積銷售人數
				Integer peopleTotal = actService.getActByPk(act_no).getAct_sell_number();
				Integer totalNum = peopleTotal + act_real_join_number;
				actService.updateActSellNumber(act_no, totalNum);
				
//寄信
				SendMail mail = new SendMail();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:m:s");
				
				String messageText = act_order_name + act_order_title + " 您好，您於 " + formatter.format(act_booking_date)
						+ " 在Feliz網站下訂活動，非常感謝，我們會在活動開始前一天通知您! ";
									
				mail.sendMail(act_order_email, "Feliz Hotel 訂購完成信件", messageText);

				//回首頁
				request.getRequestDispatcher("/front_end/index/index.jsp")
						.forward(request, response);
				return;
			}
			
		}
		
//		同會員
		if("sameAsMember".equals(action)) {
			String mem_email = (String)request.getSession().getAttribute("mem_mail");
			response.setCharacterEncoding("UTF-8");
			MemberService memberService = new MemberService();
			CreditcardService creditCardService = new CreditcardService();
			MemberClassVO memberVO = memberService.getOneBymail(mem_email);
			String cardNumber = creditCardService.getallByMem_no(memberVO.getMem_no())
								.stream().map(card -> card.getCrd_num())
								.findFirst().get();

			
			OrderVO vo = new OrderVO();
			vo.setMem_title(memberVO.getMem_sex() == 1 ? "先生" : "女士");
			vo.setMem_name(memberVO.getMem_name());
			vo.setMem_phone(memberVO.getMem_mobile());
			vo.setMem_email(memberVO.getMem_mail());
			vo.setMem_creditCard(cardNumber);
		
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.write(gson.toJson(vo));
			out.close();
		}
//      購物車結帳
		if("carCheckout".equals(action)) {
			
			HttpSession session = request.getSession();
			List<Map<String,String>> list = (List<Map<String,String>>)session.getAttribute("shoppingCar");
			Integer totalPrice = 0;
			if(list != null) {
				for(int index=0;index<list.size();index++) {
					Integer people = new Integer(list.get(index).get("act_people_number"));
					Integer price = new Integer(list.get(index).get("act_price"));
					totalPrice += people * price;
				}
			}
			request.setAttribute("totalPrice", totalPrice);
			request.getRequestDispatcher("/front_end/activity/checkout.jsp")
			.forward(request, response);
			
			return;
		}

//		前台立即結帳頁面
		if("immediateCheckout".equals(action)) {
			
			String act_name = request.getParameter("act_name");
			String act_date = request.getParameter("act_date");
			String act_session_no = request.getParameter("actSessionStartTimeSelect");//session_no
			String act_time = sessionService.getActSessionByPk(new Integer(act_session_no)).getAct_session_start_time().toString();
			Integer poepleNumber = new Integer(request.getParameter("actPeopleNumber"));
			Integer act_price = new Integer(request.getParameter("act_price"));
			Integer oneOrderPrice = act_price * poepleNumber;
			
			
						
			request.setAttribute("checkout_act_name", act_name);
			request.setAttribute("checkout_act_date", act_date);
			request.setAttribute("checkout_act_time", act_time);
			request.setAttribute("checkout_poepleNumber", poepleNumber);
			request.setAttribute("checkout_oneOrderPrice", oneOrderPrice);
	
			request.getRequestDispatcher("/front_end/activity/checkout.jsp")
			.forward(request, response);
			
			return;
		}

//		購物車開始
		if("actShoppingCart".equals(action)) {
			HttpSession session = request.getSession();
			List<Map<String,String>> list = (List<Map<String,String>>)session.getAttribute("shoppingCar");
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			boolean repeat = false; //購物車內是否重複
			Integer repeatIndex = null; //記錄重複的索引
			Map<String,String> map = null;
			String car_action = request.getParameter("carAction");

			if("add".equals(car_action)) {
		
				String act_no = request.getParameter("actNo").trim();
				String act_name = request.getParameter("actNameInCar").trim();
				String act_date = request.getParameter("actDateInCar").trim();
				String act_session_start_time = request.getParameter("actTime").trim();
				String act_people_number = request.getParameter("actPeopleNumber").trim();
				
				String act_price = String.valueOf(actService.getActByPk(new Integer(act_no)).getAct_price());
							
			
//				一開始為空
				if(list == null && map == null) {
			
					list = new ArrayList<>();
					map = new HashMap<>();
									
				}else {
					if (!list.isEmpty()) { // 不先檢查 刪光的情況get會死
						for (int index = 0; index < list.size(); index++) {
							if (list.get(index).get("act_session_start_time").equals(act_session_start_time)
									&& list.get(index).get("act_date").equals(act_date)) {
								repeat = true;
								repeatIndex = index;
							}
						}
					}
				}
				
				if(repeat) {
					list.remove(repeatIndex.intValue());
					map = new HashMap<>();
					map.put("act_no", act_no);
					map.put("act_name", act_name);
					map.put("act_date", act_date);
					map.put("act_session_start_time", act_session_start_time);
					map.put("act_people_number", act_people_number);
					map.put("act_price", act_price);
					
					list.add(map);

					final String update = "update";
					out.write(gson.toJson(update));
				}else {
					map = new HashMap<>();
					map.put("act_no", act_no);
					map.put("act_name", act_name);
					map.put("act_date", act_date);
					map.put("act_session_start_time", act_session_start_time);
					map.put("act_people_number", act_people_number);
					map.put("act_price", act_price);

					list.add(map);								
				}
				
				out.write(gson.toJson(list.size()));

			}
			
//			清除某項目
			if("delete".equals(car_action)) {
			int deleteIndex = Integer.parseInt(request.getParameter("deleteIndex"));
				list.remove(deleteIndex);
			}
			
//			清除購物車
			if("deleteAll".equals(car_action)) {
				if(list != null) {
					list.clear();			
				}
			}
				
			
			session.setAttribute("shoppingCar",list);
			
		}
		
		if("lookShoppingCart".equals(action)) {
			request.getRequestDispatcher("/front_end/activity/shoppingCar.jsp")
			.forward(request, response);
			
			return;
		}
		
		// 後台開始  查看訂單列表
		if("getAll".equals(action)) {
			
			request.getRequestDispatcher("/back_end/activity/actOrder/selectActOrder.jsp")
			.forward(request, response);
			return;
		}
		
	}

}
