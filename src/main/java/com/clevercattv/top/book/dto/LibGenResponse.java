package com.clevercattv.top.book.dto;

import com.clevercattv.top.book.entity.ClientType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LibGenResponse extends BookResponse {

    public LibGenResponse(List<Book> books) {
        this.books = books;
        type = ClientType.LIB_GEN;
    }

    private List<Book> books;

    @Data
    public static class Book {
        private String title;
        private String author;
        private LocalDate year;
        private String publisher;
        private Integer pages;
        private String language;
        private String coverurl;
    }

}
