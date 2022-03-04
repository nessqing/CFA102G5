package com.activity.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;
import com.activityClass.model.ActivityClassService;

@WebServlet("/activity/UploadExcel")
public class UploadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String savePath = this.getServletContext().getRealPath("/WEB-INF/uploadExcel");
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 100);
			factory.setRepository(tmpFile);

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}
			upload.setFileSizeMax(1024 * 1024);
			upload.setSizeMax(1024 * 1024 * 10);

			List<FileItem> fileList = upload.parseRequest(request);
			
			for (FileItem item : fileList) {
				
					String fileName = item.getFieldName();
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
					// String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
					// 取得副檔名 以Excel為例可以判斷if(xls>HSSF xlsx>XSSF)
					InputStream in = item.getInputStream();
					String saveFileName = makeFileName(fileName);
					String realSavePath = makePath(saveFileName, savePath);

					FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFileName);
					byte[] buffer = new byte[in.available()];
					int length = 0;
					while ((length = in.read(buffer)) > 0) {
						out.write(buffer, 0, length);
					}
					in.close();
					out.close();
					saveDB((realSavePath + "\\" + saveFileName), request, response);
					
					request.getRequestDispatcher("/back_end/activity/act/selectAct.jsp")
					.forward(request, response);
					return;	
				}
			
		} catch (FileUploadException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void saveDB(String path,HttpServletRequest request,HttpServletResponse response)throws IOException{
		
				ActivityService actService = new ActivityService();
				ActivityVO vo = null;
				List<ActivityVO> list = readXlsx(path,response);
				
				for(int i=0;i<list.size();i++) {
					vo = list.get(i);
					
					Integer act_class_no = vo.getAct_class_no();
					String act_name = vo.getAct_name();
					Integer act_price = vo.getAct_price();
					Integer act_schedule_time = vo.getAct_schedule_time();
					String act_instruction = vo.getAct_instruction();
					String act_gather_location = vo.getAct_gather_location();
					Double act_location_longitude = vo.getAct_location_longitude();
					Double act_location_latitude = vo.getAct_location_latitude();
					
					
					actService.addAct(act_class_no, act_name, act_price, "臺灣-花蓮",
								act_schedule_time, act_instruction, act_gather_location,
								act_location_longitude, act_location_latitude, 0,
								20, 0, 0.0, true);		
				}
			}
			
	private String getValue(XSSFCell cell) {		
		if(cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		}
		else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
			
	private List<ActivityVO> readXlsx(String path,HttpServletResponse response)throws IOException{
	
		InputStream is = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		ActivityVO vo  = null;
		List<ActivityVO> list = new ArrayList<>();

		for(int numSheet = 0 ; numSheet < workbook.getNumberOfSheets();numSheet++) {
			XSSFSheet sheet = workbook.getSheetAt(numSheet);
			
			if(sheet == null) {
				continue;
			}
			
			for(int numRow = 1;numRow<=sheet.getLastRowNum();numRow++) {
				XSSFRow row = sheet.getRow(numRow);
				if(row != null) {
					vo = new ActivityVO();
					XSSFCell act_class_name = row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_name = row.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_price = row.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_instruction = row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_schedule_time = row.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_gather_location = row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_location_longitude = row.getCell(6,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					XSSFCell act_location_latitude = row.getCell(7,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					
					ActivityClassService actClassService = new ActivityClassService();
					List<String> classNameList = actClassService.getAll()
								   							   .stream()
								   							   .map(cls -> cls.getAct_class_name())
								   							   .collect(Collectors.toList());

					if(!getValue(act_class_name).isEmpty() && classNameList.contains(getValue(act_class_name))) {
						
						Integer actClassNo = actClassService.getAll()
															.stream()
															.filter(cls -> cls.getAct_class_name().equals(getValue(act_class_name)))
															.findAny()
															.get()
															.getAct_class_no();
					
						vo.setAct_class_no(actClassNo);
						
					}
					if(!getValue(act_name).isEmpty()) {
						
						vo.setAct_name(getValue(act_name));
					}				
					if(!getValue(act_price).isEmpty()) {
						Integer actPrice = (int)Double.parseDouble(getValue(act_price));
						vo.setAct_price(actPrice);
					}
					if(!getValue(act_instruction).isEmpty()) {
						
						vo.setAct_instruction(getValue(act_instruction));
					}
					if(!getValue(act_schedule_time).isEmpty()) {
						Integer actScheduleTime = (int)Double.parseDouble(getValue(act_schedule_time));
						vo.setAct_schedule_time(actScheduleTime);
					}
					if(!getValue(act_gather_location).isEmpty()) {
						vo.setAct_gather_location(getValue(act_gather_location));
					}
					if(!getValue(act_location_longitude).isEmpty()) {
						vo.setAct_location_longitude(new Double(getValue(act_location_longitude)));
					}
					if(!getValue(act_location_latitude).isEmpty()) {
						vo.setAct_location_latitude(new Double(getValue(act_location_latitude)));
					}
				
				}				
				list.add(vo);
			}
		}
		return list;
	}
			
	private String makeFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;//UUID 生成隨機碼 為上傳檔案產生唯一檔名
	}
	private String makePath(String fileName,String savePath) {
		int hashcode = fileName.hashCode();
		int dir1 = hashcode&0xf;
		int dir2 = (hashcode&0xf0)>>4;
		String dir = savePath + "\\" + dir1 + "\\" +dir2;
		File file = new File(dir);
		if(!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}
}
