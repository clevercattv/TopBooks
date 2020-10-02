package com.clevercattv.top.book.service.facade;

import com.clevercattv.top.book.dto.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExternalApiFacade {

    /**
     *
     * @param pageable
     * @return
     */
    List<BookResponse> findAllRandomBooks(Pageable pageable);

    List<BookResponse> findAllRandomBooks(Pageable pageable, List<ClientType> clientTypes);

    /**
     * Call all clients and return not empty results.
     * @param searchParam
     * @param pageable
     * @return
     */
    List<BookResponse> findAllByAnyField(String searchParam, Pageable pageable);

    List<BookResponse> findAllByAnyField(String searchParam, Pageable pageable, List<ClientType> clientTypes);

    List<BookResponse> findAllByOrderByDateDesc(Pageable pageable);

    List<BookResponse> findAllByOrderByDateDesc(Pageable pageable, List<ClientType> clientTypes);

    BookResponse findDetailedById(String id, ClientType type);

}
