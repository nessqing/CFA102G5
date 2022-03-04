package com.activityEvaluationReport.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivityEvaluationReportVO implements Serializable {
	
	private Integer	act_evaluation_no;
	private Integer mem_no;
	private LocalDateTime act_report_date;
	private Integer act_evaluation_report_reason;
	private Integer act_evaluation_report_state;
	
}
