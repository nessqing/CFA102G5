package com.serviceCases.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.util.JDBCUtil;

public class ServiceCasesJDBCDAO implements I_ServiceCasesDAO {
	// 新增案件
	private final String INSERT = "INSERT INTO SERVICE_CASES VALUES (?, ?, ?, ?, ?, ?)";
	// 案件回覆
	private final String UPDATE = "UPDATE SERVICE_CASES set case_reply=?, case_state=? where case_no = ?";
	//搜尋一個問題
	private final String GET_ONE_STMT = "SELECT * FROM SERVICE_CASES WHERE case_no = ?";
	//搜尋全部案件
	private final String GET_ALL_STMT = "SELECT * FROM SERVICE_CASES order by case_no";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void insert(ServiceCasesVO serviceCasesVO) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			// 1, AutoIncrement
			pstmt.setString(1, null);
			pstmt.setInt(2, serviceCasesVO.getMem_no());
			pstmt.setString(3, serviceCasesVO.getCase_title());
			pstmt.setString(4, serviceCasesVO.getCase_des());
			pstmt.setString(5, serviceCasesVO.getCase_reply());
			pstmt.setInt(6, serviceCasesVO.getCase_state());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				serviceCasesVO.setCase_no(rs.getInt(1));
			}

		} catch (SQLException se) {
			se.printStackTrace();
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
	public void update(ServiceCasesVO serviceCasesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);		
			pstmt.setString(1, serviceCasesVO.getCase_reply());
			pstmt.setInt(2, serviceCasesVO.getCase_state());
			pstmt.setInt(3,serviceCasesVO.getCase_no());
			pstmt.executeUpdate();
						
		} catch (SQLException se) {
			se.printStackTrace();
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
	public List<ServiceCasesVO> getAll() {
		List<ServiceCasesVO> list = new ArrayList<>();
		ServiceCasesVO servicecasesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				servicecasesVO = new ServiceCasesVO();
				servicecasesVO.setCase_no(rs.getInt("case_no"));
				servicecasesVO.setMem_no(rs.getInt("mem_no"));
				servicecasesVO.setCase_title(rs.getString("case_title"));
				servicecasesVO.setCase_des(rs.getString("case_des"));
				servicecasesVO.setCase_reply(rs.getString("case_reply"));
				servicecasesVO.setCase_state(rs.getInt("case_state"));
				list.add(servicecasesVO);
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
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
		return list;
	}

	public static void main(String[] args) throws IOException {
	
			// 新增訊息
	//		ServiceCasesJDBCDAO dao = new ServiceCasesJDBCDAO();
	//		ServiceCasesVO vo = new ServiceCasesVO();
	//		vo.setMem_no(2);
	//		vo.setCase_title("我有一些房間問題");
	//		vo.setCase_des("我的床有點大");
	//		vo.setCase_state(1);
	//		dao.insert(vo);
	//		System.out.println(vo);
	//		System.out.println("新增案件成功");
	
	
			// 案件回覆
	//		ServiceCasesJDBCDAO dao = new ServiceCasesJDBCDAO();
	//		ServiceCasesVO vo = new ServiceCasesVO();
	//		vo.setCase_no(1);
	//		vo.setCase_reply("找麻煩嗎?");
	//		vo.setCase_state(2);
	//		dao.update(vo);
	//		System.out.println("案件回覆成功");
		
		//查詢全部
		ServiceCasesJDBCDAO dao = new ServiceCasesJDBCDAO();
		List<ServiceCasesVO> list = dao.getAll();
		for(ServiceCasesVO allServiceCases : list) {
			System.out.print(allServiceCases.getCase_no() + ",");
			System.out.print(allServiceCases.getMem_no() + ",");
			System.out.print(allServiceCases.getCase_title() + ",");
			System.out.print(allServiceCases.getCase_des() + ",");
			System.out.print(allServiceCases.getCase_reply() + ",");
			System.out.println(allServiceCases.getCase_state());
			System.out.println("---------------------");
			System.out.println("查詢全部成功");
		}
	}

	@Override
	public ServiceCasesVO findByPrimaryKey(Integer case_no) {
		ServiceCasesVO serviceCasesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, case_no);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				serviceCasesVO = new ServiceCasesVO();
				serviceCasesVO.setCase_no(rs.getInt("case_no"));
				serviceCasesVO.setMem_no(rs.getInt("mem_no"));
				serviceCasesVO.setCase_title(rs.getString("case_title"));
				serviceCasesVO.setCase_des(rs.getString("case_des"));
				serviceCasesVO.setCase_reply(rs.getString("case_reply"));
				serviceCasesVO.setCase_state(rs.getInt("case_state"));
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
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
		return serviceCasesVO;
	}

	

}
