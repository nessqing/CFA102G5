package com.activityPromotion.model;

import java.time.LocalDate;
import java.util.List;

public class ActivityPromotionService {
	private I_ActivityPromotionDAO dao;
	
	public ActivityPromotionService() {
		dao = new ActivityPromotionDAO();
	}
	
	public ActivityPromotionVO addActivityPromotion(String act_promotion_name,
			LocalDate act_promotion_start_date,LocalDate act_promotion_end_date) {
		
		ActivityPromotionVO vo = new ActivityPromotionVO();
		
		vo.setAct_promotion_name(act_promotion_name);
		vo.setAct_promotion_start_date(act_promotion_start_date);
		vo.setAct_promotion_end_date(act_promotion_end_date);
		
		return dao.insert(vo);
	}
	
	public void updateActivityPromotion(Integer act_promotion_no,String act_promotion_name,
			LocalDate act_promotion_start_date,LocalDate act_promotion_end_date) {
		
		ActivityPromotionVO vo = new ActivityPromotionVO();
		
		vo.setAct_promotion_no(act_promotion_no);
		vo.setAct_promotion_name(act_promotion_name);
		vo.setAct_promotion_start_date(act_promotion_start_date);
		vo.setAct_promotion_end_date(act_promotion_end_date);
		
		dao.update(vo);
	}
	
	public ActivityPromotionVO getActivityPromotionByPk(Integer act_promotion_no) {
		return dao.findByPk(act_promotion_no);
	}
	
	public List<ActivityPromotionVO> getAll() {
		return dao.getAll();
	}
}
