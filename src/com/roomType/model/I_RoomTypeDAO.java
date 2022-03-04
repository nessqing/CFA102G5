package com.roomType.model;

import java.sql.Date;
import java.util.List;

public interface I_RoomTypeDAO {
	public RoomTypeVO insert(RoomTypeVO roomTypeVO);

	public RoomTypeVO update(RoomTypeVO roomTypeVO);

	public RoomTypeVO getOne(Integer type_no);

	public List<RoomTypeVO> getAll();

	public List<RoomTypeVO> getAllFront();

	public void changeState(Integer type_no, Boolean type_state);

	public List<RoomTypeVO> getEnoughType(Date start_date, Date end_date, Integer qty, Integer guest);

	public List<RoomTypeVO> getNotEnoughType(Date start_date, Date end_date, Integer qty, Integer guest);

	public List<RoomTypeVO> paymentCheck(Date start_date, Date end_date, Integer qty, Integer type_no);
}
