package com.roomType.model;

import java.sql.Date;
import java.util.List;

public class RoomTypeService {

	private I_RoomTypeDAO dao;

	public RoomTypeService() {
		dao = new RoomTypeDAO();
	}

	public RoomTypeVO addRoomType(String type_name, Integer type_qty, Integer type_price, Integer type_size,
			String bed_size, String type_info, String type_facility) {

		RoomTypeVO roomTypeVO = new RoomTypeVO();
		roomTypeVO.setType_name(type_name);
		roomTypeVO.setType_qty(type_qty);
		roomTypeVO.setType_price(type_price);
		roomTypeVO.setType_size(type_size);
		roomTypeVO.setBed_size(bed_size);
		roomTypeVO.setType_info(type_info);
		roomTypeVO.setType_facility(type_facility);

		return dao.insert(roomTypeVO);
	}

	public RoomTypeVO updateRoomType(Integer type_no, String type_name, Integer type_qty, Integer type_price,
			Integer type_size, String bed_size, String type_info, String type_facility, Boolean type_state) {

		RoomTypeVO roomTypeVO = new RoomTypeVO();
		roomTypeVO.setType_no(type_no);
		roomTypeVO.setType_name(type_name);
		roomTypeVO.setType_qty(type_qty);
		roomTypeVO.setType_price(type_price);
		roomTypeVO.setType_size(type_size);
		roomTypeVO.setBed_size(bed_size);
		roomTypeVO.setType_info(type_info);
		roomTypeVO.setType_facility(type_facility);
		roomTypeVO.setType_state(type_state);

		return dao.update(roomTypeVO);
	}

	public RoomTypeVO getOneRoomType(Integer type_no) {
		return dao.getOne(type_no);
	}

	public List<RoomTypeVO> getAllRoomType() {
		return dao.getAll();
	}

	public List<RoomTypeVO> getAllRoomFront() {
		return dao.getAllFront();
	}

	public void changeState(Integer type_no, Boolean type_state) {
		dao.changeState(type_no, type_state);
	}

	public List<RoomTypeVO> getEnoughType(Date start_date, Date end_date, Integer qty, Integer guest) {
		return dao.getEnoughType(start_date, end_date, qty, guest);
	}

	public List<RoomTypeVO> getNotEnoughType(Date start_date, Date end_date, Integer qty, Integer guest) {
		return dao.getNotEnoughType(start_date, end_date, qty, guest);
	}

	public List<RoomTypeVO> paymentCheck(Date start_date, Date end_date, Integer qty, Integer type_no) {
		return dao.paymentCheck(start_date, end_date, qty, type_no);
	}
}
