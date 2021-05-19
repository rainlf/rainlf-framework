package com.railf.framework.infrastructure.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author : rain
 * @date : 2021/5/19 13:11
 */
public interface ExcelAccessor {
    /**
     * read excel from input stream
     */
    public List<Map<String, String>> readExcel(InputStream in);

    /**
     * write excel to output stream
     */
    public void writeExcel(List<Map<String, String>> content, OutputStream out);
}
