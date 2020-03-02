package com.zxyun.order.pipeline;

/**
 * @des:
 * @Author: given
 * @Date 2020/2/28 16:44
 */
public interface PipelineHandler<I,O> {
    O process (I input);
}
