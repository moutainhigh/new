package com.huayu.p.service;

import com.huayu.common.tool.DateTimeUtil;
import com.huayu.p.domain.ProjectArchives;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 项目进度EXCEL下载service
 * @author ZXL 2017-06-09 11:07
 **/
@Service
public class ProjectDownloadExcelService {

    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public void createReportWorkBook(HttpServletResponse response, String sheetName, List<ProjectArchives> list, String []keys, String columnNames[]) throws Exception{
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
        int nodeNum = 0;
        int successNodeNum = 0;
        int allWeight = 0;
        int successWeight = 0;
        Integer areaId = 0;
        for (ProjectArchives p:list) {
            if(rowNum== ExcelUtil.EXCEL_SHEET_MAXSIZE){//如果超出指定值重新创建一个sheet
                sheetNameNum++;
                sheet = ExcelUtil.createSheet(wb, cs, sheetName+sheetNameNum, keys, columnNames);
                rowNum = 1;
            }
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            if (areaId==0){
                areaId = p.getAreaId();
            }
            if (areaId.equals(p.getAreaId())){
                nodeNum+=p.getNodeNum();
                successNodeNum+=p.getSuccessNodeNum();
                allWeight+=p.getAllWeight();
                successWeight+=p.getSuccessWeight();
            } else {
                Row row2 = sheet.createRow(rowNum);
                row2.createCell(0).setCellValue("");
                row2.createCell(1).setCellValue("");
                row2.createCell(2).setCellValue(nodeNum);
                row2.createCell(3).setCellValue(successNodeNum);
                row2.createCell(4).setCellValue("");
                row2.createCell(5).setCellValue(allWeight);
                row2.createCell(6).setCellValue(successWeight);
                row2.createCell(7).setCellValue("");
                Iterator<Cell> cellIterator2 = row2.cellIterator();
                while (cellIterator2.hasNext()){
                    cellIterator2.next().setCellStyle(cs2);
                }
                nodeNum = p.getNodeNum();
                successNodeNum = p.getSuccessNodeNum();
                allWeight = p.getAllWeight();
                successWeight = p.getSuccessWeight();
                areaId = p.getAreaId();
                rowNum++;
            }

            Row row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(p.getAreaName());
            row1.createCell(1).setCellValue(p.getProjectName());
            row1.createCell(2).setCellValue(p.getNodeNum());
            row1.createCell(3).setCellValue(p.getSuccessNodeNum());
            row1.createCell(4).setCellValue(p.getSuccessNodeNumRate());
            row1.createCell(5).setCellValue(p.getAllWeight());
            row1.createCell(6).setCellValue(p.getSuccessWeight());
            row1.createCell(7).setCellValue(p.getSuccessWeightRate());
            Iterator<Cell> cellIterator = row1.cellIterator();
            while (cellIterator.hasNext()){
                cellIterator.next().setCellStyle(cs2);
            }
            rowNum++;
        }
        Row row3 = sheet.createRow(rowNum);
        row3.createCell(0).setCellValue("");
        row3.createCell(1).setCellValue("");
        row3.createCell(2).setCellValue(nodeNum);
        row3.createCell(3).setCellValue(successNodeNum);
        row3.createCell(4).setCellValue("");
        row3.createCell(5).setCellValue(allWeight);
        row3.createCell(6).setCellValue(successWeight);
        row3.createCell(7).setCellValue("");
        Iterator<Cell> cellIterator3 = row3.cellIterator();
        while (cellIterator3.hasNext()){
            cellIterator3.next().setCellStyle(cs2);
        }
        ExcelUtil.exDownloadExcel(response,sheetName,wb);
    }

    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public void createSuccessReportWorkBook(HttpServletResponse response, String sheetName, List<ProjectPlanCompile> list, String []keys, String columnNames[]) throws Exception{
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
        int allWeight = 0;
        int successWeight = 0;
        Long areaId = 0L;
        for (ProjectPlanCompile p:list) {
            if(rowNum== ExcelUtil.EXCEL_SHEET_MAXSIZE){//如果超出指定值重新创建一个sheet
                sheetNameNum++;
                sheet = ExcelUtil.createSheet(wb, cs, sheetName+sheetNameNum, keys, columnNames);
                rowNum = 1;
            }
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            if (areaId==0){
                areaId = p.getProjectId();
            }
            if (areaId.equals(p.getProjectId())){
                allWeight+=p.getAllWeight();
                successWeight+=p.getWeight();
            } else {
                Row row2 = sheet.createRow(rowNum);
                row2.createCell(0).setCellValue("");
                row2.createCell(1).setCellValue("");
                row2.createCell(2).setCellValue("");
                row2.createCell(3).setCellValue("");
                row2.createCell(4).setCellValue("");
                row2.createCell(5).setCellValue(allWeight);
                row2.createCell(6).setCellValue(successWeight);
                row2.createCell(7).setCellValue("");
                row2.createCell(8).setCellValue("");
                Iterator<Cell> cellIterator2 = row2.cellIterator();
                while (cellIterator2.hasNext()){
                    cellIterator2.next().setCellStyle(cs2);
                }
                allWeight = p.getAllWeight();
                successWeight = p.getWeight();
                areaId = p.getProjectId();
                rowNum++;
            }

            Row row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(p.getAreaName());
            row1.createCell(1).setCellValue(p.getProjectName());
            row1.createCell(2).setCellValue(p.getTaskName());
            row1.createCell(3).setCellValue(DateTimeUtil.dateToString(p.getEndDate()));
            row1.createCell(4).setCellValue(DateTimeUtil.dateToString(p.getCompleteDate()));
            row1.createCell(5).setCellValue(p.getAllWeight());
            row1.createCell(6).setCellValue(p.getWeight());
            row1.createCell(7).setCellValue(p.getSuccessWeightRate());
            row1.createCell(8).setCellValue(p.getNoCompleteRemark());
            Iterator<Cell> cellIterator = row1.cellIterator();
            while (cellIterator.hasNext()){
                cellIterator.next().setCellStyle(cs2);
            }
            rowNum++;
        }
        Row row3 = sheet.createRow(rowNum);
        row3.createCell(0).setCellValue("");
        row3.createCell(1).setCellValue("");
        row3.createCell(2).setCellValue("");
        row3.createCell(3).setCellValue("");
        row3.createCell(4).setCellValue("");
        row3.createCell(5).setCellValue(allWeight);
        row3.createCell(6).setCellValue(successWeight);
        row3.createCell(7).setCellValue("");
        row3.createCell(8).setCellValue("");
        Iterator<Cell> cellIterator3 = row3.cellIterator();
        while (cellIterator3.hasNext()){
            cellIterator3.next().setCellStyle(cs2);
        }
        ExcelUtil.exDownloadExcel(response,sheetName,wb);
    }

}
