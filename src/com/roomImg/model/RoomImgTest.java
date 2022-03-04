package com.roomImg.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomImgTest {

	public static void main(String[] args) throws IOException {
		I_RoomImgDAO dao = new RoomImgJDBCDAO();
		
		// 新增
//		RoomImgVO roomimg = new RoomImgVO();
//		roomimg.setType_no(6);
//		byte[] pic = getPictureByteArray("src/com/roomImg/model/1.jpg");
//		roomimg.setType_img(pic);
//		dao.insert(roomimg);
		
		// 刪除
//		dao.delete(24);
		
		// 查詢一筆 用PK
//		RoomImgVO roomimg = dao.getOne(4);
//		System.out.print(roomimg.getImg_no() + ",");
//		System.out.print(roomimg.getType_no() + ",");
//		System.out.println(roomimg.getType_img());
//		byte[] photo = roomimg.getType_img();
//		try {
//			readPicture(photo);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// 查詢單個房型的所有圖片
//		List<RoomImgVO> list = dao.getAllByType(2);
//		for (RoomImgVO roomimg : list) {
//			System.out.print(roomimg.getImg_no() + ",");
//			System.out.print(roomimg.getType_no() + ",");
//			System.out.println(roomimg.getType_img());
//			byte[] photo = roomimg.getType_img();
//			try {
//				readPicture(photo);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	//	將照片顯示出來
	static int count = 11;
	public static void readPicture(byte[] bytes) throws IOException {
		String imgurl = "src/com/roomImg/model/"+count+".jpg";
		FileOutputStream fos = new FileOutputStream(imgurl);
		fos.write(bytes);
		fos.flush();
		fos.close();
		count++;
	}
	
	//  新增照片 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
}
