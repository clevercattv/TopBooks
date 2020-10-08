package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.ItBookDetailedResponse;
import com.clevercattv.top.book.dto.client.ItBookResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component("itBookClient")
@PropertySource(value = "classpath:client.properties")
public class ItBookClient extends BookClientImpl<ItBookResponse> {

    @Value("${ItBook.url}${ItBook.endpoints.search}")
    private String searchEndpoint;

    @Value("${ItBook.url}${ItBook.endpoints.detailed}")
    private String detailedEndpoint;

    @Value("${ItBook.url}${ItBook.endpoints.last}")
    private String lastEndpoint;

    private final HttpEntity<String> requestEntity;

    public ItBookClient(@Qualifier("itBookClientErrorHandler") ResponseErrorHandler errorHandler,
                        RestTemplate restTemplate) {
        super(errorHandler, restTemplate);

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
    public Optional<ApiResponse<ItBookResponse>> search(String searchParam, Pageable pageable) {
        return call(String.format(searchEndpoint, searchParam), HttpMethod.GET, requestEntity);
    }

    @Override // todo[IMPROVEMENT] unchecked conversion <R> to <ItBookDetailedResponse>
    @SuppressWarnings("unchecked")
    public Optional<ApiResponse<ItBookDetailedResponse>> detailed(String searchParam) {
        return Optional.ofNullable(
                restTemplate.exchange(String.format(detailedEndpoint, searchParam),
                        HttpMethod.GET, requestEntity, ItBookDetailedResponse.class).getBody()
        ).map(ApiResponse::new);
    }

    /**
     * This API have no pagination on this endpoint
     * @param pageable ignored
     * @return ItBookBookResponse
     */
    @Override
    public Optional<ApiResponse<ItBookResponse>> last(Pageable pageable) {
        if (pageable.getPageNumber() != 0) {
            return Optional.empty();
        }
        return call(lastEndpoint, HttpMethod.GET, requestEntity);
    }

}
