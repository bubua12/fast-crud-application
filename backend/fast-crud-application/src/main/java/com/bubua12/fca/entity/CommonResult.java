package com.bubua12.fca.entity;

import lombok.Data;

/**
 * 统一响应结果
 *
 * @author bubua12
 * @since 2026/6/9
 */
@Data
public class CommonResult<T> {

    private int code;
    private String message;
    private T data;

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> CommonResult<T> error(String message) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
