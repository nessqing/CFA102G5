package com.roomRsv.controller;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

@WebListener
public class RoomRsvListener implements ServletContextListener {
	Timer timer;
	private Logger logger = Logger.getLogger(RoomRsvServlet.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("--------++房型排程器已啟動++------");
		timer = new Timer();
		logger.info("新增預約表資料");
		logger.info("刪除過期預約表");
		logger.info("逾期訂單改已完成");
		timer.schedule(new RoomRsvTimer(), 1000, 86400000);
		System.out.println("--------++結束++------");
	}
}
