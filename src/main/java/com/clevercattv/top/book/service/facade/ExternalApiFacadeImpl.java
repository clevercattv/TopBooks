package com.clevercattv.top.book.service.facade;

import com.clevercattv.top.book.client.BookClient;
import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import com.clevercattv.top.book.exception.NoSuchDetailedMessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ExternalApiFacadeImpl implements ExternalApiFacade {

    @Qualifier("externalApiFacadeClients")
    private final Map<String, BookClient<BookResponse>> clients;

    @Override
    public ApiResponse<BookResponse> findDetailedById(String id, ClientType type) {
        return clients.get(type.getClientName())
                .detailed(id)
                .orElseThrow(NoSuchDetailedMessageException::new);
    }

    @Override
    public ApiResponse<List<BookResponse>> findAllRandomBooks(Pageable pageable) {
        return null;
    }

    @Override
    public ApiResponse<List<BookResponse>> findAllRandomBooks(Pageable pageable, List<ClientType> clientTypes) {
        return null;
    }

    @Override
    public ApiResponse<List<BookResponse>> findAllByAnyField(String searchParam, Pageable pageable) {
        return getBookResponses(client -> client.search(searchParam, pageable));
    }

    @Override
    public ApiResponse<List<BookResponse>> findAllByAnyField(
            String searchParam, Pageable pageable, List<ClientType> clientTypes) {
        return getBookResponses(clientTypes, client -> client.search(searchParam, pageable));
    }

    @Override
    public ApiResponse<List<BookResponse>> findAllByOrderByDateDesc(Pageable pageable) {
        return getBookResponses(client -> client.last(pageable));
    }

    @Override
    public ApiResponse<List<BookResponse>> findAllByOrderByDateDesc(Pageable pageable, List<ClientType> clientTypes) {
        return getBookResponses(clientTypes, client -> client.last(pageable));
    }

    private ApiResponse<List<BookResponse>> getBookResponses(
            Function<BookClient<BookResponse>, Optional<ApiResponse<BookResponse>>> function) {
        return clients.values()
                .parallelStream()
                .map(function)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(ApiResponse.multipleResponse(), this::accumulateResponses, this::combineResponses);
    }

    private ApiResponse<List<BookResponse>> getBookResponses(
            List<ClientType> clientTypes,
            Function<BookClient<BookResponse>, Optional<ApiResponse<BookResponse>>> function) {
        return clients.entrySet()
                .parallelStream()
                .filter(entry -> containsClient(entry.getKey(), clientTypes))
                .map(Map.Entry::getValue)
                .map(function)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(ApiResponse.multipleResponse(), this::accumulateResponses, this::combineResponses);
    }

    private boolean containsClient(String name, List<ClientType> clientTypes) {
        return clientTypes.stream()
                .map(ClientType::getClientName)
                .anyMatch(clientName -> clientName.equals(name));
    }

    private ApiResponse<List<BookResponse>> accumulateResponses(
            ApiResponse<List<BookResponse>> listApiResponse, ApiResponse<BookResponse> apiResponse) {

        listApiResponse.getErrors().addAll(apiResponse.getErrors());
        Optional.ofNullable(apiResponse.getBody())
                .ifPresent(bookResponse -> listApiResponse.getBody().add(bookResponse));
        return listApiResponse;
    }

    private ApiResponse<List<BookResponse>> combineResponses(
            ApiResponse<List<BookResponse>> resultResponse, ApiResponse<List<BookResponse>> lastIterationResponse) {
        return resultResponse;
    }

}
