package com.activity.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityService {
	private I_ActivityDAO dao;
	
	public ActivityService() {
		dao = new ActivityDAO();
	}
	
	public ActivityVO addAct(Integer act_class_no,String act_name,
			Integer act_price,String act_location,Integer act_schedule_time,
			String act_instruction,String act_gather_location,
			Double act_location_longitude,Double act_location_latitude,
			Integer act_sell_number,Integer act_join_number,
			Integer act_evaluation_number,Double act_average_star_number,
			Boolean act_state) {
		
		ActivityVO vo = new ActivityVO();
		
		vo.setAct_class_no(act_class_no);
		vo.setAct_name(act_name);
		vo.setAct_price(act_price);
		vo.setAct_location(act_location);
		vo.setAct_schedule_time(act_schedule_time);
		vo.setAct_instruction(act_instruction);
		vo.setAct_gather_location(act_gather_location);
		vo.setAct_location_longitude(act_location_longitude);
		vo.setAct_location_latitude(act_location_latitude);
		vo.setAct_sell_number(act_sell_number);
		vo.setAct_join_number(act_join_number);
		vo.setAct_evaluation_number(act_evaluation_number);
		vo.setAct_average_star_number(act_average_star_number);
		vo.setAct_state(new Boolean(act_state));
		
		return dao.insert(vo);
	}
	
	public void updateAct(Integer act_no,Integer act_class_no,String act_name,
			Integer act_price,String act_location,Integer act_schedule_time,
			String act_instruction,String act_gather_location,
			Double act_location_longitude,Double act_location_latitude,
			Integer act_sell_number,Integer act_join_number,
			Integer act_evaluation_number,Double act_average_star_number,
			Boolean act_state) {
		
		ActivityVO vo = new ActivityVO();
		
		vo.setAct_no(act_no);
		vo.setAct_class_no(act_class_no);
		vo.setAct_name(act_name);
		vo.setAct_price(act_price);
		vo.setAct_location(act_location);
		vo.setAct_schedule_time(act_schedule_time);
		vo.setAct_instruction(act_instruction);
		vo.setAct_gather_location(act_gather_location);
		vo.setAct_location_longitude(act_location_longitude);
		vo.setAct_location_latitude(act_location_latitude);
		vo.setAct_sell_number(act_sell_number);
		vo.setAct_join_number(act_join_number);
		vo.setAct_evaluation_number(act_evaluation_number);
		vo.setAct_average_star_number(act_average_star_number);
		vo.setAct_state(act_state);
		
		dao.update(vo);
	}
	
	public ActivityVO getActByPk(Integer act_no){
		return dao.findByPk(act_no);
	}
	
	public List<ActivityVO> getActByName(String act_name){
		return dao.findByName(act_name);
	}
	
	public List<ActivityVO> getActByClassNo(Integer act_class_no){
		return dao.findByActClassNo(act_class_no);
	}
	
	public List<ActivityVO> getPopularAct(){
		return dao.getPopularAct();
	}
	//取得上架活動
	public List<ActivityVO> getAll(){
		return dao.getAll();
	}
	public void updateActSellNumber(Integer act_no, Integer act_sell_number) {
		dao.updateActSellNumber(act_no, act_sell_number);
	}
	
	public void switchActState(Integer act_no,Boolean act_state) {
		dao.switchActState(act_no, act_state);
	}
}
