package com.clevercattv.top.book.dto;

import com.clevercattv.top.book.entity.ClientType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItBookDetailedResponse extends BookResponse {
// https://api.itbook.store/

    public ItBookDetailedResponse() {
        type = ClientType.IT_BOOK;
    }

    private String error;
    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String language;
    private String isbn10;
    private String isbn13;
    private String pages;
    private String year;
    private String rating;
    private String desc;
    private String price;
    private String image;
    private String url;
    private Map<String, String> pdf;

}
