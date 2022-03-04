package com.roomImg.model;

import java.util.List;

public interface I_RoomImgDAO {
	public RoomImgVO insert(RoomImgVO roomImgVO);
	public void delete(Integer img_no);
	public RoomImgVO getOne(Integer img_no);
//	單個房型的所有圖片
	public List<RoomImgVO> getAllByType(Integer type_no);
	public List<RoomImgVO> getAll();
}
