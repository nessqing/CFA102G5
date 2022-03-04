package com.activity.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.JDBCUtil;



public class ActivityJDBCDAO implements I_ActivityDAO{

	private final String[] GET_KEY = {"act_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String GET_JOIN_NUMBER_SQL = "SELECT act_join_number FROM ACTIVITY WHERE act_no = ?";
	private final String UPDATE_SQL = "UPDATE ACTIVITY SET act_class_no = ?,act_name = ?,act_price = ?,act_location = ?,act_schedule_time = ?,act_instruction = ?"
			+ ",act_gather_location = ?,act_location_longitude = ?,act_location_latitude = ?,act_sell_number = ?,act_join_number = ?,act_evaluation_number = ?"
			+ ",act_average_star_number = ?,act_state = ? WHERE act_no = ?"; //字串串接 where前要空一行
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY WHERE act_no = ?";
	private final String SELECT_BY_NAME_SQL = "SELECT * FROM ACTIVITY WHERE act_name LIKE ?";
	private final String SELECT_BY_ACTIVITY_CLASS_SQL = "SELECT * FROM ACTIVITY WHERE act_class_no = ?";
	private final String SELECT_BY_POPULAR_ACTIVITY_SQL = "SELECT * FROM ACTIVITY WHERE act_state = true ORDER BY act_sell_number DESC LIMIT 3";
	private final String SWITCH_ACTIVITY_STATE = "UPDATE ACTIVITY SET act_state = ? WHERE act_no = ?";
	private final String ACTIVITY_JOIN_ACTIVITY_CLASS_SQL = 
			"SELECT	actClass.act_class_name,act.act_name"
			+ ",act.act_price,act.act_location,act_schedule_time"
			+ ",act.act_instruction,act.act_gather_location"
			+ ",act.act_sell_number,act.act_join_number"
			+ ",act.act_evaluation_number,act.act_average_star_number"
			+ " FROM ACTIVITY act JOIN ACTIVITY_CLASS actClass"
			+ " ON act.act_class_no = actClass.act_class_no";
	
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ActivityVO insert(ActivityVO actVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{		
			con = DriverManager.getConnection(JDBCUtil.URL,JDBCUtil.USERNAME,JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1, null); // AI
			ps.setInt(2, actVO.getAct_class_no());
			ps.setString(3,actVO.getAct_name());
			ps.setInt(4,actVO.getAct_price());
			ps.setString(5,actVO.getAct_location());
			ps.setInt(6,actVO.getAct_schedule_time());
			ps.setString(7,actVO.getAct_instruction());
			ps.setString(8,actVO.getAct_gather_location());
			ps.setDouble(9,actVO.getAct_location_longitude());
			ps.setDouble(10,actVO.getAct_location_latitude());
			ps.setInt(11,actVO.getAct_sell_number());
			ps.setInt(12,actVO.getAct_join_number());
			ps.setInt(13,actVO.getAct_evaluation_number());
			ps.setDouble(14,actVO.getAct_average_star_number());
			ps.setBoolean(15,actVO.getAct_state());
			
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				actVO.setAct_no(rs.getInt(1));
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
		
		return actVO;
	}

	@Override
	public void update(ActivityVO actVO) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setInt(1, actVO.getAct_class_no());
			ps.setString(2,actVO.getAct_name());
			ps.setInt(3,actVO.getAct_price());
			ps.setString(4,actVO.getAct_location());
			ps.setInt(5,actVO.getAct_schedule_time());
			ps.setString(6,actVO.getAct_instruction());
			ps.setString(7,actVO.getAct_gather_location());
			ps.setDouble(8,actVO.getAct_location_longitude());
			ps.setDouble(9,actVO.getAct_location_latitude());
			ps.setInt(10,actVO.getAct_sell_number());
			ps.setInt(11,actVO.getAct_join_number());
			ps.setInt(12,actVO.getAct_evaluation_number());
			ps.setDouble(13,actVO.getAct_average_star_number());
			ps.setBoolean(14,actVO.getAct_state());
			ps.setInt(15,actVO.getAct_no());
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
	public ActivityVO findByPk(Integer act_no) {
		ActivityVO actVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{			
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actVO = new ActivityVO();
				actVO.setAct_no(rs.getInt(1));
				actVO.setAct_class_no(rs.getInt(2)); 
				actVO.setAct_name(rs.getString(3));
				actVO.setAct_price(rs.getInt(4));
				actVO.setAct_location(rs.getString(5));
				actVO.setAct_schedule_time(rs.getInt(6));
				actVO.setAct_instruction(rs.getString(7));
				actVO.setAct_gather_location(rs.getString(8));
				actVO.setAct_location_longitude(rs.getDouble(9));
				actVO.setAct_location_latitude(rs.getDouble(10));
				actVO.setAct_sell_number(rs.getInt(11));
				actVO.setAct_join_number(rs.getInt(12));
				actVO.setAct_evaluation_number(rs.getInt(13));
				actVO.setAct_average_star_number(rs.getDouble(14));
				actVO.setAct_state(rs.getBoolean(15));
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
		
		return actVO;
	}

	@Override
	public List<ActivityVO> findByName(String act_name) {
		List<ActivityVO> list = new ArrayList<>();
		ActivityVO actVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_NAME_SQL);
			ps.setString(1, "%" + act_name + "%"); //塞選名稱有包含泛舟之類的活動
			rs = ps.executeQuery();
			while (rs.next()) {
				actVO = new ActivityVO();
				actVO.setAct_no(rs.getInt(1));
				actVO.setAct_class_no(rs.getInt(2)); 
				actVO.setAct_name(rs.getString(3));
				actVO.setAct_price(rs.getInt(4));
				actVO.setAct_location(rs.getString(5));
				actVO.setAct_schedule_time(rs.getInt(6));
				actVO.setAct_instruction(rs.getString(7));
				actVO.setAct_gather_location(rs.getString(8));
				actVO.setAct_location_longitude(rs.getDouble(9));
				actVO.setAct_location_latitude(rs.getDouble(10));
				actVO.setAct_sell_number(rs.getInt(11));
				actVO.setAct_join_number(rs.getInt(12));
				actVO.setAct_evaluation_number(rs.getInt(13));
				actVO.setAct_average_star_number(rs.getDouble(14));
				actVO.setAct_state(rs.getBoolean(15));
				list.add(actVO);
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
	public List<ActivityVO> findByActClassNo(Integer act_class_no) {
		List<ActivityVO> list = new ArrayList<>();
		ActivityVO actVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_CLASS_SQL);
			ps.setInt(1,act_class_no);
			rs = ps.executeQuery();
			while (rs.next()) {
				actVO = new ActivityVO();
				actVO.setAct_no(rs.getInt(1));
				actVO.setAct_class_no(rs.getInt(2)); 
				actVO.setAct_name(rs.getString(3));
				actVO.setAct_price(rs.getInt(4));
				actVO.setAct_location(rs.getString(5));
				actVO.setAct_schedule_time(rs.getInt(6));
				actVO.setAct_instruction(rs.getString(7));
				actVO.setAct_gather_location(rs.getString(8));
				actVO.setAct_location_longitude(rs.getDouble(9));
				actVO.setAct_location_latitude(rs.getDouble(10));
				actVO.setAct_sell_number(rs.getInt(11));
				actVO.setAct_join_number(rs.getInt(12));
				actVO.setAct_evaluation_number(rs.getInt(13));
				actVO.setAct_average_star_number(rs.getDouble(14));
				actVO.setAct_state(rs.getBoolean(15));
				list.add(actVO);
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
	public List<ActivityVO> getPopularAct() {
		List<ActivityVO> list = new ArrayList<>();
		ActivityVO actVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_POPULAR_ACTIVITY_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actVO = new ActivityVO();
				actVO.setAct_no(rs.getInt(1));
				actVO.setAct_class_no(rs.getInt(2)); 
				actVO.setAct_name(rs.getString(3));
				actVO.setAct_price(rs.getInt(4));
				actVO.setAct_location(rs.getString(5));
				actVO.setAct_schedule_time(rs.getInt(6));
				actVO.setAct_instruction(rs.getString(7));
				actVO.setAct_gather_location(rs.getString(8));
				actVO.setAct_location_longitude(rs.getDouble(9));
				actVO.setAct_location_latitude(rs.getDouble(10));
				actVO.setAct_sell_number(rs.getInt(11));
				actVO.setAct_join_number(rs.getInt(12));
				actVO.setAct_evaluation_number(rs.getInt(13));
				actVO.setAct_average_star_number(rs.getDouble(14));
				actVO.setAct_state(rs.getBoolean(15));
				list.add(actVO);
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
	public List<ActivityVO> getAll() {
		List<ActivityVO> list = new ArrayList<>();
		ActivityVO actVO = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actVO = new ActivityVO();
				actVO.setAct_no(rs.getInt(1));
				actVO.setAct_class_no(rs.getInt(2)); 
				actVO.setAct_name(rs.getString(3));
				actVO.setAct_price(rs.getInt(4));
				actVO.setAct_location(rs.getString(5));
				actVO.setAct_schedule_time(rs.getInt(6));
				actVO.setAct_instruction(rs.getString(7));
				actVO.setAct_gather_location(rs.getString(8));
				actVO.setAct_location_longitude(rs.getDouble(9));
				actVO.setAct_location_latitude(rs.getDouble(10));
				actVO.setAct_sell_number(rs.getInt(11));
				actVO.setAct_join_number(rs.getInt(12));
				actVO.setAct_evaluation_number(rs.getInt(13));
				actVO.setAct_average_star_number(rs.getDouble(14));
				actVO.setAct_state(rs.getBoolean(15));
				list.add(actVO);
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
	
	@Override			//第x個活動可參與的人數
	public Integer getJoinNumber(Integer act_no) {
		Integer number = null;
		Connection con = null;
		ResultSet rs = null;
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(GET_JOIN_NUMBER_SQL);
			ps.setInt(1, act_no);
			rs = ps.executeQuery();
			if(rs.next()) {
				number = rs.getInt(1);
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
		
		return number;
	}

	@Override
	public Map<String,String[]> getActJoinActClass() {
		Map<String,String[]> joinMap = new HashMap<>();
		String[] columnArray = null;
		int position = 0;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(ACTIVITY_JOIN_ACTIVITY_CLASS_SQL);
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
	
	@Override
	public void switchActState(Integer act_no, Boolean act_state) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SWITCH_ACTIVITY_STATE);
			
			ps.setBoolean(1,act_state);
			ps.setInt(2,act_no);
			
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
	
	public static void main(String[] args) {
		ActivityJDBCDAO dao = new ActivityJDBCDAO();
//		ActivityVO vo = new ActivityVO();
//		ActivityVO vo = dao.findByPk(2);
//		List<ActivityVO> list = dao.findByName("人");
//		for(ActivityVO vo : list) 
//			System.out.println(vo);
//		
//		Map<String,String[]> map = dao.getActJoinActClass();
//		for(int i=0;i<map.size();i++) {
//			String[] str = map.get(String.valueOf(i));
//			for(int j=0;j<str.length;j++) {
//				System.out.print(str[j] + " ");
//			}
//			System.out.println();
//		}
//		
//		Integer number = dao.getJoinNumber(2);
//		System.out.println(number);
//		List<ActivityVO> list = dao.findByActClassNo(2);
//		List<ActivityVO> list = dao.getAll();
//		List<ActivityVO> list = dao.getPopularAct();
//		
//		for(ActivityVO vo : list) 
//			System.out.println(vo);
//		System.out.println(number);
//		vo.setAct_no(10);
//		vo.setAct_class_no(2);
//		vo.setAct_name("泛舟達人2510");
//		vo.setAct_price(2500);
//		vo.setAct_location("花蓮市");
//		vo.setAct_schedule_time(2);
//		vo.setAct_instruction("很好玩");
//		vo.setAct_gather_location("花蓮區");
//		vo.setAct_location_longitude(123.456);
//		vo.setAct_location_latitude(456.78);
//		vo.setAct_sell_number(10);
//		vo.setAct_join_number(3);
//		vo.setAct_evaluation_number(8);
//		vo.setAct_average_star_number(4.5);
//		vo.setAct_state(true);
//		dao.insert(vo);
//		dao.update(vo);
//		System.out.println(vo);
		dao.switchActState(2, true);
	}

	@Override
	public void updateActSellNumber(Integer act_no, Integer act_sell_number) {
		// TODO Auto-generated method stub
		
	}
	
}
