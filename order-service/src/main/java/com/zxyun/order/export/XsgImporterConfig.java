package com.zxyun.order.export;

import java.util.function.BiConsumer;

/**
 * @des:
 * @Author: given
 * @Date 2020/3/19 18:44
 */
public class XsgImporterConfig<T> {
    private Integer index;
    private BiConsumer<T,String> biConsumer;

    public XsgImporterConfig(Integer index, BiConsumer<T,String> biConsumer) {
        this.index = index;
        this.biConsumer = biConsumer;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BiConsumer<T,String> getBiConsumer() {
        return biConsumer;
    }

    public void setBiConsumer(BiConsumer<T,String> biConsumer) {
        this.biConsumer = biConsumer;
    }
}
