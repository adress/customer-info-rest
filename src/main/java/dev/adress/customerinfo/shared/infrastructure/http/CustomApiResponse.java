package dev.adress.customerinfo.shared.infrastructure.http;

import java.util.Map;

public record CustomApiResponse<T>(boolean success, String message, String code, T data, Map<String, Object> errors) {

    public static <T> CustomApiResponse<T> success(T data) {
        return new CustomApiResponse<>(true, "Request was successful", "operation_success", data, null);
    }

    public static CustomApiResponse<?> error(String message, String code, Map<String, Object> errors) {
        return new CustomApiResponse<>(false, message, code, null, errors);
    }
}
