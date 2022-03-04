package com.activityOrder.model;

import java.time.LocalDateTime;
import java.util.List;

import com.activityOrderDetail.model.ActivityOrderDetailVO;

public class ActivityOrderService {
	private I_ActivityOrderDAO dao;
	
	public ActivityOrderService() {
		dao = new ActivityOrderDAO();
	}
	
	public ActivityOrderVO addActOrder(Integer mem_no,
			LocalDateTime act_booking_date,Integer act_order_total_price,
			String act_order_title,String act_order_name,
			String act_order_phone,String act_order_email,
			String act_order_credit_card) {
		
		ActivityOrderVO vo = new ActivityOrderVO();
		
		vo.setMem_no(mem_no);
		vo.setAct_booking_date(act_booking_date);
		vo.setAct_order_total_price(act_order_total_price);
		vo.setAct_order_title(act_order_title);
		vo.setAct_order_name(act_order_name);
		vo.setAct_order_phone(act_order_phone);
		vo.setAct_order_email(act_order_email);
		vo.setAct_order_credit_card(act_order_credit_card);
		
		return dao.insert(vo);
	}
	
	public void updateActOrder(Integer act_order_no,Integer mem_no,
			LocalDateTime act_booking_date,Integer act_order_total_price,
			String act_order_title,String act_order_name,
			String act_order_phone,String act_order_email,
			String act_order_credit_card) {
		
		ActivityOrderVO vo = new ActivityOrderVO();
		
		vo.setAct_order_no(act_order_no);
		vo.setMem_no(mem_no);
		vo.setAct_booking_date(act_booking_date);
		vo.setAct_order_total_price(act_order_total_price);
		vo.setAct_order_title(act_order_title);
		vo.setAct_order_name(act_order_name);
		vo.setAct_order_phone(act_order_phone);
		vo.setAct_order_email(act_order_email);
		vo.setAct_order_credit_card(act_order_credit_card);
		
		dao.update(vo);
	}
	
	public ActivityOrderVO getActOrderByPk(Integer act_order_no) {
		return dao.findByPk(act_order_no);
	}
	
	public List<ActivityOrderVO> getActOrderByMemberNo(Integer mem_no) {
		return dao.findByMemberNo(mem_no);
	}
	
	public List<ActivityOrderVO> getAll() {
		return dao.getAll();
	}
	
	public void insertWithOrderDetails(Integer mem_no,
			LocalDateTime act_booking_date,Integer act_order_total_price,
			String act_order_title,String act_order_name,
			String act_order_phone,String act_order_email,
			String act_order_credit_card,List<ActivityOrderDetailVO> list) {
		
		ActivityOrderVO vo = new ActivityOrderVO();
		
		vo.setMem_no(mem_no);
		vo.setAct_booking_date(act_booking_date);
		vo.setAct_order_total_price(act_order_total_price);
		vo.setAct_order_title(act_order_title);
		vo.setAct_order_name(act_order_name);
		vo.setAct_order_phone(act_order_phone);
		vo.setAct_order_email(act_order_email);
		vo.setAct_order_credit_card(act_order_credit_card);
		
		dao.insertWithOrderDetails(vo, list);
	}
}
