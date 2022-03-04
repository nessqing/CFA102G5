package com.coupon.model;

import java.util.List;

public class CouponTest {

	public static void main(String[] args) {
		I_CouponDAO dao = new CouponJDBCDAO();
		
		// 新增
//		CouponVO couponVO = new CouponVO();
//		couponVO.setMem_no(2);
//		couponVO.setCoupon_value(825);
//		dao.insert(couponVO);
		
		// 刪除
//		dao.delete(12);
		
		// 查詢同個會員所有未過期折價券
//		List<CouponVO> list = dao.getAllByMem(2);
//		for (CouponVO couponVO : list) {
//			System.out.print(couponVO.getCoupon_no() + ",");
//			System.out.print(couponVO.getMem_no() + ",");
//			System.out.print(couponVO.getCoupon_value() + ",");
//			System.out.println(couponVO.getCoupon_expiry());
//		}
	}
}
