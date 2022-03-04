package com.roomOrderDetail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class RoomOrderDetailJDBCDAO implements I_RoomOrderDetailDAO {
	private static final String INSERT = "INSERT INTO room_order_detail (ord_no) VALUES (?)";
	private static final String CHECKIN = "UPDATE room_order_detail SET checkin_date = CURDATE(), rm_no = ?, detail_state = 2 WHERE detail_no = ?";
	private static final String CHECKOUT = "UPDATE room_order_detail SET checkout_date = CURDATE(), rm_no = null, detail_state = 3 WHERE detail_no = ?";
	private static final String GET_ONE = "SELECT * FROM room_order_detail WHERE detail_no = ?";
	private static final String CHECKOUT_LIST = "SELECT * FROM room_order_detail d join room_order r on d.ord_no = r.ord_no WHERE end_date=curdate() AND detail_state = 2";
	private static final String STAY_LIST = "SELECT * FROM room_order_detail d join room_order r on d.ord_no = r.ord_no WHERE end_date>curdate() AND detail_state = 2";
	private static final String GET_ALL = "SELECT * FROM room_order_detail";
	private static final String GET_ALL_BY_ORDNO = "SELECT * FROM room_order_detail WHERE ord_no = ?";

	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public RoomOrderDetailVO insert(RoomOrderDetailVO detailVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, detailVO.getOrd_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return detailVO;
	}

	@Override
	public void insertAuto(RoomOrderDetailVO detailVO, Connection con) {

		try {
			PreparedStatement pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, detailVO.getOrd_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	@Override
	public void checkin(RoomOrderDetailVO detailVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(CHECKIN);

			pstmt.setString(1, detailVO.getRm_no());
			pstmt.setInt(2, detailVO.getDetail_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	@Override
	public void checkout(RoomOrderDetailVO detailVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(CHECKOUT);
			pstmt.setInt(1, detailVO.getDetail_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	@Override
	public RoomOrderDetailVO getOne(Integer detail_no) {
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ONE);
			pstmt.setInt(1, detail_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				detailVO.setCheckin_date(rs.getDate("checkin_date"));
				detailVO.setCheckout_date(rs.getDate("checkout_date"));
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return detailVO;
	}

	@Override
	public List<RoomOrderDetailVO> checkoutList() {
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(CHECKOUT_LIST);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				detailVO.setCheckin_date(rs.getDate("checkin_date"));
				detailVO.setCheckout_date(rs.getDate("checkout_date"));
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}

	@Override
	public List<RoomOrderDetailVO> stayList() {
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(STAY_LIST);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				detailVO.setCheckin_date(rs.getDate("checkin_date"));
				detailVO.setCheckout_date(rs.getDate("checkout_date"));
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}

	@Override
	public List<RoomOrderDetailVO> getAll() {
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				detailVO.setCheckin_date(rs.getDate("checkin_date"));
				detailVO.setCheckout_date(rs.getDate("checkout_date"));
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}

	@Override
	public List<RoomOrderDetailVO> getAllByOrdno(Integer ord_no) {
		List<RoomOrderDetailVO> list = new ArrayList<>();
		RoomOrderDetailVO detailVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL_BY_ORDNO);
			pstmt.setInt(1, ord_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				detailVO = new RoomOrderDetailVO();
				detailVO.setDetail_no(rs.getInt("detail_no"));
				detailVO.setOrd_no(rs.getInt("ord_no"));
				detailVO.setCheckin_date(rs.getDate("checkin_date"));
				detailVO.setCheckout_date(rs.getDate("checkout_date"));
				detailVO.setRm_no(rs.getString("rm_no"));
				detailVO.setDetail_state(rs.getInt("detail_state"));
				list.add(detailVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}
}
