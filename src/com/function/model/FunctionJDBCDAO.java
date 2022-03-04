package com.function.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class FunctionJDBCDAO implements I_FunctionDAO{
		
		private static final String INSERT_FUN ="INSERT INTO FUNCTIONS VALUES(?,?)";
		private static final String UPDATE_FUN ="UPDATE FUNCTIONS SET fun_name=? WHERE fun_no=?";
		private static final String GET_ALL_FUN ="SELECT * FROM FUNCTIONS";
		
		static {										//資料庫連線
			try {
				Class.forName(JDBCUtil.DRIVER);
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void insertFun(FunctionVO functionVO) {
			ResultSet rs = null;
			try (Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);       //輸入在try內會自動關閉
					PreparedStatement pstmt = con.prepareStatement(INSERT_FUN,PreparedStatement.RETURN_GENERATED_KEYS)){
				
				pstmt.setString(1, null);
				pstmt.setString(2, functionVO.getFun_name());
				
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					functionVO.setFun_no(rs.getInt(1));
				}
				System.out.println("新增一筆權限資料");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		

		@Override
		public void updateFun(FunctionVO functionVO) {
			try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
					PreparedStatement pstmt = con.prepareStatement(UPDATE_FUN)) {
				
				pstmt.setString(1, functionVO.getFun_name());
				pstmt.setInt(2,functionVO.getFun_no());
				
				
				pstmt.executeUpdate();
				System.out.println("修改一筆權限資料");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}       
		}


		@Override
		public List<FunctionVO> getAllFun() {
			List<FunctionVO> funAll = new ArrayList<>();
			FunctionVO fun = null;
			ResultSet rs = null;
			try(Connection con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);	//輸入在try內會自動關閉
					PreparedStatement pstmt = con.prepareStatement(GET_ALL_FUN);) 
			{
				rs = pstmt.executeQuery();
				while(rs.next()) {
					fun = new FunctionVO();
					fun.setFun_no(rs.getInt("fun_no"));
					fun.setFun_name(rs.getString("fun_name"));
		
					funAll.add(fun);
				}
					
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			return funAll;
		}



}
