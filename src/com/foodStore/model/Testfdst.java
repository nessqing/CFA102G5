package com.foodStore.model;

import java.util.List;


public class Testfdst {

	public static void main(String[] args) {
		I_FoodStoreDAO ifddao = new FoodStoreJDBCDAO();
		FoodStoreVO fdvo = new FoodStoreVO();
		
		//新增店家
//		fdvo.setFd_name("蓮香麵店");
//		fdvo.setFd_address("970花蓮縣花蓮市南京街270號");
//		fdvo.setFd_longitude(23.97543);
//		fdvo.setFd_latitude(121.59802);
//		fdvo.setFd_service("");
//		fdvo.setFd_state(true);
//		fdvo.setFd_class_no(2);
//		
//		ifddao.insertFoodStore(fdvo);
//		System.out.println(fdvo);
//		System.out.println("新增成功");
		
		//修改店家
//		fdvo.setFd_class_no(2);
//		fdvo.setFd_name("41441");
//		fdvo.setFd_address("91470花蓮縣花蓮市中和街228號");
//		fdvo.setFd_longitude(123.943);
//		fdvo.setFd_latitude(121.02);
//		fdvo.setFd_service("1111");
//		fdvo.setFd_state(true);
//		fdvo.setFd_no(5);                            //修改編號幾的資料
//		
//		ifddao.updateFoodStore(fdvo);
//		System.out.println("修改成功");
		
		//查詢店家
		FoodStoreVO vo = ifddao.getOneStore(4);
		System.out.println("編號 :"+vo.getFd_no());
		System.out.println("類別 :"+vo.getFd_class_no());
		System.out.println("店名 :"+vo.getFd_name());
		System.out.println("地址 :"+vo.getFd_address());
		System.out.println("經度 :"+vo.getFd_longitude());
		System.out.println("緯度 :"+vo.getFd_latitude());
		System.out.println("服務 :"+vo.getFd_service());
		System.out.println("狀態 :"+vo.getFd_state());
		System.out.println("查詢成功");
		
		//查詢店家類別
		
//		List<FoodStoreVO> fdFK = ifddao.findfdByFK(3);
//		for (FoodStoreVO fdfkall : fdFK) {
//		System.out.print("編號 :"+fdfkall.getFd_no());
//		System.out.print("店名 :"+fdfkall.getFd_name());
//		System.out.print("地址 :"+fdfkall.getFd_address());
//		System.out.print("經度 :"+fdfkall.getFd_longitude());
//		System.out.print("緯度 :"+fdfkall.getFd_latitude());
//		System.out.println("服務 :"+fdfkall.getFd_service());
//		System.out.print("狀態 :"+fdfkall.getFd_state());
//		System.out.println("類別 :"+fdfkall.getFd_class_no());
//		System.out.println("============================");
//		}		
//		System.out.println("查詢成功");
		
		//查詢全部店家
		
//		List<FoodStoreVO> fdFK = ifddao.getAllFoodStore();
//		for (FoodStoreVO fdfkall : fdFK) {
//		System.out.print("編號 :"+fdfkall.getFd_no());
//		System.out.print("店名 :"+fdfkall.getFd_name());
//		System.out.print("地址 :"+fdfkall.getFd_address());
//		System.out.print("經度 :"+fdfkall.getFd_longitude());
//		System.out.print("緯度 :"+fdfkall.getFd_latitude());
//		System.out.println("服務 :"+fdfkall.getFd_service());
//		System.out.print("狀態 :"+fdfkall.getFd_state());
//		System.out.println("類別 :"+fdfkall.getFd_class_no());
//		System.out.println("============================");
//		}		
//		System.out.println("查詢成功");
	}

}
