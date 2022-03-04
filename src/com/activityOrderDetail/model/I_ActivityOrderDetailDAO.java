package com.activityOrderDetail.model;

import java.sql.Connection;
import java.util.List;

//狀態
public interface I_ActivityOrderDetailDAO {
	public ActivityOrderDetailVO insert(ActivityOrderDetailVO actOrderDetailVO);
	public void update(ActivityOrderDetailVO actOrderDetailVO);
	//更新 只能針對該活動訂單明細編號 做額外資訊的更改 然後狀態要改成3(已改期)
	public ActivityOrderDetailVO findByPk(Integer act_order_detail_no);
	public List<ActivityOrderDetailVO> findByActOrderNo(Integer act_order_no);
	public List<ActivityOrderDetailVO> findByActSessionNo(Integer act_session_no);
	public List<ActivityOrderDetailVO> getActOrderDetailState(Integer act_order_detail_state);
	public List<ActivityOrderDetailVO> getAll();
	
	public void orderDetailUpdate(Integer act_real_join_number,Integer act_price_total,Integer act_order_no,Integer act_session_no);
	public void switchOrderDetailState(Integer act_order_detail_no,Integer act_order_detail_state);
	public void insertWithOrder(ActivityOrderDetailVO actOrderDetailVO,Connection con);
	public ActivityOrderDetailVO findByActOrderNoAndByActSessionNO(Integer act_order_no,Integer act_session_no);
	public void switchOrderDetailState(Integer act_order_no,Integer act_session_no,Integer act_order_detail_state);
}
