package com.activityOrderDetail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class ActivityOrderDetailJDBCDAO implements I_ActivityOrderDetailDAO {

	private final String[] GET_KEY = {"act_order_detail_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_ORDER_DETAIL";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_ORDER_DETAIL VALUES(?,?,?,?,?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_ORDER_DETAIL SET act_order_no = ?,act_session_no = ?,act_real_join_number = ?,act_order_price = ?,act_coupon_price = ? "
			+ ",act_price_total = ?,act_order_detail_state = ? WHERE act_order_detail_no = ?";
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_ORDER_DETAIL WHERE act_order_detail_no = ?";
	private final String SELECT_BY_ACTIVITY_ORDER_NO_SQL = "SELECT * FROM ACTIVITY_ORDER_DETAIL WHERE act_order_no = ?";
	private final String SELECT_BY_ACTIVITY_SESSION_NO_SQL = "SELECT * FROM ACTIVITY_ORDER_DETAIL WHERE act_session_no = ?";
	private final String SELECT_BY_ACTIVITY_ORDER_DETAIL_STATE_SQL = "SELECT * FROM ACTIVITY_ORDER_DETAIL WHERE act_order_detail_state = ?";
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Override
	public ActivityOrderDetailVO insert(ActivityOrderDetailVO actOrderDetailVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1,null);
			ps.setInt(2,actOrderDetailVO.getAct_order_no());
			ps.setInt(3,actOrderDetailVO.getAct_session_no());
			ps.setInt(4,actOrderDetailVO.getAct_real_join_number());
			ps.setInt(5,actOrderDetailVO.getAct_order_price());
			ps.setInt(6,actOrderDetailVO.getAct_coupon_price());
			ps.setInt(7,actOrderDetailVO.getAct_price_total());
			ps.setInt(8,actOrderDetailVO.getAct_order_detail_state());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actOrderDetailVO.setAct_order_detail_no(rs.getInt(1));
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
		
		return actOrderDetailVO;
	}

	@Override
	public void update(ActivityOrderDetailVO actOrderDetailVO) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setInt(1,actOrderDetailVO.getAct_order_no());
			ps.setInt(2,actOrderDetailVO.getAct_session_no());
			ps.setInt(3,actOrderDetailVO.getAct_real_join_number());
			ps.setInt(4,actOrderDetailVO.getAct_order_price());
			ps.setInt(5,actOrderDetailVO.getAct_coupon_price());
			ps.setInt(6,actOrderDetailVO.getAct_price_total());
			ps.setInt(7,actOrderDetailVO.getAct_order_detail_state());
			ps.setInt(8,actOrderDetailVO.getAct_order_detail_no()); 
			
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
	public ActivityOrderDetailVO findByPk(Integer act_order_detail_no) {
		ActivityOrderDetailVO actOrderDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1,act_order_detail_no);
			rs = ps.executeQuery();	
			while (rs.next()) {
				actOrderDetailVO = new ActivityOrderDetailVO();
				actOrderDetailVO.setAct_order_detail_no(rs.getInt(1));
				actOrderDetailVO.setAct_order_no(rs.getInt(2));
				actOrderDetailVO.setAct_session_no(rs.getInt(3));
				actOrderDetailVO.setAct_real_join_number(rs.getInt(4));
				actOrderDetailVO.setAct_order_price(rs.getInt(5));
				actOrderDetailVO.setAct_coupon_price(rs.getInt(6));
				actOrderDetailVO.setAct_price_total(rs.getInt(7));
				actOrderDetailVO.setAct_order_detail_state(rs.getInt(8));
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
		
		return actOrderDetailVO;
	}

	@Override
	public List<ActivityOrderDetailVO> findByActOrderNo(Integer act_order_no) {
		List<ActivityOrderDetailVO> list = new ArrayList<>();
		ActivityOrderDetailVO actOrderDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_ORDER_NO_SQL);
			ps.setInt(1, act_order_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actOrderDetailVO = new ActivityOrderDetailVO();
				actOrderDetailVO.setAct_order_detail_no(rs.getInt(1));
				actOrderDetailVO.setAct_order_no(rs.getInt(2));
				actOrderDetailVO.setAct_session_no(rs.getInt(3));
				actOrderDetailVO.setAct_real_join_number(rs.getInt(4));
				actOrderDetailVO.setAct_order_price(rs.getInt(5));
				actOrderDetailVO.setAct_coupon_price(rs.getInt(6));
				actOrderDetailVO.setAct_price_total(rs.getInt(7));
				actOrderDetailVO.setAct_order_detail_state(rs.getInt(8));
				list.add(actOrderDetailVO);
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
	public List<ActivityOrderDetailVO> findByActSessionNo(Integer act_session_no) {
		List<ActivityOrderDetailVO> list = new ArrayList<>();
		ActivityOrderDetailVO actOrderDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_SESSION_NO_SQL);
			ps.setInt(1, act_session_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actOrderDetailVO = new ActivityOrderDetailVO();
				actOrderDetailVO.setAct_order_detail_no(rs.getInt(1));
				actOrderDetailVO.setAct_order_no(rs.getInt(2));
				actOrderDetailVO.setAct_session_no(rs.getInt(3));
				actOrderDetailVO.setAct_real_join_number(rs.getInt(4));
				actOrderDetailVO.setAct_order_price(rs.getInt(5));
				actOrderDetailVO.setAct_coupon_price(rs.getInt(6));
				actOrderDetailVO.setAct_price_total(rs.getInt(7));
				actOrderDetailVO.setAct_order_detail_state(rs.getInt(8));
				list.add(actOrderDetailVO);
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
	public List<ActivityOrderDetailVO> getActOrderDetailState(Integer act_order_detail_state) {
		List<ActivityOrderDetailVO> list = new ArrayList<>();
		ActivityOrderDetailVO actOrderDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_ORDER_DETAIL_STATE_SQL);
			
			ps.setInt(1,act_order_detail_state);
			rs = ps.executeQuery();	
			while (rs.next()) {
				actOrderDetailVO = new ActivityOrderDetailVO();
				actOrderDetailVO.setAct_order_detail_no(rs.getInt(1));
				actOrderDetailVO.setAct_order_no(rs.getInt(2));
				actOrderDetailVO.setAct_session_no(rs.getInt(3));
				actOrderDetailVO.setAct_real_join_number(rs.getInt(4));
				actOrderDetailVO.setAct_order_price(rs.getInt(5));
				actOrderDetailVO.setAct_coupon_price(rs.getInt(6));
				actOrderDetailVO.setAct_price_total(rs.getInt(7));
				actOrderDetailVO.setAct_order_detail_state(rs.getInt(8));
				list.add(actOrderDetailVO);
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
	public List<ActivityOrderDetailVO> getAll() {
		List<ActivityOrderDetailVO> list = new ArrayList<>();
		ActivityOrderDetailVO actOrderDetailVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();	
			while (rs.next()) {
				actOrderDetailVO = new ActivityOrderDetailVO();
				actOrderDetailVO.setAct_order_detail_no(rs.getInt(1));
				actOrderDetailVO.setAct_order_no(rs.getInt(2));
				actOrderDetailVO.setAct_session_no(rs.getInt(3));
				actOrderDetailVO.setAct_real_join_number(rs.getInt(4));
				actOrderDetailVO.setAct_order_price(rs.getInt(5));
				actOrderDetailVO.setAct_coupon_price(rs.getInt(6));
				actOrderDetailVO.setAct_price_total(rs.getInt(7));
				actOrderDetailVO.setAct_order_detail_state(rs.getInt(8));
				list.add(actOrderDetailVO);
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
		ActivityOrderDetailJDBCDAO dao = new ActivityOrderDetailJDBCDAO();
//		ActivityOrderDetailVO vo = new ActivityOrderDetailVO();
//		
//		vo.setAct_order_no(2);
//		vo.setAct_session_no(2);
//		vo.setAct_real_join_number(15);
//		vo.setAct_order_price(2000);
//		vo.setAct_coupon_price(200);
//		vo.setAct_price_total(1800);
//		vo.setAct_order_detail_state(3);
//		
//		dao.insert(vo);
//		vo.setAct_order_detail_no(1);
//		dao.update(vo);
//		vo = dao.findByPk(1);
//		List<ActivityOrderDetailVO> list =dao.findByActOrderNo(2);
//		List<ActivityOrderDetailVO> list =dao.findByActSessionNo(2);
		List<ActivityOrderDetailVO> list = dao.getAll();
//		List<ActivityOrderDetailVO> list = dao.getActOrderDetailState(3);
		for(ActivityOrderDetailVO vo : list)
		System.out.println(vo);
	}

	@Override
	public void orderDetailUpdate(Integer act_real_join_number, Integer act_price_total, Integer act_order_no,
			Integer act_session_no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchOrderDetailState(Integer act_order_detail_no, Integer act_order_detail_state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertWithOrder(ActivityOrderDetailVO actOrderDetailVO, Connection con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchOrderDetailState(Integer act_order_no, Integer act_session_no, Integer act_order_detail_state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActivityOrderDetailVO findByActOrderNoAndByActSessionNO(Integer act_order_no, Integer act_session_no) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
