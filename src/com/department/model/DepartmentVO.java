package com.department.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DepartmentVO implements Serializable{
	private Integer dep_no ;
	private String dep_name;
	private Boolean dep_state;
	
	@Override
	public String toString() {
		return "DepartmentVO [dep_no=" + dep_no + ", dep_name=" + dep_name + ", dep_state=" + dep_state + "]";
	}
	
}
