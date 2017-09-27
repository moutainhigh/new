package com.huayu.common.tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ExcelUtil {

	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	//设置一个sheet最大行数
	private static final int EXCEL_SHEET_MAXSIZE = 50001;


	public static void writeExcel(HttpServletResponse response, String zipName, List<Workbook> workbookList, List<String> nameList) throws IOException {
		zipName = zipName+DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY_MM_DD);
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(zipName+".zip", "UTF-8"));
		ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
		int len;
		byte[] buff = new byte[2048];
		for (int i =  0; i<workbookList.size(); i++){
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbookList.get(i).write(os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			zipOut.putNextEntry(new ZipEntry(nameList.get(i) ));
			while ((len=is.read(buff))>0){
				zipOut.write(buff, 0, len);
			}
			zipOut.closeEntry();
			is.close();
		}
		zipOut.flush();
		zipOut.close();
	}

	public static void writeExcel(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
		fileName = fileName+DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY_MM_DD);
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName+".xls", "UTF-8"));
		ServletOutputStream out = response.getOutputStream();
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(out);
			workbook.write(bos);
			bos.flush();
			bos.close();
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * downloadExcel
	 * (传入数据导出Excel)
	 * @param response HttpServletResponse
	 * @param objList 数据集合
	 * @param fileName 导出Excel文件名
	 * @param columnNames 列名数组
	 * @param keys 列名对应的字段属性名数组
	 * @throws Exception
	 */
	public static void downloadExcel(HttpServletResponse response, List<?> objList, String fileName, String[] columnNames, String[] keys) throws Exception{
			List<Map<String,Object>> list=createExcelRecord(objList,keys);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ExcelUtil.createWorkBook(fileName,list,keys,columnNames).write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			fileName = fileName+DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY_MM_DD);
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (final IOException e) {
				throw e;
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
	}

	/**
	 * createExcelRecord
	 * (数据集合装载)
	 * @param projects 数据集合
	 * @param keys 显示字段数组
	 * @throws Exception
	 * @return list    返回类型
	 */
	private static List<Map<String, Object>> createExcelRecord(List<?> projects,String []keys) throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<>();
		for (Object project:projects) {
			Map<String, Object> mapValue = new HashMap<>();
			for(String key:keys){
				try {
					mapValue.put(key, project.getClass().getMethod("get" + (key.substring(0, 1).toUpperCase() + key.substring(1))).invoke(project));
				} catch (NoSuchMethodException e) {
					logger.warn("没有找到该属性："+key);
				}
			}
			listMap.add(mapValue);
		}
		return listMap;
	}

	/**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(String sheetName, List<Map<String, Object>> list, String []keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建两种单元格格式
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        //创建第一个sheet（页）
        short sheetNameNum = 1;
        Sheet sheet = ExcelUtil.createSheet(wb, cs, sheetName+sheetNameNum, keys, columnNames);
        //设置每行每列的值
        //行数
        int rowNum = 1;
		for (Map<String, Object> map:list) {
			if(rowNum== ExcelUtil.EXCEL_SHEET_MAXSIZE){//如果超出指定值重新创建一个sheet
				sheetNameNum++;
				sheet = ExcelUtil.createSheet(wb, cs, sheetName+sheetNameNum, keys, columnNames);
				rowNum = 1;
			}
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow(rowNum);
			// 在row行上创建一个方格
			for(short j=0;j<keys.length;j++){
				Cell cell = row1.createCell(j);
				if(map.get(keys[j])!=null){
					if(map.get(keys[j]) instanceof BigDecimal){
						cell.setCellValue(Double.parseDouble(map.get(keys[j]).toString()));
					}else if(map.get(keys[j]) instanceof Integer){
						cell.setCellValue(Integer.parseInt(map.get(keys[j]).toString()));
					}else if(map.get(keys[j]) instanceof Date){
						cell.setCellValue(DateTimeUtil.dateToString((Date)map.get(keys[j]),DateTimeUtil.YYYY_MM_DD));
					}else{
						cell.setCellValue(map.get(keys[j]).toString());
					}
				}else{
					if ("_index".equals(keys[j])){
						cell.setCellValue(rowNum);
					}else{
						cell.setCellValue(" ");
					}
				}
				cell.setCellStyle(cs2);
			}
			rowNum++;
		}
		return wb;
    }
    
    /**
    * createCellStyleTocolumn
    * (设置第一种单元格的样式（用于列名）)
    * @param wb 工作簿对象
    * @return CellStyle 返回类型
     */
    public static CellStyle createCellStyleTocolumn(Workbook wb){
    	CellStyle cs = wb.createCellStyle();
    	Font f = wb.createFont();
    	// 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        return cs;
    }
    
    /**
    * createCellStyleToValue
    * (设置第二种单元格的样式（用于值）)
    * @param wb 工作簿对象
    * @return CellStyle 返回类型
     */
	public static CellStyle createCellStyleToValue(Workbook wb){
    	CellStyle cs = wb.createCellStyle();
    	Font f = wb.createFont();
    	// 创建第二种字体样式（用于值）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        // 设置第二种单元格的样式（用于值）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        return cs;
    }
    
    /**
    * createSheet
    * (这里用一句话描述这个方法的作用)
    * @param wb 工作簿对象
    * @param cs 单元格的样式对象
    * @param sheetName sheet（页）名称
    * @param keys 列数
    * @param columnNames 列名
    * @return Sheet 返回类型
     */
    private static Sheet createSheet(Workbook wb, CellStyle cs, String sheetName, String []keys, String columnNames[]){
    	// 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(sheetName);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        return sheet;
    }

	public static void createCell(Row row, CellStyle cs, Object ... value){
		for(int i=0;i<value.length;i++){
			Cell cell = row.createCell(row.getLastCellNum()==-1?i:row.getLastCellNum());
			cell.setCellValue(null==value[i]?"":value[i].toString());
			cell.setCellStyle(cs);
		}
	}

	public static  void createCell(Row row, CellStyle cs, Object value){
		Cell cell = row.createCell(row.getLastCellNum()==-1?0:row.getLastCellNum());
		cell.setCellValue(value!=null?value.toString():"");
		cell.setCellStyle(cs);
	}

}  
