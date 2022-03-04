package com.function.model;

import java.util.List;

public class FunService {
	private I_FunctionDAO dao ;
	
	public FunService() {
		dao = new FunctionDAO();
	}
	
	public FunctionVO addFun(Integer fun_no,String fun_name) {

		FunctionVO functionVO = new FunctionVO();
		
		functionVO.setFun_no(fun_no);
		functionVO.setFun_name(fun_name);

		dao.insertFun(functionVO);
		return functionVO;
	}
	
	public FunctionVO updateFun(String fun_name,Integer fun_no) {

		FunctionVO functionVO = new FunctionVO();
		
		functionVO.setFun_name(fun_name);
		functionVO.setFun_no(fun_no);

		dao.updateFun(functionVO);
		return functionVO;
	}
	
	public List<FunctionVO> getall(){
		return dao.getAllFun();
	}
	
}
