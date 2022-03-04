package com.activitySession.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class ActivitySessionDAO implements I_ActivitySessionDAO {
	private static DataSource ds;

	private final String[] GET_KEY = {"act_session_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_SESSION";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_SESSION VALUES(?,?,?,?,?,?,?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_SESSION SET act_no = ?,act_start_date = ?,act_end_date = ?,act_session_real_number = ?,act_session_start_date = ?,act_session_start_time = ?,act_session_upper_limit = ?,act_session_lower_limit = ?,act_session_hold_state = ? WHERE act_session_no = ?";
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_SESSION WHERE act_session_no = ?";
	private final String SELECT_BY_ACTIVITY_NO_SQL = "SELECT * FROM ACTIVITY_SESSION WHERE act_no = ?";
	private final String SWITCH_ACTIVITY_SESSION_STATE_SQL = "UPDATE ACTIVITY_SESSION SET act_session_hold_state = ? WHERE act_session_no = ? ORDER BY act_session_start_date,act_session_start_time";
	private final String UPDATE_ACTIVITY_REAL_JOIN_NUMBER = "UPDATE ACTIVITY_SESSION SET act_session_real_number = ? WHERE act_session_no = ? ";
	
	static {	
		try {
			ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException ex) {
			ex.printStackTrace();
		}	
	}
	
	
	@Override
	public ActivitySessionVO insert(ActivitySessionVO actSessionVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1,null);
			ps.setInt(2, actSessionVO.getAct_no());
			ps.setObject(3,actSessionVO.getAct_start_date());
			ps.setObject(4,actSessionVO.getAct_end_date());
			ps.setInt(5,actSessionVO.getAct_session_real_number() );
			ps.setObject(6,actSessionVO.getAct_session_start_date());
			ps.setObject(7,actSessionVO.getAct_session_start_time());
			ps.setInt(8,actSessionVO.getAct_session_upper_limit());
			ps.setInt(9,actSessionVO.getAct_session_lower_limit());
			ps.setBoolean(10,actSessionVO.getAct_session_hold_state());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actSessionVO.setAct_session_no(rs.getInt(1));
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
		
		return actSessionVO;
	}

	@Override
	public void update(ActivitySessionVO actSessionVO) {
		Connection con = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setInt(1, actSessionVO.getAct_no());
			ps.setObject(2,actSessionVO.getAct_start_date());
			ps.setObject(3,actSessionVO.getAct_end_date());
			ps.setInt(4,actSessionVO.getAct_session_real_number() );
			ps.setObject(5,actSessionVO.getAct_session_start_date());
			ps.setObject(6,actSessionVO.getAct_session_start_time());
			ps.setInt(7,actSessionVO.getAct_session_upper_limit());
			ps.setInt(8,actSessionVO.getAct_session_lower_limit());
			ps.setBoolean(9,actSessionVO.getAct_session_hold_state());
			ps.setInt(10,actSessionVO.getAct_session_no());
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
	public ActivitySessionVO findByPk(Integer act_session_no) {
		ActivitySessionVO actSessionVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_session_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actSessionVO = new ActivitySessionVO();
				actSessionVO.setAct_session_no(rs.getInt(1));
				actSessionVO.setAct_no(rs.getInt(2));
				actSessionVO.setAct_start_date(rs.getDate(3).toLocalDate());
				actSessionVO.setAct_end_date(rs.getDate(4).toLocalDate());
				actSessionVO.setAct_session_real_number(rs.getInt(5));
				actSessionVO.setAct_session_start_date(rs.getDate(6).toLocalDate());
				actSessionVO.setAct_session_start_time(rs.getTime(7).toLocalTime());
				actSessionVO.setAct_session_upper_limit(rs.getInt(8));
				actSessionVO.setAct_session_lower_limit(rs.getInt(9));
				actSessionVO.setAct_session_hold_state(rs.getBoolean(10));		
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
		
		return actSessionVO;
	}

	@Override
	public List<ActivitySessionVO> findByActNo(Integer act_no) {
		List<ActivitySessionVO> list = new ArrayList<>();
		ActivitySessionVO actSessionVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_NO_SQL);
			ps.setInt(1, act_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actSessionVO = new ActivitySessionVO();
				actSessionVO.setAct_session_no(rs.getInt(1));
				actSessionVO.setAct_no(rs.getInt(2));
				actSessionVO.setAct_start_date(rs.getDate(3).toLocalDate());
				actSessionVO.setAct_end_date(rs.getDate(4).toLocalDate());
				actSessionVO.setAct_session_real_number(rs.getInt(5));
				actSessionVO.setAct_session_start_date(rs.getDate(6).toLocalDate());
				actSessionVO.setAct_session_start_time(rs.getTime(7).toLocalTime());
				actSessionVO.setAct_session_upper_limit(rs.getInt(8));
				actSessionVO.setAct_session_lower_limit(rs.getInt(9));
				actSessionVO.setAct_session_hold_state(rs.getBoolean(10));			
				list.add(actSessionVO);
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
	public void switchActSessionState(Integer act_session_no,Boolean act_session_hold_state) {
		Connection con = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SWITCH_ACTIVITY_SESSION_STATE_SQL);
			ps.setBoolean(1,act_session_hold_state);
			ps.setInt(2,act_session_no);
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
	public List<ActivitySessionVO> getAll() {
		List<ActivitySessionVO> list = new ArrayList<>();
		ActivitySessionVO actSessionVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actSessionVO = new ActivitySessionVO();
				actSessionVO.setAct_session_no(rs.getInt(1));
				actSessionVO.setAct_no(rs.getInt(2));
				actSessionVO.setAct_start_date(rs.getDate(3).toLocalDate());
				actSessionVO.setAct_end_date(rs.getDate(4).toLocalDate());
				actSessionVO.setAct_session_real_number(rs.getInt(5));
				actSessionVO.setAct_session_start_date(rs.getDate(6).toLocalDate());
				actSessionVO.setAct_session_start_time(rs.getTime(7).toLocalTime());
				actSessionVO.setAct_session_upper_limit(rs.getInt(8));
				actSessionVO.setAct_session_lower_limit(rs.getInt(9));
				actSessionVO.setAct_session_hold_state(rs.getBoolean(10));
				list.add(actSessionVO);
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
	public void updateActSessionRealNumber(Integer act_session_no, Integer act_session_real_number) {
			Connection con = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_ACTIVITY_REAL_JOIN_NUMBER);
			ps.setInt(1, act_session_real_number);
			ps.setObject(2,act_session_no);
			
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


}
