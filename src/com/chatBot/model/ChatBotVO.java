package com.chatBot.model;


import lombok.Data;



@Data
public class ChatBotVO {
	//Primary key msg_no
		private Integer qes_no;
		private String qes_title;
		private String qes_ans;
		private String qes_comp;
		private Integer qes_class;
		
}
