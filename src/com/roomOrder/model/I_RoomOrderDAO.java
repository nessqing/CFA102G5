package com.roomOrder.model;

import java.util.List;

import com.roomOrderDetail.model.RoomOrderDetailVO;

public interface I_RoomOrderDAO {
	public Integer insertAuto(RoomOrderVO roomOrderVO, List<RoomOrderDetailVO> list); // 新增訂單 自增主鍵

	public RoomOrderVO insert(RoomOrderVO roomOrderVO); // 新增訂單

	public void update(Integer type_no); // 入住時改已完成

	public void cancel(RoomOrderVO roomOrderVO); // 已取消：改總金額0、狀態，再改預約表

	public void change(RoomOrderVO roomOrderVO); // 已改期

	public void overdue(); // 逾期訂單改已完成(退房日已到，但不來住)

	public RoomOrderVO getOne(Integer ord_no); // 一筆訂單

	public List<RoomOrderVO> getAll(); // 後台：訂單管理

	public List<RoomOrderVO> getAllByOrdState(Integer ord_no); // 後台：訂單管理

	public List<RoomOrderVO> getAllByType(Integer type_no); // 後台：訂單管理

	public List<RoomOrderVO> getAllByMem(Integer mem_no); // 前台：會員中心 訂房訂單管理

	public List<RoomOrderVO> checkInList(); // 後台：待入住訂單

	public Integer getRoomStayRate();
}
