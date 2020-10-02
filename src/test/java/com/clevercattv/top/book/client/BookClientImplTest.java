package com.clevercattv.top.book.client;

import com.clevercattv.top.book.dto.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookClientImplTest {

    private static class TestBookClient extends BookClientImpl<BookResponse> {
        public TestBookClient(ResponseErrorHandler errorHandler, RestTemplate restTemplate) {
            super(errorHandler, restTemplate);
        }
    }

    @Mock
    private ResponseErrorHandler errorHandler;

    @Mock
    private RestTemplate restTemplate;

    private BookClient<BookResponse> client;
    private Class<BookResponse> resultType;

    @BeforeEach
    public void beforeEach() {
        client = spy(new TestBookClient(errorHandler, restTemplate));
        resultType = (Class<BookResponse>) GenericTypeResolver.resolveTypeArgument(client.getClass(), BookClientImpl.class);
    }

    @ParameterizedTest
    @MethodSource("checkInterfaceMethods")
    void defaultMethods_ShouldCallDefaultMethod_ReturnEmptyOptional(Function<BookClient<BookResponse>, Optional<BookResponse>> methodResult) {
        assertEquals(Optional.empty(), methodResult.apply(client));
    }

    @ParameterizedTest
    @MethodSource("call_Url")
    void call_ShouldCallOverloadedMethod_ReturnBookResponse(String url) {
        HttpMethod httpMethod = HttpMethod.GET;
        ResponseEntity<BookResponse> responseEntity = new ResponseEntity<>(new BookResponse(), HttpStatus.OK);

        when(restTemplate.exchange(url, httpMethod, null, resultType)).thenReturn(responseEntity);

        assertEquals(Optional.of(new BookResponse()), client.call(url));

        verify(restTemplate, times(1)).exchange(url, httpMethod, null, resultType);
        verify(client, times(1)).call(any());
        verify(client, times(1)).call(any(), any(), any());
        verify(client, times(1)).call(any(), any(), any(), any());
    }

    @ParameterizedTest
    @MethodSource("call_UrlHttpMethod")
    void call_ShouldCallOverloadedMethod_ReturnBookResponse(String url, HttpMethod httpMethod) {
        ResponseEntity<BookResponse> responseEntity = new ResponseEntity<>(new BookResponse(), HttpStatus.OK);

        when(restTemplate.exchange(url, httpMethod, null, resultType)).thenReturn(responseEntity);

        assertEquals(Optional.of(new BookResponse()), client.call(url, httpMethod));

        verify(restTemplate, times(1)).exchange(url, httpMethod, null, resultType);
        verify(client, times(1)).call(any(), any(), any());
        verify(client, times(1)).call(any(), any(), any(), any());
    }

    @ParameterizedTest
    @MethodSource("callCheck")
    void call(String url, HttpMethod httpMethod, HttpEntity<String> httpEntity) {
        ResponseEntity<BookResponse> responseEntity = new ResponseEntity<>(new BookResponse(), HttpStatus.OK);

        when(restTemplate.exchange(url, httpMethod, httpEntity, resultType)).thenReturn(responseEntity);

        assertEquals(Optional.of(new BookResponse()), client.call(url, httpMethod, httpEntity));

        verify(restTemplate, times(1)).exchange(url, httpMethod, httpEntity, resultType);
        verify(client, times(1)).call(any(), any(), any(), any());
    }

    private static Stream<String> call_Url() {
        return Stream.of("", "http://localhost:8080/last");
    }

    private static Stream<Arguments> callCheck() {
        return Stream.of(
                Arguments.of("http://localhost:8080/", HttpMethod.GET, new HttpEntity<>(new HttpHeaders())),
                Arguments.of("http://localhost:8080/last", HttpMethod.POST, null)
        );
    }

    private static Stream<Arguments> call_UrlHttpMethod() {
        return Stream.of(
                Arguments.of("", HttpMethod.GET),
                Arguments.of("", HttpMethod.POST),
                Arguments.of("http://localhost:8080/last", HttpMethod.GET),
                Arguments.of("http://localhost:8080/last", HttpMethod.POST)
        );
    }

    private static Stream<Function<BookClient<BookResponse>, Optional<BookResponse>>> checkInterfaceMethods() {
        return Stream.of(
                client -> client.detailed("1001601301730"),
                client -> client.last(PageRequest.of(0, 10)),
                client -> client.search("The Basics of User Experience Design", PageRequest.of(1, 10))
        );
    }

}
