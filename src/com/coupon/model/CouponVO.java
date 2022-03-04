package com.coupon.model;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CouponVO implements Serializable {
	private Integer coupon_no;
	private Integer mem_no;
	private Integer coupon_value;
	private LocalDate coupon_expiry;
}
