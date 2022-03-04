package com.creditcard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CreditcardClassDAO implements I_CreditcardClassDAO{
	
	private static DataSource ds;
	static {	
		try {
			ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException ex) {
			ex.printStackTrace();
		}	
	}
	
	private static final String[] KEY = {"crd_no"};
	private static final String ADD_CARD = "INSERT INTO CREDITCARD VALUES (?,?,?,?,?,?,?)"; 
	private static final String DELETE_CARD = "DELETE FROM CREDITCARD WHERE crd_no = ?";
	private static final String GET_ALL = "SELECT * FROM CREDITCARD";
	private static final String GET_ALL_BY_MEM_NO = "SELECT * FROM CREDITCARD WHERE mem_no = ?";

	@Override
	public void addCard(CreditcardClassVO creditcardClassVO) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
		 con =ds.getConnection();
		 pstmt = con.prepareStatement(ADD_CARD,KEY);
			pstmt.setString(1,null);
			pstmt.setInt(2, creditcardClassVO.getMem_no());
			pstmt.setString(3, creditcardClassVO.getCrd_name());
			pstmt.setString(4, creditcardClassVO.getCrd_num());
			pstmt.setString(5, creditcardClassVO.getCrd_expiry());
			pstmt.setString(6, creditcardClassVO.getCrd_security_code());
			pstmt.setString(7, creditcardClassVO.getCrd_barcode());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) {
				creditcardClassVO.setCrd_no(rs.getInt(1));
			}
		}catch (SQLException se) {
			se.printStackTrace();
			}finally {
				if (rs != null) {
					try {
					rs.close();
					}catch (SQLException SQ) {
						SQ.printStackTrace();
					}
				 }
				if (pstmt != null) {
					try {
					pstmt.close();
					}catch (SQLException SQ) {
						SQ.printStackTrace();
					}
				 }
				if (con != null) {
					try {
					con.close();
					}catch (SQLException SQ) {
						SQ.printStackTrace();
					}
				 }
			}
	}
	@Override
	public List<CreditcardClassVO> getallByMem_no(Integer mem_no) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<CreditcardClassVO> crdAll = new ArrayList<>();
		try{
		con = ds.getConnection();
	    pstmt = con.prepareStatement(GET_ALL_BY_MEM_NO);
			pstmt.setInt(1,mem_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CreditcardClassVO CreditcardClassVO = new CreditcardClassVO();
				CreditcardClassVO.setCrd_no(rs.getInt("crd_no"));
				CreditcardClassVO.setMem_no(rs.getInt("mem_no"));
				CreditcardClassVO.setCrd_name(rs.getString("crd_name"));
				CreditcardClassVO.setCrd_num(rs.getString("crd_num"));
				CreditcardClassVO.setCrd_expiry(rs.getString("crd_expiry"));
				CreditcardClassVO.setCrd_security_code(rs.getString("crd_security_code"));
				CreditcardClassVO.setCrd_barcode(rs.getString("crd_barcode"));
				
				crdAll.add(CreditcardClassVO);
			}
		}catch (SQLException se) {
			se.printStackTrace();
			}finally {
				if (rs != null) {
					try {
					rs.close();
					}catch (SQLException SQ) {
						SQ.printStackTrace();
					}
				 }
				if (pstmt != null) {
					try {
					pstmt.close();
					}catch (SQLException SQ) {
						SQ.printStackTrace();
					}
				 }
				if (con != null) {
					try {
					con.close();
					}catch (SQLException SQ) {
						SQ.printStackTrace();
					}
				 }
			}
		
		return crdAll;
	}
	@Override
	public void deleteCard(Integer crd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
	    con = ds.getConnection();
		pstmt = con.prepareStatement(DELETE_CARD);
		pstmt.setInt(1,crd_no);
		pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
				pstmt.close();
				}catch (SQLException SQ) {
					SQ.printStackTrace();
				}
			 }
			if (con != null) {
				try {
				con.close();
				}catch (SQLException SQ) {
					SQ.printStackTrace();
				}
			 }
		}
	}

	@Override
	public  List<CreditcardClassVO> getAll() {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<CreditcardClassVO> crdALL = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CreditcardClassVO CreditcardClassVO = new CreditcardClassVO();
				CreditcardClassVO.setCrd_no(rs.getInt("crd_no"));
				CreditcardClassVO.setMem_no(rs.getInt("mem_no"));
				CreditcardClassVO.setCrd_name(rs.getString("crd_name"));
				CreditcardClassVO.setCrd_num(rs.getString("crd_num"));
				CreditcardClassVO.setCrd_expiry(rs.getString("crd_expiry"));
				CreditcardClassVO.setCrd_security_code(rs.getString("crd_security_code"));
				CreditcardClassVO.setCrd_barcode(rs.getString("crd_barcode"));
				
				crdALL.add(CreditcardClassVO);	
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			if (rs != null) {
				try {
				rs.close();
				}catch (SQLException SQ) {
					SQ.printStackTrace();
				}
			 }
			if (pstmt != null) {
				try {
				pstmt.close();
				}catch (SQLException SQ) {
					SQ.printStackTrace();
				}
			 }
			if (con != null) {
				try {
				con.close();
				}catch (SQLException SQ) {
					SQ.printStackTrace();
				}
			 }
		}
		return crdALL;
	}
}
