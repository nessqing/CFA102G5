package com.employee.model;

import java.util.List;

public class EmpService {
	private I_EmployeeDAO dao;
	
	public EmpService() {
		dao = new EmployeeDAO();
	}
	
	
	public EmployeeVO addEmp(String emp_password,String emp_name,String emp_mail,Boolean emp_state,Integer dep_no) {
		
		EmployeeVO employeeVO = new EmployeeVO();
		
		employeeVO.setEmp_password(emp_password);
		employeeVO.setEmp_name(emp_name);
		employeeVO.setEmp_mail(emp_mail);
		employeeVO.setEmp_state(emp_state);
		employeeVO.setDep_no(dep_no);
		
		dao.insertEmp(employeeVO);
		return employeeVO;
	}
	
	public EmployeeVO updateEmp(String emp_password,String emp_name,String emp_mail,Boolean emp_state,Integer dep_no,Integer emp_no) {
		
		EmployeeVO employeeVO = new EmployeeVO();
		
		employeeVO.setEmp_password(emp_password);
		employeeVO.setEmp_name(emp_name);
		employeeVO.setEmp_mail(emp_mail);
		employeeVO.setEmp_state(emp_state);
		employeeVO.setDep_no(dep_no);
		employeeVO.setEmp_no(emp_no);
		
		dao.updateEmp(employeeVO);
		return employeeVO;
	}
	
	public EmployeeVO getOneEmp(Integer emp_no) {
		return dao.findEmpByPK(emp_no);
	}
	
	public List<EmployeeVO> getEmpInDep(Integer dep_no){
		return dao.findDepByFK(dep_no);
	}
	
	public List<EmployeeVO> getAllEmp(){
		return dao.getAllEmp();
	}
	public EmployeeVO Login(String emp_mail,String emp_password) {
		return dao.login(emp_mail, emp_password);
	}
	
}
