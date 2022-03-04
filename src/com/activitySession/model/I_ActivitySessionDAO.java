package com.activitySession.model;

import java.util.List;

public interface I_ActivitySessionDAO {
	public ActivitySessionVO insert(ActivitySessionVO actSessionVO);
	public void update(ActivitySessionVO actSessionVO);
	public void switchActSessionState(Integer act_session_no,Boolean act_session_hold_state);
	public ActivitySessionVO findByPk(Integer act_session_no);
	public List<ActivitySessionVO> findByActNo(Integer act_no);
	public List<ActivitySessionVO> getAll();
	public void updateActSessionRealNumber(Integer act_session_no,Integer act_session_real_number);
}
