package com.department.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class DepartmentJDBCDAO implements I_DepartmentDAO{
		
	private static final String INSERT_DEP ="INSERT INTO DEPARTMENT(dep_name,dep_state) VALUES(?,?)";
	private static final String UPDATE_DEP ="UPDATE DEPARTMENT SET dep_name=?,dep_state=? where dep_no=?";
	private static final String GET_ONE_PK = "SELECT * FROM DEPARTMENT where dep_no = ?";
	private static final String GET_ALL_DEP ="SELECT * FROM DEPARTMENT";
	
	
	static {										//資料庫連線
		try {
			Class.forName(JDBCUtil.DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insertDep(DepartmentVO departmentVO) {
		ResultSet rs = null;
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);       //輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(INSERT_DEP,PreparedStatement.RETURN_GENERATED_KEYS)){
			
			pstmt.setString(1, departmentVO.getDep_name());
			pstmt.setBoolean(2, departmentVO.getDep_state());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				departmentVO.setDep_no(rs.getInt(1));
			}
			System.out.println("新增一筆部門資料");
			
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
	public void updateDep(DepartmentVO departmentVO) {
		
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(UPDATE_DEP)) {
			
			pstmt.setString(1, departmentVO.getDep_name());
			pstmt.setBoolean(2, departmentVO.getDep_state());
			pstmt.setInt(3,departmentVO.getDep_no());
			
			pstmt.executeUpdate();
			System.out.println("修改一筆部門資料");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}       
		
		
	}

	@Override
	public List<DepartmentVO> getAllDep() {											//查詢全部部門
		List<DepartmentVO> depAll = new ArrayList<>();
		DepartmentVO dep = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_DEP);)
		{
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dep = new DepartmentVO();
				dep.setDep_no(rs.getInt("dep_no"));
				dep.setDep_name(rs.getString("dep_name"));
				dep.setDep_state(rs.getBoolean("dep_state"));
				
				depAll.add(dep);
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
		
		return depAll;
	}

	@Override
	public DepartmentVO getDepPK(Integer dep_no) {
		DepartmentVO deptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_PK);

			pstmt.setInt(1, dep_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				deptVO = new DepartmentVO();
				deptVO.setDep_no(rs.getInt("dep_no"));
				deptVO.setDep_name(rs.getString("dep_name"));
				deptVO.setDep_state(rs.getBoolean("dep_state"));
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
		return deptVO;
		
	}

}
