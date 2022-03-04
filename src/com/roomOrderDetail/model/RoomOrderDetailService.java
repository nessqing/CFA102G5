package com.roomOrderDetail.model;

import java.util.List;

public class RoomOrderDetailService {

	private I_RoomOrderDetailDAO dao;

	public RoomOrderDetailService() {
		dao = new RoomOrderDetailDAO();
	}

	public RoomOrderDetailVO insert(Integer ord_no) {

		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
		detailVO.setOrd_no(ord_no);

		return dao.insert(detailVO);
	}

	public void insertAuto(Integer ord_no) {

		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
		detailVO.setOrd_no(ord_no);

		dao.insertAuto(detailVO, null);
	}

	public void checkinDetail(Integer detail_no, String rm_no) {

		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
		detailVO.setDetail_no(detail_no);
		detailVO.setRm_no(rm_no);

		dao.checkin(detailVO);
	}

	public void checkoutDetail(Integer detail_no) {

		RoomOrderDetailVO detailVO = new RoomOrderDetailVO();
		detailVO.setDetail_no(detail_no);

		dao.checkout(detailVO);
	}

	public RoomOrderDetailVO getOneDetail(Integer detail_no) {
		return dao.getOne(detail_no);
	}

	public List<RoomOrderDetailVO> checkoutList() {
		return dao.checkoutList();
	}

	public List<RoomOrderDetailVO> stayList() {
		return dao.stayList();
	}

	public List<RoomOrderDetailVO> getAll() {
		return dao.getAll();
	}

	public List<RoomOrderDetailVO> getAllByOrdno(Integer ord_no) {
		return dao.getAllByOrdno(ord_no);
	}
}
