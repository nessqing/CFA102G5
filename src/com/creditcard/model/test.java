package com.creditcard.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.util.JDBCUtil;

public class test {
//	static {
//		try {
//			Class.forName(JDBCUtil.DRIVER);
//		}catch(ClassNotFoundException ce){
//			ce.printStackTrace();
//		}
//	}
//	public static String addCard= "INSERT INTO CREDITCARD VALUES(?,?,?,?,?,?,?)";
	
	public static void main(String[] args){
//查詢會員正常卡片	
		I_CreditcardClassDAO dao = new CreditcardClassJDBCDAO();
		CreditcardClassVO CreditcardClassVO = new CreditcardClassVO();
//		List<CreditcardClassVO> All = dao.getallByMem_no(2);
//		for(CreditcardClassVO c : All)
//			System.out.println(c);
//DELETE		

//		CreditcardClassVO.setCrd_no(9);
//		dao.deleteCard(CreditcardClassVO);
//GET ALL
//		List<CreditcardClassVO> All = dao.getAll();
//		for(CreditcardClassVO c : All)
//			System.out.println(c);		
//ADD
		CreditcardClassVO.setMem_no(6);
		CreditcardClassVO.setCrd_name("Andy5555");
		CreditcardClassVO.setCrd_num("1234567890123456");
		CreditcardClassVO.setCrd_expiry("1234");
		CreditcardClassVO.setCrd_security_code("223");
		CreditcardClassVO.setCrd_barcode("/sdfsd");
		
		dao.addCard(CreditcardClassVO);
		System.out.println(CreditcardClassVO);
	}
}
	
