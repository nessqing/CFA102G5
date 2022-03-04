package com.roomRsv.model;

import java.util.Arrays;
import java.util.List;

public class RoomRsvTest {

	public static void main(String[] args) {
		I_RoomRsvDAO dao = new RoomRsvJDBCDAO();

		// 新增
//		RoomRsvVO roomRsvVO = new RoomRsvVO();
//		roomRsvVO.setRsv_date(LocalDate.of(2021,12,31));
//		roomRsvVO.setType_no(5);
//		roomRsvVO.setRm_total(2);
//		roomRsvVO.setRsv_total(1);
//		dao.insert(roomRsvVO);

		// 修改 預定
//		RoomRsvVO roomRsvVO = new RoomRsvVO();
//		roomRsvVO.setType_no(5);
//		dao.reserve(roomRsvVO);

		// 修改 取消
//		RoomRsvVO roomRsvVO = new RoomRsvVO();
//		roomRsvVO.setType_no(5);
//		dao.cancel(roomRsvVO);

		// 查詢 同一天的各房型預訂
//		List<RoomRsvVO> list = dao.getOneDayByDate(LocalDate.of(2021,12,31));
//		for (RoomRsvVO roomRsvVO : list) {
//			System.out.print(roomRsvVO.getRsv_date() + ",");
//			System.out.print(roomRsvVO.getType_no() + ",");
//			System.out.print(roomRsvVO.getRm_total() + ",");
//			System.out.println(roomRsvVO.getRsv_total());
//		}

		// 查詢 不能預定
		List<RoomRsvVO> list = dao.getNotRsv(2, 5);
//		for (RoomRsvVO roomRsvVO : list) {
//			System.out.print(roomRsvVO.getRsv_date() + ",");
//			System.out.print(roomRsvVO.getType_no() + ",");
//			System.out.print(roomRsvVO.getRm_total() + ",");
//			System.out.println(roomRsvVO.getRsv_total());
//		}
		String[] result2 = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String notDate = list.get(i).getRsv_date().toString();
			result2[i] = notDate;
		}
		System.out.println(Arrays.toString(result2));

//		String result = list.stream().map(RoomRsvVO::getRsv_date()).collect(Collectors.joining(", "));

		// 查詢 全部
//		List<RoomRsvVO> list = dao.getAll();
//		for (RoomRsvVO roomRsvVO : list) {
//			System.out.print(roomRsvVO.getRsv_date() + ",");
//			System.out.print(roomRsvVO.getType_no() + ",");
//			System.out.print(roomRsvVO.getRm_total() + ",");
//			System.out.println(roomRsvVO.getRsv_total());
//		}

		// 查詢 同房型的每天預訂
//		List<RoomRsvVO> list = dao.getAllByType(5);
//		for (RoomRsvVO roomRsvVO : list) {
//			System.out.print(roomRsvVO.getRsv_date() + ",");
//			System.out.print(roomRsvVO.getType_no() + ",");
//			System.out.print(roomRsvVO.getRm_total() + ",");
//			System.out.println(roomRsvVO.getRsv_total());
//		}

	}
}
