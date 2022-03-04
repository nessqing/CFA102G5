package com.activityPromotion.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class ActivityPromotionJDBCDAO implements I_ActivityPromotionDAO{

	private final String[] GET_KEY = {"act_promotion_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_PROMOTION";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_PROMOTION VALUES(?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_PROMOTION SET act_promotion_name = ?,act_promotion_start_date = ?,act_promotion_end_date = ? WHERE act_promotion_no = ?";
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_PROMOTION WHERE act_promotion_no = ?";

	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Override
	public ActivityPromotionVO insert(ActivityPromotionVO actPromotionVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1, null); // AI
			ps.setString(2, actPromotionVO.getAct_promotion_name());
			ps.setObject(3, actPromotionVO.getAct_promotion_start_date());
			ps.setObject(4, actPromotionVO.getAct_promotion_end_date());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actPromotionVO.setAct_promotion_no(rs.getInt(1));
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
		
		return actPromotionVO;
	}

	@Override
	public void update(ActivityPromotionVO actPromotionVO) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setString(1, actPromotionVO.getAct_promotion_name());
			ps.setObject(2, actPromotionVO.getAct_promotion_start_date());
			ps.setObject(3,actPromotionVO.getAct_promotion_end_date());
			ps.setInt(4, actPromotionVO.getAct_promotion_no());
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
	public ActivityPromotionVO findByPk(Integer act_promotion_no) {
		ActivityPromotionVO actPromotionVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_promotion_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actPromotionVO = new ActivityPromotionVO();
				actPromotionVO.setAct_promotion_no(rs.getInt(1));
				actPromotionVO.setAct_promotion_name(rs.getString(2));
				actPromotionVO.setAct_promotion_start_date(rs.getDate(3).toLocalDate());
				actPromotionVO.setAct_promotion_end_date(rs.getDate(4).toLocalDate());
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
		
		return actPromotionVO;
	}

	@Override
	public List<ActivityPromotionVO> getAll() {
		List<ActivityPromotionVO> list = new ArrayList<>();
		ActivityPromotionVO actPromotionVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actPromotionVO = new ActivityPromotionVO();
				actPromotionVO.setAct_promotion_no(rs.getInt(1));
				actPromotionVO.setAct_promotion_name(rs.getString(2));
				actPromotionVO.setAct_promotion_start_date(rs.getDate(3).toLocalDate());
				actPromotionVO.setAct_promotion_end_date(rs.getDate(4).toLocalDate());
				list.add(actPromotionVO);
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
		ActivityPromotionJDBCDAO dao = new ActivityPromotionJDBCDAO();
//		ActivityPromotionVO vo = new ActivityPromotionVO();	
//		vo.setAct_promotion_name("夏天消暑活動促銷100");
//		vo.setAct_promotion_start_date(LocalDate.of(2021,8,23));
//		vo.setAct_promotion_end_date(LocalDate.of(2021,10,1));
//		dao.insert(vo);
//		vo.setAct_promotion_no(4);
//		
//		dao.update(vo);
//		ActivityPromotionVO vo = dao.findByPk(2);
		List<ActivityPromotionVO> list = dao.getAll();
		for(ActivityPromotionVO vo : list)
		System.out.println(vo);
		
	}
}
