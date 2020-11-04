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
import java.util.Date;
import java.util.List;

public class TempChaptureHelper {

    private static Date d1;
    private static Date d2;
    private static Date d3;
    private static Date d4;
    private static Date d5;

    public TempChaptureHelper(Date d1,Date d2,Date d3,Date d4,Date d5) {
        this.d1=d1;
        this.d2=d2;
        this.d3=d3;
        this.d4=d4;
        this.d5=d5;
    }


    public static ByteArrayInputStream ToExcel(List<TempCaptureModel> tempCaptureModels) {
         String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
         String[] HEADERs = {"社員番号", "氏名", "氏名カナ", "部門",d1.toString()," ",d2.toString()," ",d3.toString()," ",d4.toString()," ",d5.toString()," "};
         String SHEET = "UserTemperature";

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }


            Row rows = sheet.createRow(1);

            rows.createCell(0).setCellValue("");
            rows.createCell(1).setCellValue("");
            rows.createCell(2).setCellValue("");
            rows.createCell(3).setCellValue("");
            rows.createCell(4).setCellValue("体温");
            rows.createCell(5).setCellValue("症状");
            rows.createCell(6).setCellValue("体温");
            rows.createCell(7).setCellValue("症状");
            rows.createCell(8).setCellValue("体温");
            rows.createCell(9).setCellValue("症状");
            rows.createCell(10).setCellValue("体温");
            rows.createCell(11).setCellValue("症状");
            rows.createCell(12).setCellValue("体温");
            rows.createCell(13).setCellValue("症状");




            int rowIdx = 2;
            for (TempCaptureModel tempCaptureModel : tempCaptureModels) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(tempCaptureModel.getUserid());
                row.createCell(1).setCellValue(tempCaptureModel.getName());
                row.createCell(2).setCellValue(tempCaptureModel.getKananame());
                row.createCell(3).setCellValue(tempCaptureModel.getDept());

                if( tempCaptureModel.getDay1()!=null){
                    String[] da1 = tempCaptureModel.getDay1().split(",");
                    row.createCell(4).setCellValue(da1[0]);
                    row.createCell(5).setCellValue(da1[1]);
                }
                else {
                    row.createCell(4).setCellValue("");
                    row.createCell(5).setCellValue("");

                }
                if( tempCaptureModel.getDay2()!=null){
                    String[] da2 = tempCaptureModel.getDay2().split(",");
                    row.createCell(6).setCellValue( da2[0]);
                    row.createCell(7).setCellValue(da2[1]);

                }
                else{
                    row.createCell(6).setCellValue("");
                    row.createCell(7).setCellValue("");
                }
                if(tempCaptureModel.getDay3()!=null){
                    String[] da3 = tempCaptureModel.getDay3().split(",");
                    row.createCell(8).setCellValue(da3[0]);
                    row.createCell(9).setCellValue(da3[1]);

                }
                else {
                    row.createCell(8).setCellValue("");
                    row.createCell(9).setCellValue("");
                }
                if(tempCaptureModel.getDay4()!=null){
                    String[] da4 = tempCaptureModel.getDay4().split(",");
                    row.createCell(10).setCellValue(da4[0]);
                    row.createCell(11).setCellValue(da4[1]);
                }
                else {
                    row.createCell(10).setCellValue("");
                    row.createCell(11).setCellValue("");
                }
                if(tempCaptureModel.getDay5()!=null){
                    String[] da5 = tempCaptureModel.getDay5().split(",");
                    row.createCell(12).setCellValue(da5[0]);
                    row.createCell(13).setCellValue(da5[1]);
                }
                else {
                    row.createCell(12).setCellValue("");
                    row.createCell(13).setCellValue("");
                }





            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
