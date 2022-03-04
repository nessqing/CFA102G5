package com.function.model;

import java.util.List;

public interface I_FunctionDAO {
	
	public void insertFun(FunctionVO functionVO);
	public void updateFun(FunctionVO functionVO);
	public List<FunctionVO> getAllFun();
	}


