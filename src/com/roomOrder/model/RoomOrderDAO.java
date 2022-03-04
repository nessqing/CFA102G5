package com.roomOrder.model;

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

import com.roomOrderDetail.model.RoomOrderDetailDAO;
import com.roomOrderDetail.model.RoomOrderDetailVO;

public class RoomOrderDAO implements I_RoomOrderDAO {
	private static final String INSERT = "INSERT INTO room_order (mem_no, type_no, start_date, end_date, rm_num, price, total_price, note, title, name, phone, email, payment, ord_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? , NOW())";
	private static final String UPDATE = "UPDATE room_order SET ord_state = 4 WHERE ord_no = ?";
	private static final String CANCEL = "UPDATE room_order SET total_price = 0, ord_state = 3 WHERE ord_no = ?";
	private static final String CHANGE = "UPDATE room_order SET start_date = ?, end_date = ?,ord_state = 2 WHERE ord_no = ?";
	private static final String OVERDUE = "UPDATE room_order SET ord_state = 4 WHERE end_date <= CURDATE()";
	private static final String GET_ONE = "SELECT * FROM room_order WHERE ord_no = ?";
	private static final String GET_ALL = "SELECT * FROM room_order ORDER BY ord_no DESC";
	private static final String GET_ALL_BY_ORDSTATE = "SELECT * FROM room_order WHERE ord_state = ? ORDER BY ord_no DESC";
	private static final String GET_ALL_BY_TYPE = "SELECT * FROM room_order WHERE type_no = ? ORDER BY ord_no DESC";
	private static final String GET_ALL_BY_MEM = "SELECT * FROM room_order WHERE mem_no = ?";
	private static final String CHECKIN_LIST = "SELECT * FROM room_order WHERE (ord_state = 1 OR ord_state = 2) AND start_date<=curdate()";
	private static final String ROOM_STAY_RATE = "SELECT ROUND(SUM(rm_num)/(SELECT COUNT(*) FROM room WHERE rm_state!=4)*100,0) as rate FROM room_order WHERE (CURDATE() BETWEEN start_date AND SUBDATE(end_date, INTERVAL 1 DAY)) AND (ord_state = 1 OR ord_state = 4)";

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
	public RoomOrderVO insert(RoomOrderVO roomOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, roomOrderVO.getMem_no());
			pstmt.setInt(2, roomOrderVO.getType_no());
			pstmt.setDate(3, roomOrderVO.getStart_date());
			pstmt.setDate(4, roomOrderVO.getEnd_date());
			pstmt.setInt(5, roomOrderVO.getRm_num());
			pstmt.setInt(6, roomOrderVO.getPrice());
			pstmt.setInt(7, roomOrderVO.getTotal_price());
			pstmt.setString(8, roomOrderVO.getNote());
			pstmt.setString(9, roomOrderVO.getTitle());
			pstmt.setString(10, roomOrderVO.getName());
			pstmt.setString(11, roomOrderVO.getPhone());
			pstmt.setString(12, roomOrderVO.getEmail());
			pstmt.setString(13, roomOrderVO.getPayment());

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
		return roomOrderVO;
	}

	@Override
	public Integer insertAuto(RoomOrderVO roomOrderVO, List<RoomOrderDetailVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer next_ord_no = null;

		try {
			con = ds.getConnection();
			// 設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單編號
			String cols[] = { "ord_no" };
			pstmt = con.prepareStatement(INSERT, cols);
			pstmt.setInt(1, roomOrderVO.getMem_no());
			pstmt.setInt(2, roomOrderVO.getType_no());
			pstmt.setDate(3, roomOrderVO.getStart_date());
			pstmt.setDate(4, roomOrderVO.getEnd_date());
			pstmt.setInt(5, roomOrderVO.getRm_num());
			pstmt.setInt(6, roomOrderVO.getPrice());
			pstmt.setInt(7, roomOrderVO.getTotal_price());
			pstmt.setString(8, roomOrderVO.getNote());
			pstmt.setString(9, roomOrderVO.getTitle());
			pstmt.setString(10, roomOrderVO.getName());
			pstmt.setString(11, roomOrderVO.getPhone());
			pstmt.setString(12, roomOrderVO.getEmail());
			pstmt.setString(13, roomOrderVO.getPayment());
			Statement stmt = con.createStatement();
//			stmt.executeUpdate("set auto_increment_offset=20;"); // 自增主鍵-初始值
			stmt.executeUpdate("set auto_increment_increment=1;"); // 自增主鍵-遞增
			pstmt.executeUpdate();

			// 掘取對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_ord_no = rs.getInt(1);
				System.out.println("自增主鍵值= " + next_ord_no + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			// 再同時新增訂單明細
			RoomOrderDetailDAO dao = new RoomOrderDetailDAO();
			System.out.println("list.size()-A=" + list.size());
			for (RoomOrderDetailVO aDetail : list) {
				aDetail.setOrd_no(new Integer(next_ord_no));
				dao.insertAuto(aDetail, con);
			}

			// 設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增訂單編號" + next_ord_no + "時,共有明細" + list.size() + "筆同時被新增");

		} catch (SQLException se) {
			next_ord_no = null;
			if (con != null) {
				try {
					// 設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-roomOrder");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		return next_ord_no;
	}

	@Override
	public void update(Integer ord_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, ord_no);

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
	public void cancel(RoomOrderVO roomOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CANCEL);
			pstmt.setInt(1, roomOrderVO.getOrd_no());

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
	public void change(RoomOrderVO roomOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGE);

			pstmt.setDate(1, roomOrderVO.getStart_date());
			pstmt.setDate(2, roomOrderVO.getEnd_date());
			pstmt.setInt(3, roomOrderVO.getOrd_no());

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
	public void overdue() {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(OVERDUE);
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
	public RoomOrderVO getOne(Integer ord_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		RoomOrderVO roomOrderVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setInt(1, ord_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomOrderVO = new RoomOrderVO();
				roomOrderVO.setOrd_no(rs.getInt("ord_no"));
				roomOrderVO.setMem_no(rs.getInt("mem_no"));
				roomOrderVO.setType_no(rs.getInt("type_no"));
				roomOrderVO.setStart_date(rs.getDate("start_date"));
				roomOrderVO.setEnd_date(rs.getDate("end_date"));
				roomOrderVO.setRm_num(rs.getInt("rm_num"));
				roomOrderVO.setPrice(rs.getInt("price"));
				roomOrderVO.setTotal_price(rs.getInt("total_price"));
				roomOrderVO.setNote(rs.getString("note"));
				roomOrderVO.setTitle(rs.getString("title"));
				roomOrderVO.setName(rs.getString("name"));
				roomOrderVO.setPhone(rs.getString("phone"));
				roomOrderVO.setEmail(rs.getString("email"));
				roomOrderVO.setPayment(rs.getString("payment"));
				roomOrderVO.setOrd_date(rs.getDate("ord_date"));
				roomOrderVO.setOrd_state(rs.getInt("ord_state"));
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
		return roomOrderVO;
	}

	@Override
	public List<RoomOrderVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderVO> list = new ArrayList<>();
		RoomOrderVO roomOrderVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomOrderVO = new RoomOrderVO();
				roomOrderVO.setOrd_no(rs.getInt("ord_no"));
				roomOrderVO.setMem_no(rs.getInt("mem_no"));
				roomOrderVO.setType_no(rs.getInt("type_no"));
				roomOrderVO.setStart_date(rs.getDate("start_date"));
				roomOrderVO.setEnd_date(rs.getDate("end_date"));
				roomOrderVO.setRm_num(rs.getInt("rm_num"));
				roomOrderVO.setPrice(rs.getInt("price"));
				roomOrderVO.setTotal_price(rs.getInt("total_price"));
				roomOrderVO.setNote(rs.getString("note"));
				roomOrderVO.setTitle(rs.getString("title"));
				roomOrderVO.setName(rs.getString("name"));
				roomOrderVO.setPhone(rs.getString("phone"));
				roomOrderVO.setEmail(rs.getString("email"));
				roomOrderVO.setPayment(rs.getString("payment"));
				roomOrderVO.setOrd_date(rs.getDate("ord_date"));
				roomOrderVO.setOrd_state(rs.getInt("ord_state"));
				list.add(roomOrderVO);
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
	public List<RoomOrderVO> getAllByOrdState(Integer ord_state) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderVO> list = new ArrayList<>();
		RoomOrderVO roomOrderVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_ORDSTATE);
			pstmt.setInt(1, ord_state);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomOrderVO = new RoomOrderVO();
				roomOrderVO.setOrd_no(rs.getInt("ord_no"));
				roomOrderVO.setMem_no(rs.getInt("mem_no"));
				roomOrderVO.setType_no(rs.getInt("type_no"));
				roomOrderVO.setStart_date(rs.getDate("start_date"));
				roomOrderVO.setEnd_date(rs.getDate("end_date"));
				roomOrderVO.setRm_num(rs.getInt("rm_num"));
				roomOrderVO.setPrice(rs.getInt("price"));
				roomOrderVO.setTotal_price(rs.getInt("total_price"));
				roomOrderVO.setNote(rs.getString("note"));
				roomOrderVO.setTitle(rs.getString("title"));
				roomOrderVO.setName(rs.getString("name"));
				roomOrderVO.setPhone(rs.getString("phone"));
				roomOrderVO.setEmail(rs.getString("email"));
				roomOrderVO.setPayment(rs.getString("payment"));
				roomOrderVO.setOrd_date(rs.getDate("ord_date"));
				roomOrderVO.setOrd_state(rs.getInt("ord_state"));
				list.add(roomOrderVO);
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
	public List<RoomOrderVO> getAllByType(Integer type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderVO> list = new ArrayList<>();
		RoomOrderVO roomOrderVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_TYPE);
			pstmt.setInt(1, type_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomOrderVO = new RoomOrderVO();
				roomOrderVO.setOrd_no(rs.getInt("ord_no"));
				roomOrderVO.setMem_no(rs.getInt("mem_no"));
				roomOrderVO.setType_no(rs.getInt("type_no"));
				roomOrderVO.setStart_date(rs.getDate("start_date"));
				roomOrderVO.setEnd_date(rs.getDate("end_date"));
				roomOrderVO.setRm_num(rs.getInt("rm_num"));
				roomOrderVO.setPrice(rs.getInt("price"));
				roomOrderVO.setTotal_price(rs.getInt("total_price"));
				roomOrderVO.setNote(rs.getString("note"));
				roomOrderVO.setTitle(rs.getString("title"));
				roomOrderVO.setName(rs.getString("name"));
				roomOrderVO.setPhone(rs.getString("phone"));
				roomOrderVO.setEmail(rs.getString("email"));
				roomOrderVO.setPayment(rs.getString("payment"));
				roomOrderVO.setOrd_date(rs.getDate("ord_date"));
				roomOrderVO.setOrd_state(rs.getInt("ord_state"));
				list.add(roomOrderVO);
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
	public List<RoomOrderVO> getAllByMem(Integer mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderVO> list = new ArrayList<>();
		RoomOrderVO roomOrderVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_MEM);
			pstmt.setInt(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomOrderVO = new RoomOrderVO();
				roomOrderVO.setOrd_no(rs.getInt("ord_no"));
				roomOrderVO.setMem_no(rs.getInt("mem_no"));
				roomOrderVO.setType_no(rs.getInt("type_no"));
				roomOrderVO.setStart_date(rs.getDate("start_date"));
				roomOrderVO.setEnd_date(rs.getDate("end_date"));
				roomOrderVO.setRm_num(rs.getInt("rm_num"));
				roomOrderVO.setPrice(rs.getInt("price"));
				roomOrderVO.setTotal_price(rs.getInt("total_price"));
				roomOrderVO.setNote(rs.getString("note"));
				roomOrderVO.setTitle(rs.getString("title"));
				roomOrderVO.setName(rs.getString("name"));
				roomOrderVO.setPhone(rs.getString("phone"));
				roomOrderVO.setEmail(rs.getString("email"));
				roomOrderVO.setPayment(rs.getString("payment"));
				roomOrderVO.setOrd_date(rs.getDate("ord_date"));
				roomOrderVO.setOrd_state(rs.getInt("ord_state"));
				list.add(roomOrderVO);
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
	public List<RoomOrderVO> checkInList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomOrderVO> list = new ArrayList<>();
		RoomOrderVO roomOrderVO = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKIN_LIST);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomOrderVO = new RoomOrderVO();
				roomOrderVO.setOrd_no(rs.getInt("ord_no"));
				roomOrderVO.setMem_no(rs.getInt("mem_no"));
				roomOrderVO.setType_no(rs.getInt("type_no"));
				roomOrderVO.setStart_date(rs.getDate("start_date"));
				roomOrderVO.setEnd_date(rs.getDate("end_date"));
				roomOrderVO.setRm_num(rs.getInt("rm_num"));
				roomOrderVO.setPrice(rs.getInt("price"));
				roomOrderVO.setTotal_price(rs.getInt("total_price"));
				roomOrderVO.setNote(rs.getString("note"));
				roomOrderVO.setTitle(rs.getString("title"));
				roomOrderVO.setName(rs.getString("name"));
				roomOrderVO.setPhone(rs.getString("phone"));
				roomOrderVO.setEmail(rs.getString("email"));
				roomOrderVO.setPayment(rs.getString("payment"));
				roomOrderVO.setOrd_date(rs.getDate("ord_date"));
				roomOrderVO.setOrd_state(rs.getInt("ord_state"));
				list.add(roomOrderVO);
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
	public Integer getRoomStayRate() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer rate = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ROOM_STAY_RATE);
			rs = pstmt.executeQuery();

			rs.next();
			rate = rs.getInt("rate");
			System.out.print("====" + rate);
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
		return rate;
	}

}
