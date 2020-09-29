package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.dto.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import com.clevercattv.top.book.service.SearchService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "Searching books on external API", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService service;

    @GetMapping("/search")
    public List<BookResponse> findAllByAnyField(@RequestParam String search, @PageableDefault Pageable pageable) {
        return service.findAllByAnyField(search, pageable);
    }

    @GetMapping("/last")
    public List<BookResponse> findAllByOrderByDateDesc(@PageableDefault Pageable pageable) {
        return service.findAllByOrderByDateDesc(pageable);
    }

    @GetMapping("/detailed")
    public BookResponse findDetailedById(@RequestParam("id") String id, @RequestParam("type") ClientType type) {
        return service.findDetailedById(id, type);
    }

}
