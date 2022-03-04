package com.activityOrder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.activityOrderDetail.model.ActivityOrderDetailVO;
import com.util.JDBCUtil;

public class ActivityOrderJDBCDAO implements I_ActivityOrderDAO {

	private final String[] GET_KEY = {"act_order_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_ORDER";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_ORDER VALUES(?,?,?,?,?,?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_ORDER SET mem_no = ?,act_booking_date = ?,act_order_total_price = ?,act_order_title = ?,act_order_name = ?"
			+ ",act_order_phone = ?,act_order_email = ?,act_order_credit_card = ? WHERE act_order_no = ?"; //字串串接 where前要空一行
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_ORDER WHERE act_order_no = ?";
	private final String SELECT_BY_MEMBER_NO_SQL = "SELECT * FROM ACTIVITY_ORDER WHERE mem_no = ?";

	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	@Override
	public ActivityOrderVO insert(ActivityOrderVO actOrderVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1, null); // AI
			ps.setInt(2, actOrderVO.getMem_no());
			ps.setObject(3, actOrderVO.getAct_booking_date());
			ps.setInt(4, actOrderVO.getAct_order_total_price());
			ps.setString(5,actOrderVO.getAct_order_title());
			ps.setString(6, actOrderVO.getAct_order_name());
			ps.setString(7, actOrderVO.getAct_order_phone());
			ps.setString(8, actOrderVO.getAct_order_email());
			ps.setString(9,actOrderVO.getAct_order_credit_card());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actOrderVO.setAct_order_no(rs.getInt(1));
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
		
		return actOrderVO;
	}

	@Override
	public void update(ActivityOrderVO actOrderVO) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setInt(1, actOrderVO.getMem_no());
			ps.setObject(2, actOrderVO.getAct_booking_date());
			ps.setInt(3, actOrderVO.getAct_order_total_price());
			ps.setString(4,actOrderVO.getAct_order_title());
			ps.setString(5, actOrderVO.getAct_order_name());
			ps.setString(6, actOrderVO.getAct_order_phone());
			ps.setString(7, actOrderVO.getAct_order_email());
			ps.setString(8,actOrderVO.getAct_order_credit_card());
			ps.setInt(9, actOrderVO.getAct_order_no());
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
	public ActivityOrderVO findByPk(Integer act_order_no) {
		ActivityOrderVO actOrderVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_order_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actOrderVO = new ActivityOrderVO();
				actOrderVO.setAct_order_no(rs.getInt(1));
				actOrderVO.setMem_no(rs.getInt(2));
				actOrderVO.setAct_booking_date(rs.getTimestamp(3).toLocalDateTime());
				actOrderVO.setAct_order_total_price(rs.getInt(4));
				actOrderVO.setAct_order_title(rs.getString(5));
				actOrderVO.setAct_order_name(rs.getString(6));
				actOrderVO.setAct_order_phone(rs.getString(7));
				actOrderVO.setAct_order_email(rs.getString(8));
				actOrderVO.setAct_order_credit_card(rs.getString(9));
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
		
		return actOrderVO;
	}

	@Override
	public List<ActivityOrderVO> findByMemberNo(Integer mem_no) {
		List<ActivityOrderVO> list = new ArrayList<>();
		ActivityOrderVO actOrderVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_MEMBER_NO_SQL);
			ps.setInt(1, mem_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actOrderVO = new ActivityOrderVO();
				actOrderVO.setAct_order_no(rs.getInt(1));
				actOrderVO.setMem_no(rs.getInt(2));
				actOrderVO.setAct_booking_date(rs.getTimestamp(3).toLocalDateTime());
				actOrderVO.setAct_order_total_price(rs.getInt(4));
				actOrderVO.setAct_order_title(rs.getString(5));
				actOrderVO.setAct_order_name(rs.getString(6));
				actOrderVO.setAct_order_phone(rs.getString(7));
				actOrderVO.setAct_order_email(rs.getString(8));
				actOrderVO.setAct_order_credit_card(rs.getString(9));
				list.add(actOrderVO);
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
	public List<ActivityOrderVO> getAll() {
		List<ActivityOrderVO> list = new ArrayList<>();
		ActivityOrderVO actOrderVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();			
			while (rs.next()) {
				actOrderVO = new ActivityOrderVO();
				actOrderVO.setAct_order_no(rs.getInt(1));
				actOrderVO.setMem_no(rs.getInt(2));
				actOrderVO.setAct_booking_date(rs.getTimestamp(3).toLocalDateTime());
				actOrderVO.setAct_order_total_price(rs.getInt(4));
				actOrderVO.setAct_order_title(rs.getString(5));
				actOrderVO.setAct_order_name(rs.getString(6));
				actOrderVO.setAct_order_phone(rs.getString(7));
				actOrderVO.setAct_order_email(rs.getString(8));
				actOrderVO.setAct_order_credit_card(rs.getString(9));
				list.add(actOrderVO);
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
		ActivityOrderJDBCDAO dao = new ActivityOrderJDBCDAO();
//		List<ActivityOrderVO> list = dao.findByMemberNo(1);
//		ActivityOrderVO vo = new ActivityOrderVO();
//		ActivityOrderVO vo = dao.findByPk(1);
//		List<ActivityOrderVO> list = dao.getAll();
//		vo.setAct_order_no(1);
//		vo.setMem_no(1);
//		vo.setAct_booking_date(LocalDateTime.now());
//		vo.setAct_order_total_price(2000);
//		vo.setAct_order_title("先生");
//		vo.setAct_order_name("測試用1");
//		vo.setAct_order_phone("0912345631");
//		vo.setAct_order_email("xxxxx@gmail.com");
//		vo.setAct_order_credit_card("abcdefg123456789");
//		dao.insert(vo);
//		vo.setAct_order_no(1);
//		
//		dao.update(vo);
//		
//		ActivityOrderVO vo = dao.findByPk(2);
		List<ActivityOrderVO> list = dao.getAll();
		for(ActivityOrderVO vo : list)
		System.out.println(vo);
		
	}

	@Override
	public void insertWithOrderDetails(ActivityOrderVO actOrderVO, List<ActivityOrderDetailVO> list) {
		// TODO Auto-generated method stub
		
	}
	
}
