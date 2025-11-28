package com.codeWithProject.ecom.helper;

import org.springframework.stereotype.Component;

@Component
public class ResponseHelper {


    public <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public <T> ApiResponse<T> failure(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }

    public <T> ApiResponse<T> failureAnalysis(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
