package com.foodStore.model;

import java.util.List;

public interface I_FoodStoreDAO {
	public void insertFoodStore(FoodStoreVO foodstoreVO);
	public void updateFoodStore(FoodStoreVO foodstoreVO);
	public FoodStoreVO getOneStore(Integer fd_no);
	public List<FoodStoreVO> findfdByFK(int fd_class_no);
	public List<FoodStoreVO> getAllFoodStore();
}
