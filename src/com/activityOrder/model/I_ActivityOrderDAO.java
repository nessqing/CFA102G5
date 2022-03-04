package com.activityOrder.model;

import java.util.List;

import com.activityOrderDetail.model.ActivityOrderDetailVO;


public interface I_ActivityOrderDAO {
	public ActivityOrderVO insert(ActivityOrderVO actOrderVO);
	public void update(ActivityOrderVO actOrderVO);
	
	public ActivityOrderVO findByPk(Integer act_order_no);
	public List<ActivityOrderVO> findByMemberNo(Integer mem_no);
	public List<ActivityOrderVO> getAll();
	public void insertWithOrderDetails(ActivityOrderVO actOrderVO,List<ActivityOrderDetailVO> list);
}
