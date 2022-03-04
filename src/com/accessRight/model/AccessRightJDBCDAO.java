package com.accessRight.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class AccessRightJDBCDAO implements I_AccessRightDAO{
	
	private static final String INSERT_ACC ="INSERT INTO ACCESSRIGHT VALUES(?,?)";
	private static final String UPDATE_ACC = "UPDATE ACCESSRIGHT SET dep_no=?,fun_no=? WHERE dep_no=? and fun_no=?";
	private static final String DELETE_ACC = "DELETE FROM ACCESSRIGHT WHERE dep_no = ? and fun_no = ?";
	private static final String GET_FK_DEP ="SELECT * FROM ACCESSRIGHT WHERE dep_no=?";
	private static final String GET_FK_FUN ="SELECT * FROM ACCESSRIGHT WHERE fun_no=?";
	private static final String GET_ALL_ACC ="SELECT * FROM ACCESSRIGHT";
	
	static {										//資料庫連線
		try {
			Class.forName(JDBCUtil.DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(AccessRightVO accessrightVO) {
		ResultSet rs = null;
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);       
				PreparedStatement pstmt = con.prepareStatement(INSERT_ACC,PreparedStatement.RETURN_GENERATED_KEYS)){
			
			pstmt.setInt(1, accessrightVO.getDep_no());
			pstmt.setInt(2, accessrightVO.getFun_no());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				accessrightVO.setDep_no(rs.getInt(1));
				accessrightVO.setFun_no(rs.getInt(1));
			}
			System.out.println("新增一筆權限資料");
			
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
	public void update(Integer dep_no,Integer fun_no,Integer dep_no2,Integer fun_no2) {
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	
				PreparedStatement pstmt = con.prepareStatement(UPDATE_ACC)) {
			
			pstmt.setInt(1, dep_no);
			pstmt.setInt(2, fun_no);
			
			pstmt.setInt(3, dep_no2);
			pstmt.setInt(4, fun_no2);
			
			pstmt.executeUpdate();
			System.out.println("修改一筆權限資料");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}       
	}

	
	

	@Override
	public void delete(Integer dep_no, Integer fun_no) {
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	
				PreparedStatement pstmt = con.prepareStatement(DELETE_ACC)) {
			
			pstmt.setInt(1, dep_no);
			pstmt.setInt(2, fun_no);
			
			pstmt.executeUpdate();
			System.out.println("刪除一筆權限資料");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public List<AccessRightVO> findDepByFK(int dep_no) {						//查詢?部門的權限有哪些
		AccessRightVO acc = null;
		List<AccessRightVO> depAll = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_FK_DEP)
				){
			pstmt.setInt(1, dep_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				acc = new AccessRightVO();
				acc.setDep_no(rs.getInt("dep_no"));
				acc.setFun_no(rs.getInt("fun_no"));
				
				depAll.add(acc);
			}
			
			System.out.println("查詢部門權限");
			
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
		return depAll;	
	}

	@Override
	public List<AccessRightVO> findFunByFK(int fun_no) {					//查詢?權限的部門有哪些
		AccessRightVO acc = null;
		List<AccessRightVO> funAll = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_FK_FUN)
				){
			pstmt.setInt(1, fun_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				acc = new AccessRightVO();
				acc.setDep_no(rs.getInt("dep_no"));
				acc.setFun_no(rs.getInt("fun_no"));
				
				funAll.add(acc);
			}
			
			System.out.println("查詢有此權限的部門權限");
			
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
		return funAll;	
	}

	@Override
	public List<AccessRightVO> getAllAcc() {								//查全部
		List<AccessRightVO> accAll = new ArrayList<>();
		AccessRightVO acc = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_ACC);)
		{
			rs = pstmt.executeQuery();
			while(rs.next()) {
				acc = new AccessRightVO();
				acc.setDep_no(rs.getInt("dep_no"));
				acc.setFun_no(rs.getInt("fun_no"));
	
				
				accAll.add(acc);
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
		
		return accAll;
	}
}
