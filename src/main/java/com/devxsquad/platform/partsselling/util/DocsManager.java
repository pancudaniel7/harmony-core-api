package com.devxsquad.platform.partsselling.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import com.devxsquad.platform.partsselling.error.WorkBookException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DocsManager {

    private final static int FIRST_SHEET = 0;

    public static List<String> readXlsxColumnData(InputStream inputStream, int columnNumber) {
        return readXlsxColumnData(inputStream, columnNumber, FIRST_SHEET);
    }

    public static List<String> readXlsxColumnData(InputStream inputStream, int columnNumber, int sheetNumber) {
        List<String> data;
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(sheetNumber);

            data = new LinkedList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Cell cell = sheet.getRow(i).getCell(columnNumber);
                if (cell.getStringCellValue().isEmpty()) {
                    break;
                } else {
                    data.add(cell.getStringCellValue());
                }
            }
        } catch (IOException e) {
            throw new WorkBookException(e.getMessage());
        }
        return data;
    }
}
