package com.clevercattv.top.book.client;

import org.springframework.http.HttpMethod;

import java.util.Optional;

public interface BasicClient {

    <T> Optional<T> call(String query);

    <T> Optional<T> call(String query, HttpMethod httpMethod, Object... queryVariables);

}
