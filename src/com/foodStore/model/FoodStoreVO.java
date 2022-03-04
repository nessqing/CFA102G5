package com.foodStore.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FoodStoreVO implements Serializable{
	private Integer fd_no;
	private String fd_name;
	private String fd_address;
	private Double fd_longitude;
	private Double fd_latitude;
	private String fd_service;
	private Boolean fd_state;
	private Integer fd_class_no;
	
}
