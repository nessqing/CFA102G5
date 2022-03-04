package com.activityPromotionDetail.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ActivityPromotionDetailDAO implements I_ActivityPromotionDetailDAO {

	private static DataSource ds;
	
	private final String[] GET_KEY = {"act_promotion_no","act_class_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_PROMOTION_DETAIL";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_PROMOTION_DETAIL VALUES(?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_PROMOTION_DETAIL SET act_discount_price = ? WHERE act_promotion_no = ? AND act_class_no = ?";
	private final String SELECT_BY_ACTIVITY_PROMOTION_NO_SQL = "SELECT * FROM ACTIVITY_PROMOTION_DETAIL WHERE act_promotion_no = ?";
	private final String SELECT_BY_ACTIVITY_CLASS_NO_SQL = "SELECT * FROM ACTIVITY_PROMOTION_DETAIL WHERE act_class_no = ?";
	private final String ACTIVITY_PROMOTION_DETAIL_JOIN_ACTIVITY_CLASS_SQL = 
			"SELECT actClass.act_class_name,apd.act_discount_price"
			+ " FROM ACTIVITY_PROMOTION_DETAIL apd "
			+ " JOIN ACTIVITY_CLASS actClass "
			+ " ON apd.act_class_no = actClass.act_class_no";
	
	static {	
		try {
			ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException ex) {
			ex.printStackTrace();
		}	
	}
	
	@Override
	public ActivityPromotionDetailVO insert(ActivityPromotionDetailVO actPromotionDetailVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setInt(1, actPromotionDetailVO.getAct_promotion_no()); 
			ps.setInt(2, actPromotionDetailVO.getAct_class_no());
			ps.setDouble(3, actPromotionDetailVO.getAct_discount_price());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actPromotionDetailVO.setAct_promotion_no(rs.getInt(1));
				actPromotionDetailVO.setAct_class_no(rs.getInt(2));
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
		
		return actPromotionDetailVO;
	}

	@Override
	public void update(ActivityPromotionDetailVO actPromotionDetailVO) {
		Connection con = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setDouble(1, actPromotionDetailVO.getAct_discount_price());
			ps.setInt(2,actPromotionDetailVO.getAct_promotion_no()); 
			ps.setInt(3,actPromotionDetailVO.getAct_class_no());
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
	public List<ActivityPromotionDetailVO> findByActPromotionNo(Integer act_promotion_no) {
		List<ActivityPromotionDetailVO> list = new ArrayList<>();
		ActivityPromotionDetailVO actPromotionDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_PROMOTION_NO_SQL);
			ps.setInt(1, act_promotion_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actPromotionDetailVO = new ActivityPromotionDetailVO();
				actPromotionDetailVO.setAct_promotion_no(rs.getInt(1));
				actPromotionDetailVO.setAct_class_no(rs.getInt(2));
				actPromotionDetailVO.setAct_discount_price(rs.getDouble(3));
				list.add(actPromotionDetailVO);
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
	public List<ActivityPromotionDetailVO> findByActClassNo(Integer act_class_no) {
		List<ActivityPromotionDetailVO> list = new ArrayList<>();
		ActivityPromotionDetailVO actPromotionDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_CLASS_NO_SQL);
			ps.setInt(1, act_class_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actPromotionDetailVO = new ActivityPromotionDetailVO();
				actPromotionDetailVO.setAct_promotion_no(rs.getInt(1));
				actPromotionDetailVO.setAct_class_no(rs.getInt(2));
				actPromotionDetailVO.setAct_discount_price(rs.getDouble(3));
				list.add(actPromotionDetailVO);
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
	public List<ActivityPromotionDetailVO> getAll() {
		List<ActivityPromotionDetailVO> list = new ArrayList<>();
		ActivityPromotionDetailVO actPromotionDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actPromotionDetailVO = new ActivityPromotionDetailVO();
				actPromotionDetailVO.setAct_promotion_no(rs.getInt(1));
				actPromotionDetailVO.setAct_class_no(rs.getInt(2));
				actPromotionDetailVO.setAct_discount_price(rs.getDouble(3));
				list.add(actPromotionDetailVO);
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
	public Map<String, String[]> getActPromotionDetailJoinActClass() {
		Map<String, String[]> joinMap = new HashMap<>();
		int position = 0;
		String[] columnArray = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(ACTIVITY_PROMOTION_DETAIL_JOIN_ACTIVITY_CLASS_SQL);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while (rs.next()) {
				columnArray = new String[rsmd.getColumnCount()];
				for(int index=0;index<columnArray.length;index++) 
					columnArray[index] = rs.getString(index+1);
				joinMap.put(String.valueOf(position++),columnArray);
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
		
		return joinMap;
	}

}
