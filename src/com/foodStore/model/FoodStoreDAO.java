package com.foodStore.model;

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


public class FoodStoreDAO implements I_FoodStoreDAO{
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
	private static final String INSERT_FOOD_STORE ="INSERT INTO FOOD_STORE(fd_name,fd_address,fd_longitude,fd_latitude,fd_service,fd_state,fd_class_no) VALUES(?,?,?,?,?,?,?)";
	private static final String UPDATE_FOOD_STORE ="UPDATE FOOD_STORE SET fd_class_no=?,fd_name=?, fd_address=?,fd_longitude=?,fd_latitude=?,fd_service=?,fd_state=? WHERE fd_no=?";
	private static final String GET_ONE_FOOD ="SELECT * FROM FOOD_STORE WHERE fd_no=?";
	private static final String GET_FK_CLASS ="SELECT * FROM FOOD_STORE WHERE fd_class_no=?";
	private static final String GET_ALL_FOOD_STORE ="SELECT * FROM FOOD_STORE";

	@Override
	public void insertFoodStore(FoodStoreVO foodstoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FOOD_STORE,PreparedStatement.RETURN_GENERATED_KEYS);
			

			pstmt.setString(1, foodstoreVO.getFd_name());
			pstmt.setString(2, foodstoreVO.getFd_address());
			pstmt.setDouble(3, foodstoreVO.getFd_longitude());
			pstmt.setDouble(4, foodstoreVO.getFd_latitude());
			pstmt.setString(5, foodstoreVO.getFd_service());
			pstmt.setBoolean(6, foodstoreVO.getFd_state());
			pstmt.setInt(7, foodstoreVO.getFd_class_no());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				foodstoreVO.setFd_no(rs.getInt(1));
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
	public void updateFoodStore(FoodStoreVO foodstoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FOOD_STORE);
			
			pstmt.setInt(1, foodstoreVO.getFd_class_no());
			pstmt.setString(2, foodstoreVO.getFd_name());
			pstmt.setString(3, foodstoreVO.getFd_address());
			pstmt.setDouble(4, foodstoreVO.getFd_longitude());
			pstmt.setDouble(5, foodstoreVO.getFd_latitude());
			pstmt.setString(6, foodstoreVO.getFd_service());
			pstmt.setBoolean(7, foodstoreVO.getFd_state());
			pstmt.setInt(8, foodstoreVO.getFd_no());
			
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
	public List<FoodStoreVO> findfdByFK(int fd_class_no) {
		FoodStoreVO fdvo = null;
		List<FoodStoreVO> fdFKAll = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FK_CLASS);
			
			pstmt.setInt(1,fd_class_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fdvo = new FoodStoreVO();
				fdvo.setFd_no(rs.getInt("fd_no"));
				fdvo.setFd_name(rs.getString("fd_name"));
				fdvo.setFd_address(rs.getString("fd_address"));
				fdvo.setFd_longitude(rs.getDouble("fd_longitude"));
				fdvo.setFd_latitude(rs.getDouble("fd_latitude"));
				fdvo.setFd_service(rs.getString("fd_service"));
				fdvo.setFd_state(rs.getBoolean("fd_state"));
				fdvo.setFd_class_no(rs.getInt("fd_class_no"));
				
				fdFKAll.add(fdvo);
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
		return fdFKAll;
	}

	@Override
	public List<FoodStoreVO> getAllFoodStore() {
		List<FoodStoreVO> fdAll = new ArrayList<>();
		FoodStoreVO fd = null;
		Connection con= null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FOOD_STORE);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fd = new FoodStoreVO();
				fd.setFd_no(rs.getInt("fd_no"));
				fd.setFd_name(rs.getString("fd_name"));
				fd.setFd_address(rs.getString("fd_address"));
				fd.setFd_longitude(rs.getDouble("fd_longitude"));
				fd.setFd_latitude(rs.getDouble("fd_latitude"));
				fd.setFd_service(rs.getString("fd_service"));
				fd.setFd_state(rs.getBoolean("fd_state"));
				fd.setFd_class_no(rs.getInt("fd_class_no"));
				
				fdAll.add(fd);
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
		return fdAll;
	}

	@Override
	public FoodStoreVO getOneStore(Integer fd_no) {
		FoodStoreVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FOOD);
			
			pstmt.setInt(1, fd_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new FoodStoreVO();
				vo.setFd_no(rs.getInt("fd_no"));
				vo.setFd_class_no(rs.getInt("fd_class_no"));
				vo.setFd_name(rs.getString("fd_name"));
				vo.setFd_address(rs.getString("fd_address"));
				vo.setFd_longitude(rs.getDouble("fd_longitude"));
				vo.setFd_latitude(rs.getDouble("fd_latitude"));
				vo.setFd_service(rs.getString("fd_service"));
				vo.setFd_state(rs.getBoolean("fd_state"));
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured."+ se.getMessage());
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
		return vo;	
	}

}
