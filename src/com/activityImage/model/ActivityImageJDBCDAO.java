package com.activityImage.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class ActivityImageJDBCDAO implements I_ActivityImageDAO {

	private final String[] GET_KEY = {"act_img_no"};
	private final String SELECT_All_SQL = "SELECT * FROM ACTIVITY_IMAGE";
	private final String INSERT_SQL = "INSERT INTO ACTIVITY_IMAGE VALUES(?,?,?)";
	private final String DELETE_SQL = "DELETE FROM ACTIVITY_IMAGE WHERE act_img_no = ?";
	private final String UPDATE_SQL = "UPDATE ACTIVITY_IMAGE SET act_no = ?,act_img = ? WHERE act_img_no = ?";
	private final String SELECT_BY_PK_SQL = "SELECT * FROM ACTIVITY_IMAGE WHERE act_img_no = ?";
	private final String SELECT_BY_ACTIVITY_NO_SQL = "SELECT * FROM ACTIVITY_IMAGE WHERE act_no = ?";
	
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	
	@Override
	public ActivityImageVO insert(ActivityImageVO actImageVO) {
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(INSERT_SQL, GET_KEY);
			ps.setString(1, null);  //AI
			ps.setInt(2, actImageVO.getAct_no());
			ps.setBytes(3, actImageVO.getAct_img());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys(); //綁定主鍵值，這樣撈回來才有正確的Id
			if (rs.next()) {
				actImageVO.setAct_img_no(rs.getInt(1));
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
		
		return actImageVO;
	}

	@Override
	public void delete(Integer act_img_no) {
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(DELETE_SQL);
			ps.setInt(1, act_img_no);
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
	public void update(ActivityImageVO actImageVO) {		
		Connection con = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setInt(1,actImageVO.getAct_no());
			ps.setBytes(2,actImageVO.getAct_img());
			ps.setInt(3,actImageVO.getAct_img_no());
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
	public ActivityImageVO findByPk(Integer act_img_no) {
		ActivityImageVO actImageVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_PK_SQL);
			ps.setInt(1, act_img_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actImageVO = new ActivityImageVO();
				actImageVO.setAct_img_no(rs.getInt(1));
				actImageVO.setAct_no(rs.getInt(2));
				actImageVO.setAct_img(rs.getBytes(3));
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
		
		return actImageVO;
	}

	@Override
	public List<ActivityImageVO> findByActNo(Integer act_no) {
		List<ActivityImageVO> list = new ArrayList<>();
		ActivityImageVO actImageVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_BY_ACTIVITY_NO_SQL);
			ps.setInt(1, act_no);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				actImageVO = new ActivityImageVO();
				actImageVO.setAct_img_no(rs.getInt(1));
				actImageVO.setAct_no(rs.getInt(2));
				actImageVO.setAct_img(rs.getBytes(3));
				list.add(actImageVO);
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
	public List<ActivityImageVO> getAll() {
		List<ActivityImageVO> list = new ArrayList<>();
		ActivityImageVO actImageVO = null;
		Connection con = null;
		ResultSet rs = null;
		
		try{
			con =DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
			PreparedStatement ps = con.prepareStatement(SELECT_All_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				actImageVO = new ActivityImageVO();
				actImageVO.setAct_img_no(rs.getInt(1));
				actImageVO.setAct_no(rs.getInt(2));
//				actImageVO.setAct_img(rs.getBytes(3)); //圖片由img src發請求
				list.add(actImageVO);
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
	public static void main(String[] args) throws IOException {
		ActivityImageJDBCDAO dao = new ActivityImageJDBCDAO();
//		ActivityImageVO vo = new ActivityImageVO();
//		
//		FileInputStream fis = new FileInputStream("WebContent/images/activity/whale3.jpg");
//		byte[] array = new byte[fis.available()];
//		fis.read(array);		
//		vo.setAct_img_no(4);
//		vo.setAct_no(2);
//		vo.setAct_img(array);
//		dao.insert(vo);
//		fis.close();
//		dao.update(vo);//注意FK的問題
//		List<ActivityImageVO> list = dao.findByActNo(1);
//		ActivityImageVO vo =dao.findByPk(2);
		List<ActivityImageVO> list = dao.getAll();
		for(ActivityImageVO vo : list)
		System.out.println(vo);
//		dao.delete(3);
	}
}
