package com.util.activity;



import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.activityOrder.model.ActivityOrderService;
import com.activityOrder.model.ActivityOrderVO;
import com.activityOrderDetail.model.ActivityOrderDetailService;
import com.activityOrderDetail.model.ActivityOrderDetailVO;
import com.activitySession.model.ActivitySessionService;
import com.activitySession.model.ActivitySessionVO;
import com.creditcard.model.CreditcardService;
import com.member.model.MemberClassVO;
import com.member.model.MemberService;

public class ActivitySchedule extends TimerTask{
	
	@Override
	public void run() {
		SendMail mail = new SendMail();
		Map<String,String> map = new HashMap<>();	
		LocalDate now = LocalDate.now(); //當天
		
		ActivitySessionService sessionService = new ActivitySessionService();
		ActivityOrderService orderService = new ActivityOrderService();
		ActivityOrderDetailService orderDetailService = new ActivityOrderDetailService();
		MemberService memService = new MemberService();
		ActivityService actService = new ActivityService();
		
		//該場次開始前兩天 檢查是否滿足最低人數 更改該場次狀態
		List<ActivitySessionVO> sessionList = sessionService.getAll();
		
		for(ActivitySessionVO vo : sessionList) {

			LocalDate date = vo.getAct_session_start_date();
			Period period = Period.between(now,date);
			if(period.getMonths() < 1  && period.getDays() > 0 && period.getDays() <= 2) {	
				if(vo.getAct_session_real_number() >= 3 && vo.getAct_session_real_number() <= 10) {					
					sessionService.switchActSessionState(vo.getAct_session_no(),true);
				}else {
					sessionService.switchActSessionState(vo.getAct_session_no(),false);
				}
				//判斷 已改期 需要>0
				if(period.getDays() == 1 && vo.getAct_session_real_number() > 0 && vo.getAct_session_real_number() < 3){

					Integer session_no = vo.getAct_session_no();
					List<Integer> list = orderDetailService.
											getActOrderDetailByActSessionNo(session_no)
											.stream()
											.map(detail -> detail.getAct_order_no())
											.collect(Collectors.toList());
					
					
					for(Integer orderNo : list) {
						
						ActivityOrderVO order = orderService.getActOrderByPk(orderNo);
						MemberClassVO mem = memService.getOne(order.getMem_no());
						String email = mem.getMem_mail();
						
						if(!map.containsKey(email) || !map.containsValue(String.valueOf(session_no)) && sessionService.getActSessionByPk(session_no).getAct_session_hold_state() == false) {
							map.put("email", email);
							map.put("sessionNo",String.valueOf(session_no));
							ActivitySessionVO sessionVO = sessionService.getActSessionByPk(session_no);
							Integer act_no = sessionVO.getAct_no();
							LocalDate cancel_date = sessionVO.getAct_session_start_date();
							LocalTime cancel_time = sessionVO.getAct_session_start_time();
							String amOrPm = cancel_time.compareTo(LocalTime.of(12,0)) == -1 ? "上午" : "下午";
							
							String mem_name = mem.getMem_name();
							String act_name = actService.getActByPk(act_no).getAct_name();
							CreditcardService creditCardService = new CreditcardService();
							String credit_card = creditCardService.getallByMem_no(mem.getMem_no()).stream()
																  .map(c -> c.getCrd_num())
																  .findFirst()
																  .get();

							String messageText = "親愛的 "+ mem_name + "您好，您預計在 "
												 + cancel_date + " " + amOrPm + cancel_time +" 的 "+ act_name
												 + "因參與人數未達到標準3人，因此未舉辦，已退款至您的信用卡: "+ credit_card
												 + "。\nFeliz Hotel提醒您當天請勿前往現場 "
												 + "造成您的不便，敬請見諒";
							
							mail.sendMail(map.get("email"),"Feliz Hotel提醒您場次取消通知", messageText);

							Integer act_order_detail_no = orderDetailService.getByActOrderNoAndByActSessionNO(orderNo, session_no)
									.getAct_order_detail_no();
							orderDetailService.switchOrderDetailState(act_order_detail_no, 2);
						}
					}
				}
			}
		}
		
		//過濾掉 明細狀態為2(已取消的情況)
		List<ActivityOrderDetailVO> detailList = orderDetailService.getAll()
												.stream().filter(detail -> detail.getAct_order_detail_state() != 2)
												.collect(Collectors.toList());
		//抓明細
		for(ActivityOrderDetailVO datailVO : detailList) {
			//抓場次vo
			ActivitySessionVO sesionVO = sessionService.getActSessionByPk(datailVO.getAct_session_no());
			LocalDate start_date = sesionVO.getAct_session_start_date(); //已結帳下的場次的開始日期			
			LocalTime start_time = sesionVO.getAct_session_start_time();

			Period period = Period.between(now,start_date);

			if(period.getMonths() < 1 && period.getDays() == 1) {			
				
				Integer order_no = datailVO.getAct_order_no();
				
				ActivityVO act_vo = actService.getActByPk(sesionVO.getAct_no());
				String act_name = act_vo.getAct_name();
				String act_gather_location = act_vo.getAct_gather_location();
				
				Integer mem_no = orderService.getActOrderByPk(order_no).getMem_no();
				MemberClassVO mem = memService.getOne(mem_no);
				String email = mem.getMem_mail();
				String mem_name = mem.getMem_name();
				
				Integer session_no = sesionVO.getAct_session_no();
				
				String amOrPm = start_time.compareTo(LocalTime.of(12,0)) == -1 ? "上午" : "下午";
				
				String messageText = "親愛的 "+ mem_name + " 您好，您訂購的 " + act_name + " 活動於 "
								   + start_date + " " + amOrPm + " " + start_time + " 開始，請前往 "
								   + act_gather_location +" 集合，謝謝您，祝您遊玩愉快!";
				
				
				//同會員同場次 只寄一次信
				if(!map.containsKey(email) || !map.containsValue(String.valueOf(session_no)) && sesionVO.getAct_session_hold_state() == true) {
					map.put("email", email);
					
					map.put("sessionNo",String.valueOf(session_no));
					mail.sendMail(map.get("email"),"Feliz Hotel提醒您活動快開始了", messageText);

				}
			}
		}		
	}
}
