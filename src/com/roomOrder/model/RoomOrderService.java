package com.roomOrder.model;

import java.sql.Date;
import java.util.List;

import com.roomOrderDetail.model.RoomOrderDetailVO;

public class RoomOrderService {

	private I_RoomOrderDAO dao;

	public RoomOrderService() {
		dao = new RoomOrderDAO();
	}

	public RoomOrderVO insert(Integer mem_no, Integer type_no, Date start_date, Date end_date, Integer rm_num,
			Integer price, Integer total_price, String note, String title, String name, String phone, String email,
			String payment) {

		RoomOrderVO roomOrderVO = new RoomOrderVO();
		roomOrderVO.setMem_no(mem_no);
		roomOrderVO.setType_no(type_no);
		roomOrderVO.setStart_date(start_date);
		roomOrderVO.setEnd_date(end_date);
		roomOrderVO.setRm_num(rm_num);
		roomOrderVO.setPrice(price);
		roomOrderVO.setTotal_price(total_price);
		roomOrderVO.setNote(note);
		roomOrderVO.setTitle(title);
		roomOrderVO.setName(name);
		roomOrderVO.setPhone(phone);
		roomOrderVO.setEmail(email);
		roomOrderVO.setPayment(payment);

		return dao.insert(roomOrderVO);
	}

	public Integer insertAuto(Integer mem_no, Integer type_no, Date start_date, Date end_date, Integer rm_num,
			Integer price, Integer total_price, String note, String title, String name, String phone, String email,
			String payment, List<RoomOrderDetailVO> list) {

		RoomOrderVO roomOrderVO = new RoomOrderVO();
		roomOrderVO.setMem_no(mem_no);
		roomOrderVO.setType_no(type_no);
		roomOrderVO.setStart_date(start_date);
		roomOrderVO.setEnd_date(end_date);
		roomOrderVO.setRm_num(rm_num);
		roomOrderVO.setPrice(price);
		roomOrderVO.setTotal_price(total_price);
		roomOrderVO.setNote(note);
		roomOrderVO.setTitle(title);
		roomOrderVO.setName(name);
		roomOrderVO.setPhone(phone);
		roomOrderVO.setEmail(email);
		roomOrderVO.setPayment(payment);

		return dao.insertAuto(roomOrderVO, list);
	}

	public void updateRoomOrder(Integer ord_no) {
		dao.update(ord_no);
	}

	public void cancel(Integer ord_no) {

		RoomOrderVO roomOrderVO = new RoomOrderVO();
		roomOrderVO.setOrd_no(ord_no);

		dao.cancel(roomOrderVO);
	}

	public void changeRoomOrder(Integer ord_no, Date start_date, Date end_date) {

		RoomOrderVO roomOrderVO = new RoomOrderVO();
		roomOrderVO.setOrd_no(ord_no);
		roomOrderVO.setStart_date(start_date);
		roomOrderVO.setEnd_date(end_date);

		dao.change(roomOrderVO);
	}

	public void overdue() {
		dao.overdue();
	}

	public RoomOrderVO getOneRoomOrder(Integer ord_no) {
		return dao.getOne(ord_no);
	}

	public List<RoomOrderVO> getAllByOrdState(Integer ord_state) {
		return dao.getAllByOrdState(ord_state);
	}

	public List<RoomOrderVO> getAllByType(Integer type_no) {
		return dao.getAllByType(type_no);
	}

	public List<RoomOrderVO> getAllRoomOrder() {
		return dao.getAll();
	}

	public List<RoomOrderVO> getAllByMemRoomOrder(Integer mem_no) {
		return dao.getAllByMem(mem_no);
	}

	public List<RoomOrderVO> checkInList() {
		return dao.checkInList();
	}

	public Integer getRoomStayRate() {
		return dao.getRoomStayRate();
	}
}
