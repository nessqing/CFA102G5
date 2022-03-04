package com.util.activity;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ActivityScheduleListener implements ServletContextListener {
	
	Timer timer;
    
    public void contextDestroyed(ServletContextEvent sce)  {
    	timer.cancel();
    }

	
    public void contextInitialized(ServletContextEvent sce)  {

    	timer = new Timer();
    	timer.schedule(new ActivitySchedule(), 1000, 86400000);
    	
    	//86400000 one day
    }
	
}
