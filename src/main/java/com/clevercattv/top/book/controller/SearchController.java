package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.entity.Book;
import com.clevercattv.top.book.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService service;

    @GetMapping
    public List<Book> findBooks(String query, List<String> clientEndpoints) {
        return service.findBooks(query, clientEndpoints);
    }

}
