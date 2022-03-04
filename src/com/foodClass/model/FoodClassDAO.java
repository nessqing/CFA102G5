package com.foodClass.model;

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



public class FoodClassDAO implements I_FoodClassDAO{
	
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
			
	private static final String INSERT_FOOD_CLASS ="INSERT INTO FOOD_CLASS(fd_class_name,fd_class_state) VALUES(?,?)";
	private static final String UPDATE_FOOD_CLASS ="UPDATE FOOD_CLASS SET fd_class_name=?,fd_class_state=? WHERE fd_class_no=?";
	private static final String GET_FOOD_CLASS_PK = "SELECT * FROM FOOD_CLASS WHERE fd_class_no = ?";
	private static final String GET_ALL_FOOD_CLASS ="SELECT * FROM FOOD_CLASS";
	@Override
	public void insertFoodClass(FoodClassVO foodclassVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FOOD_CLASS,PreparedStatement.RETURN_GENERATED_KEYS);
					
			pstmt.setString(1, foodclassVO.getFd_class_name());
			pstmt.setBoolean(2, foodclassVO.getFd_class_state());;

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				foodclassVO.setFd_class_no(rs.getInt(1));
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
	public void updateFoodClass(FoodClassVO foodclassVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FOOD_CLASS);
			
			pstmt.setString(1, foodclassVO.getFd_class_name());
			pstmt.setBoolean(2, foodclassVO.getFd_class_state());;
			pstmt.setInt(3, foodclassVO.getFd_class_no());
			
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
	public List<FoodClassVO> getAllFoodClass() {
		List<FoodClassVO> fdAllClass = new ArrayList<>();
		FoodClassVO fdClass = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FOOD_CLASS);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fdClass = new FoodClassVO();
				fdClass.setFd_class_no(rs.getInt("fd_class_no"));
				fdClass.setFd_class_name(rs.getString("fd_class_name"));
				fdClass.setFd_class_state(rs.getBoolean("fd_class_state"));
				
				fdAllClass.add(fdClass);
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
		return fdAllClass;
	}

	@Override
	public FoodClassVO getClassPK(Integer fd_class_no) {
		FoodClassVO fdClassVO = null;
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOOD_CLASS_PK);
			
			pstmt.setInt(1, fd_class_no);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fdClassVO = new FoodClassVO();
				fdClassVO.setFd_class_no(rs.getInt("fd_class_no"));
				fdClassVO.setFd_class_name(rs.getString("fd_class_name"));
				fdClassVO.setFd_class_state(rs.getBoolean("fd_class_state"));
				
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return fdClassVO;
	}

}
