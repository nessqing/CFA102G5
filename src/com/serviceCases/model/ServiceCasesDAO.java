package com.serviceCases.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.util.JDBCUtil;



public class ServiceCasesDAO implements I_ServiceCasesDAO{
private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
		// 新增案件
		private final String INSERT = "INSERT INTO SERVICE_CASES VALUES (?, ?, ?, ?, ?, ?)";
		// 案件回覆
		private final String UPDATE = "UPDATE SERVICE_CASES set case_reply=?, case_state=? where case_no = ?";
		//搜尋一個問題
		private final String GET_ONE_STMT = "SELECT * FROM SERVICE_CASES WHERE case_no = ?";
		//搜尋全部案件
		private final String GET_ALL_STMT = "SELECT * FROM SERVICE_CASES order by case_no";

	@Override
	public void insert(ServiceCasesVO serviceCasesVO) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			// 1, AutoIncrement
			pstmt.setString(1, null);
			pstmt.setInt(2, serviceCasesVO.getMem_no());
			pstmt.setString(3, serviceCasesVO.getCase_title());
			pstmt.setString(4, serviceCasesVO.getCase_des());
			pstmt.setString(5, serviceCasesVO.getCase_reply());
			pstmt.setInt(6, serviceCasesVO.getCase_state());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				serviceCasesVO.setCase_no(rs.getInt(1));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
	public void update(ServiceCasesVO serviceCasesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);		
			pstmt.setString(1, serviceCasesVO.getCase_reply());
			pstmt.setInt(2, serviceCasesVO.getCase_state());
			pstmt.setInt(3,serviceCasesVO.getCase_no());
			pstmt.executeUpdate();
						
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
	public List<ServiceCasesVO> getAll() {
		List<ServiceCasesVO> list = new ArrayList<>();
		ServiceCasesVO servicecasesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				servicecasesVO = new ServiceCasesVO();
				servicecasesVO.setCase_no(rs.getInt("case_no"));
				servicecasesVO.setMem_no(rs.getInt("mem_no"));
				servicecasesVO.setCase_title(rs.getString("case_title"));
				servicecasesVO.setCase_des(rs.getString("case_des"));
				servicecasesVO.setCase_reply(rs.getString("case_reply"));
				servicecasesVO.setCase_state(rs.getInt("case_state"));
				list.add(servicecasesVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
	
	@Override
	public ServiceCasesVO findByPrimaryKey(Integer case_no) {
		ServiceCasesVO serviceCasesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, case_no);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				serviceCasesVO = new ServiceCasesVO();
				serviceCasesVO.setCase_no(rs.getInt("case_no"));
				serviceCasesVO.setMem_no(rs.getInt("mem_no"));
				serviceCasesVO.setCase_title(rs.getString("case_title"));
				serviceCasesVO.setCase_des(rs.getString("case_des"));
				serviceCasesVO.setCase_reply(rs.getString("case_reply"));
				serviceCasesVO.setCase_state(rs.getInt("case_state"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return serviceCasesVO;
	}
}
