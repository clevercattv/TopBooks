package com.clevercattv.top.book.service.facade;

import com.clevercattv.top.book.client.BookClient;
import com.clevercattv.top.book.dto.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import com.clevercattv.top.book.exception.NoSuchDetailedMessageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalApiFacadeImplTest {

    private static final BookResponse IT_BOOK_RESPONSE = new BookResponse() {{
        setType(ClientType.IT_BOOK);
    }};

    private static final BookResponse LIB_GEN_RESPONSE = new BookResponse() {{
        setType(ClientType.LIB_GEN);
    }};

    @Mock
    private BookClient<BookResponse> basicClient;
    @Mock
    private BookClient<BookResponse> itBookClient;
    @Mock
    private BookClient<BookResponse> libGenClient;

    @Mock
    private Map<String, BookClient<BookResponse>> clients;

    private ExternalApiFacade facade;

    @BeforeEach
    void setUp() {
        facade = new ExternalApiFacadeImpl(clients);
    }

    @Test
    void findDetailedById_emptyResponse_ReturnsException() {
        String id = "1300202030";
        ClientType type = ClientType.IT_BOOK;
        Optional<BookResponse> result = Optional.empty();

        when(clients.get(type.getClientName())).thenReturn(itBookClient);
        when(itBookClient.detailed(id)).thenReturn(result);

        assertThrows(NoSuchDetailedMessageException.class, () -> facade.findDetailedById(id, type));

        verify(itBookClient, times(1)).detailed(id);
        verifyClients(Arrays.asList(basicClient, libGenClient),
                client -> verify(client, never()).detailed(id));
    }

    @Test
    void findDetailedById_response_ReturnsException() {
        String id = "1300202030";
        ClientType type = ClientType.IT_BOOK;
        Optional<BookResponse> result = Optional.of(IT_BOOK_RESPONSE);

        when(clients.get(type.getClientName())).thenReturn(itBookClient);
        when(itBookClient.detailed(id)).thenReturn(result);

        assertEquals(IT_BOOK_RESPONSE, facade.findDetailedById(id, type));

        verify(itBookClient, times(1)).detailed(id);
        verifyClients(Arrays.asList(basicClient, libGenClient),
                client -> verify(client, never()).detailed(id));
    }

    @Test
    void findAllRandomBooks() {
    }

    @ParameterizedTest
    @MethodSource("findAllByAnyFieldArguments")
    void findAllByAnyField_MultipleClients_ReturnNotEmptyResponseList(
            BookResponse basicResponse,
            BookResponse itBookResponse,
            BookResponse libGenResponse,
            List<BookResponse> resultList,
            String search,
            Pageable pageable) {
        when(basicClient.search(search, pageable)).thenReturn(Optional.ofNullable(basicResponse));
        when(itBookClient.search(search, pageable)).thenReturn(Optional.ofNullable(itBookResponse));
        when(libGenClient.search(search, pageable)).thenReturn(Optional.ofNullable(libGenResponse));
        when(clients.values()).thenReturn(Arrays.asList(basicClient, itBookClient, libGenClient));

        assertIterableEquals(
                resultList,
                facade.findAllByAnyField(search, pageable));

        verify(clients, times(1)).values();
        verifyClients(Arrays.asList(basicClient, itBookClient, libGenClient),
                client -> verify(client, times(1)).search(search, pageable));
    }

    @ParameterizedTest
    @MethodSource("findAllByOrderByDateDescArguments")
    void findAllByOrderByDateDesc_MultipleClients_ReturnNotEmptyResponseList(
            BookResponse basicResponse,
            BookResponse itBookResponse,
            BookResponse libGenResponse,
            List<BookResponse> resultList,
            Pageable pageable) {
        when(basicClient.last(pageable)).thenReturn(Optional.ofNullable(basicResponse));
        when(itBookClient.last(pageable)).thenReturn(Optional.ofNullable(itBookResponse));
        when(libGenClient.last(pageable)).thenReturn(Optional.ofNullable(libGenResponse));
        when(clients.values()).thenReturn(Arrays.asList(basicClient, itBookClient, libGenClient));

        assertIterableEquals(
                resultList,
                facade.findAllByOrderByDateDesc(pageable));

        verify(clients, times(1)).values();
        verifyClients(Arrays.asList(basicClient, itBookClient, libGenClient),
                client -> verify(client, times(1)).last(pageable));
    }

    private static Stream<Arguments> findAllByOrderByDateDescArguments() {
        return Stream.of(
                Arguments.of(null, null, null,
                        Collections.emptyList(), PageRequest.of(0, 10)),
                Arguments.of(null, null, LIB_GEN_RESPONSE,
                        Collections.singletonList(LIB_GEN_RESPONSE), PageRequest.of(1, 10)),
                Arguments.of(null, IT_BOOK_RESPONSE, LIB_GEN_RESPONSE,
                        Arrays.asList(IT_BOOK_RESPONSE, LIB_GEN_RESPONSE), PageRequest.of(1, 20))
        );
    }

    private static Stream<Arguments> findAllByAnyFieldArguments() {
        return Stream.of(
                Arguments.of(null, null, null,
                        Collections.emptyList(), "", PageRequest.of(0, 10)),
                Arguments.of(null, null, LIB_GEN_RESPONSE,
                        Collections.singletonList(LIB_GEN_RESPONSE), "DevOps WTF?", PageRequest.of(1, 10)),
                Arguments.of(null, IT_BOOK_RESPONSE, LIB_GEN_RESPONSE,
                        Arrays.asList(IT_BOOK_RESPONSE, LIB_GEN_RESPONSE), "Search", PageRequest.of(1, 20))
        );
    }

    private void verifyClients(List<BookClient<BookResponse>> clients,
                               Function<BookClient<BookResponse>, Optional<BookResponse>> function) {
        clients.forEach(function::apply);
    }

}
