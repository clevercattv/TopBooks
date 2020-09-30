package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.LibGenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
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
                        RestTemplate restTemplate,
                        ObjectMapper objectMapper) {
        super(errorHandler, restTemplate, objectMapper);
    }

    @Override
    public Optional<LibGenResponse> last(Pageable pageable) {
        Optional<List<LibGenResponse.Book>> booksOptional = batchCall(String.format(lastEndpoint,
                pageable.getOffset(),
                pageable.getPageSize(),
                LocalDate.now().format(formatter)
        ));
        return booksOptional.map(LibGenResponse::new);
    }

}
