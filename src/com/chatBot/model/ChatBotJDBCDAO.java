package com.chatBot.model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JDBCUtil;

public class ChatBotJDBCDAO implements I_ChatBotDAO{
	
		//新增問題
		private final String INSERT = "INSERT INTO chat_bot VALUES (?, ?, ?, ?, ?)";
		//修改問題
		private final String UPDATE = "UPDATE chat_bot set qes_title=?, qes_ans=?, qes_comp=?, qes_class=? where qes_no = ?";
		//刪除問題
		private final String DELETE = "DELETE FROM chat_bot where qes_no = ?";
		//搜尋一個問題
		private final String GET_ONE_STMT = "SELECT * FROM chat_bot WHERE qes_no = ?";
		//顯示所有問題
		private final String GET_ALL_STMT = "SELECT * FROM chat_bot order by qes_no";

		static {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException ce) {
				ce.printStackTrace();
			}
		}

		@Override
		public void insert(ChatBotVO chatBotVO) {
			ResultSet rs = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
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
		public void update(ChatBotVO chatBotVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setString(1, chatBotVO.getQes_title() );
				pstmt.setString(2, chatBotVO.getQes_ans());
				pstmt.setString(3, chatBotVO.getQes_comp());
				pstmt.setInt(4, chatBotVO.getQes_class());
				pstmt.setInt(5, chatBotVO.getQes_no());
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
		public void delete(Integer qes_no) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
				pstmt = con.prepareStatement(DELETE);
				pstmt.setInt(1, qes_no);
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
		public ChatBotVO findByPrimaryKey(Integer qes_no) {
			ChatBotVO chatbotVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
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
				con = DriverManager.getConnection(JDBCUtil.URL, JDBCUtil.USERNAME, JDBCUtil.PASSWORD);
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
		//以下測試用
//		public static void main(String[] args) throws IOException {
			
			
			//新增問題
//			ChatBotJDBCDAO dao = new ChatBotJDBCDAO();
//			ChatBotVO vo = new ChatBotVO();
//			vo.setQes_title("入住時間");
//			vo.setQes_ans("入住時間");
//			vo.setQes_comp("入住時間");
//			vo.setQes_class(1);
//			dao.insert(vo);
//			System.out.println(vo);
//			System.out.println("新增成功");
			
			//更新問題
//			ChatBotJDBCDAO dao = new ChatBotJDBCDAO();
//			ChatBotVO vo = new ChatBotVO();
//			vo.setQes_title("入住時2間");
//			vo.setQes_ans("xxxx");
//			vo.setQes_comp("xxxx");
//			vo.setQes_class(4);
//			vo.setQes_no(3);
//			dao.update(vo);
//			System.out.println("更新成功");
			
			//刪除問題
//			ChatBotJDBCDAO dao = new ChatBotJDBCDAO();
//			dao.delete(2);
//			System.out.println("刪除成功");
			
			//查詢問題
//			ChatBotJDBCDAO dao = new ChatBotJDBCDAO();
//			ChatBotVO vo = dao.findByPrimaryKey(4);
//			System.out.print(vo.getQes_no() + ",");
//			System.out.print(vo.getQes_title() + ",");
//			System.out.print(vo.getQes_ans() + ",");
//			System.out.print(vo.getQes_comp() + ",");
//			System.out.println(vo.getQes_class());
//			System.out.println("---------------------");
//			System.out.println("查詢問題成功");
			
			//查詢全部
//			ChatBotJDBCDAO dao = new ChatBotJDBCDAO();
//			List<ChatBotVO> list = dao.getAll();
//			for(ChatBotVO allChatBot : list) {
//				System.out.print(allChatBot.getQes_no() + ",");
//				System.out.print(allChatBot.getQes_title() + ",");
//				System.out.print(allChatBot.getQes_ans() + ",");
//				System.out.print(allChatBot.getQes_comp() + ",");
//				System.out.println(allChatBot.getQes_class());
//				System.out.println("---------------------");
//				System.out.println("查詢全部成功");
//			}
//
//			
//			
//		}
}
