package com.room.model;

import java.util.List;

public class RoomService {

	private I_RoomDAO dao;

	public RoomService() {
		dao = new RoomDAO();
	}

	public RoomVO addRoom(String rm_no, Integer type_no, String rm_info) {

		RoomVO roomVO = new RoomVO();
		roomVO.setRm_no(rm_no);
		roomVO.setType_no(type_no);
		roomVO.setRm_info(rm_info);

		return dao.insert(roomVO);
	}

	public RoomVO updateRoom(String rm_no, Integer type_no, String rm_info, Integer rm_state, String name_title) {

		RoomVO roomVO = new RoomVO();
		roomVO.setRm_no(rm_no);
		roomVO.setType_no(type_no);
		roomVO.setRm_info(rm_info);
		roomVO.setRm_state(rm_state);
		roomVO.setName_title(name_title);

		return dao.update(roomVO);
	}

	public void checkinRoom(String rm_no, String name_title) {

		RoomVO roomVO = new RoomVO();
		roomVO.setRm_no(rm_no);
		roomVO.setName_title(name_title);

		dao.updateCheckin(roomVO);
	}

	public void checkoutRoom(String rm_no) {

		RoomVO roomVO = new RoomVO();
		roomVO.setRm_no(rm_no);

		dao.updateCheckout(roomVO);
	}

	public void cleanRoom(String rm_no) {

		RoomVO roomVO = new RoomVO();
		roomVO.setRm_no(rm_no);

		dao.updateCheckout(roomVO);
	}

	public RoomVO getOneRoom(String rm_no) {
		return dao.getOne(rm_no);
	}

	public Integer getRmTotal(Integer type_no) {
		return dao.getRmTotal(type_no);
	}

	public List<RoomVO> getAllRoom() {
		return dao.getAll();
	}

	public List<RoomVO> getAllByTypeState(Integer type_no) {
		return dao.getAllByTypeState(type_no);
	}

	public List<RoomVO> getAllByRmState(Integer rm_state) {
		return dao.getAllByRmState(rm_state);
	}
}
