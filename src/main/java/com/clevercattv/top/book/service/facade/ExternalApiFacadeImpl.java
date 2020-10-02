package com.clevercattv.top.book.service.facade;

import com.clevercattv.top.book.client.BookClient;
import com.clevercattv.top.book.dto.BookResponse;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExternalApiFacadeImpl implements ExternalApiFacade {

    @Qualifier("externalApiFacadeClients")
    private final Map<String, BookClient<BookResponse>> clients;

    @Override
    public BookResponse findDetailedById(String id, ClientType type) {
        return clients.get(type.getClientName()).detailed(id)
                .orElseThrow(NoSuchDetailedMessageException::new);
    }

    @Override
    public List<BookResponse> findAllRandomBooks(Pageable pageable) {
        return null;
    }

    @Override
    public List<BookResponse> findAllRandomBooks(Pageable pageable, List<ClientType> clientTypes) {
        return null;
    }

    @Override
    public List<BookResponse> findAllByAnyField(String searchParam, Pageable pageable) {
        return getBookResponses(client -> client.search(searchParam, pageable));
    }

    @Override
    public List<BookResponse> findAllByAnyField(String searchParam, Pageable pageable, List<ClientType> clientTypes) {
        return getBookResponses(clientTypes, client -> client.search(searchParam, pageable));
    }

    @Override
    public List<BookResponse> findAllByOrderByDateDesc(Pageable pageable) {
        return getBookResponses(client -> client.last(pageable));
    }

    @Override
    public List<BookResponse> findAllByOrderByDateDesc(Pageable pageable, List<ClientType> clientTypes) {
        return getBookResponses(clientTypes, client -> client.last(pageable));
    }

    private List<BookResponse> getBookResponses(
            Function<BookClient<? extends BookResponse>, Optional<? extends BookResponse>> function) {
        return clients.values()
                .parallelStream()
                .map(function)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<BookResponse> getBookResponses(
            List<ClientType> clientTypes,
            Function<BookClient<? extends BookResponse>, Optional<? extends BookResponse>> function) {
        return clients.entrySet()
                .parallelStream()
                .filter(entry -> containsClient(entry.getKey(), clientTypes))
                .map(Map.Entry::getValue)
                .map(function)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private boolean containsClient(String name, List<ClientType> clientTypes) {
        return clientTypes.stream()
                .map(ClientType::getClientName)
                .anyMatch(clientName -> clientName.equals(name));
    }

}
