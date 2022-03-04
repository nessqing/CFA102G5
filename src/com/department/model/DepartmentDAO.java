package com.department.model;

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


public class DepartmentDAO implements I_DepartmentDAO{
	
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
	
	private static final String INSERT_DEP ="INSERT INTO DEPARTMENT(dep_name,dep_state) VALUES(?,?)";
	private static final String UPDATE_DEP ="UPDATE DEPARTMENT SET dep_name=?,dep_state=? where dep_no=?";
	private static final String GET_ONE_PK = "SELECT * FROM DEPARTMENT where dep_no = ?";
	private static final String GET_ALL_DEP ="SELECT * FROM DEPARTMENT";
	
	@Override
	public void insertDep(DepartmentVO departmentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_DEP,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, departmentVO.getDep_name());
			pstmt.setBoolean(2, departmentVO.getDep_state());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				departmentVO.setDep_no(rs.getInt(1));
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
	public void updateDep(DepartmentVO departmentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_DEP);

			pstmt.setString(1, departmentVO.getDep_name());
			pstmt.setBoolean(2, departmentVO.getDep_state());
			pstmt.setInt(3, departmentVO.getDep_no());

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
	public List<DepartmentVO> getAllDep() {
		List<DepartmentVO> list = new ArrayList<DepartmentVO>();
		DepartmentVO deptVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DEP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				deptVO = new DepartmentVO();
				deptVO.setDep_no(rs.getInt("dep_no"));
				deptVO.setDep_name(rs.getString("dep_name"));
				deptVO.setDep_state(rs.getBoolean("dep_state"));
				list.add(deptVO); 
			}

		} catch (SQLException se) {
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
		return list;
	}
	@Override
	public DepartmentVO getDepPK(Integer dep_no) {
		DepartmentVO deptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
