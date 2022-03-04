package com.activityPromotion.model;

import java.util.List;

public interface I_ActivityPromotionDAO {
	public ActivityPromotionVO insert(ActivityPromotionVO actPromotionVO);
	public void update(ActivityPromotionVO actPromotionVO);
	
	public ActivityPromotionVO findByPk(Integer act_promotion_no);
	public List<ActivityPromotionVO> getAll();
}
