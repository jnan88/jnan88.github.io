package cn.zcdev.startspringboot2.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 描述：Excel文件操作工具类
 * 
 * <pre>
 * 依赖slf4j、poi、poi-ooxml、servlet
 
&lt;dependency&gt;
&lt;groupId&gt;org.apache.poi&lt;/groupId&gt;
&lt;artifactId&gt;poi&lt;/artifactId&gt;
&lt;version&gt;4.1.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
&lt;groupId&gt;org.apache.poi&lt;/groupId&gt;
&lt;artifactId&gt;poi-ooxml&lt;/artifactId&gt;
&lt;version&gt;4.1.0&lt;/version&gt;
&lt;/dependency&gt;
 * </pre>
 * 
 * @author jnan88@qq.com
 * @version: 0.0.1 2019年8月14日-下午6:23:52
 *
 */
public class ExcelUtil {
	private static Logger	log			= LoggerFactory.getLogger(ExcelUtil.class);
	private Workbook		workbook	= null;
	private Sheet			sheet		= null;
	private Row				row			= null;
	private int				rowIndex	= 0;

	/**
	 * 
	 * <pre>
	 * XLS: excel 2003
	 * XLSX: excel 2007
	 * SXSS: excel 2007导出升级版，导出数据较多时使用该类型，采用的方式是达到一定批量先写入文件，降低内存的使用
	 * </pre>
	 *
	 */
	public enum ExcelType {
		XLS, XLSX, SXSS;
		public static ExcelType fromFileName(String fileName) {
			fileName = fileName.toUpperCase();
			if (fileName.endsWith(XLS.name())) {
				return XLS;
			}
			if (fileName.endsWith(XLSX.name())) {
				return XLSX;
			}
			return null;
		}
	}

	/**
	 * 新建一个可以写的Excel工作簿
	 * 
	 * @param excelType
	 *            文件类型
	 * @return
	 */
	public static ExcelUtil createWorkbook(ExcelType excelType) {
		ExcelUtil instance = new ExcelUtil();
		switch (excelType) {
		case XLS:
			instance.workbook = new HSSFWorkbook();
			break;
		case XLSX:
			instance.workbook = new XSSFWorkbook();
			break;
		case SXSS:
			instance.workbook = new SXSSFWorkbook(1000);
			break;
		default:
			throw new IllegalArgumentException("excelType is not found = " + excelType);
		}
		return instance;
	}

	/**
	 * 由输入流创建可写Excel
	 * 
	 * @param isXls
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static ExcelUtil createWorkbook(ExcelType excelType, InputStream input) throws IOException {
		ExcelUtil instance = new ExcelUtil();
		switch (excelType) {
		case XLS:
			instance.workbook = new HSSFWorkbook(input);
			break;
		case XLSX:
			instance.workbook = new XSSFWorkbook(input);
			break;
		case SXSS:
			instance.workbook = new SXSSFWorkbook(new XSSFWorkbook(input), 1000);
			break;
		default:
			throw new IllegalArgumentException("workBook excelType is not found = " + excelType);
		}
		return instance;
	}

	/**
	 * 创建工作表,可创建多个
	 * 
	 * @param sheetname
	 * @return
	 */
	public ExcelUtil createSheet(String sheetname) {
		if (null == workbook) {
			throw new IllegalArgumentException("workbook is not null.");
		}
		this.sheet = workbook.createSheet(sheetname);
		return this;
	}

	/**
	 * 获取当前操作的工作簿
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * @return the {@link #sheet}
	 */
	public Sheet getSheet() {
		return sheet;
	}

	public Sheet getSheetAt(int index) {
		if (null == workbook) {
			throw new IllegalArgumentException("workbook is not null.");
		}
		sheet = workbook.getSheetAt(index);
		return sheet;
	}

	public void close() {
		closeQuietly(workbook);
	}

	/**
	 * 
	 * @param input
	 *            输入流
	 * @param fileName
	 *            文件名，根据文件名是.xls还是xlsx判断文件格式类型
	 * @param maxCell
	 *            读取的列数，-1表示全部读取
	 * @return
	 */
	public static List<Map<Integer, String>> read(InputStream input, ExcelType excelType, int maxCell) {
		ExcelUtil instance = readWorkbook(input, excelType);
		instance.close();
		return readSheet(instance.getSheet(), maxCell);
	}

	/**
	 * 注意操作完毕需要调用close关闭workbook流
	 * 
	 * @param input
	 * @param excelType
	 * @return
	 */
	public static ExcelUtil readWorkbook(InputStream input, ExcelType excelType) {
		if (null == input) {
			throw new IllegalArgumentException("input is not null.");
		}
		ExcelUtil instance = new ExcelUtil();
		Workbook workBook = null;
		try {
			switch (excelType) {
			case XLS:
				workBook = new HSSFWorkbook(input);
				break;
			case XLSX:
				workBook = new XSSFWorkbook(input);
				break;
			default:
				throw new IllegalArgumentException("excelType is not found = " + excelType);
			}
		} catch (IOException e) {
			log.warn("读取文件异常:" + e.getMessage());
		} finally {
			closeQuietly(input);
		}
		instance.workbook = workBook;
		instance.sheet = workBook.getSheetAt(0);
		return instance;
	}

