package com.clevercattv.top.book.client.util;

import java.util.Map;

public interface QueryConverter {

    String fromQuery(String query);

    Map<String, String> getKeywords();

}
