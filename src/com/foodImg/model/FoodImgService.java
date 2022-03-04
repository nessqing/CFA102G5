package com.foodImg.model;

import java.util.List;


public class FoodImgService {
	private I_FoodImgDAO dao;
	
	public FoodImgService() {
		dao = new FoodImgDAO();
	}
	
	public FoodImgVO addImg(Integer fd_no,byte[] fd_img) {

		FoodImgVO foodImgVO = new FoodImgVO();
		foodImgVO.setFd_no(fd_no);
		foodImgVO.setFd_img(fd_img);

		dao.insert(foodImgVO);
		return foodImgVO;
	}
	
	public FoodImgVO updateImg(Integer fd_no,byte[] fd_img,Integer fd_img_no) {

		FoodImgVO foodImgVO = new FoodImgVO();
		foodImgVO.setFd_no(fd_no);
		foodImgVO.setFd_img(fd_img);
		foodImgVO.setFd_img_no(fd_img_no);

		dao.update(foodImgVO);
		return foodImgVO;
	}
	
	public List<FoodImgVO> foodImg(Integer fd_no){
		return dao.findimgByFK(fd_no);
	}
	
	public List<FoodImgVO> allImg(){
		return dao.getAllImg();
	}
	public void deleteAcc(Integer fd_img_no) {
		dao.delete(fd_img_no);
	}
	
	public FoodImgVO findByPk(Integer fd_img_no) {
		return dao.findimgByPK(fd_img_no);
	}
	public FoodImgVO findImgOne(Integer fd_no) {
		return dao.findImgOne(fd_no);
	}
}
