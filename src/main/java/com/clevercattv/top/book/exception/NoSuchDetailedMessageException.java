package com.clevercattv.top.book.exception;

public class NoSuchDetailedMessageException extends RuntimeException {

    private static final long serialVersionUID = 2277915251043191023L;

    public NoSuchDetailedMessageException() {
    }

    public NoSuchDetailedMessageException(String message) {
        super(message);
    }
}
