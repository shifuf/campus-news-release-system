package com.campus.exception;


import com.campus.util.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice

public class GlobalExceptionHandler {



//    /**
//     * 运行时异常处理器
//     * @param e e
//     * @return {@link Result}<{@link String}>
//     */
//    @ExceptionHandler(RuntimeException.class)
//    public Result<String> runtimeExceptionHandler(RuntimeException e) {
//        return Result.failed(e.getMessage(), "服务器出现异常");
//    }
//
//
//    /**
//     * 异常处理器
//     * 兜底处理所有异常
//     * @param e e
//     * @return {@link Result}<{@link String}>
//     */
//    @ExceptionHandler(Exception.class)
//    public Result<String> exceptionHandler(Exception e) {
//        return Result.failed(e.getMessage(), "服务器出现异常");
//    }
}
