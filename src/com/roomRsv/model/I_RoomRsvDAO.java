package com.roomRsv.model;

import java.sql.Date;
import java.util.List;

public interface I_RoomRsvDAO {
	public void insert();

//	改期=先用舊日期cancel，再用新日期reserve
	public void reserve(Integer qty, Integer type_no, Date start_date, Date end_date);

	public void cancel(Integer qty, Integer type_no, Date start_date, Date end_date);

	public void checkOutEarly(Integer type_no, Date end_date);

	public void delete();

	public RoomRsvVO getOneByDateType(Date rsv_date, Integer type_no);

	public List<RoomRsvVO> getOneDayByDate(Date rsv_date); // 同一天的各房型預訂

	public List<RoomRsvVO> getNotRsv(Integer stay, Integer type_no); // 取得不可預約的日期 前台房型詳情用

	public List<RoomRsvVO> getAll(); // 全部

	public List<RoomRsvVO> getAllByType(Integer type_no); // 同房型的每天預訂

}
