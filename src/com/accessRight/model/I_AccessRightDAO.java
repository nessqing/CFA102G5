package com.accessRight.model;

import java.util.List;


public interface I_AccessRightDAO {
	public void insert(AccessRightVO accessrightVO);
	public void update(Integer dep_no,Integer fun_no,Integer dep_no2,Integer fun_no2);
	public void delete(Integer dep_no,Integer fun_no);
	public List<AccessRightVO> findDepByFK(int dep_no);
	public List<AccessRightVO> findFunByFK(int fun_no);
	public List<AccessRightVO> getAllAcc();
}
