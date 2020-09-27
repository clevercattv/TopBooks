package com.clevercattv.top.book.dto;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
public class ItBookBookResponse {
// https://api.itbook.store/
    @Id
    private Long isbn13;

    private String title;
    private String authors;
    private String publisher;
    private String pages;
    private String year;
    private String desc; // description
    private String image;
    private String url;

}
