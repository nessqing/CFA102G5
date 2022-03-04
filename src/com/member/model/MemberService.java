package com.member.model;

import java.util.List;

public class MemberService {
	
	private I_MemberClassDAO dao;
	
	public MemberService() {
		dao = new MemberClassDAO();
	}
	
	public MemberClassVO addMember(String mem_name,Integer mem_sex,String mem_mail,String mem_password,
			String mem_mobile,byte[] mem_img,String mem_add,Boolean mem_state) {
		
		MemberClassVO memberVO = new MemberClassVO();
		
		memberVO.setMem_name(mem_name);
		memberVO.setMem_sex(mem_sex);
		memberVO.setMem_mail(mem_mail);
		memberVO.setMem_password(mem_password);
		memberVO.setMem_mobile(mem_mobile);
		memberVO.setMem_img(mem_img);
		memberVO.setMem_add(mem_add);
		memberVO.setMem_state(mem_state);
		
		dao.addMember(memberVO);
		return memberVO;
		
	}
	
	public MemberClassVO updateMember(String mem_name,Integer mem_sex,String mem_password,
			String mem_mobile,byte[] mem_img,String mem_add,Integer mem_no) {
		
		MemberClassVO memberVO = new MemberClassVO();
		memberVO.setMem_name(mem_name);
		memberVO.setMem_sex(mem_sex);
		memberVO.setMem_password(mem_password);
		memberVO.setMem_mobile(mem_mobile);
		memberVO.setMem_img(mem_img);
		memberVO.setMem_add(mem_add);
		memberVO.setMem_no(mem_no);
		
		dao.updateMember(memberVO);
		return memberVO;
	}
	
	public MemberClassVO updatePassword(String mem_password,Integer mem_no) {
		
		MemberClassVO memberVO = new MemberClassVO();
		memberVO.setMem_password(mem_password);
		memberVO.setMem_no(mem_no);
		
		dao.updatePassword(memberVO);
		return memberVO;
	}
	
	public MemberClassVO updateMemberstate(Boolean mem_state,Integer mem_no) {
		
		MemberClassVO memberVO = new MemberClassVO();
		memberVO.setMem_state(mem_state);
		memberVO.setMem_no(mem_no);

		dao.updateMemberstate(memberVO);
		return memberVO;
	}
	
	public MemberClassVO getOneBymail(String mem_mail) {
		
		return dao.getOneBymail(mem_mail);
	}
	
	public MemberClassVO getOneByMobile(String mem_mobile) {
		
		return dao.getOneByMobile(mem_mobile);
	}
	
	public List<MemberClassVO> getAllBySex(Integer mem_sex) {
		
		return dao.getAllBySex(mem_sex);
	}
	
	public List<MemberClassVO> getAllByState(Boolean mem_state) {
		
		return dao.getAllByState(mem_state);
	}
	
	public List<MemberClassVO> getAll(){
		
		return dao.getAll();
	}

	public MemberClassVO  getOne(Integer mem_no){
		return dao.getOneByPK(mem_no);
	}
	
	public MemberClassVO isUser(String mem_mail, String mem_password) {
		return dao.isUser(mem_mail,mem_password);
	}
	
	
	
	
	

}
