package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.BookResponse;
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
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(produces = "application/json")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ExternalApiFacade facade;

    @GetMapping(value = "/search", params = {"search"})
    public ApiResponse<List<BookResponse>> findAllByAnyField(
            @RequestParam @NotBlank String search,
            @PageableDefault Pageable pageable) {
        ApiResponse<List<BookResponse>> allByAnyField = facade.findAllByAnyField(search, pageable);
        return allByAnyField;
    }

    @GetMapping(value = "/search", params = {"search", "clientTypes"})
    public ApiResponse<List<BookResponse>> findAllByAnyField(
            @RequestParam @NotBlank String search,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) @NotEmpty List<ClientType> clientTypes) {
        return facade.findAllByAnyField(search, pageable, clientTypes);
    }

    @GetMapping(value = "/last")
    public ApiResponse<List<BookResponse>> findAllByOrderByDateDesc(
            @PageableDefault Pageable pageable) {
        return facade.findAllByOrderByDateDesc(pageable);
    }

    @GetMapping(value = "/last", params = {"clientTypes"})
    public ApiResponse<List<BookResponse>> findAllByOrderByDateDesc(
            @PageableDefault Pageable pageable,
            @RequestParam @NotEmpty List<ClientType> clientTypes) {
        return facade.findAllByOrderByDateDesc(pageable, clientTypes);
    }

    @GetMapping("/detailed")
    public ApiResponse<BookResponse> findDetailedById(
            @RequestParam @NotBlank String id,
            @RequestParam ClientType type) {
        return facade.findDetailedById(id, type);
    }

}
