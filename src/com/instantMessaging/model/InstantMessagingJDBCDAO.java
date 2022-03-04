package com.instantMessaging.model;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class InstantMessagingJDBCDAO implements I_InstantMessagingDAO{
	//新增訊息
	private final String INSERT = "INSERT INTO instant_messaging VALUES (?, ?, ?, ?, ?, ?)";
	//修改訊息
	private final String UPDATE = "UPDATE instant_messaging set msg=? where msg_no = ?";
	//顯示某個會員所有所有訊息
	private final String GET_SOMEONE_ALL = "SELECT * FROM instant_messaging WHERE mem_no = ?";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	@Override
	public void insert(InstantMessagingVO instantMessagingVO) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
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
			se.printStackTrace();
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
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);		
			pstmt.setString(1, instantMessagingVO.getMsg());
			pstmt.setInt(2,instantMessagingVO.getMsg_no());
			pstmt.executeUpdate();
						
		} catch (SQLException se) {
			se.printStackTrace();
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
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
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
			se.printStackTrace();
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
	//以下測試用
	public static void main(String[] args) throws IOException {
		
		
		//新增訊息
//		InstantMessagingJDBCDAO dao = new InstantMessagingJDBCDAO();
//		InstantMessagingVO vo = new InstantMessagingVO();
//		vo.setMem_no(2);
//		vo.setMsg_direct(true);
//		vo.setNow_clk(LocalDateTime.now());
//		vo.setMsg("我的房間沒有整理過");
////		FileInputStream fis = new FileInputStream("img/1.jpg");
////		byte[] array = new byte[fis.available()];
////		fis.read(array);
//		vo.setMsg_img(array);
//		fis.close();
//		dao.insert(vo);
//		System.out.println(vo);
//		System.out.println("新增成功");
//		System.out.println(LocalDateTime.now());
		
//		InstantMessagingJDBCDAO dao = new InstantMessagingJDBCDAO();
//		InstantMessagingVO vo = new InstantMessagingVO();
//		vo.setMem_no(2);
//		vo.setMsg_direct(true);
//		vo.setNow_clk(LocalDateTime.now());
//		vo.setMsg("我的床沒有整理過");
//		dao.insert(vo);
//		System.out.println(vo);
//		System.out.println("新增成功");
//		System.out.println(LocalDateTime.now());
		
		
		
		
		
		
//		
		//修改訊息
//		InstantMessagingJDBCDAO dao = new InstantMessagingJDBCDAO();
//		InstantMessagingVO vo = new InstantMessagingVO();
//		vo.setMsg_no(20);
//		vo.setMsg("fuck");
//		dao.update(vo);
//		System.out.println("更新成功");
		
		
		//顯示某個會員所有所有訊息
//		InstantMessagingJDBCDAO dao = new InstantMessagingJDBCDAO();
//		List<InstantMessagingVO> list = dao.findByMemNo(2);
//		for(InstantMessagingVO co : list)
//			System.out.println(co);
//		System.out.println("查詢成功");
	
		
	}

	
}
