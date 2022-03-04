package com.employee.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.util.JDBCUtil;

public class EmployeeJDBCDAO implements I_EmployeeDAO{
	
	private static final String INSERT_EMP ="INSERT INTO EMPLOYEE(emp_password,emp_name,emp_mail,emp_state,dep_no)VALUES(?,?,?,?,?)";
	private static final String UPDATE_EMP ="UPDATE EMPLOYEE SET dep_no=?,emp_password=?, emp_name=?,emp_mail=?,emp_state=? WHERE emp_no=?";
	private static final String GET_ONE_EMP ="SELECT * FROM EMPLOYEE WHERE emp_no=?";
	private static final String GET_ONE_DEP ="SELECT * FROM EMPLOYEE WHERE dep_no=?";
	private static final String LOGIN = "SELECT * FROM EMPLOYEE WHERE emp_mail=? and emp_password =?";
	private static final String GET_ALL_EMP ="SELECT * FROM EMPLOYEE";
	
	static {										//資料庫連線
		try {
			Class.forName(JDBCUtil.DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insertEmp(EmployeeVO employeeVO) {
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(INSERT_EMP,PreparedStatement.RETURN_GENERATED_KEYS)) {
			
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
			System.out.println("新增一筆員工資料");
			
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
	public void updateEmp(EmployeeVO employeeVO) {
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(UPDATE_EMP)) {
			
			pstmt.setInt(1, employeeVO.getDep_no());
			pstmt.setString(2, employeeVO.getEmp_password());
			pstmt.setString(3, employeeVO.getEmp_name());
			pstmt.setString(4, employeeVO.getEmp_mail());
			pstmt.setBoolean(5, employeeVO.getEmp_state());
			pstmt.setInt(6, employeeVO.getEmp_no());
			
			pstmt.executeUpdate();
			System.out.println("修改一筆員工資料");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}       
	}

	@Override
	public EmployeeVO findEmpByPK(int emp_no) {
		EmployeeVO emp = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_EMP)
				){
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
				System.out.println("查詢一筆員工");
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
		return emp;	
	}
	
	@Override
	public List<EmployeeVO> findDepByFK(int dep_no) {					//查詢某部門所有員工
		EmployeeVO dep = null;
		List<EmployeeVO> depAll = new ArrayList<>();
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_DEP)
				){
			
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
	public List<EmployeeVO> getAllEmp() {							//查詢全部員工
		List<EmployeeVO> empAll = new ArrayList<>();
		EmployeeVO emp = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_EMP);) 
		{
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
		
		
		return empAll;
	}
	@Override
	public EmployeeVO login(String emp_mail, String emp_password) {
		EmployeeVO emp = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
				PreparedStatement pstmt = con.prepareStatement(LOGIN)
				){
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
		return emp;	
	}

}
