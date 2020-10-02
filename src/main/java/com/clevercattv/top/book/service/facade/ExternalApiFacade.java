package com.clevercattv.top.book.service.facade;

import com.clevercattv.top.book.dto.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExternalApiFacade {

    List<BookResponse> findAllRandomBooks(Pageable pageable);

    List<BookResponse> findAllByAnyField(String searchParam, Pageable pageable);

    List<BookResponse> findAllByOrderByDateDesc(Pageable pageable);

    BookResponse findDetailedById(String id, ClientType type);
}
