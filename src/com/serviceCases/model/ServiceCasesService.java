package com.serviceCases.model;

import java.util.List;



public class ServiceCasesService {
	private I_ServiceCasesDAO dao;
	
	public ServiceCasesService() {
		dao = new ServiceCasesDAO();
		
	}
	
	public ServiceCasesVO addServiceCases(Integer mem_no,String case_title,String case_des,
			String case_reply,Integer case_state) {
		ServiceCasesVO serviceCasesVO = new ServiceCasesVO();
		serviceCasesVO.setMem_no(mem_no);
		serviceCasesVO.setCase_title(case_title);
		serviceCasesVO.setCase_des(case_des);
		serviceCasesVO.setCase_reply(case_reply);
		serviceCasesVO.setCase_state(case_state);
		dao.insert(serviceCasesVO);
		return serviceCasesVO;		
	}
	
	public ServiceCasesVO updateServiceCases(Integer case_no,Integer mem_no,String case_title,String case_des,
			String case_reply,Integer case_state) {
		ServiceCasesVO serviceCasesVO = new ServiceCasesVO();
		serviceCasesVO.setCase_no(case_no);
		serviceCasesVO.setMem_no(mem_no);
		serviceCasesVO.setCase_title(case_title);
		serviceCasesVO.setCase_des(case_des);
		serviceCasesVO.setCase_reply(case_reply);
		serviceCasesVO.setCase_state(case_state);
		dao.update(serviceCasesVO);
		return serviceCasesVO;		
	}
	
	public ServiceCasesVO getOneServiceCases(Integer case_no) {
		return dao.findByPrimaryKey(case_no);
	}
	public List<ServiceCasesVO> getAll(){
		return dao.getAll();
	}

}
