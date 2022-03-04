package com.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.MultipartConfig;
import javax.sql.DataSource;

@MultipartConfig
public class MemberClassDAO implements I_MemberClassDAO{
	
	
	private static DataSource ds;
	static {	
		try {
			ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException ex) {
			ex.printStackTrace();
		}	
	}
	
	private static final String ADD_MEMBER = "INSERT INTO MEMBER VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL = "SELECT * FROM MEMBER";
	private static final String UPDATE_MEMBER = "UPDATE MEMBER SET mem_name=?, mem_sex=?,"
		                              + "mem_password=?, mem_mobile=?, mem_img=?,"
		                              + "mem_add=? WHERE mem_no=?";
	private static final String GET_ONE_BY_MAIL = "SELECT * FROM MEMBER WHERE mem_mail=?";
	private static final String GET_ONE_BY_MOBILE = "SELECT * FROM MEMBER WHERE mem_mobile=?";
	private static final String GET_ALL_SEX = "SELECT * FROM MEMBER WHERE mem_sex=?";
	private static final String UPDATE_PASSWORD = "UPDATE MEMBER SET mem_password =? WHERE mem_no=?";
	private static final String GET_ALL_BY_STATE = "SELECT * FROM MEMBER WHERE mem_state=?";
	private static final String UPDATE_MEMBER_STATE = "UPDATE MEMBER SET mem_state=? WHERE mem_no=?";
	private static final String GET_ONE_BY_PK = "SELECT * FROM MEMBER WHERE mem_no=?";
	private static final String IS_USER = "SELECT * FROM MEMBER WHERE mem_mail= ? AND mem_password= ? ";
	@Override
	public void addMember(MemberClassVO memberClassVO) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(ADD_MEMBER,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1,null);
			pstmt.setString(2,memberClassVO.getMem_name());
			pstmt.setInt(3,memberClassVO.getMem_sex());
			pstmt.setString(4,memberClassVO.getMem_mail());
			pstmt.setString(5,memberClassVO.getMem_password());			
			pstmt.setString(6,memberClassVO.getMem_mobile());
			pstmt.setBytes(7,memberClassVO.getMem_img());
			pstmt.setString(8,memberClassVO.getMem_add());
			pstmt.setBoolean(9,memberClassVO.getMem_state());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				memberClassVO.setMem_no(rs.getInt(1));
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
	public void updateMember(MemberClassVO memberClassVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_MEMBER);
			pstmt.setString(1, memberClassVO.getMem_name());
			pstmt.setInt(2, memberClassVO.getMem_sex());
			pstmt.setString(3, memberClassVO.getMem_password());
			pstmt.setString(4, memberClassVO.getMem_mobile());
			pstmt.setBytes(5,memberClassVO.getMem_img());
			pstmt.setString(6, memberClassVO.getMem_add());
			pstmt.setInt(7, memberClassVO.getMem_no());
			
