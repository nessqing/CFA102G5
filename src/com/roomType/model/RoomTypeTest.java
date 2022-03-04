package com.roomType.model;

import java.util.List;

public class RoomTypeTest {

	public static void main(String[] args) {
		I_RoomTypeDAO dao = new RoomTypeJDBCDAO();

		// 新增一個房型物件,設定值，再用dao裡面的方法insert新增這個物件
//		RoomTypeVO roomTypeVO = new RoomTypeVO();
//		roomTypeVO.setType_name("02房型名稱");
//		roomTypeVO.setType_qty(1);
//		roomTypeVO.setType_price(11);
//		roomTypeVO.setType_size(1);
//		roomTypeVO.setBed_size("1張單人床");
//		roomTypeVO.setType_info("1這個房間有三張分很開的單人床");
//		roomTypeVO.setType_facility("1有樸克牌");
//		dao.insert(roomTypeVO);

		// 修改
//		RoomTypeVO roomTypeVO = new RoomTypeVO();
//		roomTypeVO.setType_no(11);
//		roomTypeVO.setType_name("X修改的房型名稱");
//		roomTypeVO.setType_qty(9);
//		roomTypeVO.setType_price(9);
//		roomTypeVO.setType_size(9);
//		roomTypeVO.setBed_size("9張單人床");
//		roomTypeVO.setType_info("這個房間有99張分很開的單人床");
//		roomTypeVO.setType_facility("9有樸克牌");
//		roomTypeVO.setType_state(false);
//		dao.update(roomTypeVO);

		// 查詢一筆 用PK
//		RoomTypeVO roomTypeVO = dao.getOne(12);
//		System.out.print(roomTypeVO.getType_no() + ",");
//		System.out.print(roomTypeVO.getType_name() + ",");
//		System.out.print(roomTypeVO.getType_qty() + ",");
//		System.out.print(roomTypeVO.getType_price() + ",");
//		System.out.print(roomTypeVO.getType_size() + ",");
//		System.out.print(roomTypeVO.getBed_size() + ",");
//		System.out.print(roomTypeVO.getType_info() + ",");
//		System.out.print(roomTypeVO.getType_facility() + ",");
//		System.out.println(roomTypeVO.getType_state());

		// 查詢足夠的房型
		List<RoomTypeVO> list = dao.getEnoughType(java.sql.Date.valueOf("2021-10-08"),
				java.sql.Date.valueOf("2021-10-09"), 4, 3);
		for (RoomTypeVO roomTypeVO : list) {
			System.out.print(roomTypeVO.getType_no() + ",");
			System.out.print(roomTypeVO.getType_name() + ",");
			System.out.print(roomTypeVO.getType_qty() + ",");
			System.out.print(roomTypeVO.getType_price() + ",");
			System.out.print(roomTypeVO.getType_size() + ",");
			System.out.print(roomTypeVO.getBed_size() + ",");
			System.out.print(roomTypeVO.getType_info() + ",");
			System.out.print(roomTypeVO.getType_facility() + ",");
			System.out.println(roomTypeVO.getType_state());
		}

		// 查詢全部
//		List<RoomTypeVO> list = dao.getAll();
//		for (RoomTypeVO roomTypeVO : list) {
//			System.out.print(roomTypeVO.getType_no() + ",");
//			System.out.print(roomTypeVO.getType_name() + ",");
//			System.out.print(roomTypeVO.getType_qty() + ",");
//			System.out.print(roomTypeVO.getType_price() + ",");
//			System.out.print(roomTypeVO.getType_size() + ",");
//			System.out.print(roomTypeVO.getBed_size() + ",");
//			System.out.print(roomTypeVO.getType_info() + ",");
//			System.out.print(roomTypeVO.getType_facility() + ",");
//			System.out.println(roomTypeVO.getType_state());
//		}

	}
}
