package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.BookResponse;
import com.clevercattv.top.book.entity.Book;
import com.clevercattv.top.book.entity.User;
import com.clevercattv.top.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/{id}")
    public ApiResponse<Book> findBook(@PathVariable Long id, User user) {
        return ApiResponse.ok(service.findBook(id, user.getId()));
    }

    @GetMapping
    public ApiResponse<List<Book>> findAllBooks(User user) {
        return ApiResponse.ok(service.findAllBooks(user.getId()));
    }

    @PostMapping(params = {"book"})
    public ApiResponse<Long> addBook(
            // BookResponse is a list of books, change it to new class
            BookResponse book, User user) {
        return ApiResponse.ok(service.addBook(book, user));
    }

    @PostMapping(params = {"books"})
    public ApiResponse<List<Long>> addBooks(
            @RequestBody List<BookResponse> books, User user) {
        return ApiResponse.ok(service.addBooks(books, user));
    }

    @PutMapping
    public ApiResponse<Book> updateBook(Book book, User user) {
        return ApiResponse.ok(service.updateBook(book, user.getId()));
    }

    @DeleteMapping(value = "/{id}", params = {"id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id, User user) {
        service.deleteBook(id, user.getId());
    }

    @DeleteMapping(params = {"ids"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooks(List<Long> ids, User user) {
        service.deleteBooks(ids, user.getId());
    }

}
