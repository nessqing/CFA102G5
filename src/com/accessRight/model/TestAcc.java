package com.accessRight.model;

import java.util.List;

public class TestAcc {

	public static void main(String[] args) {
		I_AccessRightDAO actest = new AccessRightJDBCDAO();
		AccessRightVO acvo = new AccessRightVO();

//		新增部門權限=====================================	
		
//		acvo.setDep_no(5);
//		acvo.setFun_no(2);
//		
//		actest.insert(acvo);
//		System.out.println(acvo);
//		System.out.println("新增成功");

		// 修改=====================================
		
//		actest.update(3, 3, 1, 3);	//哪一個部門DEP=?是多少FUN=?
//		System.out.println("修改成功");
		
		// 刪除=====================================
		
//		actest.delete(1, 1);			//哪一個部門DEP=?是多少FUN=?
//		System.out.println("刪除成功");

		// 查詢部門權限
//		List<AccessRightVO> depFK = actest.findDepByFK(2); // 查詢部門?的權限
//		for (AccessRightVO depall : depFK) {
//			System.out.println("權限 :" + depall.getFun_no());
//			System.out.println("============================");
//		}
//		System.out.println("查詢成功");

		// 查詢部門權限
//		List<AccessRightVO> funFK = actest.findFunByFK(3); // 查詢權限?有哪些部門
//		for (AccessRightVO funall : funFK) {
//			System.out.println("部門 :" + funall.getDep_no());
//			System.out.println("============================");
//		}
//		System.out.println("查詢成功");

		// 查詢全部=====================================

		List<AccessRightVO> list = actest.getAllAcc();
			for (AccessRightVO acc : list) {
			System.out.print(acc.getDep_no() + ",");
			System.out.println(acc.getFun_no() + ",");

			}
			System.out.println("查詢成功");
	}

}
