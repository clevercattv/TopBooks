package com.clevercattv.top.book.dto;

import com.clevercattv.top.book.entity.ClientType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItBookResponse extends BookResponse {
// https://api.itbook.store/

    public ItBookResponse() {
        type = ClientType.IT_BOOK;
    }

    private String error;
    private String total;
    private String page;
    private List<Book> books;

    @Data
    static class Book {
        private String title;
        private String subtitle;
        private String isbn13;
        private String price;
        private String image;
        private String url;
    }

}
