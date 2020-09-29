package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.LibGenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter
@Component("libGenClient")
public class LibGenClient extends BookClientImpl<LibGenResponse> {

    private static final String ENDPOINT = "http://libgen.rs/json.php" +
            "?fields=title,author,year,publisher,pages,language,coverurl" +
            "&limit1=%s" +
            "&limit2=%s" +
            "&mode=last" +
            "&timefirst=%s";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LibGenClient(@Qualifier("libGenClientErrorHandler") ResponseErrorHandler errorHandler,
                        RestTemplate restTemplate,
                        ObjectMapper objectMapper) {
        super(errorHandler, restTemplate, objectMapper);
    }

    @Override
    public Optional<LibGenResponse> last(Pageable pageable) {
        Optional<List<LibGenResponse.Book>> booksOptional = batchCall(String.format(ENDPOINT,
                pageable.getOffset(),
                pageable.getPageSize(),
                LocalDate.now().format(formatter)
        ));
        return booksOptional.map(LibGenResponse::new);
    }

}
