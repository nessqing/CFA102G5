package com.room.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class RoomJDBCDAO implements I_RoomDAO {
	private static final String INSERT = "INSERT INTO room (rm_no, type_no, rm_info) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE room SET type_no = ?, rm_info = ?, rm_state = ?, name_title = ? WHERE rm_no = ?";
	private static final String UPDATE_CHECKIN = "UPDATE room SET rm_state = 2, name_title = ? WHERE rm_no = ?";
	private static final String UPDATE_CHECKOUT = "UPDATE room SET rm_state = 3, name_title = null WHERE rm_no = ?";
	private static final String UPDATE_CLEAN = "UPDATE room SET rm_state = 1 WHERE rm_no = ?";
	private static final String GET_ONE = "SELECT * FROM room WHERE rm_no = ?";
	private static final String GET_RMTOTAL = "select count(*) as rm_qty from room where rm_state != 4 and type_no = ?";
	private static final String GET_ALL = "SELECT * FROM room ORDER BY rm_no";
	private static final String GET_ALL_BY_TYPE_STATE = "SELECT * FROM room WHERE type_no = ? AND rm_state = 1";
	private static final String GET_ALL_BY_RM_STATE = "SELECT * FROM room WHERE rm_state = ?";

	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public RoomVO insert(RoomVO roomVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, roomVO.getRm_no());
			pstmt.setInt(2, roomVO.getType_no());
			pstmt.setString(3, roomVO.getRm_info());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return roomVO;
	}

	@Override
	public RoomVO update(RoomVO roomVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, roomVO.getType_no());
			pstmt.setString(2, roomVO.getRm_info());
			pstmt.setInt(3, roomVO.getRm_state());
			pstmt.setString(4, roomVO.getName_title());
			pstmt.setString(5, roomVO.getRm_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return roomVO;
	}

	@Override
	public void updateCheckin(RoomVO roomVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_CHECKIN);

			pstmt.setString(1, roomVO.getName_title());
			pstmt.setString(2, roomVO.getRm_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	@Override
	public void updateCheckout(RoomVO roomVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_CHECKOUT);
			pstmt.setString(1, roomVO.getRm_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

	@Override
	public void updateClean(RoomVO roomVO) {

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_CHECKOUT);
			pstmt.setString(1, roomVO.getRm_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

	@Override
	public RoomVO getOne(String rm_no) {
		RoomVO roomVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ONE);
			pstmt.setString(1, rm_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomVO = new RoomVO();
				roomVO.setRm_no(rs.getString("rm_no"));
				roomVO.setType_no(rs.getInt("type_no"));
				roomVO.setRm_info(rs.getString("rm_info"));
				roomVO.setRm_state(rs.getInt("rm_state"));
				roomVO.setName_title(rs.getString("name_title"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return roomVO;
	}

	@Override
	public Integer getRmTotal(Integer type_no) {
		Integer rm_qty = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_RMTOTAL);
			pstmt.setInt(1, type_no);

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return rm_qty;
	}

	@Override
	public List<RoomVO> getAll() {
		List<RoomVO> list = new ArrayList<>();
		RoomVO roomVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomVO = new RoomVO();
				roomVO.setRm_no(rs.getString("rm_no"));
				roomVO.setType_no(rs.getInt("type_no"));
				roomVO.setRm_info(rs.getString("rm_info"));
				roomVO.setRm_state(rs.getInt("rm_state"));
				roomVO.setName_title(rs.getString("name_title"));
				list.add(roomVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}

	@Override
	public List<RoomVO> getAllByTypeState(Integer type_no) {
		List<RoomVO> list = new ArrayList<>();
		RoomVO roomVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL_BY_TYPE_STATE);
			pstmt.setInt(1, type_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomVO = new RoomVO();
				roomVO.setRm_no(rs.getString("rm_no"));
				roomVO.setType_no(rs.getInt("type_no"));
				roomVO.setRm_info(rs.getString("rm_info"));
				roomVO.setRm_state(rs.getInt("rm_state"));
				roomVO.setName_title(rs.getString("name_title"));
				list.add(roomVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}

	@Override
	public List<RoomVO> getAllByRmState(Integer rm_state) {
		List<RoomVO> list = new ArrayList<>();
		RoomVO roomVO = null;
		ResultSet rs = null;

		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL_BY_RM_STATE);
			pstmt.setInt(1, rm_state);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomVO = new RoomVO();
				roomVO.setRm_no(rs.getString("rm_no"));
				roomVO.setType_no(rs.getInt("type_no"));
				roomVO.setRm_info(rs.getString("rm_info"));
				roomVO.setRm_state(rs.getInt("rm_state"));
				roomVO.setName_title(rs.getString("name_title"));
				list.add(roomVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}
}