	/**
	 * 获取单元格格式：默认字体加粗、居中+背景色
	 * 
	 * @param indexedColors
	 * @return
	 */
	public CellStyle getCellStyle(IndexedColors indexedColors) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		cellStyle.setFillForegroundColor(indexedColors.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return cellStyle;
	}

	/**
	 * 添加合并单元格
	 * 
	 * @param firstRow
	 *            起始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            起始列
	 * @param lastCol
	 *            结束列
	 */
	public void addMerge(int firstRow, int lastRow, int firstCol, int lastCol) {
		if (null == sheet) {
			throw new IllegalArgumentException("sheet is not null.");
		}
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	/**
	 * 读取excel的指定sheet
	 * 
	 * @param sheetIndex
	 * @param maxCell
	 * @return
	 */
	public List<Map<Integer, String>> readSheet(int sheetIndex, int maxCell) {
		if (null == workbook) {
			throw new IllegalArgumentException("workbook is not null.");
		}
		sheet = workbook.getSheetAt(sheetIndex);
		// 利用foreach循环 遍历sheet中的所有行
		List<Map<Integer, String>> excelData = new LinkedList<Map<Integer, String>>();
		int rowNum = 0;
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			rowNum++;
			if (rowNum == 1) {
				// 不读取第一行
				continue;
			}
			if (rowNum == 60000) {
				log.warn("解析达到60000行终止...");
				break;
			}
			// 遍历row中的所有方格
			Map<Integer, String> rowData = new LinkedHashMap<Integer, String>();
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				// 列数由0开始
				int cellSize = cell.getColumnIndex();
				// 一行的每一列数据
				rowData.put(cellSize, getCellText(cell));

				if (maxCell > 0 && (cellSize + 1) >= maxCell) {
					break;
				}
			}
			// 每一行数据
			excelData.add(rowData);
		}
		return excelData;
	}

	/**
	 * 读取一个工作表的数据，注意不会读取第一行
	 * 
	 * @param sheet
	 *            不能为null
	 * @param maxCell
	 *            由0开始读取1行,-1表示读取全部
	 * @return
	 */
	public static List<Map<Integer, String>> readSheet(Sheet sheet, int maxCell) {
		if (null == sheet) {
			throw new IllegalArgumentException("sheet is not null.");
		}
		// 利用foreach循环 遍历sheet中的所有行
		List<Map<Integer, String>> excelData = new LinkedList<Map<Integer, String>>();
		int rowNum = 0;
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			rowNum++;
			if (rowNum == 1) {
				// 不读取第一行
				continue;
			}
			if (rowNum == 60000) {
				log.warn("解析达到60000行终止...");
				break;
			}
			// 遍历row中的所有方格
			Map<Integer, String> rowData = new LinkedHashMap<Integer, String>();
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				// 列数由0开始
				int cellSize = cell.getColumnIndex();
				// 一行的每一列数据
				rowData.put(cellSize, getCellText(cell));
				if (maxCell > 0 && (cellSize + 1) >= maxCell) {
					break;
				}
			}
			// 每一行数据
			excelData.add(rowData);
		}
		return excelData;
	}

	/**
	 * 往工作表的写入数据,2003、2007通用
	 * 
	 * @param rowNum
	 *            行编号，从0开始
	 * @param contents
	 *            内容
	 * @return
	 */
	public boolean writeRow(int rowNum, Object... contents) {
		return writeRow(rowNum, null, contents);
	}

	/**
	 * 往工作表的写入数据,2003、2007通用
	 * 
	 * @param rowNum
	 *            行编号，从0开始
	 * @param contents
	 *            内容
	 * @return
	 */
	public boolean writeRow(int rowNum, List<String> contents) {
		return writeRow(rowNum, null, contents);
	}

	/**
	 * 往工作表的写入数据,2003、2007通用
	 * 
	 * @param rowNum
	 *            行编号，从0开始
	 * @param rowStyle
	 *            行样式
	 * @param contents
	 *            内容
	 * @return
	 */
	public boolean writeRow(int rowNum, CellStyle rowStyle, Object... contents) {
		if (null == sheet) {
			throw new IllegalArgumentException("sheet is not null.");
		}
		// 创建一行
		Row row = sheet.createRow(rowNum);
		if (null != rowStyle) {
			row.setRowStyle(rowStyle);
		}
		int length = contents.length;
		for (int column = 0; column < length; column++) {
			Cell cell = row.createCell(column, CellType.STRING);
			cell.setCellValue(String.valueOf(contents[column]));
		}
		return true;
	}

	public boolean writeRow(int rowNum, int cellNum, CellStyle cellStyle, Object content) {
		if (null == sheet) {
			throw new IllegalArgumentException("sheet is not null.");
		}
		if (null == row) {
			row = sheet.createRow(rowNum);
			rowIndex = rowNum;
		} else if (rowNum > rowIndex) {
			row = sheet.createRow(rowNum);
			rowIndex = rowNum;
		}
		Cell cell = row.createCell(cellNum, CellType.STRING);
		cell.setCellValue(String.valueOf(content));
		if (null != cellStyle) {
			cell.setCellStyle(cellStyle);
		}
		return true;
	}

	/**
	 * 往工作表的写入数据,2003、2007通用
	 * 
	 * @param rowNum
	 *            行编号，从0开始
	 * @param rowStyle
	 *            行样式
	 * @param contents
	 *            内容
	 * @return
	 */
	public boolean writeRow(int rowNum, CellStyle rowStyle, List<String> contents) {
		if (null == sheet) {
			throw new IllegalArgumentException("sheet is not null.");
		}
		// 创建一行
		Row row = sheet.createRow(rowNum);
		if (null != rowStyle) {
			row.setRowStyle(rowStyle);
		}
		int length = contents.size();
		for (int column = 0; column < length; column++) {
			Cell cell = row.createCell(column, CellType.STRING);
			cell.setCellValue(contents.get(column));
		}
		return true;
	}

	/**
	 * 写入输出流，已经flush();
	 * 
	 * @param out
	 * @return
	 */
	public boolean writeTo(OutputStream out) {
		if (null == workbook) {
			throw new IllegalArgumentException("workbook is not null.");
		}
		if (null == out) {
			throw new IllegalArgumentException("out is not null.");
		}
		try {
			workbook.write(out);
			out.flush();
			return true;
		} catch (IOException e) {
			// Auto-generated catch block
		} finally {
			closeQuietly(out);
		}
		return false;
	}

	/**
	 * 获取输出流
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		if (null == workbook) {
			throw new IllegalArgumentException("workbook is not null.");
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream swapStream = null;
		try {
			workbook.write(baos);
			swapStream = new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException e) {
			// Auto-generated catch block
		}
		return swapStream;
	}

	public static final String			stream_contentType	= "application/octet-stream";
	public static final String			xls_contentType		= "application/vnd.ms-excel";
	public static final String			xlsx_contentType	= "application/x-xls";
	public static final String			doc_contentType		= "application/msword";
	public static final String			avi_contentType		= "video/avi";
	public static final String			pdf_contentType		= "application/pdf";
	public static final String			exe_contentType		= "application/x-msdownload";
	public static final String			apk_contentType		= "application/vnd.android.package-archive";
	public static final String			txt_contentType		= "text/plain";
	private static Map<String, String>	fileNameFix			= new LinkedHashMap<String, String>();
	static {
		fileNameFix.put("txt", txt_contentType);
		fileNameFix.put("apk", apk_contentType);
		fileNameFix.put("exe", exe_contentType);
		fileNameFix.put("pdf", pdf_contentType);
		fileNameFix.put("avi", avi_contentType);
		fileNameFix.put("doc", doc_contentType);
		fileNameFix.put("xls", xls_contentType);
		fileNameFix.put("xlsx", xlsx_contentType);
	}

	/**
	 * 根据文件后缀名获取响应格式
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getContentType(String fileName) {
		String type = fileNameFix.get(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase());
		if (null != type) {
			return type;
		}
		return stream_contentType;
	}

	public void down(HttpServletResponse response, String fileName) {
		log.warn("下载文件:{}.", fileName);
		OutputStream out = null;
		try {
			response.setContentType(getContentType(fileName));
			response.setStatus(200);
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			response.setStatus(301);
		} finally {
			close();
			closeQuietly(out);
		}
	}

	/**
	 * 以字符串格式读取单元格内容
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellText(Cell cell) {
		if (null == cell) {
			return "";
		}
		String cellVal = null;
		switch (cell.getCellType()) {
		case NUMERIC:
			cellVal = String.valueOf(cell.getNumericCellValue());
			break;
		case FORMULA:
			cellVal =  cell.getCellFormula();
			break;
		case BOOLEAN:
			cellVal =  String.valueOf(cell.getBooleanCellValue());
			break;
		default:
			cellVal = cell.getStringCellValue();
			break;
		}
		return trimToEmpty(cellVal);
	}

	private static String trimToEmpty(String str) {
		return str == null ? "" : str.trim();
	}

	private static void closeQuietly(final Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (final IOException ignored) { // NOPMD
			}
		}
	}
}
