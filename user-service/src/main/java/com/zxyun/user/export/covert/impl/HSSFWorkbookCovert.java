package com.zxyun.user.export.covert.impl;

import com.zxyun.user.export.XSGCell;
import com.zxyun.user.export.XSGSheet;
import com.zxyun.user.export.XSGWorkBook;
import com.zxyun.user.export.covert.Covert;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/9 8:07
 */
public class HSSFWorkbookCovert implements Covert<XSGWorkBook, HSSFWorkbook> {
    @Override
    public HSSFWorkbook covert(XSGWorkBook xsgWorkBook) {
        //导出转换的对象不能为空
        if (xsgWorkBook == null || CollectionUtils.isEmpty(xsgWorkBook.getXsgSheets())) {
            return null;
        }

        HSSFWorkbook workbook = new HSSFWorkbook();

        for (XSGSheet xsgSheet : xsgWorkBook.getXsgSheets()) {
            if (xsgSheet == null) {
                continue;
            }

            List data = xsgSheet.getData();
            List<XSGCell> xsgCells = xsgSheet.getXsgCells();

            if (CollectionUtils.isEmpty(data) ||CollectionUtils.isEmpty(xsgCells)) {
                continue;
            }

            HSSFSheet sheet = workbook.createSheet(xsgSheet.getTitle());

            HSSFRow headerRow = sheet.createRow(0);

            //创建头
            for (int i = 0; i < xsgCells.size(); i++) {
                XSGCell xsgCell = xsgCells.get(i);
                headerRow.createCell(i).setCellValue(xsgCell.getHeaderName());
            }

            //创建数据表
            for (int j = 0; j < data.size(); j++) {
                Object obj = data.get(j);
                if (obj == null) {
                    continue;
                }

                int lastRowNum = sheet.getLastRowNum();
                HSSFRow dataRow = sheet.createRow(lastRowNum + 1);

                for (int i = 0; i < xsgCells.size(); i++) {
                    XSGCell xsgCell = xsgCells.get(i);
                    dataRow.createCell(i).setCellValue((String)xsgCell.getBodyFunction().apply(obj));
                }
            }
        }

        return workbook;
    }
}
