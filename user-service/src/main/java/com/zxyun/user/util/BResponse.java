package com.zxyun.user.util;

import java.io.Serializable;
import java.util.function.Consumer;

public class BResponse<T> implements Serializable {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    private String error;

    public BResponse(Boolean success, Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public BResponse(Boolean success, Integer code, String msg, String error) {
        this.success = success;
        this.error = error;
        this.code = code;
        this.msg = msg;
    }

    public static <T> BResponse<T> ok() {
        return new BResponse<T>(Boolean.TRUE,null, null, (T)null);
    }

    public static <T> BResponse<T> ok(T data) {
        return new BResponse<T>(Boolean.TRUE,null, null, data);
    }

    public static <T> BResponse<T> ok(Integer code, String msg, T data) {
        return new BResponse<T>(Boolean.TRUE,code, msg, data);
    }

    public static <T> BResponse<T> fail(Integer code, String msg) {
        return new BResponse<T>(Boolean.FALSE, code, msg, (T)null);
    }

    public static <T> BResponse<T> fail() {
        return new BResponse<T>(Boolean.FALSE, null, null, (String)null);
    }

    public static <T> BResponse<T> fail(String error) {
        return new BResponse<T>(Boolean.FALSE, null, null, error);
    }

    public static <T> BResponse<T> fail(Integer code, String msg, String error) {
        return new BResponse<T>(Boolean.FALSE, code, msg, error);
    }


    public boolean isSuccess () {
        return success == null ? false : success.booleanValue();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (data != null) {
            consumer.accept(data);
        }
    }

    public T get () {
        return data;
    }

    public T valid () throws Exception {
        if (success == null) {
            return data;
        }

        if (!isSuccess()) {
            throw new Exception(error);
        }

        return data;
    }

    public String error() {
        return error;
    }

    public Integer code() {
        return code;
    }

    private String msg() {
        return msg;
    }
}
