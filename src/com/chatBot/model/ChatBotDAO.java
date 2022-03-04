package com.chatBot.model;

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


public class ChatBotDAO implements I_ChatBotDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 新增問題
	private final String INSERT = "INSERT INTO chat_bot VALUES (?, ?, ?, ?, ?)";
	// 修改問題
	private final String UPDATE = "UPDATE chat_bot set qes_title=?, qes_ans=?, qes_comp=?, qes_class=? where qes_no = ?";
	// 刪除問題
	private final String DELETE = "DELETE FROM chat_bot where qes_no = ?";
	// 搜尋一個問題
	private final String GET_ONE_STMT = "SELECT * FROM chat_bot WHERE qes_no = ?";
	// 顯示所有問題
	private final String GET_ALL_STMT = "SELECT * FROM chat_bot order by qes_no";

	@Override
	public void insert(ChatBotVO chatBotVO) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
			//1, AutoIncrement
			pstmt.setString(1, null);
			pstmt.setString(2, chatBotVO.getQes_title() );
			pstmt.setString(3, chatBotVO.getQes_ans());
			pstmt.setString(4, chatBotVO.getQes_comp());
			pstmt.setInt(5, chatBotVO.getQes_class());
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				chatBotVO.setQes_no(rs.getInt(1));
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
	public void update(ChatBotVO chatBotVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, chatBotVO.getQes_title() );
			pstmt.setString(2, chatBotVO.getQes_ans());
			pstmt.setString(3, chatBotVO.getQes_comp());
			pstmt.setInt(4, chatBotVO.getQes_class());
			pstmt.setInt(5, chatBotVO.getQes_no());
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
	public void delete(Integer qes_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, qes_no);
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
	public ChatBotVO findByPrimaryKey(Integer qes_no) {
		ChatBotVO chatbotVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, qes_no);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				chatbotVO = new ChatBotVO();
				chatbotVO.setQes_no(rs.getInt("qes_no"));
				chatbotVO.setQes_title(rs.getString("qes_title"));
				chatbotVO.setQes_ans(rs.getString("qes_ans"));
				chatbotVO.setQes_comp(rs.getString("qes_comp"));
				chatbotVO.setQes_class(rs.getInt("qes_class"));
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
		return chatbotVO;
	}

	@Override
	public List<ChatBotVO> getAll() {
		List<ChatBotVO> list = new ArrayList<ChatBotVO>();
		ChatBotVO chatbotVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				chatbotVO = new ChatBotVO();
				chatbotVO.setQes_no(rs.getInt("qes_no"));
				chatbotVO.setQes_title(rs.getString("qes_title"));
				chatbotVO.setQes_ans(rs.getString("qes_ans"));
				chatbotVO.setQes_comp(rs.getString("qes_comp"));
				chatbotVO.setQes_class(rs.getInt("qes_class"));
				list.add(chatbotVO);
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

}
