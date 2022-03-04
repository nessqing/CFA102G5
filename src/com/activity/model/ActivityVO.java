package com.activity.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityVO implements Serializable{
	private Integer act_no;
	private Integer act_class_no;
	private String  act_name;
	private Integer act_price;
	private String  act_location;
	private Integer act_schedule_time;
	private String  act_instruction;
	private String  act_gather_location;
	private Double  act_location_longitude;
	private Double  act_location_latitude;
	private Integer	act_sell_number;
	private Integer	act_join_number;
	private Integer	act_evaluation_number;
	private Double  act_average_star_number;
	private Boolean act_state = true;
	
}