			pstmt.executeUpdate();
		
		}catch (SQLException se) {
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
	public MemberClassVO getOneBymail(String mem_mail) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		MemberClassVO memberClassVO = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MAIL);
			
			pstmt.setString(1,mem_mail);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			    memberClassVO = new MemberClassVO();
				memberClassVO.setMem_no(rs.getInt("mem_no"));
				memberClassVO.setMem_name(rs.getString("mem_name"));
				memberClassVO.setMem_sex(rs.getInt("mem_sex"));
				memberClassVO.setMem_mail(rs.getString("mem_mail"));
				memberClassVO.setMem_password(rs.getString("mem_password"));
				memberClassVO.setMem_mobile(rs.getString("mem_mobile"));
				memberClassVO.setMem_img(rs.getBytes("mem_img"));
				memberClassVO.setMem_add(rs.getString("mem_add"));
				memberClassVO.setMem_state(rs.getBoolean("mem_state"));
				
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
		
		return memberClassVO;
	}

	@Override
	public MemberClassVO getOneByMobile(String mem_mobile) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		MemberClassVO memberClassVO = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MOBILE);
			
			pstmt.setString(1,mem_mobile);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			    memberClassVO = new MemberClassVO();
				memberClassVO.setMem_no(rs.getInt("mem_no"));
				memberClassVO.setMem_name(rs.getString("mem_name"));
				memberClassVO.setMem_sex(rs.getInt("mem_sex"));
				memberClassVO.setMem_password(rs.getString("mem_password"));
				memberClassVO.setMem_mobile(rs.getString("mem_mobile"));
				memberClassVO.setMem_img(rs.getBytes("mem_img"));
				memberClassVO.setMem_add(rs.getString("mem_add"));
				memberClassVO.setMem_state(rs.getBoolean("mem_state"));
				
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
		
		return memberClassVO;
	}

	@Override
	public List<MemberClassVO> getAllBySex(Integer mem_sex) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<MemberClassVO> memAll = new ArrayList<>();
		try { 
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_SEX);
			pstmt.setInt(1,mem_sex);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberClassVO MemberClassVO = new MemberClassVO();
				MemberClassVO.setMem_no(rs.getInt("mem_no"));
				MemberClassVO.setMem_name(rs.getString("mem_name"));
				MemberClassVO.setMem_mail(rs.getString("mem_mail"));
				MemberClassVO.setMem_password(rs.getString("mem_password"));
				MemberClassVO.setMem_mobile(rs.getString("mem_mobile"));
				MemberClassVO.setMem_img(rs.getBytes("mem_img"));
				MemberClassVO.setMem_add(rs.getString("mem_add"));
				MemberClassVO.setMem_state(rs.getBoolean("mem_state"));
				
				memAll.add(MemberClassVO);
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
		return memAll;
	}

	@Override
	public List<MemberClassVO> getAllByState(Boolean mem_state) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<MemberClassVO> memAll = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STATE);
			pstmt.setBoolean(1,mem_state);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberClassVO MemberClassVO = new MemberClassVO();
				MemberClassVO.setMem_no(rs.getInt("mem_no"));
				MemberClassVO.setMem_name(rs.getString("mem_name"));
				MemberClassVO.setMem_sex(rs.getInt("mem_sex"));
				MemberClassVO.setMem_mail(rs.getString("mem_mail"));
				MemberClassVO.setMem_password(rs.getString("mem_password"));
				MemberClassVO.setMem_mobile(rs.getString("mem_mobile"));
				MemberClassVO.setMem_img(rs.getBytes("mem_img"));
				MemberClassVO.setMem_add(rs.getString("mem_add"));
				
				memAll.add(MemberClassVO);
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
		return memAll;
	}

	@Override
	public List<MemberClassVO> getAll() {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<MemberClassVO> memAll = new ArrayList<>();
		try {
			con = ds.getConnection();
		    pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberClassVO MemberClassVO = new MemberClassVO();
				MemberClassVO.setMem_no(rs.getInt("mem_no"));
				MemberClassVO.setMem_name(rs.getString("mem_name"));
				MemberClassVO.setMem_sex(rs.getInt("mem_sex"));
				MemberClassVO.setMem_mail(rs.getString("mem_mail"));
				MemberClassVO.setMem_password(rs.getString("mem_password"));
				MemberClassVO.setMem_mobile(rs.getString("mem_mobile"));
				MemberClassVO.setMem_img(rs.getBytes("mem_img"));
				MemberClassVO.setMem_add(rs.getString("mem_add"));
				MemberClassVO.setMem_state(rs.getBoolean("mem_state"));
				
				memAll.add(MemberClassVO);
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
			return memAll;
			
		}

	@Override
	public void updatePassword(MemberClassVO memberClassVO) { 
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PASSWORD);
				 
			pstmt.setString(1,memberClassVO.getMem_password());
			pstmt.setInt(2, memberClassVO.getMem_no());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			se.printStackTrace();
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

	@Override
	public void updateMemberstate(MemberClassVO memberClassVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_MEMBER_STATE);
			pstmt.setBoolean(1,!(memberClassVO.getMem_state()));
			pstmt.setInt(2, memberClassVO.getMem_no());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			se.printStackTrace();
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

	@Override
	public MemberClassVO getOneByPK(Integer mem_no) {
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	Connection con = null;
	MemberClassVO memberClassVO  = null;
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ONE_BY_PK);
		pstmt.setInt(1,mem_no);
		rs = pstmt.executeQuery();
		while(rs.next()){
			memberClassVO = new MemberClassVO();
			memberClassVO.setMem_no(rs.getInt("mem_no"));
			memberClassVO.setMem_name(rs.getString("mem_name"));
			memberClassVO.setMem_sex(rs.getInt("mem_sex"));
			memberClassVO.setMem_mail(rs.getString("mem_mail"));
			memberClassVO.setMem_password(rs.getString("mem_password"));
			memberClassVO.setMem_mobile(rs.getString("mem_mobile"));
			memberClassVO.setMem_img(rs.getBytes("mem_img"));
			memberClassVO.setMem_add(rs.getString("mem_add"));
			memberClassVO.setMem_state(rs.getBoolean("mem_state"));	
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
	
		return memberClassVO;
	}

	@Override
	public MemberClassVO isUser(String mem_mail, String mem_password) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		MemberClassVO memberClassVO  = null;
		try {		
		con = ds.getConnection();
		pstmt = con.prepareStatement(IS_USER);
		pstmt.setString(1,mem_mail);
		pstmt.setString(2,mem_password);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			memberClassVO = new MemberClassVO();
			memberClassVO = new MemberClassVO();
			memberClassVO.setMem_no(rs.getInt("mem_no"));
			memberClassVO.setMem_name(rs.getString("mem_name"));
			memberClassVO.setMem_sex(rs.getInt("mem_sex"));
			memberClassVO.setMem_mail(rs.getString("mem_mail"));
			memberClassVO.setMem_password(rs.getString("mem_password"));
			memberClassVO.setMem_mobile(rs.getString("mem_mobile"));
			memberClassVO.setMem_img(rs.getBytes("mem_img"));
			memberClassVO.setMem_add(rs.getString("mem_add"));
			memberClassVO.setMem_state(rs.getBoolean("mem_state"));	
		}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return memberClassVO;
	}
}

