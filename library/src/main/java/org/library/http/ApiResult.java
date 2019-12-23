package org.library.http;

import androidx.annotation.Nullable;

/**
 * 统一的API请求返回体
 *
 * @param <T> data泛型
 */
public class ApiResult<T> {
    private int code;
    private String message;
    @Nullable
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}
