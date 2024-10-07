package com.campus.util.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回结果封装类
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据封装
     */
    private T data;

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 成功返回默认消息
     *
     */
    public static <T> Result<T> success() {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     * @param  message 提示信息
     */
    public static <T> Result<T> success(String message) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), message, null);
    }
    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> Result<T> failed(ResultCode errorCode) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> Result<T> failed(ResultCode errorCode,String message) {
        return new Result<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> Result<T> failed(String message) {
        return new Result<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param data 失败返回封装数据
     */
    public static <T> Result<T> failed(T data) {
        return new Result<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }

    /**
     * 失败返回封装结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> Result<T> failed(T data, String message) {
        return new Result<T>(ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed() {
        return failed(ResultCode.FAILED.getMessage());
    }


    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
