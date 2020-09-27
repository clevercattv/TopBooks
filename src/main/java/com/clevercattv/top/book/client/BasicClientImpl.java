package com.clevercattv.top.book.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component // todo requestFromQuery move to another class | remove this class (move all in interface)
public abstract class BasicClientImpl implements BasicClient {

    protected final ResponseErrorHandler errorHandler;

    public BasicClientImpl(@Qualifier("basicClientErrorHandler") ResponseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public <T> Optional<T> call(String query) {
        return call(query, HttpMethod.GET);
    }

    @Override
    public <T> Optional<T> call(String query, HttpMethod httpMethod, Object... queryVariables) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(errorHandler);
        ResponseEntity<T> exchange = restTemplate.exchange(requestFromQuery(query), httpMethod,
                null, new ParameterizedTypeReference<T>() {
                }, queryVariables);
        if (exchange.getStatusCode().isError()) {
            return Optional.empty();
        }
        return Optional.ofNullable(exchange.getBody());
    }

    protected abstract String requestFromQuery(String query);

}
