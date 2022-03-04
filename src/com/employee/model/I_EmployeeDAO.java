package com.employee.model;

import java.util.List;

public interface I_EmployeeDAO {
	public void insertEmp(EmployeeVO employeeVO);		
	public void updateEmp(EmployeeVO employeeVO);		
	public EmployeeVO findEmpByPK(int emp_no);
	public List<EmployeeVO> findDepByFK(int dep_no);
	public List<EmployeeVO> getAllEmp();
	public EmployeeVO login(String emp_mail,String emp_password);
}
