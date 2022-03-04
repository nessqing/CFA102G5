package com.coupon.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CouponDAO implements I_CouponDAO {
	private static final String INSERT = "INSERT INTO coupon (mem_no, coupon_value, coupon_expiry) VALUES (?, ?, ADDDATE(CURDATE(),INTERVAL 1 MONTH))";
	private static final String DELETE = "DELETE FROM coupon WHERE coupon_no = ?";
	private static final String GET_ALL_BY_MEM = "SELECT * FROM coupon WHERE mem_no = ? AND coupon_expiry >= CURDATE() ORDER BY coupon_value DESC";
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public CouponVO insert(CouponVO couponVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, couponVO.getMem_no());
			pstmt.setInt(2, couponVO.getCoupon_value());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return couponVO;
	}
	
	@Override
	public void delete(Integer coupon_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, coupon_no);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public List<CouponVO> getAllByMem(Integer mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<CouponVO> list = new ArrayList<>();
		CouponVO couponVO = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_MEM);
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
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
