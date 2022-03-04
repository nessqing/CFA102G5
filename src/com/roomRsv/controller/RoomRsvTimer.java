package com.roomRsv.controller;

import java.util.TimerTask;

import com.roomOrder.model.RoomOrderService;
import com.roomRsv.model.RoomRsvService;

public class RoomRsvTimer extends TimerTask {

	@Override
	public void run() {
		RoomRsvService roomRsvSvc = new RoomRsvService();
		roomRsvSvc.addRoomRsv();
		System.out.println("正在新增預約表");

		roomRsvSvc.delete();
		System.out.println("正在刪除過期預約表");

		RoomOrderService roomOrderSvc = new RoomOrderService();
		roomOrderSvc.overdue();
		System.out.println("正在將逾期訂單改已完成");
	}

}
