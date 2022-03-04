package com.activityPromotionDetail.model;

import java.util.List;
import java.util.Map;

public interface I_ActivityPromotionDetailDAO {
	public ActivityPromotionDetailVO insert(ActivityPromotionDetailVO actPromotionDetailVO);
	public void update(ActivityPromotionDetailVO actPromotionDetailVO);
	
	public List<ActivityPromotionDetailVO> findByActPromotionNo(Integer act_promotion_no);
	public List<ActivityPromotionDetailVO> findByActClassNo(Integer act_class_no);
	public List<ActivityPromotionDetailVO> getAll();
	
	public Map<String,String[]> getActPromotionDetailJoinActClass();
}
