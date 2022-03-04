package com.foodClass.model;

import java.util.List;

public interface I_FoodClassDAO {

	public void insertFoodClass(FoodClassVO foodclassVO);
	public void updateFoodClass(FoodClassVO foodclassVO);
	public FoodClassVO getClassPK(Integer fd_class_no);
	public List<FoodClassVO> getAllFoodClass();
}
