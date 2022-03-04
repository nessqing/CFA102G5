package com.activityOrderDetail.model;

import java.util.List;

public class ActivityOrderDetailService {
	private I_ActivityOrderDetailDAO dao;
	
	public ActivityOrderDetailService() {
		dao = new ActivityOrderDetailDAO();
	}
	
	public ActivityOrderDetailVO addActOrderDetail(Integer act_order_no,
			Integer act_session_no,Integer act_real_join_number,
			Integer act_order_price, Integer act_coupon_price,
			Integer act_price_total,Integer act_order_detail_state) {
		
		ActivityOrderDetailVO vo = new ActivityOrderDetailVO();
		
		vo.setAct_order_no(act_order_no);
		vo.setAct_session_no(act_session_no);
		vo.setAct_real_join_number(act_real_join_number);
		vo.setAct_order_price(act_order_price);
		vo.setAct_coupon_price(act_coupon_price);
		vo.setAct_price_total(act_price_total);
		vo.setAct_order_detail_state(act_order_detail_state);
		
		return dao.insert(vo);
	}
	
	public void updateActOrderDetail(Integer act_order_detail_no,Integer act_order_no,
			Integer act_session_no,Integer act_real_join_number,
			Integer act_order_price, Integer act_coupon_price,
			Integer act_price_total,Integer act_order_detail_state) {
		
		ActivityOrderDetailVO vo = new ActivityOrderDetailVO();
		
		vo.setAct_order_detail_no(act_order_detail_no);
		vo.setAct_order_no(act_order_no);
		vo.setAct_session_no(act_session_no);
		vo.setAct_real_join_number(act_real_join_number);
		vo.setAct_order_price(act_order_price);
		vo.setAct_coupon_price(act_coupon_price);
		vo.setAct_price_total(act_price_total);
		vo.setAct_order_detail_state(act_order_detail_state);
		
		dao.update(vo);
	}
	
	public ActivityOrderDetailVO getActOrderDetailByPk(Integer act_order_detail_no){
		return dao.findByPk(act_order_detail_no);
	}
	
	public List<ActivityOrderDetailVO> getActOrderDetailByActOrderNo(Integer act_order_no){
		return dao.findByActOrderNo(act_order_no);
	}
	
	public List<ActivityOrderDetailVO> getActOrderDetailByActSessionNo(Integer act_session_no){
		return dao.findByActSessionNo(act_session_no);
	}
	//五種狀態下拉式選單 各帶值1~5
	public List<ActivityOrderDetailVO> getActOrderDetailByState(Integer act_order_detail_state){
		return dao.getActOrderDetailState(act_order_detail_state);
	}
	
	public List<ActivityOrderDetailVO> getAll(){
		return dao.getAll();
	}
	
	public void orderDetailUpdate(Integer act_real_join_number,Integer act_price_total,Integer act_order_no,Integer act_session_no) {
		dao.orderDetailUpdate(act_real_join_number, act_price_total, act_order_no, act_session_no);
	}
	
	public void switchOrderDetailState(Integer act_order_detail_no,Integer act_order_detail_state) {
		dao.switchOrderDetailState(act_order_detail_no, act_order_detail_state);
	}
	
	public void switchOrderDetailState(Integer act_order_no,Integer act_session_no,Integer act_order_detail_state) {
		dao.switchOrderDetailState(act_order_no, act_session_no,act_order_detail_state);
	}
	
	public ActivityOrderDetailVO getByActOrderNoAndByActSessionNO(Integer act_order_no,Integer act_session_no){
		return dao.findByActOrderNoAndByActSessionNO(act_order_no, act_session_no);
	}
	
}
