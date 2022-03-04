package com.roomOrderDetail.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RoomOrderDetailVO implements Serializable {
	private Integer detail_no;
	private Integer ord_no;
	private Date checkin_date;
	private Date checkout_date;
	private String rm_no;
	private Integer detail_state;
}
