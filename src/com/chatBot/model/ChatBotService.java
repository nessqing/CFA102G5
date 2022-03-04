package com.chatBot.model;

import java.util.List;

public class ChatBotService {
private I_ChatBotDAO dao;
	
	public ChatBotService() {
		dao = new ChatBotDAO();
	}
	
	public ChatBotVO addChatBot(String qes_title,String qes_ans, String qes_comp,Integer qes_class) {
		ChatBotVO chatBotVO = new ChatBotVO();
		chatBotVO.setQes_title(qes_title);
		chatBotVO.setQes_ans(qes_ans);
		chatBotVO.setQes_comp(qes_comp);
		chatBotVO.setQes_class(qes_class);
		dao.insert(chatBotVO);
		
		return chatBotVO;
	}
	
	public ChatBotVO updateChatBot(Integer qes_no, String qes_title,String qes_ans, String qes_comp,Integer qes_class) {
		ChatBotVO chatBotVO = new ChatBotVO();
		chatBotVO.setQes_title(qes_title);
		chatBotVO.setQes_ans(qes_ans);
		chatBotVO.setQes_comp(qes_comp);
		chatBotVO.setQes_class(qes_class);
		chatBotVO.setQes_no(qes_no);
		dao.update(chatBotVO);
		
		return chatBotVO;
	}
	
	public void deleteChatBot(Integer qes_no) {
		dao.delete(qes_no);
	}
	
	public ChatBotVO getOneChatBot(Integer qes_no) {
		return dao.findByPrimaryKey(qes_no);
	}
	
	public List<ChatBotVO> getAll() {
		return dao.getAll();
	}

}
