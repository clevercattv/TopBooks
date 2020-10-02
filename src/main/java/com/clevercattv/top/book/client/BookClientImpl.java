package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public abstract class BookClientImpl<T extends BookResponse> implements BookClient<T> {

    protected final RestTemplate restTemplate;
    private final Class<T> actualTypeArgument;

    @SuppressWarnings("unchecked")
    public BookClientImpl(@Qualifier("basicClientErrorHandler") ResponseErrorHandler errorHandler,
                          RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        actualTypeArgument = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BookClientImpl.class);

        restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Optional<T> call(String query, HttpMethod httpMethod,
                            HttpEntity<?> requestEntity, Object... queryVariables) {
        return Optional.ofNullable(
                restTemplate.exchange(query, httpMethod, requestEntity, actualTypeArgument, queryVariables)
                        .getBody()
        );
    }

}
