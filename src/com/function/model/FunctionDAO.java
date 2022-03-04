package com.function.model;

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


public class FunctionDAO implements I_FunctionDAO{

	//連線池
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	private static final String INSERT_FUN ="INSERT INTO FUNCTIONS VALUES(?,?)";
	private static final String UPDATE_FUN ="UPDATE FUNCTIONS SET fun_name=? WHERE fun_no=?";
	private static final String GET_ALL_FUN ="SELECT * FROM FUNCTIONS";
	@Override
	public void insertFun(FunctionVO functionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FUN,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, null);
			pstmt.setString(2, functionVO.getFun_name());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				functionVO.setFun_no(rs.getInt(1));
			}
			
		}catch (SQLException se){
			se.printStackTrace();
			throw new RuntimeException("A database error occured."+se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}  
	}

	@Override
	public void updateFun(FunctionVO functionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FUN);
					
			pstmt.setString(1, functionVO.getFun_name());
			pstmt.setInt(2,functionVO.getFun_no());
			
			pstmt.executeUpdate();
			
		}catch (SQLException se){
			se.printStackTrace();
			throw new RuntimeException("A database error occured."+se.getMessage());
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}        		
	}

	@Override
	public List<FunctionVO> getAllFun() {
		List<FunctionVO> funAll = new ArrayList<>();
		FunctionVO fun = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FUN);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fun = new FunctionVO();
				fun.setFun_no(rs.getInt("fun_no"));
				fun.setFun_name(rs.getString("fun_name"));
	
				funAll.add(fun);
			}
				
		}catch (SQLException se){
			se.printStackTrace();
			throw new RuntimeException("A database error occured."+se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}      
		return funAll;
	}

}
