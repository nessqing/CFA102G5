package com.coupon.model;

import java.util.List;

public class CouponService {
	
	private I_CouponDAO dao;

	public CouponService() {
		dao = new CouponDAO();
	}
	
	public CouponVO addCoupon(Integer mem_no, Integer coupon_value) {

		CouponVO couponVO = new CouponVO();
		couponVO.setMem_no(mem_no);
		couponVO.setCoupon_value(coupon_value);
		dao.insert(couponVO);
		
		return dao.insert(couponVO);
	}
	
	public void deleteCoupon(Integer coupon_no) {
		dao.delete(coupon_no);
	}
	
	public List<CouponVO> getAllByMem(Integer mem_no) {
		return dao.getAllByMem(mem_no);
	}
}
