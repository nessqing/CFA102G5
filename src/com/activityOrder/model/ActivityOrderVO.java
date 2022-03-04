package com.activityOrder.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivityOrderVO implements Serializable{
	private Integer act_order_no;
	private Integer mem_no;
	private LocalDateTime act_booking_date;
	private Integer act_order_total_price;
	private String act_order_title;
	private String act_order_name;
	private String act_order_phone;
	private String act_order_email;
	private String act_order_credit_card;
	
}

