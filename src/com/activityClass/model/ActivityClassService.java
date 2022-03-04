package com.activityClass.model;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityClassService {
	private I_ActivityClassDAO dao;
	
	public ActivityClassService() {
		dao = new ActivityClassDAO();
	}
	
	public ActivityClassVO addActClass(String act_class_name) {
		
		ActivityClassVO vo = new ActivityClassVO();
		vo.setAct_class_name(act_class_name);
		
		return dao.insert(vo);
	}
	
	public void updateActClass(Integer act_class_no,
			String act_class_name,Boolean act_class_state) {
		
		ActivityClassVO vo = new ActivityClassVO();
		
		vo.setAct_class_no(act_class_no);
		vo.setAct_class_name(act_class_name);
		vo.setAct_class_state(act_class_state);
		
		dao.update(vo);
	}
	
	public ActivityClassVO getActClassByPk(Integer act_class_no) {
		return dao.findByPk(act_class_no);
	}
	public void switchActClassState(Integer act_class_no,Boolean act_class_state) {
		dao.switchActivityClassState(act_class_no, act_class_state);
	}
	public List<ActivityClassVO> getAll() {
		return dao.getAll();
	}
	
}
