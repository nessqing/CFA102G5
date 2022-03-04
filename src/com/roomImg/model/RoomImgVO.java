package com.roomImg.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RoomImgVO implements Serializable {
	private Integer img_no;
	private Integer type_no;
	private byte[] type_img;
}
