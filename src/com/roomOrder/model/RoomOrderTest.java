package com.roomOrder.model;

public class RoomOrderTest {

	public static void main(String[] args) {
		I_RoomOrderDAO dao = new RoomOrderJDBCDAO();

		// 新增
//		RoomOrderVO roomOrderVO = new RoomOrderVO();
//		roomOrderVO.setMem_no(2);
//		roomOrderVO.setType_no(1);
//		roomOrderVO.setStart_date(LocalDate.of(2021,9,1));
//		roomOrderVO.setEnd_date(LocalDate.of(2021,9,2));
//		roomOrderVO.setRm_num(1);
//		roomOrderVO.setPrice(3000);
//		roomOrderVO.setTotal_price(3000);
//		roomOrderVO.setNote("希望能提前入住");
//		roomOrderVO.setTitle("女士");
//		roomOrderVO.setName("我不說");	
//		roomOrderVO.setPhone("0911111222");
//		roomOrderVO.setEmail("sfsfsd@gmail.com");	
//		roomOrderVO.setPayment("000011112222");	
//		dao.insert(roomOrderVO);

		// UPDATE 已完成
//		RoomOrderVO roomOrderVO = new RoomOrderVO();
//		dao.update(roomOrderVO);

		// 已取消
//		RoomOrderVO roomOrderVO = new RoomOrderVO();
//		roomOrderVO.setOrd_no(1);
//		dao.cancel(roomOrderVO);

		// 已改期
//		RoomOrderVO roomOrderVO = new RoomOrderVO();
//		roomOrderVO.setStart_date(LocalDate.of(2021,9,11));
//		roomOrderVO.setEnd_date(LocalDate.of(2021,9,12));
//		roomOrderVO.setOrd_no(3);
//		dao.change(roomOrderVO);

		// 查詢一筆 用PK
//		RoomOrderVO roomOrderVO = dao.getOne(1);
//		System.out.print(roomOrderVO.getOrd_no() + ",");
//		System.out.print(roomOrderVO.getMem_no() + ",");
//		System.out.print(roomOrderVO.getType_no() + ",");
//		System.out.print(roomOrderVO.getStart_date() + ",");
//		System.out.print(roomOrderVO.getEnd_date() + ",");
//		System.out.print(roomOrderVO.getRm_num() + ",");
//		System.out.print(roomOrderVO.getPrice() + ",");
//		System.out.print(roomOrderVO.getTotal_price() + ",");
//		System.out.print(roomOrderVO.getNote() + ",");
//		System.out.print(roomOrderVO.getTitle() + ",");
//		System.out.print(roomOrderVO.getName() + ",");
//		System.out.print(roomOrderVO.getTotal_price() + ",");
//		System.out.print(roomOrderVO.getPhone() + ",");
//		System.out.print(roomOrderVO.getEmail() + ",");
//		System.out.print(roomOrderVO.getPayment() + ",");
//		System.out.print(roomOrderVO.getOrd_date() + ",");
//		System.out.println(roomOrderVO.getOrd_state());

		// 查詢 全部
//		List<RoomOrderVO> list = dao.getAll();
//		for (RoomOrderVO roomOrderVO : list) {
//		System.out.print(roomOrderVO.getOrd_no() + ",");
//		System.out.print(roomOrderVO.getMem_no() + ",");
//		System.out.print(roomOrderVO.getType_no() + ",");
//		System.out.print(roomOrderVO.getStart_date() + ",");
//		System.out.print(roomOrderVO.getEnd_date() + ",");
//		System.out.print(roomOrderVO.getRm_num() + ",");
//		System.out.print(roomOrderVO.getPrice() + ",");
//		System.out.print(roomOrderVO.getTotal_price() + ",");
//		System.out.print(roomOrderVO.getNote() + ",");
//		System.out.print(roomOrderVO.getTitle() + ",");
//		System.out.print(roomOrderVO.getName() + ",");
//		System.out.print(roomOrderVO.getTotal_price() + ",");
//		System.out.print(roomOrderVO.getPhone() + ",");
//		System.out.print(roomOrderVO.getEmail() + ",");
//		System.out.print(roomOrderVO.getPayment() + ",");
//		System.out.print(roomOrderVO.getOrd_date() + ",");
//		System.out.println(roomOrderVO.getOrd_state());
//		}

		// 查詢 會員的全部訂單
//		List<RoomOrderVO> list = dao.getAllByMem(2);
//		for (RoomOrderVO roomOrderVO : list) {
//		System.out.print(roomOrderVO.getOrd_no() + ",");
//		System.out.print(roomOrderVO.getMem_no() + ",");
//		System.out.print(roomOrderVO.getType_no() + ",");
//		System.out.print(roomOrderVO.getStart_date() + ",");
//		System.out.print(roomOrderVO.getEnd_date() + ",");
//		System.out.print(roomOrderVO.getRm_num() + ",");
//		System.out.print(roomOrderVO.getPrice() + ",");
//		System.out.print(roomOrderVO.getTotal_price() + ",");
//		System.out.print(roomOrderVO.getNote() + ",");
//		System.out.print(roomOrderVO.getTitle() + ",");
//		System.out.print(roomOrderVO.getName() + ",");
//		System.out.print(roomOrderVO.getTotal_price() + ",");
//		System.out.print(roomOrderVO.getPhone() + ",");
//		System.out.print(roomOrderVO.getEmail() + ",");
//		System.out.print(roomOrderVO.getPayment() + ",");
//		System.out.print(roomOrderVO.getOrd_date() + ",");
//		System.out.println(roomOrderVO.getOrd_state());
//		}

	}
}
