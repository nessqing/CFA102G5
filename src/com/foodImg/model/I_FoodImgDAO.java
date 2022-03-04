package com.foodImg.model;

import java.util.List;

public interface I_FoodImgDAO {
	public void insert(FoodImgVO foodImgVO);
	public void update(FoodImgVO foodImgVO);
	public List<FoodImgVO> findimgByFK(Integer fd_no);					//一間店家所有照片
	public FoodImgVO findimgByPK(Integer fd_img_no);					//一張照片
	public FoodImgVO findImgOne(Integer fd_no);						//店家一張照片
	public List<FoodImgVO> getAllImg();								//全部照片
	public void delete(Integer fd_img_no);
}
