package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.ItBookDetailedResponse;
import com.clevercattv.top.book.dto.ItBookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Getter
@Component("itBookClient")
public class ItBookClient extends BookClientImpl<ItBookResponse> {

    private static final String API_URL = "https://api.itbook.store/1.0";
    private static final String SEARCH_ENDPOINT = API_URL + "/search/%s";
    private static final String DETAILED_ENDPOINT = API_URL + "/books/%s";
    private static final String LAST_ENDPOINT = API_URL + "/new";

    private final HttpEntity<String> requestEntity;

    public ItBookClient(@Qualifier("itBookClientErrorHandler") ResponseErrorHandler errorHandler,
                        RestTemplate restTemplate,
                        ObjectMapper objectMapper) {
        super(errorHandler, restTemplate, objectMapper);

        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Application");
        requestEntity = new HttpEntity<>(headers);
    }

    /**
     * Client for work with api.itbook.store.
     * Have search (without pagination)
     * @param searchParam Book title
     * @param pageable Ignored
     * @return BookResponse
     */
    @Override
    public Optional<ItBookResponse> search(String searchParam, Pageable pageable) {
        return call(String.format(SEARCH_ENDPOINT, searchParam), HttpMethod.GET, requestEntity);
    }

    @Override // todo[FIX] unchecked conversion
    @SuppressWarnings("unchecked")
    public Optional<ItBookDetailedResponse> detailed(String searchParam) {
        return Optional.ofNullable(
                restTemplate.exchange(String.format(DETAILED_ENDPOINT, searchParam),
                        HttpMethod.GET, requestEntity, ItBookDetailedResponse.class).getBody()
        );
    }

    /**
     * This API have no pagination on this endpoint
     * @param pageable ignored
     * @return ItBookBookResponse
     */
    @Override
    public Optional<ItBookResponse> last(Pageable pageable) {
        return call(LAST_ENDPOINT, HttpMethod.GET, requestEntity);
    }

}
