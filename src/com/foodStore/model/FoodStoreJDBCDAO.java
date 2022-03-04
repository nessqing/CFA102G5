package com.foodStore.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.util.JDBCUtil;

public class FoodStoreJDBCDAO implements I_FoodStoreDAO{
	
	private static final String INSERT_FOOD_STORE ="INSERT INTO FOOD_STORE(fd_name,fd_address,fd_longitude,fd_latitude,fd_service,fd_state,fd_class_no) VALUES(?,?,?,?,?,?,?)";
	private static final String UPDATE_FOOD_STORE ="UPDATE FOOD_STORE SET fd_class_no=?,fd_name=?, fd_address=?,fd_longitude=?,fd_latitude=?,fd_service=?,fd_state=? WHERE fd_no=?";
	private static final String GET_ONE_FOOD ="SELECT * FROM FOOD_STORE WHERE fd_no=?";
	private static final String GET_FK_CLASS ="SELECT * FROM FOOD_STORE WHERE fd_class_no=?";
	private static final String GET_ALL_FOOD_STORE ="SELECT * FROM FOOD_STORE";
	
	
	static {										//資料庫連線
		try {
			Class.forName(JDBCUtil.DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insertFoodStore(FoodStoreVO foodstoreVO) {
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(INSERT_FOOD_STORE,PreparedStatement.RETURN_GENERATED_KEYS)) {
			
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
			System.out.println("新增一筆店家資料");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	public void updateFoodStore(FoodStoreVO foodstoreVO) {
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(UPDATE_FOOD_STORE)) {
			
			pstmt.setInt(1, foodstoreVO.getFd_class_no());
			pstmt.setString(2, foodstoreVO.getFd_name());
			pstmt.setString(3, foodstoreVO.getFd_address());
			pstmt.setDouble(4, foodstoreVO.getFd_longitude());
			pstmt.setDouble(5, foodstoreVO.getFd_latitude());
			pstmt.setString(6, foodstoreVO.getFd_service());
			pstmt.setBoolean(7, foodstoreVO.getFd_state());
			pstmt.setInt(8, foodstoreVO.getFd_no());
			
			pstmt.executeUpdate();
			System.out.println("修改一筆店家資料");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}       
	}

	@Override
	public List<FoodStoreVO> findfdByFK(int fd_class_no) {
		FoodStoreVO fdvo = null;
		List<FoodStoreVO> fdFKAll = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_FK_CLASS)
				){
			
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
		return fdFKAll;
	}

	@Override
	public List<FoodStoreVO> getAllFoodStore() {
		List<FoodStoreVO> fdAll = new ArrayList<>();
		FoodStoreVO fd = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_FOOD_STORE);) 
		{
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
		return fdAll;
	}

	@Override
	public FoodStoreVO getOneStore(Integer fd_no){
		FoodStoreVO vo = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_FOOD)){
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
		return vo;	
		
	

}
}
