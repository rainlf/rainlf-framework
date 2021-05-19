package com.railf.framework.adapter.dto;

import lombok.Data;

/**
 * @author : rain
 * @date : 2021/4/29 18:43
 */
@Data
public class APIResponse<T> {
    private boolean success;
    private T data;
    private String errorCode;
    private String errorMessage;

    public static <T> APIResponse<T> ok() {
        APIResponse<T> apiResponse = new APIResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(null);
        return apiResponse;
    }

    public static <T> APIResponse<T> ok(T data) {
        APIResponse<T> apiResponse = new APIResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> APIResponse<T> error(String errorMessage) {
        APIResponse<T> apiResponse = new APIResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setErrorMessage(errorMessage);
        return apiResponse;
    }

}
