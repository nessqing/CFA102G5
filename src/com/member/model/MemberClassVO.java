package com.member.model;

import java.io.Serializable;

import java.util.Arrays;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberClassVO implements Serializable{

		private Integer mem_no;
		private String mem_name;
		private Integer mem_sex;
		private String mem_mail;
		private String mem_password; //5
		private String mem_mobile;
		private byte[] mem_img;
		private String mem_add;
		private Boolean mem_state; //預設停權
		
		
}
