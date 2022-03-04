package com.function.model;

import java.io.Serializable;


import lombok.Data;
@Data
public class FunctionVO implements Serializable{
	private Integer fun_no;
	private String fun_name;
	
	@Override
	public String toString() {
		return "FunctionVO [fun_no=" + fun_no + ", fun_name=" + fun_name + "]";
	}

}
