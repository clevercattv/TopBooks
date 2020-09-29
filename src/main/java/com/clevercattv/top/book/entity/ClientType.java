package com.clevercattv.top.book.entity;

import lombok.Getter;

@Getter
public enum ClientType {
    IT_BOOK("itBookClient"),
    LIB_GEN(""),
    BOOK_BRAINZ(""),
    GOOD_READS(""),
    GOOGLE(""),
    ARCHIVE("");

    private String clientName;

    ClientType(String clientName) {
        this.clientName = clientName;
    }

}
