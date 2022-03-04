package com.employee.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class EmployeeDAO implements I_EmployeeDAO{
	
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
		
		private static final String INSERT_EMP ="INSERT INTO EMPLOYEE(emp_password,emp_name,emp_mail,emp_state,dep_no)VALUES(?,?,?,?,?)";
		private static final String UPDATE_EMP ="UPDATE EMPLOYEE SET dep_no=?,emp_password=?, emp_name=?,emp_mail=?,emp_state=? WHERE emp_no=?";
		private static final String GET_ONE_EMP ="SELECT * FROM EMPLOYEE WHERE emp_no=?";
		private static final String GET_ONE_DEP ="SELECT * FROM EMPLOYEE WHERE dep_no=?";
		private static final String LOGIN = "SELECT * FROM EMPLOYEE WHERE emp_mail=? and emp_password =?";
		private static final String GET_ALL_EMP ="SELECT * FROM EMPLOYEE";
		
	@Override
	public void insertEmp(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_EMP,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, employeeVO.getEmp_password());
			pstmt.setString(2, employeeVO.getEmp_name());
			pstmt.setString(3, employeeVO.getEmp_mail());
			pstmt.setBoolean(4, employeeVO.getEmp_state());
			pstmt.setInt(5, employeeVO.getDep_no());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				employeeVO.setEmp_no(rs.getInt(1));
			}
			
		}catch (SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			throw new RuntimeException("電子郵件重複，請重新輸入。");
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
	public void updateEmp(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_EMP);
					
			pstmt.setInt(1, employeeVO.getDep_no());
			pstmt.setString(2, employeeVO.getEmp_password());
			pstmt.setString(3, employeeVO.getEmp_name());
			pstmt.setString(4, employeeVO.getEmp_mail());
			pstmt.setBoolean(5, employeeVO.getEmp_state());
			pstmt.setInt(6, employeeVO.getEmp_no());
			
			pstmt.executeUpdate();
			
		}catch (SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			throw new RuntimeException("電子郵件重複，請重新輸入。");
		}catch (SQLException se){
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
	public EmployeeVO findEmpByPK(int emp_no) {
		EmployeeVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_EMP);
			
			pstmt.setInt(1, emp_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				emp = new EmployeeVO();
				emp.setEmp_no(rs.getInt("emp_no"));
				emp.setEmp_password(rs.getString("emp_password"));
				emp.setEmp_name(rs.getString("emp_name"));
				emp.setEmp_mail(rs.getString("emp_mail"));
				emp.setEmp_state(rs.getBoolean("emp_state"));
				emp.setDep_no(rs.getInt("dep_no"));
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
		return emp;	
	}

	@Override
	public List<EmployeeVO> findDepByFK(int dep_no) {
		EmployeeVO dep = null;
		List<EmployeeVO> depAll = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_DEP);
			
			pstmt.setInt(1, dep_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dep = new EmployeeVO();
				dep.setEmp_no(rs.getInt("emp_no"));
				dep.setEmp_name(rs.getString("emp_name"));
				dep.setEmp_mail(rs.getString("emp_mail"));
				dep.setEmp_state(rs.getBoolean("emp_state"));
				dep.setDep_no(rs.getInt("dep_no"));
				
				depAll.add(dep);
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
		return depAll;
	}

	@Override
	public List<EmployeeVO> getAllEmp() {
		List<EmployeeVO> empAll = new ArrayList<>();
		EmployeeVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_EMP);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				emp = new EmployeeVO();
				emp.setEmp_no(rs.getInt("emp_no"));
				emp.setDep_no(rs.getInt("dep_no"));
				emp.setEmp_name(rs.getString("emp_name"));
				emp.setEmp_mail(rs.getString("emp_mail"));
				emp.setEmp_state(rs.getBoolean("emp_state"));
				
				empAll.add(emp);
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
		return empAll;	
		}

	@Override
	public EmployeeVO login(String emp_mail, String emp_password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		EmployeeVO emp = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);
			pstmt.setString(1, emp_mail);
			pstmt.setString(2, emp_password);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				emp = new EmployeeVO();
				emp.setEmp_no(rs.getInt("emp_no"));
				emp.setEmp_password(rs.getString("emp_password"));
				emp.setEmp_name(rs.getString("emp_name"));
				emp.setEmp_mail(rs.getString("emp_mail"));
				emp.setEmp_state(rs.getBoolean("emp_state"));
				emp.setDep_no(rs.getInt("dep_no"));
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
		return emp;	
	}

}
