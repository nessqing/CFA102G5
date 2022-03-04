package com.activityEvaluationReport.model;

import java.util.List;


public interface I_ActivityEvaluationReportDAO {
	public ActivityEvaluationReportVO insert(ActivityEvaluationReportVO actEvaluationReportVO);
	public void delete(Integer act_evaluation_no,Integer mem_no);
	
	public List<ActivityEvaluationReportVO> findByActEvaluationNo(Integer act_evaluation_no);
	public List<ActivityEvaluationReportVO> findByMemberNo(Integer mem_no);
	public List<ActivityEvaluationReportVO> getAll();
}
