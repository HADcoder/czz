package com.blackchicktech.healthdiet.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PoiExportUtil {
	private final static Logger logger = LoggerFactory.getLogger(PoiExportUtil.class);

	public static byte[] exportToExcel(String title, List<String> headers, List<List<String>> lines) {
		if (lines == null || lines.size() <= 0 || headers == null || headers.size() <= 0) {
			return null;
		}
		int size = headers.size();
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");

			//title style
			HSSFCellStyle cs0 = wb.createCellStyle();
			cs0.setAlignment(HorizontalAlignment.CENTER);
			cs0.setVerticalAlignment(VerticalAlignment.CENTER);
			cs0.setBorderBottom(BorderStyle.THIN);
			cs0.setBorderLeft(BorderStyle.THIN);
			cs0.setBorderRight(BorderStyle.THIN);
			cs0.setBorderTop(BorderStyle.THIN);
			cs0.setWrapText(true);
			//title font
			HSSFFont ff0 = wb.createFont();
			ff0.setFontHeightInPoints((short) 14);
			ff0.setBold(true);
			cs0.setFont(ff0);


			HSSFRow row0 = sheet.createRow(0);
			row0.setHeight((short) 500);
			HSSFCell cell0 = row0.createCell(0);
			cell0.setCellValue(title);
			cell0.setCellStyle(cs0);
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (size - 1)));


			HSSFRow row = sheet.createRow(1);

			for (int i = 0; i < size; i++) {
				sheet.setColumnWidth(i, 32 * 120);
			}

			//header style
			HSSFCellStyle cs = wb.createCellStyle();
			cs.setAlignment(HorizontalAlignment.CENTER);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);
			cs.setBorderBottom(BorderStyle.THIN);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setWrapText(true);
			//header font
			HSSFFont ff = wb.createFont();
			ff.setFontHeightInPoints((short) 10);
			ff.setBold(true);
			cs.setFont(ff);


			//body style
			HSSFCellStyle cs2 = wb.createCellStyle();
			cs2.setAlignment(HorizontalAlignment.CENTER);
			cs2.setVerticalAlignment(VerticalAlignment.CENTER);
			cs2.setBorderBottom(BorderStyle.THIN);
			cs2.setBorderLeft(BorderStyle.THIN);
			cs2.setBorderRight(BorderStyle.THIN);
			cs2.setBorderTop(BorderStyle.THIN);
			cs2.setWrapText(true);
			//body font
			HSSFFont ff2 = wb.createFont();
			ff2.setFontHeightInPoints((short) 10);
			cs2.setFont(ff2);

			HSSFCell cell;
			for (int i = 0; i < size; i++) {
				cell = row.createCell(i);
				cell.setCellValue(headers.get(i));
				cell.setCellStyle(cs);
			}

			for (int i = 0; i < lines.size(); i++) {
				List<String> ls = lines.get(i);
				row = sheet.createRow(i + 2);
				if (i == 0 || !lines.get(i - 1).get(0).equals(ls.get(0))) {
					cell = row.createCell(0);
					cell.setCellValue(ls.get(0));
					cell.setCellStyle(cs2);
				}
				for (int j = 1; j < ls.size(); j++) {
					cell = row.createCell(j);
					cell.setCellValue(ls.get(j));
					cell.setCellStyle(cs2);
				}
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				wb.write(os);
			} catch (IOException e) {
				logger.error("exception occurs while writing to files", e);
			}
			byte[] content = os.toByteArray();
			try {
				os.close();
			} catch (IOException e) {
				logger.error("could not close file handle", e);
			}
			return content;
		} catch (Exception e) {
			logger.error("exception occurs while generating excel files", e);
		}
		return null;
	}
}
