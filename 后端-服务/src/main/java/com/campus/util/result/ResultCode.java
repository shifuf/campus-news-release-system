package com.campus.util.result;

/**
 * API返回码封装类
 *
 * @author Yhx
 * @date 2023/5/15 16:12
 */
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAILED(400, "操作失败");
    private final Integer code;
    private final String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
