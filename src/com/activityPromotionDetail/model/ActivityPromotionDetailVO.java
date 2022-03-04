package com.activityPromotionDetail.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityPromotionDetailVO implements Serializable{
	private Integer act_promotion_no;
	private Integer act_class_no;
	private Double act_discount_price;
	
}
