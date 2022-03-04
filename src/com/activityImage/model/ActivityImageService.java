package com.activityImage.model;

import java.util.List;

public class ActivityImageService {
	private I_ActivityImageDAO dao;
	
	public ActivityImageService() {
		dao = new ActivityImageDAO();
	}
	
	public ActivityImageVO addActImage(Integer act_no,byte[] act_img) {
		ActivityImageVO vo = new ActivityImageVO();
		
		vo.setAct_no(act_no);
		vo.setAct_img(act_img);
		
		return dao.insert(vo);
	}
	
	public void deleteActImage(Integer act_img_no) {
		ActivityImageVO vo = new ActivityImageVO();
		
		vo.setAct_img_no(act_img_no);
		
		dao.delete(act_img_no);
	}

	public void updateActImage(Integer act_img_no,
			Integer act_no,byte[] act_img) {
		ActivityImageVO vo = new ActivityImageVO();
		
		vo.setAct_img_no(act_img_no);
		vo.setAct_no(act_no);
		vo.setAct_img(act_img);
		
		dao.update(vo);
	}
	
	public ActivityImageVO getActImageByPk(Integer act_img_no) {
		return dao.findByPk(act_img_no);
	}
	
	public List<ActivityImageVO> getActImageByActNo(Integer act_no) {
		return dao.findByActNo(act_no);
	}
	
	public List<ActivityImageVO> getAll() {
		return dao.getAll();
	}
	
}
