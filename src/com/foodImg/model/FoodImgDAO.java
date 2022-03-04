package com.foodImg.model;

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


public class FoodImgDAO implements I_FoodImgDAO{

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
	private static final String INSERT_IMG ="INSERT INTO FOOD_IMG(fd_no,fd_img) VALUES(?,?)";
	private static final String UPDATE_IMG ="UPDATE FOOD_IMG SET fd_no=?,fd_img=? WHERE fd_img_no=?";
	private static final String GET_FOODSTORE_IMG ="SELECT * FROM FOOD_IMG WHERE fd_no=?";
	private static final String GET_PK ="SELECT * FROM FOOD_IMG WHERE fd_img_no=?";
	private static final String GET_STORE_ONE ="SELECT * FROM FOOD_IMG WHERE fd_no=?";
	private static final String GET_ALL_IMG ="SELECT * FROM FOOD_IMG";
	private static final String DELETE_IMG = "DELETE FROM FOOD_IMG WHERE fd_img_no = ?";
	@Override
	public void insert(FoodImgVO foodImgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_IMG,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, foodImgVO.getFd_no());
			pstmt.setBytes(2, foodImgVO.getFd_img());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				foodImgVO.setFd_img_no(rs.getInt(1));
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
	public void update(FoodImgVO foodImgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_IMG);
			
			pstmt.setInt(1, foodImgVO.getFd_no());
			pstmt.setBytes(2, foodImgVO.getFd_img());
			pstmt.setInt(3,foodImgVO.getFd_img_no());
			
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
	public List<FoodImgVO> findimgByFK(Integer fd_no) {
		FoodImgVO fimg = null;
		List<FoodImgVO> fdimgAll = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOODSTORE_IMG);
			
			pstmt.setInt(1, fd_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fimg = new FoodImgVO();
				fimg.setFd_img_no(rs.getInt("fd_img_no"));
				fimg.setFd_no(rs.getInt("fd_no"));
				fimg.setFd_img(rs.getBytes("fd_img"));
				
				fdimgAll.add(fimg);
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
		return fdimgAll;
	}

	@Override
	public List<FoodImgVO> getAllImg() {
		List<FoodImgVO> imgAll = new ArrayList<>();
		FoodImgVO fdimg = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_IMG);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fdimg = new FoodImgVO();
				fdimg.setFd_img_no(rs.getInt("fd_img_no"));
				fdimg.setFd_no(rs.getInt("fd_no"));
				fdimg.setFd_img(rs.getBytes("fd_img"));

				
				imgAll.add(fdimg);
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
		return imgAll;
	}

	@Override
	public void delete(Integer fd_img_no) {
		Connection con =null;
		PreparedStatement pstmt =null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_IMG);
			
			pstmt.setInt(1, fd_img_no);

			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			e.printStackTrace();
			throw new RuntimeException("A database error occured."+e.getMessage());
		} finally {
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


	public FoodImgVO findimgByPK(Integer fd_img_no) {
		FoodImgVO fimg = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PK);
			
			pstmt.setInt(1, fd_img_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fimg = new FoodImgVO();
				fimg.setFd_img_no(rs.getInt("fd_img_no"));
				fimg.setFd_no(rs.getInt("fd_no"));
				fimg.setFd_img(rs.getBytes("fd_img"));
				
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
		return fimg;
	}

	@Override
	public FoodImgVO findImgOne(Integer fd_no) {
		FoodImgVO fimg = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_ONE);
			
			pstmt.setInt(1, fd_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fimg = new FoodImgVO();
				fimg.setFd_img_no(rs.getInt("fd_img_no"));
				fimg.setFd_no(rs.getInt("fd_no"));
				fimg.setFd_img(rs.getBytes("fd_img"));
				
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
		return fimg;
	}
}
