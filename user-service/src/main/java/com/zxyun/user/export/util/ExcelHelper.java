package com.zxyun.user.export.util;

import com.zxyun.user.export.XSGCell;
import com.zxyun.user.export.XSGSheet;
import com.zxyun.user.export.XSGWorkBook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @des: Excel导出
 * @Author: given
 * @Date 2019/9/6 18:33
 */
public class ExcelHelper {

    private XSGWorkBook xsgWorkBook;

    public ExcelHelper(XSGWorkBook xsgWorkBook) {
        this.xsgWorkBook = xsgWorkBook;
    }

    public interface XsgWorkBookConfig {
        ExcelHelper build ();
    }

    //todo 导出
    public <T> void export () {
        System.out.println("book title:" + xsgWorkBook.getTitle());

        for (XSGSheet xsgSheet : xsgWorkBook.getXsgSheets()) {
            System.out.println("\n");
            System.out.println("sheet title:" + xsgSheet.getTitle());

            System.out.println(xsgSheet.getXsgCells().stream().filter(e -> e != null).map(e -> ((XSGCell)e).getHeaderName()).collect(Collectors.joining("|")) + "\n");

            for (Object obj : xsgSheet.getData()) {
                System.out.println("\n");
                for (Object objr : xsgSheet.getXsgCells()) {
                    XSGCell xsgCell = (XSGCell)objr;
                    System.out.print(xsgCell.getBodyFunction().apply(obj) + "|");
                }
            }
        }
    }

    public interface XsgSheetConfig {

        <T> XsgCellConfig<T> and (List<? super T> data);

        XsgWorkBookConfig bookTitle (String bookName);
    }

    public interface XsgCellConfig<T> {
       XsgCellConfig<T> append (String headerName, Function<? super T, String> bodyFunction);

       XsgCellConfig<T> append (String headerName, Function<? super T, String> bodyFunction, String align);

       XsgSheetConfig sheetTitle(String sheetName);
    }

    public static class Builder implements XsgWorkBookConfig, XsgSheetConfig {

        private static XSGWorkBook xsgWorkBook;

        public Builder(XSGSheet xsgSheet) {
            if (xsgWorkBook == null) {
                xsgWorkBook = new XSGWorkBook();
            }
            xsgWorkBook.addXsgSheet(xsgSheet);
        }

        public static <T> XsgCellConfig<? extends T> from (List<? super T> data) {
            return XsgCellConfigImpl.from(data);
        }


        @Override
        public <T> XsgCellConfig<T> and(List<? super T> data) {
            return XsgCellConfigImpl.from(data);
        }

        @Override
        public XsgWorkBookConfig bookTitle(String bookName) {
            xsgWorkBook.setTitle(bookName);
            return this;
        }

        @Override
        public ExcelHelper build () {
            return new ExcelHelper(xsgWorkBook);
        }
    }

    public static class XsgCellConfigImpl<T> implements XsgCellConfig<T> {

        private XSGSheet<T> xsgSheet;

        private List<XSGCell<? super T>> xsgCells;

        List<? super T> data;

        public XsgCellConfigImpl(List<? super T> data) {
            this.data = data;
            this.xsgCells = new ArrayList<>();
        }

        public static <T> XsgCellConfigImpl<T> from (List<? super T> data) {
            return new XsgCellConfigImpl<>(data);
        }

        @Override
        public XsgCellConfig<T> append(String headerName, Function<? super T,String> bodyFunction) {
            XSGCell<T> xsgCell = new XSGCell<T>(headerName, bodyFunction);
            xsgCells.add(xsgCell);
            return this;
        }

        @Override
        public XsgCellConfig<T> append(String headerName, Function<? super T,String> bodyFunction, String align) {
            XSGCell<T> xsgCell = new XSGCell<T>(headerName, bodyFunction, align);
            xsgCells.add(xsgCell);
            return this;
        }

        @Override
        public XsgSheetConfig sheetTitle(String sheetName) {
            xsgSheet = new XSGSheet();
            xsgSheet.setData(data);
            xsgSheet.setXsgCells(xsgCells);
            xsgSheet.setTitle(sheetName);
            return new Builder(xsgSheet);
        }
    }
}
