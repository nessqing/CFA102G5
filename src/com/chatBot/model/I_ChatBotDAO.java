package com.chatBot.model;

import java.util.*;

import com.instantMessaging.model.InstantMessagingVO;

public interface I_ChatBotDAO {
	public void insert(ChatBotVO chatBotVO);
    public void update(ChatBotVO chatBotVO);
    public void delete(Integer qes_no);
    public ChatBotVO findByPrimaryKey(Integer qes_no);
    public List<ChatBotVO> getAll();
    
}
