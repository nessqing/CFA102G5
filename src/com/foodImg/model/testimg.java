package com.foodImg.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class testimg {

	public static void main(String[] args) throws IOException {

		I_FoodImgDAO dao = new FoodImgJDBCDAO();
		FoodImgVO advo = new FoodImgVO();
		
		//新增一張店家照片
//		advo.setFd_no(2);												//店家編號
//		byte[] pic = getPicture("src/com/foodImg/model/3.jpg");			//店家照片
//		advo.setFd_img(pic);
//		dao.insert(advo);
//		System.out.println("新增成功");
		
		//修改一張店家照片
//		advo.setFd_no(1);												//要修改的店家編號
//		byte[] pic = getPicture("src/com/foodImg/model/3.jpg");		
//		advo.setFd_img(pic);
//		advo.setFd_img_no(1);
//		dao.update(advo);
//		System.out.println("修改成功");
		
		//查詢店家照片
//		List<FoodImgVO> vo = dao.findimgByFK(1);
//		for (FoodImgVO imgno : vo) {
//			System.out.println("照片編號 :"+imgno.getFd_img_no());
//			System.out.println("店家編號 :"+imgno.getFd_no());
//			System.out.println("照片 :"+imgno.getFd_img());
//			
//			System.out.println("============================");
//			}		
//			System.out.println("查詢成功");
		
			
		//查詢所有照片	
		List<FoodImgVO> list = dao.getAllImg();
		for (FoodImgVO imgall : list) {
			System.out.println("照片編號 :"+imgall.getFd_img_no());
			System.out.println("店家編號 :"+imgall.getFd_no());
			System.out.println("照片 :"+imgall.getFd_img());

			System.out.println("============================");
			}
			System.out.println("查詢成功");
	}



public static byte[] getPicture(String photo) throws IOException {			//新增照片
	FileInputStream fil = new FileInputStream(photo);
	byte[] buffer = new byte[fil.available()];
	fil.read(buffer);
	fil.close();
	return buffer;
}


}