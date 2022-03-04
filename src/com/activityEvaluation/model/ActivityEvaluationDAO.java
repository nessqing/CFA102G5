package com.activityEvaluation.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ActivityEvaluationDAO implements I_ActivityEvaluationDAO{
	private static DataSource ds;
	
	private final String[] GET_KEY = {"act_evaluation_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_EVALUATION";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_EVALUATION VALUES(?,?,?,?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_EVALUATION SET act_no = ?,mem_no = ?,act_evaluation_star_number = ?,act_evaluation_message = ?"
			+ ",act_evaluation_date = ?,act_evaluation_state = ? WHERE act_evaluation_no = ?"; //字串串接 where前要空一行
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_EVALUATION WHERE act_evaluation_no = ?";
	private final String SELECT_BY_ACTIVITY_NO_SQL = "SELECT * FROM ACTIVITY_EVALUATION WHERE act_no = ?";
	private final String SELECT_BY_MEMBER_NO_SQL = "SELECT * FROM ACTIVITY_EVALUATION WHERE mem_no = ?";
	
	static {	
		try {
			ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException ex) {
			ex.printStackTrace();
		}	
	}
	
	
	@Override
	public ActivityEvaluationVO insert(ActivityEvaluationVO actEvaluationVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1, null); // AI
			ps.setInt(2,actEvaluationVO.getAct_no());
			ps.setInt(3,actEvaluationVO.getMem_no());
			ps.setInt(4,actEvaluationVO.getAct_evaluation_star_number());
			ps.setString(5,actEvaluationVO.getAct_evaluation_message());
			ps.setObject(6,actEvaluationVO.getAct_evaluation_date());
			ps.setBoolean(7,actEvaluationVO.getAct_evaluation_state());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actEvaluationVO.setAct_evaluation_no(rs.getInt(1));
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
		
		return actEvaluationVO;
	}

	@Override
	public void update(ActivityEvaluationVO actEvaluationVO) {
		Connection con = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setInt(1,actEvaluationVO.getAct_no());
			ps.setInt(2,actEvaluationVO.getMem_no());
			ps.setInt(3,actEvaluationVO.getAct_evaluation_star_number());
			ps.setString(4,actEvaluationVO.getAct_evaluation_message());
			ps.setObject(5,actEvaluationVO.getAct_evaluation_date());
			ps.setBoolean(6,actEvaluationVO.getAct_evaluation_state());
			ps.setInt(7,actEvaluationVO.getAct_evaluation_no());
			ps.executeUpdate();

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
	}

	@Override
	public ActivityEvaluationVO findByPk(Integer act_evaluation_no) {
		ActivityEvaluationVO actEvaluationVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_evaluation_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actEvaluationVO = new ActivityEvaluationVO();
				actEvaluationVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationVO.setAct_no(rs.getInt(2));
				actEvaluationVO.setMem_no(rs.getInt(3));
				actEvaluationVO.setAct_evaluation_star_number(rs.getInt(4));
				actEvaluationVO.setAct_evaluation_message(rs.getString(5));
				actEvaluationVO.setAct_evaluation_date(rs.getTimestamp(6).toLocalDateTime());
				actEvaluationVO.setAct_evaluation_state(rs.getBoolean(7));
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
		
		return actEvaluationVO;
	}

	@Override
	public List<ActivityEvaluationVO> findByActNo(Integer act_no) {
		List<ActivityEvaluationVO> list = new ArrayList<>();
		ActivityEvaluationVO actEvaluationVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_NO_SQL);
			ps.setInt(1, act_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actEvaluationVO = new ActivityEvaluationVO();
				actEvaluationVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationVO.setAct_no(rs.getInt(2));
				actEvaluationVO.setMem_no(rs.getInt(3));
				actEvaluationVO.setAct_evaluation_star_number(rs.getInt(4));
				actEvaluationVO.setAct_evaluation_message(rs.getString(5));
				actEvaluationVO.setAct_evaluation_date(rs.getTimestamp(6).toLocalDateTime());
				actEvaluationVO.setAct_evaluation_state(rs.getBoolean(7));
				list.add(actEvaluationVO);
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
	public List<ActivityEvaluationVO> findByMemberNo(Integer member_no) {
		List<ActivityEvaluationVO> list = new ArrayList<>();
		ActivityEvaluationVO actEvaluationVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_MEMBER_NO_SQL);
			ps.setInt(1, member_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actEvaluationVO = new ActivityEvaluationVO();
				actEvaluationVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationVO.setAct_no(rs.getInt(2));
				actEvaluationVO.setMem_no(rs.getInt(3));
				actEvaluationVO.setAct_evaluation_star_number(rs.getInt(4));
				actEvaluationVO.setAct_evaluation_message(rs.getString(5));
				actEvaluationVO.setAct_evaluation_date(rs.getTimestamp(6).toLocalDateTime());
				actEvaluationVO.setAct_evaluation_state(rs.getBoolean(7));
				list.add(actEvaluationVO);
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
	public List<ActivityEvaluationVO> getAll() {
		List<ActivityEvaluationVO> list = new ArrayList<>();
		ActivityEvaluationVO actEvaluationVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actEvaluationVO = new ActivityEvaluationVO();
				actEvaluationVO.setAct_evaluation_no(rs.getInt(1));
				actEvaluationVO.setAct_no(rs.getInt(2));
				actEvaluationVO.setMem_no(rs.getInt(3));
				actEvaluationVO.setAct_evaluation_star_number(rs.getInt(4));
				actEvaluationVO.setAct_evaluation_message(rs.getString(5));
				actEvaluationVO.setAct_evaluation_date(rs.getTimestamp(6).toLocalDateTime());
				actEvaluationVO.setAct_evaluation_state(rs.getBoolean(7));
				list.add(actEvaluationVO);
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
}
