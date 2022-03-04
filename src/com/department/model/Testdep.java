package com.department.model;

import java.util.List;

public class Testdep {

	public static void main(String[] args) {
		I_DepartmentDAO deptest = new DepartmentJDBCDAO();
		DepartmentVO devo = new DepartmentVO();
		
		
		//	新增=====================================	
		
//		devo.setDep_name("產銷部門");
//		devo.setDep_state(true);
//		
//		deptest.insertDep(devo);
//		System.out.println(devo);
//		System.out.println("新增成功");
		
		//	修改=====================================
	
//		devo.setDep_no(9);
//		devo.setDep_name("饕客部門");
//		devo.setDep_state(true);
//		
//		deptest.updateDep(devo);
//		System.out.println("修改成功");
		// 查詢PK=====================================
		devo = deptest.getDepPK(1);
		
		System.out.println(devo.getDep_no());
		System.out.println(devo.getDep_name());
		System.out.println(devo.getDep_state());
		
		// 查詢=====================================
		
//		List<DepartmentVO> list = deptest.getAllDep();
//			for (DepartmentVO dep : list) {
//			System.out.print(dep.getDep_no() + ",");
//			System.out.print(dep.getDep_name() + ",");
//			System.out.println(dep.getDep_state());
//			}
//			System.out.println("查詢成功");
		
	}

}
