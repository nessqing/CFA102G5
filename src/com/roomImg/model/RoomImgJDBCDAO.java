package com.roomImg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class RoomImgJDBCDAO implements I_RoomImgDAO {
	private static final String INSERT = "INSERT INTO room_img (type_no, type_img) VALUES (?, ?)";
	private static final String DELETE = "DELETE FROM room_img WHERE img_no = ?";
	private static final String GET_ONE = "SELECT * FROM room_img WHERE img_no = ?";
	private static final String GET_ALL_BY_TYPE = "SELECT * FROM room_img WHERE type_no = ?";
	private static final String GET_ALL = "SELECT * FROM room_img";
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public RoomImgVO insert(RoomImgVO roomImgVO) {
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, roomImgVO.getType_no());
			pstmt.setBytes(2, roomImgVO.getType_img());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return roomImgVO;
	}

	@Override
	public void delete(Integer img_no) {
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, img_no);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		
	}

	@Override
	public RoomImgVO getOne(Integer img_no) {
		RoomImgVO roomImgVO = null;
		ResultSet rs = null;
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ONE);
			pstmt.setInt(1, img_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				roomImgVO = new RoomImgVO();
				roomImgVO.setImg_no(rs.getInt("img_no"));
				roomImgVO.setType_no(rs.getInt("type_no"));
				roomImgVO.setType_img(rs.getBytes("type_img"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return roomImgVO;
	}

	@Override
	public List<RoomImgVO> getAllByType(Integer type_no) {
		List<RoomImgVO> list = new ArrayList<>();
		RoomImgVO roomImgVO = null;
		ResultSet rs = null;
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL_BY_TYPE);
			pstmt.setInt(1, type_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomImgVO = new RoomImgVO();
				roomImgVO.setImg_no(rs.getInt("img_no"));
				roomImgVO.setType_no(rs.getInt("type_no"));
				roomImgVO.setType_img(rs.getBytes("type_img"));
				list.add(roomImgVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<RoomImgVO> getAll() {
		List<RoomImgVO> list = new ArrayList<>();
		RoomImgVO roomImgVO = null;
		ResultSet rs = null;
		
		try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD)) {
			PreparedStatement pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomImgVO = new RoomImgVO();
				roomImgVO.setImg_no(rs.getInt("img_no"));
				roomImgVO.setType_no(rs.getInt("type_no"));
				roomImgVO.setType_img(rs.getBytes("type_img"));
				list.add(roomImgVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return list;
	}
}
