package com.employee.model;

import java.util.List;

public class TestEmp {

	public static void main(String[] args) {
		I_EmployeeDAO iemp = new EmployeeJDBCDAO();
		EmployeeVO emp = new EmployeeVO();
		
		//新增

		emp.setEmp_password("2525267");
		emp.setEmp_name("隨便");
		emp.setEmp_mail("Ddmsamd@gmail.com");
		emp.setEmp_state(true);
		emp.setDep_no(1);
		
		iemp.insertEmp(emp);
		System.out.println(emp);
		
		//修改
//		emp.setDep_no(1);
//		emp.setEmp_password("66666");
//		emp.setEmp_name("一三");
//		emp.setEmp_mail("11122222@gmail.com");
//		emp.setEmp_state(true);
//		emp.setEmp_no(1);
//		
//		iemp.updateEmp(emp);
//		System.out.println("修改成功");
		
		//查詢
//		EmployeeVO empPK = iemp.findEmpByPK(5);
//		System.out.println("編號 :"+empPK.getEmp_no());
//		System.out.println("密碼 :"+empPK.getEmp_password());
//		System.out.println("姓名 :"+empPK.getEmp_name());
//		System.out.println("郵件 :"+empPK.getEmp_mail());
//		System.out.println("狀態 :"+empPK.getEmp_state());
//		System.out.println("部門 :"+empPK.getDep_no());
//		
//		System.out.println("查詢成功");
		
//		EmployeeVO empPK = iemp.login("4a5a9@yahoo.com.tw", "dsa456a");
//
//		System.out.println("編號 :"+empPK.getEmp_no());
//		System.out.println("密碼 :"+empPK.getEmp_password());
//		System.out.println("姓名 :"+empPK.getEmp_name());
//		System.out.println("郵件 :"+empPK.getEmp_mail());
//		System.out.println("狀態 :"+empPK.getEmp_state());
//		System.out.println("部門 :"+empPK.getDep_no());
//		
//		System.out.println("查詢成功");
		
		
//		//查詢部門員工
//		List<EmployeeVO> empFK = iemp.findDepByFK(1);
//		for (EmployeeVO empall : empFK) {
//		System.out.println("編號 :"+empall.getEmp_no());
//		System.out.println("姓名 :"+empall.getEmp_name());
//		System.out.println("郵件 :"+empall.getEmp_mail());
//		System.out.println("狀態 :"+empall.getEmp_state());
//		System.out.println("部門 :"+empall.getDep_no());
//		System.out.println("============================");
//		}		
//		System.out.println("查詢成功");
//		
//		//查詢全部	
//		List<EmployeeVO> list = iemp.getAllEmp();
//		for (EmployeeVO empall : list) {
//		System.out.println("編號 :"+empall.getEmp_no());
//		System.out.println("部門 :"+empall.getDep_no());
//		System.out.println("姓名 :"+empall.getEmp_name());
//		System.out.println("郵件 :"+empall.getEmp_mail());
//		System.out.println("狀態 :"+empall.getEmp_state());
//		System.out.println("============================");
//		}
//		System.out.println("查詢成功");
	}

}
