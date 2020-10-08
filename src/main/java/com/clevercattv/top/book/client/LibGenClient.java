package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.LibGenResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component("libGenClient")
@PropertySource(value = "classpath:client.properties")
public class LibGenClient extends BookClientImpl<LibGenResponse> {

    @Value("${LibGen.url}${LibGen.endpoints.last}")
    private String lastEndpoint;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LibGenClient(@Qualifier("libGenClientErrorHandler") ResponseErrorHandler errorHandler,
                        RestTemplate restTemplate) {
        super(errorHandler, restTemplate);
    }

    @Override
    public Optional<ApiResponse<LibGenResponse>> last(Pageable pageable) {
        String query = String.format(lastEndpoint,
                pageable.getOffset(),
                pageable.getPageSize(),
                LocalDate.now().format(formatter)
        );
        return Optional.ofNullable(restTemplate.exchange(
                query, HttpMethod.GET, null, new ParameterizedTypeReference<List<LibGenResponse.Book>>() {
                }).getBody())
                .map(LibGenResponse::new)
                .map(ApiResponse::new);
    }

}
