package com.instantMessaging.model;
import java.util.*;

public interface I_InstantMessagingDAO {
	//新增訊息
	public void insert(InstantMessagingVO instantMessagingVO);
	//更新訊息
    public void update(InstantMessagingVO instantMessagingVO);
    //顯示某個會員所有所有訊息
    public List<InstantMessagingVO> findByMemNo(Integer mem_no);
    
    
}
