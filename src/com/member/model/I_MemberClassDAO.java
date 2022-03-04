package com.member.model;

import java.util.List;

public interface I_MemberClassDAO {
	void addMember(MemberClassVO memberClassVO);
	void updateMember(MemberClassVO memberClassVO);
	void updatePassword(MemberClassVO memberClassVO);
	void updateMemberstate(MemberClassVO memberClassVO);	
	MemberClassVO getOneBymail(String mem_mail);
	MemberClassVO getOneByMobile(String mem_mobile);
	List<MemberClassVO> getAllBySex(Integer mem_sex);
	List<MemberClassVO> getAllByState(Boolean memstate);
	List<MemberClassVO> getAll();
	MemberClassVO getOneByPK(Integer mem_no);
	public MemberClassVO isUser(String mem_mail, String mem_password);
}
