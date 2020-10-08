package com.clevercattv.top.book.service.facade;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExternalApiFacade {

    ApiResponse<List<BookResponse>> findAllRandomBooks(Pageable pageable);

    ApiResponse<List<BookResponse>> findAllRandomBooks(Pageable pageable, List<ClientType> clientTypes);

    ApiResponse<List<BookResponse>> findAllByAnyField(String searchParam, Pageable pageable);

    ApiResponse<List<BookResponse>> findAllByAnyField(String searchParam, Pageable pageable, List<ClientType> clientTypes);

    ApiResponse<List<BookResponse>> findAllByOrderByDateDesc(Pageable pageable);

    ApiResponse<List<BookResponse>> findAllByOrderByDateDesc(Pageable pageable, List<ClientType> clientTypes);

    ApiResponse<BookResponse> findDetailedById(String id, ClientType type);

}
