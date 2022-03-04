package com.roomImg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RoomImgDAO implements I_RoomImgDAO {
	private static final String INSERT = "INSERT INTO room_img (type_no, type_img) VALUES (?, ?)";
	private static final String DELETE = "DELETE FROM room_img WHERE img_no = ?";
	private static final String GET_ONE = "SELECT * FROM room_img WHERE img_no = ?";
	private static final String GET_ALL_BY_TYPE = "SELECT * FROM room_img WHERE type_no = ?";
	private static final String GET_ALL = "SELECT * FROM room_img";
	
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
	public RoomImgVO insert(RoomImgVO roomImgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, roomImgVO.getType_no());
			pstmt.setBytes(2, roomImgVO.getType_img());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return roomImgVO;
	}

	@Override
	public void delete(Integer img_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, img_no);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public RoomImgVO getOne(Integer img_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		RoomImgVO roomImgVO = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setInt(1, img_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				roomImgVO = new RoomImgVO();
				roomImgVO.setImg_no(rs.getInt("img_no"));
				roomImgVO.setType_no(rs.getInt("type_no"));
				roomImgVO.setType_img(rs.getBytes("type_img"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return roomImgVO;
	}

	@Override
	public List<RoomImgVO> getAllByType(Integer type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomImgVO> list = new ArrayList<>();
		RoomImgVO roomImgVO = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_TYPE);
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
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<RoomImgVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RoomImgVO> list = new ArrayList<>();
		RoomImgVO roomImgVO = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roomImgVO = new RoomImgVO();
				roomImgVO.setImg_no(rs.getInt("img_no"));
				roomImgVO.setType_no(rs.getInt("type_no"));
				roomImgVO.setType_img(rs.getBytes("type_img"));
				list.add(roomImgVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
