package com.foodImg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class FoodImgJDBCDAO implements I_FoodImgDAO{

	private static final String INSERT_IMG ="INSERT INTO FOOD_IMG VALUES(?,?,?)";
	private static final String UPDATE_IMG ="UPDATE FOOD_IMG SET fd_no=?,fd_img=? WHERE fd_img_no=?";
	private static final String GET_FOODSTORE_IMG ="SELECT * FROM FOOD_IMG WHERE fd_no=?";
	private static final String GET_PK ="SELECT * FROM FOOD_IMG WHERE fd_img_no=?";
	private static final String GET_STORE_ONE ="SELECT * FROM FOOD_IMG WHERE fd_no=?";
	private static final String GET_ALL_IMG ="SELECT * FROM FOOD_IMG";
	private static final String DELETE_IMG = "DELETE FROM FOOD_IMG WHERE fd_img_no = ?";
	
	static {										//資料庫連線
		try {
			Class.forName(JDBCUtil.DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insert(FoodImgVO foodImgVO) {
		ResultSet rs = null;
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);       //輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(INSERT_IMG,PreparedStatement.RETURN_GENERATED_KEYS)){
			
			pstmt.setString(1,null);
			pstmt.setInt(2, foodImgVO.getFd_no());
			pstmt.setBytes(3, foodImgVO.getFd_img());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				foodImgVO.setFd_img_no(rs.getInt(1));
			}
			System.out.println("新增一筆店家照片");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(FoodImgVO foodImgVO) {
		
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(UPDATE_IMG)) {
			
			pstmt.setInt(1, foodImgVO.getFd_no());
			pstmt.setBytes(2, foodImgVO.getFd_img());
			pstmt.setInt(3,foodImgVO.getFd_img_no());
			
			pstmt.executeUpdate();
			System.out.println("修改一筆店家照片");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}       
	}

	@Override
	public List<FoodImgVO> findimgByFK(Integer fd_no) {
		FoodImgVO fimg = null;
		List<FoodImgVO> fdimgAll = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_FOODSTORE_IMG)
				){
			
			pstmt.setInt(1, fd_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fimg = new FoodImgVO();
				fimg.setFd_img_no(rs.getInt("fd_img_no"));
				fimg.setFd_no(rs.getInt("fd_no"));
				fimg.setFd_img(rs.getBytes("fd_img"));
				
				fdimgAll.add(fimg);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return fdimgAll;
	}

	@Override
	public List<FoodImgVO> getAllImg() {
		List<FoodImgVO> imgAll = new ArrayList<>();
		FoodImgVO fdimg = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_IMG);) 
		{
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fdimg = new FoodImgVO();
				fdimg.setFd_img_no(rs.getInt("fd_img_no"));
				fdimg.setFd_no(rs.getInt("fd_no"));
				fdimg.setFd_img(rs.getBytes("fd_img"));

				
				imgAll.add(fdimg);
			}
				
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return imgAll;
	}

	@Override
	public void delete(Integer fd_img_no) {
		Connection con = null;
		PreparedStatement pstmt =null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
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

	@Override
	public FoodImgVO findimgByPK(Integer fd_img_no) {
		FoodImgVO fimg = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_PK);){
			
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
		}  
		return fimg;
	}

	@Override
	public FoodImgVO findImgOne(Integer fd_no) {
		FoodImgVO fimg = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_STORE_ONE)){
			
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
		}  
		return fimg;
	}
	
}
