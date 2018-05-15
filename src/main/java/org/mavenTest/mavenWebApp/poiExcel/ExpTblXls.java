package org.mavenTest.mavenWebApp.poiExcel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import org.mavenTest.mavenWebApp.exaCDI.LineItem;
import org.mavenTest.mavenWebApp.exaCDI.MemoryDatabase;
//import org.mavenTest.mavenWebApp.exaCDI.MemoryDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named("expXlsCtrl")
@RequestScoped
public class ExpTblXls implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ExpTblXls.class);
	
	private static String contentTypeXLS = "application/vnd.ms-excel";
	private static String contentTypeXLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	private List<LineItem> lineItems = new ArrayList<>();
	
	private	static final String[] headers = {
		"ID", "Product Name", "Price", "Quantity"
	};
	
	private String radXls = ""; 
	private int nbrRow = 0;

	
	@EJB
	private MemoryDatabase database;

	@PostConstruct
	public void initialise() {
		//radXls = "xlsx";
		setRadXls("xlsx");
		nbrRow = 250;
		
//		itemList = database.defaultLineItems();
		//lineItems = database.defaultLineItems();
		lineItems = LineItem.generate(nbrRow);
	}
	
	
	/*************************************************************************/
	public void createWB(String fileName) {
		logger.info("creating WB["+ fileName +"] of type: " + radXls);
		
		//fileName += "." + radXls;
		fileName += "." + getRadXls();
		
		try {
			Workbook wBook = createWorkbook(getRadXls());
			Sheet wbSheet = wBook.createSheet("some sheet");
			
			Map<String,	CellStyle> styles = createStyles(wBook);
			
			//set printer
			PrintSetup printSetup = wbSheet.getPrintSetup();
			printSetup.setLandscape(true);
			wbSheet.setFitToPage(true);
			wbSheet.setHorizontallyCenter(true);

			//title	row
			Row	titleRow = wbSheet.createRow(0);
			titleRow.setHeightInPoints(28);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("Create Workbook Demo!");
			titleCell.setCellStyle(styles.get("title"));
			wbSheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$D$1"));

			//header row
			Row	headerRow = wbSheet.createRow(2);
			headerRow.setHeightInPoints(20);
			Cell headerCell;
			for	(int i = 0;	i < headers.length; i++) {
				headerCell = headerRow.createCell(i);
				headerCell.setCellValue(headers[i]);
				headerCell.setCellStyle(styles.get("header"));
			}
			
			//write data
			int rowIndex = 3;
			for (LineItem lineItem : lineItems) {
				Row row = wbSheet.createRow(rowIndex++);
				Cell cell0 = row.createCell(0);
				cell0.setCellValue(lineItem.getId());
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(lineItem.getProductName());
				Cell cell2 = row.createCell(2);
				cell2.setCellValue(lineItem.getPrice());
				Cell cell3 = row.createCell(3);
				cell3.setCellValue(lineItem.getQuantity());
			}
			
			this.donwloadWorkbook(wBook, fileName);
			wBook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createXSSFwb(String fileName) throws Exception {
		logger.info("creating WB of type: xlsx");
		
		//fileName += ".xlsx";
		fileName += "." + getRadXls();

		XSSFWorkbook xWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = xWorkbook.createSheet("Data Test");

		Row rowHead = sheet.createRow(0);
		Cell ch1 = rowHead.createCell(0);
		ch1.setCellValue("ID");
		Cell ch2 = rowHead.createCell(1);
		ch2.setCellValue("Name");
		Cell ch3 = rowHead.createCell(2);
		ch3.setCellValue("Price");
		Cell ch4 = rowHead.createCell(3);
		ch4.setCellValue("QTY");

		int rowIndex = 1;

		Iterator<LineItem> iterator = lineItems.iterator();
		while (iterator.hasNext()) {
			LineItem lineItem = iterator.next();

			Row row = sheet.createRow(rowIndex++);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(lineItem.getId());
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(lineItem.getProductName());
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(lineItem.getPrice());
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(lineItem.getQuantity());
		}

		// Add another sheet
		rowIndex = 1;
		XSSFSheet sheet2 = xWorkbook.createSheet("Data Test2");

		// Create style
		XSSFCellStyle cellStyle = sheet2.getWorkbook().createCellStyle();
		XSSFFont font = sheet2.getWorkbook().createFont();
		
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLUE.index);
		cellStyle.setFont(font);

		Row rowH2 = sheet2.createRow(0);
		Cell ch11 = rowH2.createCell(0);
		ch11.setCellValue("ID");
		ch11.setCellStyle(cellStyle);

		Cell ch12 = rowH2.createCell(1);
		ch12.setCellValue("Name");
		ch12.setCellStyle(cellStyle);

		Cell ch13 = rowH2.createCell(2);
		ch13.setCellValue("Price");
		ch13.setCellStyle(cellStyle);

		Cell ch14 = rowH2.createCell(3);
		ch14.setCellValue("QTY");
		ch14.setCellStyle(cellStyle);

		for (LineItem li : lineItems) {
			Row row2 = sheet2.createRow(rowIndex++);

			int z = 0;
			for (int i = 0; i < 4; i++) {
				Cell cellX = row2.createCell(z++);
				switch (z) {
					case 1:
						cellX.setCellValue(li.getId());
						break;
					case 2:
						cellX.setCellValue(li.getProductName());
						break;
					case 3:
						cellX.setCellValue(li.getPrice());
						break;
					case 4:
						cellX.setCellValue(li.getQuantity());
						break;
					default:
						break;
				}
			}
		}
		
		this.donwloadWorkbookXSSF(xWorkbook, fileName);
		xWorkbook.close();
	}

	public void jxlsExpTemplate(String fileName) throws IOException {
	    try {
	    	logger.info("jxlsExpTemplate: " + fileName + " Ext: " + getRadXls());
	    	//fileName += ".xlsx";
	    	//String fileExt = "." + radXls;
	    	//fileName += fileExt;
	    	fileName += "." + getRadXls();
	    	
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			if (getRadXls() == "xls") {
				ec.setResponseContentType(ExpTblXls.contentTypeXLS);
			}
			else {
				ec.setResponseContentType(ExpTblXls.contentTypeXLSX);
			}
			//ec.setResponseContentType(ExpTblXls.contentTypeXLS); 
			//ec.setResponseContentLength(contentLength);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			OutputStream output = ec.getResponseOutputStream();
			
			//output = this.jxlsData(output);
			String xlsTemplate = "itemTemp" + "." + getRadXls();
			try(InputStream is = new BufferedInputStream(getClass().getResourceAsStream(xlsTemplate))) {
			//try(InputStream is = new BufferedInputStream(getClass().getResourceAsStream("itemTemp" + fileExt))) {
				Context context = new Context();
				context.putVar("lineItems", lineItems);
				JxlsHelper.getInstance().processTemplate(is, output, context);
			}
			
			fc.responseComplete(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*************************************************************************/
	/* Helper methods */
	public void changeRows(AjaxBehaviorEvent event) {
		logger.info("new number of rows: " + nbrRow);
		lineItems = LineItem.generate(nbrRow);
	}
	
	static Workbook createWorkbook(String type) {
		switch (type) {
			case "xls":
				return new HSSFWorkbook();
				//break;
			case "xlsx":
				return new XSSFWorkbook();
				//break;
			case "xlss":
				return new SXSSFWorkbook();
				//break;
			default:
				return null;
				//break;
		}
		//	if ("HSSF".equals(type))
		//      return new HSSFWorkbook();
		//  else if ("XSSF".equals(type))
		//      return new XSSFWorkbook();
		//  else if ("SXSSF".equals(type))
		//      return new SXSSFWorkbook();
		//  else
		//      usage("Unknown type \"" + type + "\"");
		//  return null;
    	
		//	if (fileExt.endsWith("xlsx")) {
		//		return new XSSFWorkbook();
		//	} else if (fileExt.endsWith("xls")) {
		//		return new HSSFWorkbook();
		//	} else {
		//		throw new Exception("invalid file name, should be xls or xlsx");
		//	}
    }
	
	private void donwloadWorkbookXSSF(XSSFWorkbook xWorkBook, String fileName) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
			
			response.reset();
			
			if (getRadXls() == "xls") {
				response.setContentType(ExpTblXls.contentTypeXLS);
			}
			else {
				response.setContentType(ExpTblXls.contentTypeXLSX);
			}
			//response.setContentType(ExpTblXls.contentTypeXLS);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			xWorkBook.write(response.getOutputStream());
			xWorkBook.close();
			
			fc.responseComplete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void donwloadWorkbook(Workbook wBook, String fileName) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
			response.reset();
			
			if (getRadXls() == "xls") {
				response.setContentType(ExpTblXls.contentTypeXLS);
			}
			else {
				response.setContentType(ExpTblXls.contentTypeXLSX);
			}
			
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			wBook.write(response.getOutputStream());
			wBook.close();
			fc.responseComplete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Create a library	of cell	styles */
	private	static Map<String, CellStyle> createStyles(Workbook	wb) {
		Map<String, CellStyle> styles = new HashMap<String,	CellStyle>();
		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short)16);
		titleFont.setColor(IndexedColors.WHITE.getIndex());
		titleFont.setBold(true);
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(titleFont);
		styles.put("title",	style);
		
		Font headFont = wb.createFont();
		headFont.setFontHeightInPoints((short)11);
		headFont.setColor(IndexedColors.BLACK.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headFont);
		style.setWrapText(true);
		styles.put("header", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setWrapText(true);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula_2",	style);
		
		return styles;
	}

	
	
	/*************************************************************************/	
	/* Getter & Setter */
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

//	public List<LineItem> getItemList() {
//		return itemList;
//	}
//	public void setItemList(List<LineItem> value) {
//		this.itemList = value;
//	}

	public String getRadXls() {
		return radXls;
	}
	public void setRadXls(String value) {
		this.radXls = value;
	}

	public int getNbrRow() {
		return nbrRow;
	}
	public void setNbrRow(int value) {
		this.nbrRow = value;
	}

}
