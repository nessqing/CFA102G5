package com.roomImg.model;

import java.util.List;

import com.roomType.model.RoomTypeVO;

public class RoomImgService {
	
	private I_RoomImgDAO dao;

	public RoomImgService() {
		dao = new RoomImgDAO();
	}
	
	public RoomImgVO addRoomImg(Integer type_no, byte[] type_img) {

		RoomImgVO roomImgVO = new RoomImgVO();
		roomImgVO.setType_no(type_no);
		roomImgVO.setType_img(type_img);
		
		return dao.insert(roomImgVO);
	}
	
	public void deleteRoomImg(Integer img_no) {
		dao.delete(img_no);
	}
	
	public RoomImgVO getOneRoomImg(Integer img_no) {
		return dao.getOne(img_no);
	}
	
	public List<RoomImgVO> getAllByType(Integer type_no) {
		return dao.getAllByType(type_no);
	}
	
	public List<RoomImgVO> getAll() {
		return dao.getAll();
	}
}
