package com.creditcard.model;

import java.io.Serializable;

import com.member.model.MemberClassVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreditcardClassVO implements Serializable{
	
	private Integer crd_no;
	private Integer mem_no;
	private String crd_name;
	private String crd_num;
	private String crd_expiry;
	private String crd_security_code;
	private String crd_barcode;

}
