package com.accessRight.model;

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

public class AccessRightDAO implements I_AccessRightDAO{

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
	private static final String INSERT_ACC ="INSERT INTO ACCESSRIGHT VALUES(?,?)";
	private static final String UPDATE_ACC = "UPDATE ACCESSRIGHT SET dep_no=?,fun_no=? WHERE dep_no=? and fun_no=?";
	private static final String DELETE_ACC = "DELETE FROM ACCESSRIGHT WHERE dep_no = ? and fun_no = ?";
	private static final String GET_FK_DEP ="SELECT * FROM ACCESSRIGHT WHERE dep_no=?";
	private static final String GET_FK_FUN ="SELECT * FROM ACCESSRIGHT WHERE fun_no=?";
	private static final String GET_ALL_ACC ="SELECT * FROM ACCESSRIGHT";
	@Override
	public void insert(AccessRightVO accessrightVO) {
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ACC,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, accessrightVO.getDep_no());
			pstmt.setInt(2, accessrightVO.getFun_no());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				accessrightVO.setDep_no(rs.getInt(1));
				accessrightVO.setFun_no(rs.getInt(1));
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
	public void update(Integer dep_no, Integer fun_no, Integer dep_no2, Integer fun_no2) {
		Connection con =null;
		PreparedStatement pstmt =null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ACC);
					
			pstmt.setInt(1, dep_no);
			pstmt.setInt(2, fun_no);
			
			pstmt.setInt(3, dep_no2);
			pstmt.setInt(4, fun_no2);
			
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
	public void delete(Integer dep_no, Integer fun_no) {
		Connection con =null;
		PreparedStatement pstmt =null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ACC);
			
			pstmt.setInt(1, dep_no);
			pstmt.setInt(2, fun_no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<AccessRightVO> findDepByFK(int dep_no) {
		AccessRightVO acc = null;
		List<AccessRightVO> depAll = new ArrayList<>();
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FK_DEP);
			
			pstmt.setInt(1, dep_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				acc = new AccessRightVO();
				acc.setDep_no(rs.getInt("dep_no"));
				acc.setFun_no(rs.getInt("fun_no"));
				
				depAll.add(acc);
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
		return depAll;	
	}

	@Override
	public List<AccessRightVO> findFunByFK(int fun_no) {
		AccessRightVO acc = null;
		List<AccessRightVO> funAll = new ArrayList<>();
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FK_FUN);
			
			pstmt.setInt(1, fun_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				acc = new AccessRightVO();
				acc.setDep_no(rs.getInt("dep_no"));
				acc.setFun_no(rs.getInt("fun_no"));
				
				funAll.add(acc);
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

	@Override
	public List<AccessRightVO> getAllAcc() {
		List<AccessRightVO> accAll = new ArrayList<>();
		AccessRightVO acc = null;
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ACC);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				acc = new AccessRightVO();
				acc.setDep_no(rs.getInt("dep_no"));
				acc.setFun_no(rs.getInt("fun_no"));
	
				
				accAll.add(acc);
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
		return accAll;
	}

}
