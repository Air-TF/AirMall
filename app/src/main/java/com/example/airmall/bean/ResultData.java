package com.example.airmall.bean;

/**
 *  统一响应结构
 */
public abstract class ResultData<T> {

    private static final String OK = "ok";
    private static final String ERROR = "error";

    private Meta meta;     // 元数据
    private T data;   // 响应内容

    public ResultData success() {
        this.meta = new Meta(true, OK);
        return this;
    }

    public ResultData success(T data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }

    public ResultData failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public ResultData failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }

    /**
     * 请求元数据
     */
    public class Meta {

        private boolean success;
        private String message;

        public Meta(boolean success) {
            this.success = success;
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}