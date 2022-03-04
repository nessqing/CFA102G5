package com.function.model;

import java.util.List;

import com.employee.model.EmployeeVO;

public class Testfun {

	public static void main(String[] args) {
		I_FunctionDAO funtest = new FunctionJDBCDAO();
		FunctionVO funvo = new FunctionVO();

//		新增=====================================	
		
//			funvo.setFun_name("隨便權限");
//
//			funtest.insertFun(funvo);
//			System.out.println(funvo);
//			System.out.println("新增成功");
			
//		修改=====================================
		
//			funvo.setFun_no(7);
//			funvo.setFun_name("沒有權限");
//			
//			funtest.updateFun(funvo);
//			System.out.println("修改成功");
			
//			//查詢全部	
//			List<FunctionVO> list = funtest.getAllFun();
//			for (FunctionVO funAll : list) {
//			System.out.println("部門 :"+funAll.getFun_no());
//			System.out.println("姓名 :"+funAll.getFun_name());
//
//			System.out.println("============================");
//			}
//			System.out.println("查詢成功");
		}

}
