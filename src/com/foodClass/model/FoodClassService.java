package com.foodClass.model;

import java.util.List;


public class FoodClassService {
private I_FoodClassDAO dao;
	
	public FoodClassService() {
		dao = new FoodClassDAO();
	}
	
	public FoodClassVO addFoodClass(String fd_class_name, Boolean fd_class_state) {

		FoodClassVO foodClassVO = new FoodClassVO();
		foodClassVO.setFd_class_name(fd_class_name);
		foodClassVO.setFd_class_state(fd_class_state);

		dao.insertFoodClass(foodClassVO);
		return foodClassVO;
	}
	
	public FoodClassVO updateFoodClass(String fd_class_name, Boolean fd_class_state,Integer fd_class_no) {

		FoodClassVO foodClassVO = new FoodClassVO();

		foodClassVO.setFd_class_name(fd_class_name);
		foodClassVO.setFd_class_state(fd_class_state);
		foodClassVO.setFd_class_no(fd_class_no);

		dao.updateFoodClass(foodClassVO);
		return foodClassVO;
	}
	
	public List<FoodClassVO> getAll() {
		return dao.getAllFoodClass();
	}
	
	public FoodClassVO getClassPK(Integer fd_class_no) {
		return dao.getClassPK(fd_class_no);
	}
	
}
