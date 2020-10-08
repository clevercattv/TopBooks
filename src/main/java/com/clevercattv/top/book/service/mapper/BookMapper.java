package com.clevercattv.top.book.service.mapper;

import com.clevercattv.top.book.dto.client.BookResponse;
import com.clevercattv.top.book.dto.client.ItBookResponse;
import com.clevercattv.top.book.dto.client.LibGenResponse;
import com.clevercattv.top.book.entity.Book;
import com.clevercattv.top.book.entity.ClientType;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Service
@Getter
public class BookMapper {

    private final Map<ClientType, Function<BookResponse, Book>> mappers = new EnumMap<>(ClientType.class);

    public BookMapper() {
        mappers.put(ClientType.LIB_GEN, response -> fromLibGen((LibGenResponse) response));
        mappers.put(ClientType.IT_BOOK, response -> fromItBook((ItBookResponse) response));
    }

    public List<Book> fromResponses(List<BookResponse> response) {
        return response.stream()
                .map(this::fromResponse)
                .collect(Collectors.toList());
    }

    public Book fromResponse(BookResponse response) {
        return mappers.get(response.getType()).apply(response);
    }

    Book fromLibGen(LibGenResponse response) {
        return null;
    }

    Book fromItBook(ItBookResponse response) {
        return null;
    }

}
