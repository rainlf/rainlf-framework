package com.railf.framework.infrastructure.excel;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author : rain
 * @date : 2021/5/6 13:51
 */
@Slf4j
@Component
public class ExcelAccessorImpl implements ExcelAccessor {

    /**
     * read excel from input stream
     */
    @Override
    public List<Map<String, String>> readExcel(InputStream in) {
        Preconditions.checkNotNull(in);

        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = workbook.getSheetAt(0);

        // header row
        List<String> header = new ArrayList<>();
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            header.add(String.valueOf(sheet.getRow(0).getCell(i)));
        }

        // content rows (from line 1)
        List<Map<String, String>> content = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Map<String, String> cellMap = new HashMap<>();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cellMap.put(header.get(j), row.getCell(j).toString());
            }
            content.add(cellMap);
        }
        return content;
    }

    /**
     * write excel to output stream
     */
    @Override
    public void writeExcel(List<Map<String, String>> content, OutputStream out) {
        Preconditions.checkArgument(content != null && !content.isEmpty());

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // header row
        int i = 0;
        Row row = sheet.createRow(0);
        Set<String> header = content.get(0).keySet();
        for (String head : header) {
            row.createCell(i++).setCellValue(head);
        }

        // content rows (from line 1)
        for (i = 0; i < content.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String, String> cellMap = content.get(i);
            int j = 0;
            for (String head : header) {
                row.createCell(j++).setCellValue(cellMap.get(head));
            }
        }

        try {
            workbook.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
