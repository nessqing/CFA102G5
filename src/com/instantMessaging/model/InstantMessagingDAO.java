package com.instantMessaging.model;

import java.io.IOException;
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


public class InstantMessagingDAO implements I_InstantMessagingDAO{
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
		//新增訊息
		private final String INSERT = "INSERT INTO instant_messaging VALUES (?, ?, ?, ?, ?, ?)";
		//修改訊息
		private final String UPDATE = "UPDATE instant_messaging set msg=? where msg_no = ?";
		//顯示某個會員所有所有訊息
		private final String GET_SOMEONE_ALL = "SELECT * FROM instant_messaging WHERE mem_no = ?";
		
		
		@Override
		public void insert(InstantMessagingVO instantMessagingVO) {
			ResultSet rs = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
				//1, AutoIncrement
				pstmt.setString(1, null);
				pstmt.setInt(2, instantMessagingVO.getMem_no() );
				pstmt.setBoolean(3, instantMessagingVO.getMsg_direct());
				pstmt.setString(4, instantMessagingVO.getMsg());
				pstmt.setBytes(5, instantMessagingVO.getMsg_img());
				pstmt.setObject(6, instantMessagingVO.getNow_clk());
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					instantMessagingVO.setMsg_no(rs.getInt(1));
				}
				
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
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
		public void update(InstantMessagingVO instantMessagingVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);		
				pstmt.setString(1, instantMessagingVO.getMsg());
				pstmt.setInt(2,instantMessagingVO.getMsg_no());
				pstmt.executeUpdate();
							
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
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
		public List<InstantMessagingVO> findByMemNo(Integer mem_no) {
			List<InstantMessagingVO> list = new ArrayList<>();
			InstantMessagingVO instantmessagingVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_SOMEONE_ALL);		
				pstmt.setInt(1, mem_no);
				rs = pstmt.executeQuery();
							
				while (rs.next()) {
					instantmessagingVO = new InstantMessagingVO();
					instantmessagingVO.setMsg_no(rs.getInt(1));
					instantmessagingVO.setMem_no(rs.getInt(2));
					instantmessagingVO.setMsg_direct(rs.getBoolean(3));
					instantmessagingVO.setMsg(rs.getString(4));
					instantmessagingVO.setMsg_img(rs.getBytes(5));
					instantmessagingVO.setNow_clk(rs.getTimestamp(6).toLocalDateTime());
					list.add(instantmessagingVO);
		
				}
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
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
