package com.kenon.kenonapp.Helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.PasswordModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"社員番号", "氏名", "氏名カナ", "部門","mail","管理権限","パスワード"};
    static String SHEET = "UserInfo";
    //"パスワード"
    //作成日時

    public static ByteArrayInputStream ToExcel(List<EmployeeModel> employeeModels) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (EmployeeModel employeeModel : employeeModels) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(employeeModel.getUserId());
                row.createCell(1).setCellValue(employeeModel.getFullName());
                row.createCell(2).setCellValue(employeeModel.getFullNameInKata());
                row.createCell(3).setCellValue(employeeModel.getDepartment());
                row.createCell(4).setCellValue(employeeModel.getEmail());
                row.createCell(5).setCellValue(employeeModel.isAdmin());
               // row.createCell(6).setCellValue(employeeModel.getCreateDate().toString());
                row.createCell(6).setCellValue("");

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<EmployeeModel> ToDBuser(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<EmployeeModel> employeeModelList = new ArrayList<EmployeeModel>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                EmployeeModel employeeModel = new EmployeeModel();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            employeeModel.setUserId(currentCell.getStringCellValue());
                            break;

                        case 1:
                            employeeModel.setFullName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            employeeModel.setFullNameInKata(currentCell.getStringCellValue());
                            break;

                        case 3:
                            employeeModel.setDepartment(currentCell.getStringCellValue());
                            break;
                        case 4:
                            employeeModel.setEmail(currentCell.getStringCellValue());
                            break;
                        case 5:
                            employeeModel.setAdmin(currentCell.getBooleanCellValue());
                            break;


                        default:
                            break;
                    }

                    cellIdx++;
                }

                employeeModelList.add(employeeModel);
            }

            workbook.close();

            return employeeModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<PasswordModel> ToDBpassword(InputStream is) {
        try{
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<PasswordModel> passwordModelList = new ArrayList<PasswordModel>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                PasswordModel passwordModel = new PasswordModel();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            passwordModel.setUserId(currentCell.getStringCellValue());
                            break;

                        case 6:
                            String sha256hex = DigestUtils.sha256Hex(currentCell.getStringCellValue());
                            passwordModel.setPassword(sha256hex);
                            break;
                        case 2:
                           // String generatedString = RandomStringUtils.random(32, true, true);
                            passwordModel.setToken("");
                            break;


                        default:
                            break;
                    }

                    cellIdx++;
                }

                passwordModelList.add(passwordModel);
            }

            workbook.close();

            return passwordModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    public static List<String> AllID(InputStream is) {
        try{
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<String> employeeModelList = new ArrayList<String>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();



                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            employeeModelList.add(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }


            }

            workbook.close();

            return employeeModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }



}
