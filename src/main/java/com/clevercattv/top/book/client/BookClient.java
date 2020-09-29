package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.BookResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Optional;

public interface BookClient<T extends BookResponse> {

    default Optional<T> last(Pageable pageable) {
        return Optional.empty();
    }

    default Optional<T> search(String searchParam, Pageable pageable) {
        return Optional.empty();
    }

    default <R extends  BookResponse> Optional<R> detailed(String searchParam) {
        return Optional.empty();
    }

    default Optional<T> call(String query) {
        return call(query, HttpMethod.GET);
    }

    default Optional<T> call(String query, HttpMethod httpMethod, Object... queryVariables) {
        return call(query, httpMethod, null, queryVariables);
    }

    Optional<T> call(String query, HttpMethod httpMethod, HttpEntity<?> requestEntity, Object... queryVariables);

    default <R> Optional<List<R>> batchCall(String query) {
        return batchCall(query, HttpMethod.GET);
    }

    default <R> Optional<List<R>> batchCall(String query, HttpMethod httpMethod, Object... queryVariables) {
        return batchCall(query, httpMethod, null, queryVariables);
    }

    <R> Optional<List<R>> batchCall(String query, HttpMethod httpMethod, HttpEntity<?> requestEntity, Object... queryVariables);

}
