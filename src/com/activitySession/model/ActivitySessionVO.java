package com.activitySession.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ActivitySessionVO implements Serializable{
	private Integer act_session_no;
	private Integer act_no;
	private LocalDate act_start_date;
	private LocalDate act_end_date;
	private Integer act_session_real_number;
	private LocalDate act_session_start_date;
	private LocalTime act_session_start_time;
	private Integer act_session_upper_limit;
	private Integer act_session_lower_limit;
	private Boolean act_session_hold_state = false;
	
}
