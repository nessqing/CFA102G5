package com.roomOrderDetail.model;

public class RoomOrderDetailTest {

	public static void main(String[] args) {
		I_RoomOrderDetailDAO dao = new RoomOrderDetailJDBCDAO();

		// 新增
//		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
//		detailVO.setOrd_no(5);
//		dao.insert(detailVO);

		// 入住
//		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
//		detailVO.setRm_no("305");
//		detailVO.setDetail_no(2);
//		dao.checkin(detailVO);

		// 退房
//		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
//		detailVO.setDetail_no(2);
//		dao.checkout(detailVO);

		// 查詢一筆 用PK
//		RoomOrderDetailVO detailVO = dao.getOne(1);
//		System.out.print(detailVO.getDetail_no() + ",");
//		System.out.print(detailVO.getOrd_no() + ",");
//		System.out.print(detailVO.getCheckin_date() + ",");
//		System.out.print(detailVO.getCheckout_date() + ",");
//		System.out.print(detailVO.getRm_no() + ",");
//		System.out.println(detailVO.getDetail_state());

		// 查詢全部
//		List<RoomOrderDetailVO> list = dao.getAll();
//		for (RoomOrderDetailVO detailVO : list) {
//			System.out.print(detailVO.getDetail_no() + ",");
//			System.out.print(detailVO.getOrd_no() + ",");
//			System.out.print(detailVO.getCheckin_date() + ",");
//			System.out.print(detailVO.getCheckout_date() + ",");
//			System.out.print(detailVO.getRm_no() + ",");
//			System.out.println(detailVO.getDetail_state());
//		}

		// 查詢全部 用訂單編號
//		List<RoomOrderDetailVO> list = dao.getAllByOrdno(1);
//		for (RoomOrderDetailVO detailVO : list) {
//			System.out.print(detailVO.getDetail_no() + ",");
//			System.out.print(detailVO.getOrd_no() + ",");
//			System.out.print(detailVO.getCheckin_date() + ",");
//			System.out.print(detailVO.getCheckout_date() + ",");
//			System.out.print(detailVO.getRm_no() + ",");
//			System.out.println(detailVO.getDetail_state());
//		}
	}
}
