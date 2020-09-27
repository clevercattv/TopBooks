package com.clevercattv.top.book.exception.client;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component("basicClientErrorHandler")
public class BasicClientErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            throw new HttpServerErrorException(httpResponse.getStatusCode(), httpResponse.getStatusText());
        }
        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            throw new HttpClientErrorException(httpResponse.getStatusCode(), httpResponse.getStatusText());
        }
        if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new UnknownHttpStatusCodeException(
                    httpResponse.getRawStatusCode(), httpResponse.getStatusText(),
                    httpResponse.getHeaders(), StreamUtils.copyToByteArray(httpResponse.getBody()),
                    null
            );
        }
    }
}
