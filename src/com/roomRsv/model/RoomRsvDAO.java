package com.roomRsv.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RoomRsvDAO implements I_RoomRsvDAO {

	private static final String INSERT = "INSERT INTO ROOM_RSV(type_no, rsv_date, rm_total) WITH RECURSIVE dates (v_date) AS ( SELECT CURDATE() UNION ALL SELECT v_date + INTERVAL 1 DAY FROM dates WHERE v_date + INTERVAL 1 DAY <= ADDDATE(CURDATE(), INTERVAL 3 MONTH )) SELECT t.type_no, d.v_date, c.total FROM dates d NATURAL JOIN ROOM_TYPE t LEFT JOIN ROOM_RSV r on (d.v_date = r.rsv_date AND t.type_no = r.type_no) JOIN (SELECT type_no, COUNT(*) as total FROM room WHERE rm_state != 4 GROUP BY type_no) c ON c.type_no = t.type_no WHERE r.rsv_date IS NULL";
	private static final String RESERVE = "UPDATE room_rsv SET rsv_total = rsv_total+? WHERE type_no = ? AND (rsv_date BETWEEN ? AND SUBDATE( ?, INTERVAL 1 DAY))";
	private static final String CANCEL = "UPDATE room_rsv SET rsv_total = rsv_total-? WHERE type_no = ? AND (rsv_date BETWEEN ? AND SUBDATE( ?, INTERVAL 1 DAY))";
	private static final String CHECK_OUT_EARLY = "UPDATE room_rsv SET rsv_total = rsv_total-1 WHERE type_no = ? AND (rsv_date BETWEEN CURDATE() AND SUBDATE(?, INTERVAL 1 DAY))";
	private static final String DELETE = "DELETE FROM room_rsv WHERE rsv_date < CURDATE()";
	private static final String GET_ONE_BY_DATE_TYPE = "SELECT * FROM room_rsv WHERE rsv_date = ? AND rm_type = ?";
	private static final String GET_ONEDAY_BY_DATE = "SELECT * FROM room_rsv WHERE rsv_date = ?";
	private static final String GET_NOT_RSV = "SELECT * FROM room_rsv WHERE (rm_total-rsv_total) < ? AND type_no = ?";
	private static final String GET_ALL = "SELECT * FROM room_rsv ORDER BY rsv_date";
	private static final String GET_ALL_BY_TYPE = "SELECT * FROM room_rsv WHERE type_no = ?";

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
	public void insert() {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
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
	public void reserve(Integer qty, Integer type_no, Date start_date, Date end_date) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(RESERVE);

			pstmt.setInt(1, qty);
			pstmt.setInt(2, type_no);
			pstmt.setDate(3, start_date);
			pstmt.setDate(4, end_date);

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
	public void cancel(Integer qty, Integer type_no, Date start_date, Date end_date) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CANCEL);

			pstmt.setInt(1, qty);
			pstmt.setInt(2, type_no);
			pstmt.setDate(3, start_date);
			pstmt.setDate(4, end_date);

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
	public void checkOutEarly(Integer type_no, Date end_date) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_OUT_EARLY);

			pstmt.setInt(1, type_no);
			pstmt.setDate(2, end_date);

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
	public void delete() {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
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
	public RoomRsvVO getOneByDateType(Date rsv_date, Integer type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		RoomRsvVO roomRsvVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_DATE_TYPE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomRsvVO = new RoomRsvVO();
				roomRsvVO.setRsv_date(rs.getDate("rsv_date"));
				roomRsvVO.setType_no(rs.getInt("type_no"));
				roomRsvVO.setRm_total(rs.getInt("rm_total"));
				roomRsvVO.setRsv_total(rs.getInt("rsv_total"));
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
		return roomRsvVO;
	}

	@Override
	public List<RoomRsvVO> getOneDayByDate(Date rsv_date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomRsvVO> list = new ArrayList<>();
		RoomRsvVO roomRsvVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONEDAY_BY_DATE);
			pstmt.setObject(1, rsv_date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomRsvVO = new RoomRsvVO();
				roomRsvVO.setRsv_date(rs.getDate("rsv_date"));
				roomRsvVO.setType_no(rs.getInt("type_no"));
				roomRsvVO.setRm_total(rs.getInt("rm_total"));
				roomRsvVO.setRsv_total(rs.getInt("rsv_total"));
				list.add(roomRsvVO);
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
	public List<RoomRsvVO> getNotRsv(Integer qty, Integer type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomRsvVO> list = new ArrayList<>();
		RoomRsvVO roomRsvVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NOT_RSV);
			pstmt.setObject(1, qty);
			pstmt.setObject(2, type_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomRsvVO = new RoomRsvVO();
				roomRsvVO.setRsv_date(rs.getDate("rsv_date"));
				roomRsvVO.setType_no(rs.getInt("type_no"));
				roomRsvVO.setRm_total(rs.getInt("rm_total"));
				roomRsvVO.setRsv_total(rs.getInt("rsv_total"));
				list.add(roomRsvVO);
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
	public List<RoomRsvVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomRsvVO> list = new ArrayList<>();
		RoomRsvVO roomRsvVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomRsvVO = new RoomRsvVO();
				roomRsvVO.setRsv_date(rs.getDate("rsv_date"));
				roomRsvVO.setType_no(rs.getInt("type_no"));
				roomRsvVO.setRm_total(rs.getInt("rm_total"));
				roomRsvVO.setRsv_total(rs.getInt("rsv_total"));
				list.add(roomRsvVO);
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
	public List<RoomRsvVO> getAllByType(Integer type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomRsvVO> list = new ArrayList<>();
		RoomRsvVO roomRsvVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_TYPE);
			pstmt.setInt(1, type_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomRsvVO = new RoomRsvVO();
				roomRsvVO.setRsv_date(rs.getDate("rsv_date"));
				roomRsvVO.setType_no(rs.getInt("type_no"));
				roomRsvVO.setRm_total(rs.getInt("rm_total"));
				roomRsvVO.setRsv_total(rs.getInt("rsv_total"));
				list.add(roomRsvVO);
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
