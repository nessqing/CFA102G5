package com.activitySession.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ActivitySessionService {
	private I_ActivitySessionDAO dao;
	
	public ActivitySessionService() {
		dao = new ActivitySessionDAO();
	}
	
	public ActivitySessionVO addActSession(Integer act_no,
			LocalDate act_start_date,LocalDate act_end_date,
			Integer act_session_real_number,LocalDate act_session_start_date,
			LocalTime act_session_start_time,Integer act_session_upper_limit,
			Integer act_session_lower_limit,Boolean act_session_hold_state) {
		
		ActivitySessionVO vo = new ActivitySessionVO();
		
		vo.setAct_no(act_no);
		vo.setAct_start_date(act_start_date);
		vo.setAct_end_date(act_end_date);
		vo.setAct_session_real_number(act_session_real_number);
		vo.setAct_session_start_date(act_session_start_date);
		vo.setAct_session_start_time(act_session_start_time);
		vo.setAct_session_upper_limit(act_session_upper_limit);
		vo.setAct_session_lower_limit(act_session_lower_limit);
		vo.setAct_session_hold_state(act_session_hold_state);
		
		return dao.insert(vo);
	}
	
	public void updateActSession(Integer act_session_no,Integer act_no,
			LocalDate act_start_date,LocalDate act_end_date,
			Integer act_session_real_number,LocalDate act_session_start_date,
			LocalTime act_session_start_time,Integer act_session_upper_limit,
			Integer act_session_lower_limit,Boolean act_session_hold_state) {
		
		ActivitySessionVO vo = new ActivitySessionVO();
		
		vo.setAct_session_no(act_session_no);
		vo.setAct_no(act_no);
		vo.setAct_start_date(act_start_date);
		vo.setAct_end_date(act_end_date);
		vo.setAct_session_real_number(act_session_real_number);
		vo.setAct_session_start_date(act_session_start_date);
		vo.setAct_session_start_time(act_session_start_time);
		vo.setAct_session_upper_limit(act_session_upper_limit);
		vo.setAct_session_lower_limit(act_session_lower_limit);
		vo.setAct_session_hold_state(act_session_hold_state);
		
		dao.update(vo);
	}
	
	public ActivitySessionVO getActSessionByPk(Integer act_session_no) {
		return dao.findByPk(act_session_no);
	}
	
	
	public void switchActSessionState(Integer act_session_no,Boolean act_session_hold_state) {
		dao.switchActSessionState(act_session_no, act_session_hold_state);
	}
	
	public List<ActivitySessionVO> getAll() {
		return dao.getAll();
	}
	
	public List<ActivitySessionVO> getActSessionByActNo(Integer act_no) {
		return dao.findByActNo(act_no);
	}
	
	public void updateActSessionRealJoinNumber(Integer act_session_no,Integer act_session_real_number) {
		dao.updateActSessionRealNumber(act_session_no, act_session_real_number);
	}
	
	public List<ActivitySessionVO> getActSessionShowFront() {
		return dao.getAll().stream()
				.filter(act -> act.getAct_session_hold_state() == true)
				.collect(Collectors.toList());
	}
}
