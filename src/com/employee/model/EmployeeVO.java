package com.employee.model;

import java.io.Serializable;


import lombok.Data;
@Data
public class EmployeeVO implements Serializable{
	private Integer emp_no;
	private String emp_password;
	private String emp_name;
	private String emp_mail;
	private Boolean emp_state;
	private Integer dep_no;
	
	@Override
	public String toString() {
		return "EmployeeVO [emp_no=" + emp_no + ", emp_password=" + emp_password + ", emp_name=" + emp_name
				+ ", emp_mail=" + emp_mail + ", emp_state=" + emp_state + ", dep_no=" + dep_no + "]";
	}

	
}
