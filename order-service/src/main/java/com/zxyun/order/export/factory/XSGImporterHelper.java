package com.zxyun.order.export.factory;

import com.zxyun.order.export.XsgImporterConfig;
import com.zxyun.order.export.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @des: 文件导入读取相关
 * @Author: given
 * @Date 2020/3/19 15:14
 */
public class XSGImporterHelper<T>{

    private static final Logger logger = LoggerFactory.getLogger(XSGImporterHelper.class);

    private InputStream in;

    private String fileName;

    private Class<T> clazz;

    private List<XsgImporterConfig<T>> importerConfigs;

    public XSGImporterHelper(InputStream in, String fileName, Class<T> clazz, List<XsgImporterConfig<T>> importerConfigs) {
        this.in = in;
        this.fileName = fileName;
        this.clazz = clazz;
        this.importerConfigs = importerConfigs;
    }

    public static XsgIn builder () {
        return new Builder();
    }

    public interface XsgIn {
        XsgFile from (InputStream in);
    }

    public interface XsgFile {
        XsgImporterTo fileName (String fileName);
    }

    public interface XsgImporterTo {
        <T> XsgTemplate<T> to (Class<T> clazz);
    }

    public interface XsgTemplate<T> {
        XsgTemplate<T> append (Integer index, BiConsumer<T, String> consumer);

        XsgBuilder end ();
    }

    public interface XsgBuilder{
        XSGImporterHelper build ();
    }

    public static class Builder<T> implements XsgIn, XsgFile, XsgImporterTo, XsgTemplate<T>, XsgBuilder {

        private InputStream in;

        private String fileName;

        private Class<T> clazz;

        private List<XsgImporterConfig<T>> importerConfigs;

        public Builder() {
            this.importerConfigs = new ArrayList<>();
        }

        @Override
        public XsgFile from(InputStream in) {
            this.in = in;
            return this;
        }

        @Override
        public XsgImporterTo fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        @Override
        public <U> XsgTemplate<U> to(Class<U> clazz) {
            this.clazz = (Class<T>)clazz;
            return (XsgTemplate<U>)this;
        }

        @Override
        public XsgTemplate<T> append(Integer index, BiConsumer<T, String> consumer) {
            if (index >= 0 && index < Integer.MAX_VALUE) {
                XsgImporterConfig<T> importerConfig = new XsgImporterConfig(index, consumer);
                importerConfigs.add(importerConfig);
            }
            return this;
        }

        @Override
        public XsgBuilder end() {
            return this;
        }

        @Override
        public XSGImporterHelper<T> build() {
            return new XSGImporterHelper(in, fileName, clazz, importerConfigs);
        }
    }


    /**
     * 正式导出
     * @return
     */
    public List<T> importer () throws Exception {
        return ExcelUtil.importer(in, fileName, clazz, importerConfigs, null);
    }


}
