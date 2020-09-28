package com.clevercattv.top.book.client;

import com.clevercattv.top.book.client.util.QueryConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.util.Optional;

@Getter
@Component
public class LibGenClient extends BasicClientImpl {

    private static final String ENDPOINT = "http://libgen.rs/json.php";
    private final QueryConverter converter;

    public LibGenClient(@Qualifier("libGenClientErrorHandler") ResponseErrorHandler errorHandler,
                        @Qualifier("libGenQueryConverter") QueryConverter converter) {
        super(errorHandler);
        this.converter = converter;
    }

    @Override
    public <T> Optional<T> call(String query) {
        return super.call(converter.fromQuery(String.format("%s%s", ENDPOINT, query)));
    }

}
