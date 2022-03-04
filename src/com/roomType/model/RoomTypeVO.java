package com.roomType.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RoomTypeVO implements Serializable {
	private Integer type_no;
	private String type_name;
	private Integer type_qty;
	private Integer type_price;
	private Integer type_size;
	private String bed_size;
	private String type_info;
	private String type_facility;
	private Boolean type_state;
}
