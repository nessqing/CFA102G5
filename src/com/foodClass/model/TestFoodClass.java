package com.foodClass.model;

import java.util.List;

public class TestFoodClass {

	public static void main(String[] args) {
		I_FoodClassDAO fdclasstest = new FoodClassJDBCDAO();
		FoodClassVO fdvo = new FoodClassVO();
//	新增=====================================	
		
//		fdvo.setFd_class_name("好吃");
//		fdvo.setFd_class_state(true);
//		
//		fdclasstest.insertFoodClass(fdvo);
//		
//		System.out.println("新增成功");
//		System.out.println(fdvo);
		
		//	修改=====================================
	
//		fdvo.setFd_class_no(7);
//		fdvo.setFd_class_name("很好吃");
//		fdvo.setFd_class_state(true);
//		
//		fdclasstest.updateFoodClass(fdvo);
//		System.out.println("修改成功");
		
		// 查詢=====================================
		fdvo = fdclasstest.getClassPK(1);
		
		System.out.println(fdvo.getFd_class_no());
		System.out.println(fdvo.getFd_class_name());
		System.out.println(fdvo.getFd_class_state());
		// 查詢=====================================
		
//		List<FoodClassVO> list = fdclasstest.getAllFoodClass();
//			for (FoodClassVO fdclass : list) {
//			System.out.print(fdclass.getFd_class_no() + ",");
//			System.out.print(fdclass.getFd_class_name() + ",");
//			System.out.println(fdclass.getFd_class_state());
//			}
//			System.out.println("查詢成功");
	}

}
