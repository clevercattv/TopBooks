package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.client.BasicClient;
import com.clevercattv.top.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    @Qualifier("basicClientImpl")
    private final BasicClient client;

    @GetMapping
    public Book getBooks() {
        return null;
    }

    @GetMapping("test")
    public List<Book> getLibGen() {
        final String uri = "http://libgen.rs/json.php?fields=id,title,series,author,year,edition,publisher,pages,language,filesize,extension,timeadded,timelastmodified,coverurl&limit1=10&mode=last&timefirst=2020-01-01";
//        final String uri = "https://api.itbook.store/1.0/search/mongodb";


        Optional<List<Book>> optionalBooks = client.call(uri);
        return optionalBooks.orElse(Collections.emptyList());
    }



}
