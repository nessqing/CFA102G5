package com.activityEvaluation.model;

import java.util.List;


public interface I_ActivityEvaluationDAO {
	public ActivityEvaluationVO insert(ActivityEvaluationVO actEvaluationVO);
	public void update(ActivityEvaluationVO actEvaluationVO);
	public ActivityEvaluationVO findByPk(Integer act_evaluation_no);
	public List<ActivityEvaluationVO> findByActNo(Integer act_no);
	public List<ActivityEvaluationVO> findByMemberNo(Integer mem_no);
	public List<ActivityEvaluationVO> getAll();
}
