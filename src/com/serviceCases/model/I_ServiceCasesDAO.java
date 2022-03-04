package com.serviceCases.model;

import java.util.*;



public interface I_ServiceCasesDAO {
	//新增案件
	public void insert(ServiceCasesVO serviceCasesVO);
	//案件回覆
    public void update(ServiceCasesVO serviceCasesVO);
    //搜尋一個案件
    public ServiceCasesVO findByPrimaryKey(Integer case_no);
    //搜尋全部案件
    public List<ServiceCasesVO> getAll();
    
    
 
}
