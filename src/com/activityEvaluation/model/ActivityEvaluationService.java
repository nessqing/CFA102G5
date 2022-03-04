package com.activityEvaluation.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityEvaluationService {
	private I_ActivityEvaluationDAO dao;
	
	public ActivityEvaluationService() {
		dao = new ActivityEvaluationDAO();
	}
	
	public ActivityEvaluationVO addActEvaluation(Integer act_no,
			Integer mem_no,Integer act_evaluation_star_number,String act_evaluation_message,
			LocalDateTime act_evaluation_date,Boolean act_evaluation_state) {
		
		ActivityEvaluationVO vo = new ActivityEvaluationVO();
		
		vo.setAct_no(act_no);
		vo.setMem_no(mem_no);
		vo.setAct_evaluation_star_number(act_evaluation_star_number);
		vo.setAct_evaluation_message(act_evaluation_message);
		vo.setAct_evaluation_date(act_evaluation_date);
		vo.setAct_evaluation_state(act_evaluation_state);
		
		return dao.insert(vo);
	}
	
	public void updateActEvaluation(Integer act_evaluation_no,Integer act_no,
			Integer mem_no,Integer act_evaluation_star_number,String act_evaluation_message,
			LocalDateTime act_evaluation_date,Boolean act_evaluation_state) {
		
		ActivityEvaluationVO vo = new ActivityEvaluationVO();
		
		vo.setAct_evaluation_no(act_evaluation_no);
		vo.setAct_no(act_no);
		vo.setMem_no(mem_no);
		vo.setAct_evaluation_star_number(act_evaluation_star_number);
		vo.setAct_evaluation_message(act_evaluation_message);
		vo.setAct_evaluation_date(act_evaluation_date);
		vo.setAct_evaluation_state(act_evaluation_state);
		
		dao.update(vo);
	}
	
	public ActivityEvaluationVO getActEvaluationByPk(String act_evaluation_no) {
		return dao.findByPk(new Integer(act_evaluation_no));
	}
	
	public List<ActivityEvaluationVO> getActEvaluationByActNo(String act_no) {
		return dao.findByActNo(new Integer(act_no));
	}
	
	public List<ActivityEvaluationVO> getActEvaluationByMemberNo(String mem_no) {
		return dao.findByMemberNo(new Integer(mem_no));
	}
	
	//取得全部上架的
	public List<ActivityEvaluationVO> getAll() {
		return dao.getAll().stream()
				.filter(act -> act.getAct_evaluation_state() == true)
				.collect(Collectors.toList());
				
	}
	
}
