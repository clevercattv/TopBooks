package com.clevercattv.top.book.service;

import com.clevercattv.top.book.service.facade.ExternalApiFacade;
import com.clevercattv.top.book.dto.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ExternalApiFacade apiFacade;

    public List<BookResponse> findAllByAnyField(String search, Pageable pageable) {
        return apiFacade.findAllByAnyField(search, pageable);
    }

    public List<BookResponse> findAllByOrderByDateDesc(Pageable pageable) {
        return apiFacade.findAllByOrderByDateDesc(pageable);
    }

    public BookResponse findDetailedById(String id, ClientType type) {
        return apiFacade.findDetailedById(id, type);
    }
}
