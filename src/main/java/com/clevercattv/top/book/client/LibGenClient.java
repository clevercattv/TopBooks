package com.clevercattv.top.book.client;

import com.clevercattv.top.book.client.util.LibGenQueryConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Getter
@Component
public class LibGenClient extends BasicClientImpl {

    private final String endpoint = "http://libgen.rs/json.php";
    private final LibGenQueryConverter converter;


    public LibGenClient(@Qualifier("libGenClientErrorHandler") ResponseErrorHandler errorHandler,
                        LibGenQueryConverter converter) {
        super(errorHandler);
        this.converter = converter;
    }

    @Override
    protected String requestFromQuery(String query) {
        return converter.fromQuery(String.format("%s%s", endpoint, query));
    }

}
