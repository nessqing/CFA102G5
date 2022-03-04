package com.instantMessaging.model;

import java.time.LocalDateTime;
import java.util.List;

public class InstantMessagingService {
	private I_InstantMessagingDAO dao;
	
	public InstantMessagingService() {
		dao = new InstantMessagingDAO();
	}
	
	public InstantMessagingVO addInstantMessaging(Integer mem_no,Boolean msg_direct,
			String msg,byte[] msg_img,LocalDateTime now_clk) {
		InstantMessagingVO instantMessagingVO = new InstantMessagingVO();
		instantMessagingVO.setMem_no(mem_no);
		instantMessagingVO.setMsg_direct(msg_direct);
		instantMessagingVO.setMsg(msg);
		instantMessagingVO.setMsg_img(msg_img);
		instantMessagingVO.setNow_clk(now_clk);
		dao.insert(instantMessagingVO);
		
		return instantMessagingVO;		
	}
	
	public InstantMessagingVO updateInstantMessaging(Integer msg_no,Integer mem_no,Boolean msg_direct,
			String msg,byte[] msg_img,LocalDateTime now_clk) {
		InstantMessagingVO instantMessagingVO = new InstantMessagingVO();
		instantMessagingVO.setMsg_no(msg_no);
		instantMessagingVO.setMem_no(mem_no);
		instantMessagingVO.setMsg_direct(msg_direct);
		instantMessagingVO.setMsg(msg);
		instantMessagingVO.setMsg_img(msg_img);
		instantMessagingVO.setNow_clk(now_clk);
		dao.update(instantMessagingVO);
		
		return instantMessagingVO;		
	}
	
	public List<InstantMessagingVO> findByMemNo(Integer mem_no) {
		return dao.findByMemNo(mem_no);		
	}

	
}
