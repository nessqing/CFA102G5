package com.activityEvaluationReport.model;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityEvaluationReportService {
	private I_ActivityEvaluationReportDAO dao;
	
	public ActivityEvaluationReportService() {
		dao = new ActivityEvaluationReportDAO();
	}
	
	public ActivityEvaluationReportVO addActEvaluationReport(
			Integer act_evaluation_no,Integer mem_no,
			LocalDateTime act_report_date,Integer act_evaluation_report_reason,
			Integer act_evaluation_report_state) {
		
		ActivityEvaluationReportVO vo = new ActivityEvaluationReportVO();	
		
		vo.setAct_evaluation_no(act_evaluation_no);
		vo.setMem_no(mem_no);
		vo.setAct_report_date(act_report_date);
		vo.setAct_evaluation_report_reason(act_evaluation_report_reason);
		vo.setAct_evaluation_report_state(act_evaluation_report_state);
		
		return dao.insert(vo);
	}
	
	public void deleteActEvaluationReport(Integer act_evaluation_no,
			Integer mem_no) {
		dao.delete(act_evaluation_no, mem_no);
	}
	
	public List<ActivityEvaluationReportVO> getActEvaluationReportByActEvaluationNo(Integer act_evaluation_no){
		return dao.findByActEvaluationNo(act_evaluation_no);
	}
	
	public List<ActivityEvaluationReportVO> getActEvaluationReportByMemberNo(Integer mem_no){
		return dao.findByMemberNo(mem_no);
	}
	
	public List<ActivityEvaluationReportVO> getAll(){
		return dao.getAll();
	}
}
