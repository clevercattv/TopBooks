package com.clevercattv.top.book.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LibGenBookResponse {

    private String title;
    private String author;
    private LocalDate year;
    private String publisher;
    private Integer pages;
    private String language;
    private String coverurl; // image

}
