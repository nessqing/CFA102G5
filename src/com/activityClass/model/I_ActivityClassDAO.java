package com.activityClass.model;

import java.util.List;

public interface I_ActivityClassDAO {
	public ActivityClassVO insert(ActivityClassVO actClassVO);
	public void update(ActivityClassVO actClassVO);
	public ActivityClassVO findByPk(Integer act_class_no);
	public void switchActivityClassState(Integer act_class_no,Boolean act_class_state);
	public List<ActivityClassVO> getAll();
}
