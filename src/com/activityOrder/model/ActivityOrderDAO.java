package com.activityOrder.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.activityOrderDetail.model.ActivityOrderDetailDAO;
import com.activityOrderDetail.model.ActivityOrderDetailVO;


public class ActivityOrderDAO implements I_ActivityOrderDAO {
	private static DataSource ds;
	
	private final String[] GET_KEY = {"act_order_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_ORDER";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_ORDER VALUES(?,?,?,?,?,?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_ORDER SET mem_no = ?,act_booking_date = ?,act_order_total_price = ?,act_order_title = ?,act_order_name = ?"
			+ ",act_order_phone = ?,act_order_email = ?,act_order_credit_card = ? WHERE act_order_no = ?"; //字串串接 where前要空一行
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_ORDER WHERE act_order_no = ?";
	private final String SELECT_BY_MEMBER_NO_SQL = "SELECT * FROM ACTIVITY_ORDER WHERE mem_no = ?";

	static {	
		try {
			ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException ex) {
			ex.printStackTrace();
		}	
	}
	
	
	
	@Override
	public ActivityOrderVO insert(ActivityOrderVO actOrderVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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

	@Override
	public void insertWithOrderDetails(ActivityOrderVO actOrderVO, List<ActivityOrderDetailVO> list) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			//交易開始
			con.setAutoCommit(false);
			
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
			
			Integer act_order_no = null;
			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				act_order_no = rs.getInt(1);
				actOrderVO.setAct_order_no(act_order_no);
System.out.println("訂單自增主鍵值:"+act_order_no);				
			}else {
System.out.println("未取得自增主鍵值");				
			}

			rs.close();
			ActivityOrderDetailDAO dao = new ActivityOrderDetailDAO();
			for(ActivityOrderDetailVO vo : list) {
				vo.setAct_order_no(act_order_no);
				dao.insertWithOrder(vo, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
System.out.println("新增訂單編號" + act_order_no + "時,共有" + list.size()
			+ "筆訂單明細同時被新增");			
			
		} catch (SQLException ex) {
			if(con != null) {
				try {
System.out.println("Rollback");
					con.rollback();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
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
