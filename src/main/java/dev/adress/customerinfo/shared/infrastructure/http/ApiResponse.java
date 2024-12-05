package dev.adress.customerinfo.shared.infrastructure.http;

import java.util.Map;

public record ApiResponse<T>(boolean success, String message, String code, T data, Map<String, Object> errors) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Request was successful", "operation_success", data, null);
    }

    public static ApiResponse<?> error(String message, String code, Map<String, Object> errors) {
        return new ApiResponse<>(false, message, code, null, errors);
    }
}
