package com.activityPromotion.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ActivityPromotionVO implements Serializable{
	
	private Integer act_promotion_no;
	private String  act_promotion_name;
	private LocalDate act_promotion_start_date;
	private LocalDate act_promotion_end_date;
	
	
}
