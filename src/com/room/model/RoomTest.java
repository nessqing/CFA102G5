package com.room.model;

public class RoomTest {

	public static void main(String[] args) {
		I_RoomDAO dao = new RoomJDBCDAO();

		// 新增一個房間物件,設定值，再用dao裡面的方法insert新增這個物件
//		RoomVO roomVO = new RoomVO();
//		roomVO.setRm_no("111");
//		roomVO.setType_no(1);
//		roomVO.setRm_info("XXX風景好美！");
//		dao.insert(roomVO);

		// 修改
//		RoomVO roomVO = new RoomVO();
//		roomVO.setRm_no("301");
//		roomVO.setType_no(3);
//		roomVO.setRm_info("3F，禁菸房，山景，出電梯右轉第一間");
//		roomVO.setRm_state(1);
//		roomVO.setName_title("222");
//		dao.update(roomVO);

		// 修改 入住
//		RoomVO roomVO = new RoomVO();
//		roomVO.setRm_no("111");
//		roomVO.setName_title("郭老師");
//		dao.updateCheckin(roomVO);

		// 修改 退房
//		RoomVO roomVO = new RoomVO();
//		roomVO.setRm_no("111");
//		dao.updateCheckout(roomVO);

		// 查詢一筆 用PK
//		RoomVO roomVO = dao.getOne("111");
//		System.out.print(roomVO.getRm_no() + ",");
//		System.out.print(roomVO.getType_no() + ",");
//		System.out.print(roomVO.getRm_info() + ",");
//		System.out.print(roomVO.getRm_state() + ",");
//		System.out.println(roomVO.getName_title());

		// 查詢全部
//		List<RoomVO> list = dao.getAll();
//		for (RoomVO roomVO : list) {
//			System.out.print(roomVO.getRm_no() + ",");
//			System.out.print(roomVO.getType_no() + ",");
//			System.out.print(roomVO.getRm_info() + ",");
//			System.out.print(roomVO.getRm_state() + ",");
//			System.out.println(roomVO.getName_title());
//		}

		// 查詢該房型的空房，入住時選房間用
//		List<RoomVO> list = dao.getAllByTypeState(5);
//		for (RoomVO roomVO : list) {
//			System.out.print(roomVO.getRm_no() + ",");
//			System.out.print(roomVO.getType_no() + ",");
//			System.out.print(roomVO.getRm_info() + ",");
//			System.out.print(roomVO.getRm_state() + ",");
//			System.out.println(roomVO.getName_title());
//		}

	}
}
