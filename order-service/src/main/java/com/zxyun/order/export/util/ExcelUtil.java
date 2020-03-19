package com.zxyun.order.export.util;

import com.zxyun.order.export.XSGWorkBook;
import com.zxyun.order.export.covert.Covert;
import com.zxyun.order.export.covert.impl.HSSFWorkbookCovert;
import com.zxyun.order.export.eunm.ExcelType;
import com.zxyun.order.export.XsgImporterConfig;
import com.zxyun.order.util.AggregationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/9 7:52
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static void export (HttpServletResponse response, XSGWorkBook xsgWorkBook) throws IOException {
        Covert<XSGWorkBook,HSSFWorkbook> covert = new HSSFWorkbookCovert();
        HSSFWorkbook workbook = covert.covert(xsgWorkBook);

        if (workbook == null) {
            return;
        }

        String fileName = xsgWorkBook.getFileName();

        try {
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e1) {
        }

        OutputStream out = null;
        try {
            String headStr = "attachment; filename=\"" + fileName + "\"";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            out = response.getOutputStream();
            workbook.write(out);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
            workbook.close();
        }
    }

    public static void export (XSGWorkBook xsgWorkBook) throws IOException {
        Covert<XSGWorkBook,HSSFWorkbook> covert = new HSSFWorkbookCovert();
        HSSFWorkbook workbook = covert.covert(xsgWorkBook);

        if (workbook == null) {
            return;
        }

        String fileName = xsgWorkBook.getFileName();

        try {
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e1) {
        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        OutputStream out = null;
        try {
            String headStr = "attachment; filename=\"" + fileName + "\"";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            out = response.getOutputStream();
            workbook.write(out);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
            workbook.close();
        }
    }

    public static <T> List<T> importer (InputStream in, String fileName, Class<T> clazz, List<XsgImporterConfig<T>> importerConfigs, String sheetName) throws Exception {
        if (in == null) {
            logger.error("InputStream is null !");
            return null;
        }
        if (StringUtils.isBlank(fileName)) {
            logger.error("FileName is blank !");
            return null;
        }
        if (isEmpty(importerConfigs)) {
            logger.error("ImporterConfigs is empty !");
            return null;
        }
        Workbook workbook = getWorkbook(in, checkExcelType(fileName));
        if (workbook == null) {
            return null;
        }
        Sheet sheet;
        if (StringUtils.isNotBlank(sheetName)) {
            sheet = workbook.getSheet(sheetName);
        } else {
            sheet = workbook.getSheetAt(0);
        }
        if (sheet == null) {
            return null;
        }
        List<String> headerRow = new ArrayList();
        Iterator rowIterator = sheet.iterator();
        Map<Integer, List<BiConsumer<T, String>>> consumerMap = AggregationUtils.toMapList(importerConfigs,XsgImporterConfig::getIndex, XsgImporterConfig::getBiConsumer);
        List<T> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = (Row)rowIterator.next();
            int cellCount = 0;
            Iterator cellIterator = row.iterator();
            T t = clazz.newInstance();
            while (cellIterator.hasNext()) {
                Cell cell = (Cell)cellIterator.next();
                if (row.getRowNum() != 0 && cellCount > headerRow.size() - 1) {
                    break;
                }
                if (StringUtils.isBlank(cell.toString())) {
                    if (row.getRowNum() == 0) {
                        break;
                    }
                    ++cellCount;
                }
                String cellValue = "";
                CellType cellType = cell.getCellTypeEnum();
                switch(cellType) {
                    case STRING:
                        cellValue = cell.getRichStringCellValue().getString();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            if (date != null) {
                                cellValue = date.toString();
                            }
                        } else {
                            cell.setCellType(CellType.STRING);
                            cellValue = cell.getRichStringCellValue().getString();
                        }
                        break;
                    case BOOLEAN:
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                }
                if (row.getRowNum() == 0) {
                    headerRow.add(cellValue.toString());
                } else {
                    List<BiConsumer<T, String>> biConsumers = consumerMap.get(cellCount);
                    if (!isEmpty(biConsumers)) {
                        for (BiConsumer<T, String> biConsumer : biConsumers) {
                            biConsumer.accept(t, cellValue);
                        }
                    }
                }
                list.add(t);
                ++cellCount;
            }
        }
        return list;
    }

    public static ExcelType checkExcelType(String filePath) {
        if (filePath.endsWith(ExcelType.XLS.getSuffix())) {
            return ExcelType.XLS;
        } else {
            return filePath.endsWith(ExcelType.XLSX.getSuffix()) ? ExcelType.XLSX : ExcelType.NONE;
        }
    }

    public static Workbook getWorkbook(InputStream is, ExcelType type) throws IOException {
        Workbook wb = null;
        switch(type) {
            case XLS:
                wb = new HSSFWorkbook(is);
                break;
            case XLSX:
                wb = new XSSFWorkbook(is);
                break;
            default:
                logger.error("The file type does not exist !");
        }
        return wb;
    }

    private static boolean isEmpty (Collection collection) {
        return (collection == null || collection.isEmpty());
    }
}
