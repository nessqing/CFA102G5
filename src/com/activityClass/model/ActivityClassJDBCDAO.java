package com.activityClass.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class ActivityClassJDBCDAO implements I_ActivityClassDAO {

	private final String[] GET_KEY = {"act_class_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_CLASS";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_CLASS VALUES(?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_CLASS SET act_class_name = ?,act_class_state = ? WHERE act_class_no = ?";
	private final String SWITCH_ACTIVITY_CLASS_STATE = "UPDATE ACTIVITY_CLASS SET act_class_state = ? WHERE act_class_no = ?";
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_CLASS WHERE act_class_no = ?";
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ActivityClassVO insert(ActivityClassVO actClassVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1, null); // AI
			ps.setString(2, actClassVO.getAct_class_name());
			ps.setBoolean(3, actClassVO.getAct_class_state());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actClassVO.setAct_class_no(rs.getInt(1));
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
		
		return actClassVO;
	}

	@Override
	public void update(ActivityClassVO actClassVO) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setString(1, actClassVO.getAct_class_name());
			ps.setBoolean(2, actClassVO.getAct_class_state());
			ps.setInt(3, actClassVO.getAct_class_no());
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
	public ActivityClassVO findByPk(Integer act_class_no) {
		ActivityClassVO actClassVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_class_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actClassVO = new ActivityClassVO();
				actClassVO.setAct_class_no(rs.getInt(1));
				actClassVO.setAct_class_name(rs.getString(2));
				actClassVO.setAct_class_state(rs.getBoolean(3));
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
		
		return actClassVO;
	}

	@Override
	public void switchActivityClassState(Integer act_class_no,Boolean act_class_state) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SWITCH_ACTIVITY_CLASS_STATE);
			
			ps.setBoolean(1,act_class_state);
			ps.setInt(2,act_class_no);
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
	public List<ActivityClassVO> getAll() {
		List<ActivityClassVO> list = new ArrayList<>();
		ActivityClassVO actClassVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actClassVO = new ActivityClassVO();
				actClassVO.setAct_class_no(rs.getInt(1));
				actClassVO.setAct_class_name(rs.getString(2));
				actClassVO.setAct_class_state(rs.getBoolean(3));
				list.add(actClassVO);
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
		ActivityClassJDBCDAO dao = new ActivityClassJDBCDAO();
//		ActivityClassVO vo = new ActivityClassVO();
//		vo.setAct_class_name("水上活動");
//		vo.setAct_class_no(1);
//		vo.setAct_class_state(true);
//		ActivityClassVO vo = dao.insert(vo);
//		dao.update(vo);
//		ActivityClassVO vo = dao.findByPk(3);
		dao.switchActivityClassState(5, false);
//		System.out.println(vo);
		
//		List<ActivityClassVO> list = dao.getAll();
//		for(ActivityClassVO vo : list) 
//			System.out.println(vo);
		
		
	}
}
