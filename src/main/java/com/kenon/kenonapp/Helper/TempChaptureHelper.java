package com.kenon.kenonapp.Helper;

import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.TempCaptureModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class TempChaptureHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"社員番号", "氏名", "氏名カナ", "部門","day1","day2","day3","day4","day5"};
    static String SHEET = "UserTemperature";


    public static ByteArrayInputStream ToExcel(List<TempCaptureModel> tempCaptureModels) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (TempCaptureModel tempCaptureModel : tempCaptureModels) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(tempCaptureModel.getUserid());
                row.createCell(1).setCellValue(tempCaptureModel.getName());
                row.createCell(2).setCellValue(tempCaptureModel.getKananame());
                row.createCell(3).setCellValue(tempCaptureModel.getDept());
                row.createCell(4).setCellValue(tempCaptureModel.getDay1());
                row.createCell(5).setCellValue(tempCaptureModel.getDay2());
                row.createCell(6).setCellValue(tempCaptureModel.getDay3());
                row.createCell(7).setCellValue(tempCaptureModel.getDay4());
                row.createCell(8).setCellValue(tempCaptureModel.getDay5());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
