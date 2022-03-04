package com.roomRsv.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RoomRsvVO implements Serializable {
	private Date rsv_date;
	private Integer type_no;
	private Integer rm_total;
	private Integer rsv_total;
}
