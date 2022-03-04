package com.foodClass.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class FoodClassVO implements Serializable{
	private Integer fd_class_no;
	private String fd_class_name;
	private Boolean fd_class_state;
	
	

	@Override
	public String toString() {
		return "FoodClassVO [fd_class_no=" + fd_class_no + ", fd_class_name=" + fd_class_name + ", fd_class_state="
				+ fd_class_state + "]";
	}

}
