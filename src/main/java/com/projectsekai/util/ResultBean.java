package com.projectsekai.util;

import java.io.Serializable;

/**
 * 返回页面的基础bean，提供统一返回标准
 *
 * @author znn
 */
public class ResultBean<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3649363290407304698L;

    /**
     * result结果
     */
    public static final String SUCCESS = "1";

    /**
     * result结果
     */
    public static final String FAILURE = "0";

    /**
     * 返回结果
     */
    private String result;

    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 错误编码	10000:数据操作失败，20000：网络连接失败,30000：参数验证失败
     */
    private String errCode;

    /**
     * 返回结果
     */
    private T data;

    private String message;

    /**
     * 有查询的数据时，返回的查询结果信息
     */
    private PageVO page;

    /**
     * 构造一个默认成功的结果
     */
    public ResultBean() {
        this.result = SUCCESS;
        this.errCode = "";
        this.errMsg = "";
        this.message = "";
        this.page = new PageVO();
    }

    /**
     * 构造一个设置了data返回值的成功的结果
     *
     * @param data
     */
    public ResultBean(T data) {
        this.result = SUCCESS;
        this.data = data;
    }

    /**
     * 构造错误结果，设置错误消息和错误结果
     *
     * @param errorCode 错误编码
     * @param errorMsg  错误消息
     */
    public ResultBean(String errorCode, String errorMsg) {
        this.fail(errorCode, errorMsg);
    }

    public void fail(String errorCode, String errorMsg) {
        this.result = FAILURE;
        this.errCode = errorCode;
        this.errMsg = errorMsg;
    }

    public void fail(String errorMsg) {
        this.result = FAILURE;
        this.errMsg = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageVO getPage() {
        return page;
    }

    public void setPage(PageVO page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ResultBean [result=" + result + ", errMsg=" + errMsg + ", errCode=" + errCode + ", data=" + data
                + ", page=" + page + "]";
    }

}
