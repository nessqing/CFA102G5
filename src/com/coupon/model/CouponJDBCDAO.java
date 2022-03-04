package com.coupon.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class CouponJDBCDAO implements I_CouponDAO {
	private static final String INSERT = "INSERT INTO coupon (mem_no, coupon_value, coupon_expiry) VALUES (?, ?, ADDDATE(CURDATE(),INTERVAL 1 MONTH))";
	private static final String DELETE = "DELETE FROM coupon WHERE coupon_no = ?";
	private static final String GET_ALL_BY_MEM = "SELECT * FROM coupon WHERE mem_no = ? AND coupon_expiry >= CURDATE() ORDER BY coupon_value DESC";
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	@Override
	public CouponVO insert(CouponVO couponVO) {
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, couponVO.getMem_no());
			pstmt.setInt(2, couponVO.getCoupon_value());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return couponVO;
	}
	
	@Override
	public void delete(Integer coupon_no) {
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, coupon_no);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	@Override
	public List<CouponVO> getAllByMem(Integer mem_no) {
		List<CouponVO> list = new ArrayList<>();
		CouponVO couponVO = null;
		ResultSet rs = null;
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL_BY_MEM);
			pstmt.setInt(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				couponVO = new CouponVO();
				couponVO.setCoupon_no(rs.getInt("coupon_no"));
				couponVO.setMem_no(rs.getInt("mem_no"));
				couponVO.setCoupon_value(rs.getInt("coupon_value"));
				couponVO.setCoupon_expiry(rs.getDate("coupon_expiry").toLocalDate());
				list.add(couponVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}
}
