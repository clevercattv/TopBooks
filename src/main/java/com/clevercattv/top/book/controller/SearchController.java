package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.dto.BookResponse;
import com.clevercattv.top.book.entity.ClientType;
import com.clevercattv.top.book.service.facade.ExternalApiFacade;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(produces = "application/json")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ExternalApiFacade facade;

    @GetMapping(value = "/search", params = {"search"})
    public List<BookResponse> findAllByAnyField(
            @RequestParam @NotBlank String search,
            @PageableDefault Pageable pageable) {
        return facade.findAllByAnyField(search, pageable);
    }

    @GetMapping(value = "/search", params = {"search", "clientTypes"})
    public List<BookResponse> findAllByAnyField(
            @RequestParam @NotBlank String search,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) List<ClientType> clientTypes) {
        return facade.findAllByAnyField(search, pageable, clientTypes);
    }

    @GetMapping(value = "/last", params = {"pageable"})
    public List<BookResponse> findAllByOrderByDateDesc(
            @PageableDefault Pageable pageable) {
        return facade.findAllByOrderByDateDesc(pageable);
    }

    @GetMapping(value = "/last", params = {"pageable", "clientTypes"})
    public List<BookResponse> findAllByOrderByDateDesc(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) List<ClientType> clientTypes) {
        return facade.findAllByOrderByDateDesc(pageable, clientTypes);
    }

    @GetMapping("/detailed")
    public BookResponse findDetailedById(
            @RequestParam @NotBlank String id,
            @RequestParam ClientType type) {
        return facade.findDetailedById(id, type);
    }

}
