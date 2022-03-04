package com.activityEvaluationReport.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;


public class ActivityEvaluationReportJDBCDAO implements I_ActivityEvaluationReportDAO {

	private final String[] GET_KEY = {"act_evaluation_no","mem_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_EVALUATION_REPORT";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_EVALUATION_REPORT VALUES(?,?,?,?,?)";
	private final String DELETE_SQL = "DELETE FROM ACTIVITY_EVALUATION_REPORT WHERE act_evaluation_no = ? AND mem_no = ?";
	private final String SELECT_BY_ACTIVITY_EVALUATION_NO_SQL = "SELECT * FROM ACTIVITY_EVALUATION_REPORT WHERE act_evaluation_no = ?";
	private final String SELECT_BY_MEMBER_NO_SQL = "SELECT * FROM ACTIVITY_EVALUATION_REPORT WHERE mem_no = ?";
	
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Override
	public ActivityEvaluationReportVO insert(ActivityEvaluationReportVO actEvaluationReportVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setInt(1,actEvaluationReportVO.getAct_evaluation_no());
			ps.setInt(2,actEvaluationReportVO.getMem_no());
			ps.setObject(3,actEvaluationReportVO.getAct_report_date());
			ps.setInt(4,actEvaluationReportVO.getAct_evaluation_report_reason());
			ps.setInt(5,actEvaluationReportVO.getAct_evaluation_report_state());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actEvaluationReportVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationReportVO.setMem_no(rs.getInt(2));
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			if(con != null) {
				try {
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return actEvaluationReportVO;
	}

	@Override
	public void delete(Integer act_evaluation_no,Integer mem_no) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(DELETE_SQL);
			ps.setInt(1,act_evaluation_no);
			ps.setInt(2,mem_no);		
			ps.executeUpdate();
			
		}catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}finally {
			if(con != null) {
				try {
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<ActivityEvaluationReportVO> findByActEvaluationNo(Integer act_evaluation_no) {
		List<ActivityEvaluationReportVO> list = new ArrayList<>();
		ActivityEvaluationReportVO actEvaluationReportVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_EVALUATION_NO_SQL);
			ps.setInt(1, act_evaluation_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actEvaluationReportVO = new ActivityEvaluationReportVO();
				actEvaluationReportVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationReportVO.setMem_no(rs.getInt(2));
				actEvaluationReportVO.setAct_report_date(rs.getTimestamp(3).toLocalDateTime());
				actEvaluationReportVO.setAct_evaluation_report_reason(rs.getInt(4));
				actEvaluationReportVO.setAct_evaluation_report_state(rs.getInt(5));
				list.add(actEvaluationReportVO);
			}
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			if(con != null) {
				try {
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return list;
	}

	@Override
	public List<ActivityEvaluationReportVO> findByMemberNo(Integer mem_no) {
		List<ActivityEvaluationReportVO> list = new ArrayList<>();
		ActivityEvaluationReportVO actEvaluationReportVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_MEMBER_NO_SQL);
			ps.setInt(1, mem_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actEvaluationReportVO = new ActivityEvaluationReportVO();
				actEvaluationReportVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationReportVO.setMem_no(rs.getInt(2));
				actEvaluationReportVO.setAct_report_date(rs.getTimestamp(3).toLocalDateTime());
				actEvaluationReportVO.setAct_evaluation_report_reason(rs.getInt(4));
				actEvaluationReportVO.setAct_evaluation_report_state(rs.getInt(5));
				list.add(actEvaluationReportVO);
			}
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			if(con != null) {
				try {
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return list;
	}

	@Override
	public List<ActivityEvaluationReportVO> getAll() {
		List<ActivityEvaluationReportVO> list = new ArrayList<>();
		ActivityEvaluationReportVO actEvaluationReportVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();		
			while (rs.next()) {
				actEvaluationReportVO = new ActivityEvaluationReportVO();
				actEvaluationReportVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationReportVO.setMem_no(rs.getInt(2));
				actEvaluationReportVO.setAct_report_date(rs.getTimestamp(3).toLocalDateTime());
				actEvaluationReportVO.setAct_evaluation_report_reason(rs.getInt(4));
				actEvaluationReportVO.setAct_evaluation_report_state(rs.getInt(5));
				list.add(actEvaluationReportVO);
			}
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			if(con != null) {
				try {
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return list;
	}
	public static void main(String[] args) {
		ActivityEvaluationReportJDBCDAO dao = new ActivityEvaluationReportJDBCDAO();
//		ActivityEvaluationReportVO vo = new ActivityEvaluationReportVO();
		
//		vo.setAct_evaluation_no(4);
//		vo.setMem_no(1);
//		vo.setAct_report_date(LocalDateTime.now());
//		vo.setAct_evaluation_report_reason(2);
//		vo.setAct_evaluation_report_state(2);
//		
//		dao.insert(vo);
//		dao.update(vo,3,1);
//		List<ActivityEvaluationReportVO> list = dao.findByActEvaluationNo(3);
//		List<ActivityEvaluationReportVO> list = dao.findByMemberNo(1);
		List<ActivityEvaluationReportVO> list = dao.getAll();
		for(ActivityEvaluationReportVO vo : list)
		System.out.println(vo);
	}

}
