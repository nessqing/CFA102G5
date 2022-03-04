package com.activityEvaluation.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ActivityEvaluationVO implements Serializable {
	
	private Integer act_evaluation_no;
	private Integer act_no;
	private Integer mem_no;
	private Integer act_evaluation_star_number;
	private String  act_evaluation_message;
	private LocalDateTime act_evaluation_date;
	private Boolean act_evaluation_state;
	
}
