package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.BookResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Optional;

public interface BookClient<T extends BookResponse> {

    default Optional<ApiResponse<T>> last(Pageable pageable) {
        return Optional.empty();
    }

    default Optional<ApiResponse<T>> search(String searchParam, Pageable pageable) {
        return Optional.empty();
    }

    default <R extends BookResponse> Optional<ApiResponse<R>> detailed(String searchParam) {
        return Optional.empty();
    }

    default Optional<ApiResponse<T>> call(String query) {
        return call(query, HttpMethod.GET);
    }

    default Optional<ApiResponse<T>> call(String query, HttpMethod httpMethod) {
        return call(query, httpMethod, null);
    }

    Optional<ApiResponse<T>> call(String query, HttpMethod httpMethod, HttpEntity<?> requestEntity);

}
