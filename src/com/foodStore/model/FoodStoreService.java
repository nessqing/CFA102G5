package com.foodStore.model;

import java.util.List;

public class FoodStoreService {
	private I_FoodStoreDAO dao;
	
	public FoodStoreService() {
		dao = new FoodStoreDAO();
	}
	
	public FoodStoreVO addFoodStore(String fd_name,String fd_address,Double fd_longitude,
			Double fd_latitude,String fd_service,Boolean fd_state,Integer fd_class_no) {
		
		FoodStoreVO foodStoreVO = new FoodStoreVO();
		

		foodStoreVO.setFd_name(fd_name);
		foodStoreVO.setFd_address(fd_address);
		foodStoreVO.setFd_longitude(fd_longitude);
		foodStoreVO.setFd_latitude(fd_latitude);
		foodStoreVO.setFd_service(fd_service);
		foodStoreVO.setFd_state(fd_state);
		foodStoreVO.setFd_class_no(fd_class_no);
		
		dao.insertFoodStore(foodStoreVO);
		return foodStoreVO;
	}
	
	public FoodStoreVO updateFoodStore(String fd_name,String fd_address,Double fd_longitude,
			Double fd_latitude,String fd_service,Boolean fd_state,Integer fd_class_no,Integer fd_no) {
		
		FoodStoreVO foodStoreVO = new FoodStoreVO();
		
		foodStoreVO.setFd_name(fd_name);
		foodStoreVO.setFd_address(fd_address);
		foodStoreVO.setFd_longitude(fd_longitude);
		foodStoreVO.setFd_latitude(fd_latitude);
		foodStoreVO.setFd_service(fd_service);
		foodStoreVO.setFd_state(fd_state);
		foodStoreVO.setFd_class_no(fd_class_no);
		foodStoreVO.setFd_no(fd_no);
		
		dao.updateFoodStore(foodStoreVO);
		return foodStoreVO;
	}
	public FoodStoreVO getOneStore(Integer fd_no) {
		return dao.getOneStore(fd_no);
	}
	
	public List<FoodStoreVO> findfdByFK(Integer fd_class_no){
		return dao.findfdByFK(fd_class_no);
	}
	
	public List<FoodStoreVO> getAllFoodStore(){
		return dao.getAllFoodStore();
	}
	
}