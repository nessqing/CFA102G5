package com.activityClass.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class ActivityClassVO implements Serializable{

	private Integer act_class_no;
	private String  act_class_name;
	private Boolean act_class_state = true;
	
}
