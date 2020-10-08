package com.clevercattv.top.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private final List<String> errors;
    private final T body;

    public ApiResponse(List<String> errors) {
        this.errors = errors;
        body = null;
    }

    public ApiResponse(T body) {
        this.body = body;
        errors = Collections.emptyList();
    }

    public static <T> ApiResponse<T> ok(T body) {
        return new ApiResponse<>(Collections.emptyList(), body);
    }

    public static <T> ApiResponse<List<T>> emptyList() {
        return new ApiResponse<>(new ArrayList<>(), new ArrayList<>());
    }

}
