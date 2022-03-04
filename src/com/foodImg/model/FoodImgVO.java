package com.foodImg.model;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Data;

@Data
public class FoodImgVO implements Serializable{

	private Integer fd_img_no;
	private Integer fd_no;
	private byte[] fd_img;
	
}
