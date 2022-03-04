package com.activityPromotionDetail.model;

import java.util.List;

public class ActivityPromotionDetailService {
	private I_ActivityPromotionDetailDAO dao;
	
	public ActivityPromotionDetailService() {
		dao = new ActivityPromotionDetailDAO();
	}
	
	public ActivityPromotionDetailVO addActPromotionDetail(Integer act_promotion_no,
			Integer act_class_no,Double act_discount_price) {
		
		ActivityPromotionDetailVO vo = new ActivityPromotionDetailVO();
		
		vo.setAct_promotion_no(act_promotion_no);
		vo.setAct_class_no(act_class_no);
		vo.setAct_discount_price(act_discount_price);
		
		return dao.insert(vo);
	}
	
	//改折數
	public void updateActPromotionDetail(Integer act_promotion_no,
			Integer act_class_no,Double act_discount_price) {
		
		ActivityPromotionDetailVO vo = new ActivityPromotionDetailVO();
		
		vo.setAct_promotion_no(act_promotion_no);
		vo.setAct_class_no(act_class_no);
		vo.setAct_discount_price(act_discount_price);
		
		dao.update(vo);
	}
	
	public List<ActivityPromotionDetailVO> getActPromotionDetailByActPromotionNo(Integer act_promotion_no){
		return dao.findByActPromotionNo(act_promotion_no);
	}
	
	public List<ActivityPromotionDetailVO> getActPromotionDetailByActClassNo(Integer act_class_no){
		return dao.findByActClassNo(act_class_no);
	}
	
	public List<ActivityPromotionDetailVO> getAll(){
		return dao.getAll();
	}
}
