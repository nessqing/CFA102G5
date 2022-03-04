package com.roomOrderDetail.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RoomOrderDetailDAO implements I_RoomOrderDetailDAO {
	private static final String INSERT = "INSERT INTO room_order_detail (ord_no) VALUES (?)";
	private static final String CHECKIN = "UPDATE room_order_detail SET checkin_date = CURDATE(), rm_no = ?, detail_state = 2 WHERE detail_no = ?";
	private static final String CHECKOUT = "UPDATE room_order_detail SET checkout_date = CURDATE(), rm_no = null, detail_state = 3 WHERE detail_no = ?";
	private static final String GET_ONE = "SELECT * FROM room_order_detail WHERE detail_no = ?";
	private static final String CHECKOUT_LIST = "SELECT * FROM room_order_detail d join room_order r on d.ord_no = r.ord_no WHERE end_date=curdate() AND detail_state = 2";
	private static final String STAY_LIST = "SELECT * FROM room_order_detail d join room_order r on d.ord_no = r.ord_no WHERE end_date>curdate() AND detail_state = 2";
	private static final String GET_ALL = "SELECT * FROM room_order_detail";
	private static final String GET_ALL_BY_ORDNO = "SELECT * FROM room_order_detail WHERE ord_no = ?";

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public RoomOrderDetailVO insert(RoomOrderDetailVO detailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, detailVO.getOrd_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return detailVO;
	}

	@Override
	public void insertAuto(RoomOrderDetailVO detailVO, Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, detailVO.getOrd_no());

			Statement stmt = con.createStatement();
//			stmt.executeUpdate("set auto_increment_offset=22;"); // 自增主鍵-初始值
			stmt.executeUpdate("set auto_increment_increment=1;"); // 自增主鍵-遞增
			pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderDetail");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
//			注意！不要關，關了之後幾筆增不了
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
		}
	}

	@Override
	public void checkin(RoomOrderDetailVO detailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKIN);

			pstmt.setString(1, detailVO.getRm_no());
			pstmt.setInt(2, detailVO.getDetail_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void checkout(RoomOrderDetailVO detailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKOUT);
			pstmt.setInt(1, detailVO.getDetail_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public RoomOrderDetailVO getOne(Integer detail_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setInt(1, detail_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				if (rs.getDate("checkin_date") != null) {
					detailVO.setCheckin_date(rs.getDate("checkin_date"));
				}
				if (rs.getDate("checkout_date") != null) {
					detailVO.setCheckout_date(rs.getDate("checkout_date"));
				}
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return detailVO;
	}

	@Override
	public List<RoomOrderDetailVO> checkoutList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKOUT_LIST);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				if (rs.getDate("checkin_date") != null) {
					detailVO.setCheckin_date(rs.getDate("checkin_date"));
				}
				if (rs.getDate("checkout_date") != null) {
					detailVO.setCheckout_date(rs.getDate("checkout_date"));
				}
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<RoomOrderDetailVO> stayList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(STAY_LIST);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				if (rs.getDate("checkin_date") != null) {
					detailVO.setCheckin_date(rs.getDate("checkin_date"));
				}
				if (rs.getDate("checkout_date") != null) {
					detailVO.setCheckout_date(rs.getDate("checkout_date"));
				}
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<RoomOrderDetailVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				if (rs.getDate("checkin_date") != null) {
					detailVO.setCheckin_date(rs.getDate("checkin_date"));
				}
				if (rs.getDate("checkout_date") != null) {
					detailVO.setCheckout_date(rs.getDate("checkout_date"));
				}
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<RoomOrderDetailVO> getAllByOrdno(Integer ord_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_ORDNO);
			pstmt.setInt(1, ord_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				if (rs.getDate("checkin_date") != null) {
					detailVO.setCheckin_date(rs.getDate("checkin_date"));
				}
				if (rs.getDate("checkout_date") != null) {
					detailVO.setCheckout_date(rs.getDate("checkout_date"));
				}
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
