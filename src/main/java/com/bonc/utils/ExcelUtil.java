package com.bonc.utils;

import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.entity.CollTableData;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : booo
 * @Date: 2020-06-24
 */
public class ExcelUtil {

	/**
	 * List<Map<String, Object>>生成Excel对象返回
	 *
	 * @param data 需要处理的数据，其中Map可以是不同格式的Map
	 */
	public static Workbook generateXSLX(List<Map<String, Object>> data, List<CollBusinessTableConfig> nameList, CollBusinessTableType tableType) {
		Workbook wb = new XSSFWorkbook();
		boolean isFrist = true;

		/*创建表单*/
		Sheet sheet;
		if (tableType != null) {
			sheet = wb.createSheet(tableType.getBusinessTypeTableCode());
		} else {
			sheet = wb.createSheet("导出数据");
		}
		//设置字体
		Font headFont = wb.createFont();
		headFont.setFontHeightInPoints((short) 14);
		headFont.setFontName("Courier New");
		headFont.setItalic(false);
		headFont.setStrikeout(false);
		//设置单元格为文本
		CellStyle sheetStyle = wb.createCellStyle();
		XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
		sheetStyle.setDataFormat(dataFormat.getFormat("@"));
		//设置头部单元格样式
		CellStyle headStyle = wb.createCellStyle();
		headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
		//设置单元格颜色
		//设置填充方案
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//设置自定义填充颜色
		headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headStyle.setBorderLeft(BorderStyle.THICK);
		headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBorderRight(BorderStyle.THICK);
		headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBorderTop(BorderStyle.THICK);
		headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setAlignment(HorizontalAlignment.CENTER);    //设置水平对齐方式
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
		headStyle.setShrinkToFit(true);  //自动伸缩
		headStyle.setFont(headFont);  //设置字体
		headStyle.setDataFormat(dataFormat.getFormat("@"));
		/*设置数据单元格格式*/
		CellStyle dataStyle = wb.createCellStyle();
		dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
		dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		dataStyle.setShrinkToFit(true);  //自动伸缩
		dataStyle.setDataFormat(dataFormat.getFormat("@"));
		/*创建行Rows及单元格Cells*/
		//第一行为标题
		Row headRow = sheet.createRow(0);
		if (data != null) {
			for (int i = 1; i <= data.size(); i++) {
				Row dataRow = sheet.createRow(i);
				for (int i1 = 0; i1 < nameList.size(); i1++) {
					sheet.setDefaultColumnStyle(i1, sheetStyle);
					if (isFrist) {
						Cell cell = headRow.createCell(i1);
						cell.setCellValue(nameList.get(i1).getTableConfigName() + "(" + nameList.get(i1).getTableConfigCode() + ")");
						cell.setCellStyle(headStyle);
					}
					Cell cell = dataRow.createCell(i1);
					Object o = data.get(i - 1).get(nameList.get(i1).getTableConfigCode());
					if (o != null) {
						cell.setCellValue(o.toString());
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(dataStyle);
				}
				isFrist = false;
			}
		} else {
			for (int i1 = 0; i1 < nameList.size(); i1++) {
				sheet.setDefaultColumnStyle(i1, sheetStyle);
				Cell cell = headRow.createCell(i1);
				cell.setCellValue(nameList.get(i1).getTableConfigName() + "(" + nameList.get(i1).getTableConfigCode() + ")");
				cell.setCellStyle(headStyle);
			}
		}
		/*设置列自动对齐*/
		for (int i = 0; i < headRow.getLastCellNum(); i++) {
			sheet.autoSizeColumn(i);
		}
		return wb;
	}

	/**
	 * 通用生成excle
	 *
	 * @param data 需要处理的数据，其中Map可以是不同格式的Map
	 */
	public static Workbook generateXSLX(List<Map<String, Object>> data, Map<String, String> nameMap, String name) {
		Workbook wb = new XSSFWorkbook();
		boolean isFrist = true;

		/*创建表单*/
		Sheet sheet;
		if (name != null && !"".equals(name)) {
			sheet = wb.createSheet(name);
		} else {
			sheet = wb.createSheet("导出数据");
		}
		//设置字体
		Font headFont = wb.createFont();
		headFont.setFontHeightInPoints((short) 14);
		headFont.setFontName("Courier New");
		headFont.setItalic(false);
		headFont.setStrikeout(false);
		//设置单元格为文本
		CellStyle sheetStyle = wb.createCellStyle();
		XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
		sheetStyle.setDataFormat(dataFormat.getFormat("@"));
		//设置头部单元格样式
		CellStyle headStyle = wb.createCellStyle();
		headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
		//设置填充方案
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//设置自定义填充颜色
		headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headStyle.setBorderLeft(BorderStyle.THICK);
		headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBorderRight(BorderStyle.THICK);
		headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBorderTop(BorderStyle.THICK);
		headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		headStyle.setShrinkToFit(true);  //自动伸缩
		headStyle.setFont(headFont);  //设置字体
		headStyle.setDataFormat(dataFormat.getFormat("@"));
		/*设置数据单元格格式*/
		CellStyle dataStyle = wb.createCellStyle();
		dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
		dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
		dataStyle.setDataFormat(dataFormat.getFormat("@"));
		/*创建行Rows及单元格Cells*/
		//第一行为标题
		Row headRow = sheet.createRow(0);
		if (data != null) {
			for (int i = 1; i <= data.size(); i++) {
				Row dataRow = sheet.createRow(i);
				int i1 = 0;
				for (String key : nameMap.keySet()) {
					sheet.setDefaultColumnStyle(i1, sheetStyle);
					if (isFrist) {
						Cell cell = headRow.createCell(i1);
						cell.setCellValue(nameMap.get(key) + "(" + key + ")");
						cell.setCellStyle(headStyle);
					}
					Cell cell = dataRow.createCell(i1);
					Object o = data.get(i - 1).get(key);
					if (o != null) {
						cell.setCellValue(o.toString());
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(dataStyle);
					i1++;
				}
				isFrist = false;
			}
		} else {
			int i1 = 0;
			for (String key : nameMap.keySet()) {
				sheet.setDefaultColumnStyle(i1, sheetStyle);
				Cell cell = headRow.createCell(i1);
				cell.setCellValue(nameMap.get(key) + "(" + key + ")");
				cell.setCellStyle(headStyle);
				i1++;
			}
		}
		/*设置列自动对齐*/
		for (int i = 0; i < headRow.getLastCellNum(); i++) {
			sheet.autoSizeColumn(i);
		}
		return wb;
	}

	/**
	 * 通用生成excle
	 *
	 * @param data 需要处理的数据，其中Map可以是不同格式的Map
	 */
	public static Workbook generateXSLX(List<Map<String, Object>> data) {
		Workbook wb = new XSSFWorkbook();
		boolean isFrist = true;

		/*创建表单*/
		Sheet sheet = wb.createSheet("导出数据");
		//设置字体
		Font headFont = wb.createFont();
		headFont.setFontHeightInPoints((short) 14);
		headFont.setFontName("Courier New");
		headFont.setItalic(false);
		headFont.setStrikeout(false);
		//设置单元格为文本
		CellStyle sheetStyle = wb.createCellStyle();
		XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
		sheetStyle.setDataFormat(dataFormat.getFormat("@"));
		//设置头部单元格样式
		CellStyle headStyle = wb.createCellStyle();
		headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
		//设置填充方案
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//设置自定义填充颜色
		headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headStyle.setBorderLeft(BorderStyle.THICK);
		headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBorderRight(BorderStyle.THICK);
		headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBorderTop(BorderStyle.THICK);
		headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		headStyle.setShrinkToFit(true);  //自动伸缩
		headStyle.setFont(headFont);  //设置字体
		headStyle.setDataFormat(dataFormat.getFormat("@"));
		/*设置数据单元格格式*/
		CellStyle dataStyle = wb.createCellStyle();
		dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
		dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
		dataStyle.setDataFormat(dataFormat.getFormat("@"));
		/*创建行Rows及单元格Cells*/
		//第一行为标题
		Row headRow = sheet.createRow(0);
		for (int i = 1; i <= data.size(); i++) {
			Row dataRow = sheet.createRow(i);
			int i1 = 0;
			for (String key : data.get(i - 1).keySet()) {
				sheet.setDefaultColumnStyle(i1, sheetStyle);
				if (isFrist) {
					Cell cell = headRow.createCell(i1);
					cell.setCellValue(key);
					cell.setCellStyle(headStyle);
				}
				Cell cell = dataRow.createCell(i1);
				Object o = data.get(i - 1).get(key);
				if (o != null) {
					cell.setCellValue(o.toString());
				} else {
					cell.setCellValue("");
				}
				cell.setCellStyle(dataStyle);
				i1++;
			}
		}
		/*设置列自动对齐*/
		for (int i = 0; i < headRow.getLastCellNum(); i++) {
			sheet.autoSizeColumn(i);
		}
		return wb;
	}

	/***
	 *
	 * @return
	 * todo:多个sheet页
	 */
	public static Workbook createXSLXTemplateList(List<Map<String, Object>> list) {
		Workbook wb = new XSSFWorkbook();
		int index = 0;
		for (Map<String, Object> map : list) {
			String name = map.get("name").toString();
			Map<String, String> nameMap = (Map<String, String>) map.get("nameMap");
			List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
			boolean isFrist = true;
			/*创建表单*/
			Sheet sheet = wb.createSheet();
			wb.setSheetName(index, name);
			index++;
			//设置字体
			Font headFont = wb.createFont();
			headFont.setFontHeightInPoints((short) 14);
			headFont.setFontName("Courier New");
			headFont.setItalic(false);
			headFont.setStrikeout(false);
			//设置单元格为文本
			CellStyle sheetStyle = wb.createCellStyle();
			XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
			sheetStyle.setDataFormat(dataFormat.getFormat("@"));
			//设置头部单元格样式
			CellStyle headStyle = wb.createCellStyle();
			headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
			//设置填充方案
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			//设置自定义填充颜色
			headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			headStyle.setBorderLeft(BorderStyle.THICK);
			headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headStyle.setBorderRight(BorderStyle.THICK);
			headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			headStyle.setBorderTop(BorderStyle.THICK);
			headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			headStyle.setAlignment(HorizontalAlignment.CENTER);    //设置水平对齐方式
			headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		headStyle.setShrinkToFit(true);  //自动伸缩
			headStyle.setFont(headFont);  //设置字体
			headStyle.setDataFormat(dataFormat.getFormat("@"));
			/*设置数据单元格格式*/
			CellStyle dataStyle = wb.createCellStyle();
			dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
			dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
			dataStyle.setBorderLeft(BorderStyle.THIN);
			dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			dataStyle.setBorderRight(BorderStyle.THIN);
			dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			dataStyle.setBorderTop(BorderStyle.THIN);
			dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
			dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		dataStyle.setShrinkToFit(true);  //自动伸缩
			dataStyle.setDataFormat(dataFormat.getFormat("@"));
			/*创建行Rows及单元格Cells*/
			//第一行为标题
			Row headRow = sheet.createRow(0);
			if (data != null) {
				for (int i = 1; i <= data.size(); i++) {
					Row dataRow = sheet.createRow(i);
					int i1 = 0;
					for (String key : nameMap.keySet()) {
						sheet.setDefaultColumnStyle(i1, sheetStyle);
						if (isFrist) {
							Cell cell = headRow.createCell(i1);
							cell.setCellValue(nameMap.get(key));
							headStyle.setShrinkToFit(true);  //自动伸缩
							cell.setCellStyle(headStyle);
						}
						Cell cell = dataRow.createCell(i1);
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
						Object obj=data.get(i - 1).get(key);
						if(obj!=null){
							cell.setCellValue(obj.toString());
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(dataStyle);
						i1++;
					}
					isFrist = false;
				}
			} else {
				int i1 = 0;
				for (String key : nameMap.keySet()) {
					sheet.setDefaultColumnStyle(i1, sheetStyle);
					Cell cell = headRow.createCell(i1);
					cell.setCellValue(nameMap.get(key));
					headStyle.setShrinkToFit(true);
					cell.setCellStyle(headStyle);
					i1++;
				}
			}
			/*设置列自动对齐*/
			for (int i = 0; i < headRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}
		}
		return wb;
	}
	public static Workbook createXSLXTemplate(Map<String, Object> map) {
		Workbook wb = new XSSFWorkbook();
		int index = 0;
			String name = map.get("name").toString();
			Map<String, String> nameMap = (Map<String, String>) map.get("nameMap");
			List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
			boolean isFrist = true;
			/*创建表单*/
			Sheet sheet = wb.createSheet();
			wb.setSheetName(index, name);
			//设置字体
			Font headFont = wb.createFont();
			headFont.setFontHeightInPoints((short) 14);
			headFont.setFontName("Courier New");
			headFont.setItalic(false);
			headFont.setStrikeout(false);
			//设置单元格为文本
			CellStyle sheetStyle = wb.createCellStyle();
			XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
			sheetStyle.setDataFormat(dataFormat.getFormat("@"));
			//设置头部单元格样式
			CellStyle headStyle = wb.createCellStyle();
			headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
			//设置填充方案
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			//设置自定义填充颜色
			headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			headStyle.setBorderLeft(BorderStyle.THICK);
			headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headStyle.setBorderRight(BorderStyle.THICK);
			headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			headStyle.setBorderTop(BorderStyle.THICK);
			headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			headStyle.setAlignment(HorizontalAlignment.CENTER);    //设置水平对齐方式
			headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		headStyle.setShrinkToFit(true);  //自动伸缩
			headStyle.setFont(headFont);  //设置字体
			headStyle.setDataFormat(dataFormat.getFormat("@"));
			/*设置数据单元格格式*/
			CellStyle dataStyle = wb.createCellStyle();
			dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
			dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
			dataStyle.setBorderLeft(BorderStyle.THIN);
			dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			dataStyle.setBorderRight(BorderStyle.THIN);
			dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			dataStyle.setBorderTop(BorderStyle.THIN);
			dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
			dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
//		dataStyle.setShrinkToFit(true);  //自动伸缩
			dataStyle.setDataFormat(dataFormat.getFormat("@"));
			/*创建行Rows及单元格Cells*/
			//第一行为标题
			Row headRow = sheet.createRow(0);
			if (data != null) {
				for (int i = 1; i <= data.size(); i++) {
					Row dataRow = sheet.createRow(i);
					int i1 = 0;
					for (String key : nameMap.keySet()) {
						sheet.setDefaultColumnStyle(i1, sheetStyle);
						if (isFrist) {
							Cell cell = headRow.createCell(i1);
							cell.setCellValue(nameMap.get(key));
							headStyle.setShrinkToFit(true);  //自动伸缩
							cell.setCellStyle(headStyle);
						}
						Cell cell = dataRow.createCell(i1);
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
						System.out.println(key);
						Object str=data.get(i - 1).get(key);
						if(str!=null){
							cell.setCellValue(str.toString());
						}else{
							cell.setCellValue("");
						}
						cell.setCellStyle(dataStyle);
						i1++;
					}
					isFrist = false;
				}
			} else {
				int i1 = 0;
				for (String key : nameMap.keySet()) {
					sheet.setDefaultColumnStyle(i1, sheetStyle);
					Cell cell = headRow.createCell(i1);
					cell.setCellValue(nameMap.get(key));
					headStyle.setShrinkToFit(true);
					cell.setCellStyle(headStyle);
					i1++;
				}
			}
			/*设置列自动对齐*/
			for (int i = 0; i < headRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}

		return wb;
	}
	/**
	 * 处理数据长度不一定的情况
	 *
	 * @param headRow 第一行
	 * @param key     数据的key值
	 * @return 返回key所在的列号，不存在的key则新建cell返回其列号
	 */
	public int checkHeadRow(Row headRow, String key) {
		//当改行没有一条数据时，getLastCellNum的值为 -1
		if (headRow.getLastCellNum() == -1) {
			headRow.createCell(0).setCellValue(key);
			return 0;
		}
		//遍历标题行，找到相同的返回列号，否则创建一个cell返回列号
		for (int i = 0; i < headRow.getLastCellNum(); i++) {
			if (headRow.getCell(i).getStringCellValue().equalsIgnoreCase(key)) {
				return i;
			}
		}
		headRow.createCell(headRow.getLastCellNum()).setCellValue(key);
		return headRow.getLastCellNum() - 1;
	}


	public static List<Map<String, String>> parseExcel(File excle) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			//.是特殊字符，需要转义！！！！！
			String[] split = excle.getName().split("\\.");
			XSSFWorkbook wb;
			InputStream inputStream = new FileInputStream(excle);
			//根据文件后缀（xls/xlsx）进行判断
			if ("xlsx".equals(split[1])) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("文件类型错误!");
				return null;
			}
			XSSFSheet xssfSheet = wb.getSheetAt(0);
			XSSFRow titleRow = xssfSheet.getRow(0);
			//循环取每行的数据
			for (int rowIndex = 1; rowIndex < xssfSheet.getPhysicalNumberOfRows(); rowIndex++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
				if (xssfRow == null) {
					continue;
				}
				Map<String, String> map = new LinkedHashMap<>();
				//循环取每个单元格(cell)的数据
				for (int cellIndex = 0; cellIndex < xssfRow.getPhysicalNumberOfCells(); cellIndex++) {
					XSSFCell titleCell = titleRow.getCell(cellIndex);
					XSSFCell xssfCell = xssfRow.getCell(cellIndex);
					map.put(titleCell.getStringCellValue(), getValue(xssfCell));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	private static String getValue(XSSFCell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return cell.getRawValue();
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}


	/**
	 * 处理数据纵表
	 * 读取excle数据格式化成CollTableData列表
	 *
	 * @param excle
	 * @return
	 */
	public static List<List<CollTableData>> readExcle(File excle) {
		List<List<CollTableData>> tableDate = new ArrayList<>();
		try {
			//.是特殊字符，需要转义！！！！！
			String[] split = excle.getName().split("\\.");
			Workbook wb;
			InputStream inputStream = new FileInputStream(excle);
			//根据文件后缀（xls/xlsx）进行判断
			if ("xls".equals(split[1])) {
				wb = new HSSFWorkbook(inputStream);
			} else if ("xlsx".equals(split[1])) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("文件类型错误!");
				return null;
			}
			//获取所有的Sheet
			for (int sIndex = 0; sIndex < wb.getNumberOfSheets(); sIndex++) {
				Sheet sheet = wb.getSheetAt(sIndex);
				String tableName = null;
				try {
					tableName = getName(sheet.getSheetName());
					if ("导出数据".equals(tableName)) {
						tableName = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//获取字段名
				Row title = sheet.getRow(0);
				//跳过上面1行提示数据
				for (int rIndex = sheet.getFirstRowNum() + 1; rIndex <= sheet.getLastRowNum(); rIndex++) {
					Row row = sheet.getRow(rIndex);
					StringBuilder cellString = new StringBuilder();
					if (row != null) {
						//排除全是""的行
						for (int cIndex = row.getFirstCellNum(); cIndex < row.getLastCellNum(); cIndex++) {
							Cell cell = row.getCell(cIndex);
							if (cell != null) {
								cellString.append(cell.toString().trim());
							}
						}
						if (cellString != null && !"".equals(cellString.toString())) {
							List<CollTableData> rowData = new ArrayList<>();
							//读取每个单元格
							for (int cIndex = row.getFirstCellNum(); cIndex < row.getLastCellNum(); cIndex++) {
								Cell titleCell = title.getCell(cIndex);
								Cell cell = row.getCell(cIndex);
								CollTableData bean = new CollTableData();
								if (titleCell != null && cell != null) {
									//格式化数字类型数据
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										double cellValue = cell.getNumericCellValue();
										bean.setTableConfigCode(getName(titleCell.getStringCellValue()));
										bean.setDataValue(String.valueOf(cellValue));
										bean.setTableBusinessCode(tableName);
										continue;
									}
									bean.setTableConfigCode(getName(titleCell.getStringCellValue()));
									bean.setDataValue(cell.getStringCellValue());
									bean.setTableBusinessCode(tableName);
								}
								rowData.add(bean);
							}
							tableDate.add(rowData);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return tableDate;
	}

	/**
	 * 公共excle处理方法，返回List<Map<String, String>>
	 *
	 * @param excle
	 * @return
	 */
	public static List<Map<String, String>> readExcleOfCommon(File excle) {
		List<Map<String, String>> dataList = new ArrayList<>();
		try {
			//.是特殊字符，需要转义！！！！！
			String[] split = excle.getName().split("\\.");
			Workbook wb;
			InputStream inputStream = new FileInputStream(excle);
			//根据文件后缀（xls/xlsx）进行判断
			if ("xls".equals(split[1])) {
				wb = new HSSFWorkbook(inputStream);
			} else if ("xlsx".equals(split[1])) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("文件类型错误!");
				return null;
			}
			//获取所有的Sheet
			for (int sIndex = 0; sIndex < wb.getNumberOfSheets(); sIndex++) {
				Sheet sheet = wb.getSheetAt(sIndex);
				String tableName = sheet.getSheetName();    //获取字段名
				Row title = sheet.getRow(0);
				//跳过上面1行提示数据
				for (int rIndex = sheet.getFirstRowNum() + 1; rIndex <= sheet.getLastRowNum(); rIndex++) {
					Row row = sheet.getRow(rIndex);
					StringBuilder cellString = new StringBuilder();
					if (row != null) {
						//排除全是""的行
						for (int cIndex = row.getFirstCellNum(); cIndex < row.getLastCellNum(); cIndex++) {
							Cell cell = row.getCell(cIndex);
							if (cell != null) {
								cellString.append(cell.toString().trim());
							}
						}
						if (cellString != null && !"".equals(cellString.toString())) {
							Map<String, String> map = new HashMap<>();
							//读取每个单元格
							for (int cIndex = row.getFirstCellNum(); cIndex < row.getLastCellNum(); cIndex++) {
								Cell titleCell = title.getCell(cIndex);
								Cell cell = row.getCell(cIndex);
								String key = getName(titleCell.getStringCellValue());
								if (titleCell != null && cell != null) {
									//格式化数字类型数据
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										double cellValue = cell.getNumericCellValue();
										map.put(key, String.valueOf(cellValue));
										continue;
									}
									map.put(key, cell.getStringCellValue());
								}
							}
							dataList.add(map);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dataList;
	}

	public static List<Map<String, String>> readExcleCommon(File excle) {
		List<Map<String, String>> dataList = new ArrayList<>();
		try {
			//.是特殊字符，需要转义！！！！！
			String[] split = excle.getName().split("\\.");
			Workbook wb;
			InputStream inputStream = new FileInputStream(excle);
			//根据文件后缀（xls/xlsx）进行判断
			if ("xls".equals(split[1])) {
				wb = new HSSFWorkbook(inputStream);
			} else if ("xlsx".equals(split[1])) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("文件类型错误!");
				return null;
			}
			//获取所有的Sheet
			for (int sIndex = 0; sIndex < wb.getNumberOfSheets(); sIndex++) {
				Map<String, String> map = new HashMap<>();
				Sheet sheet = wb.getSheetAt(sIndex);
				//获取表明
				String tableName = sheet.getSheetName();
				if (!"coll_personnel_maintain".toLowerCase().equals(tableName.toLowerCase())) {
					//如果为人人员基础信息表，那么则将表导入派发任务
					map.put("sendTaskTableCode", tableName);
					dataList.add(map);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dataList;
	}

	public static String getName(String str) {
		String result = null;
		Matcher mat = Pattern.compile("(?<=\\()(\\S+)(?=\\))").matcher(str);
		while (mat.find()) {
			result = mat.group();
		}
		return result;
	}

	/**
	 * 处理数据纵表
	 *
	 * @param excle
	 * @return
	 */
	public static String getSheetName(File excle) {
		String tableName = null;
		try {
			//.是特殊字符，需要转义！！！！！
			String[] split = excle.getName().split("\\.");
			Workbook wb;
			InputStream inputStream = new FileInputStream(excle);
			//根据文件后缀（xls/xlsx）进行判断
			if ("xls".equals(split[1])) {
				wb = new HSSFWorkbook(inputStream);
			} else if ("xlsx".equals(split[1])) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("文件类型错误!");
				return null;
			}
			//获取所有的Sheet
			for (int sIndex = 0; sIndex < wb.getNumberOfSheets(); sIndex++) {
				Sheet sheet = wb.getSheetAt(sIndex);
				tableName = sheet.getSheetName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableName;
	}

}
