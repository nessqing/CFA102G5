package com.accessRight.model;

import java.util.List;

public class AccessRightService {
	private I_AccessRightDAO dao;
	
	public AccessRightService() {
		dao = new AccessRightDAO();
	}
	
	public AccessRightVO addAcc(Integer dep_no,Integer fun_no) {
		
		AccessRightVO accessRightVO = new AccessRightVO();
		
		accessRightVO.setDep_no(dep_no);
		accessRightVO.setFun_no(fun_no);
		
		dao.insert(accessRightVO);
		return accessRightVO;
	}
	
	public AccessRightVO updateAcc(Integer dep_no,Integer fun_no,Integer dep_no2,Integer fun_no2) {
		
		AccessRightVO accessRightVO = new AccessRightVO();
		
		accessRightVO.setDep_no(dep_no);
		accessRightVO.setFun_no(fun_no);
		accessRightVO.setDep_no(dep_no2);
		accessRightVO.setFun_no(fun_no2);
		
		dao.update(dep_no, fun_no, dep_no2, fun_no2);
		return accessRightVO;
	}
	
	public void deleteAcc(Integer dep_no,Integer fun_no) {
		dao.delete(dep_no, fun_no);
	}
	
	public List<AccessRightVO> depByFun(Integer dep_no){
		return dao.findDepByFK(dep_no);
	}
	
	public List<AccessRightVO> funBydep(Integer fun_no){
		return dao.findFunByFK(fun_no);
	}
	
	public List<AccessRightVO> accAll(){
		return dao.getAllAcc();
	}
	
}
