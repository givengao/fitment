package com.zxyun.user.export.util;

import com.zxyun.user.export.XSGWorkBook;
import com.zxyun.user.export.covert.Covert;
import com.zxyun.user.export.covert.impl.HSSFWorkbookCovert;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/9 7:52
 */
public class ExcelUtil {

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
}
