package com.clevercattv.top.book.client.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class AbstractQueryConverterTest {

    private static final String KEY_1 = "images";
    private static final String KEY_2 = "author";
    private static final String VALUE_1 = "image";
    private static final String VALUE_2 = "authors";

    private final QueryConverter converter = new AbstractQueryConverter() {
        @Override
        public Map<String, String> getKeywords() {
            Map<String, String> map = new HashMap<>();
            map.put(KEY_1, VALUE_1);
            map.put(KEY_2, VALUE_2);
            return map;
        }
    };

    @Test
    void fromQuery_ShouldReturnEmptyString_WhenEmptyQuery() {
        String input = "";
        String expected = "";

        Assertions.assertEquals(expected, converter.fromQuery(input));
    }

    @Test
    void fromQuery_ShouldReturnQuery_WhenNoKeywords() {
        String input = "?fields=id,title,series";
        String expected = "?fields=id,title,series";

        Assertions.assertEquals(expected, converter.fromQuery(input));
    }

    @Test
    void fromQuery_ShouldReturnConvertedQuery_WhenContainsKeywords() {
        String input = String.format("?fields=id,title,series,%s,%s", KEY_1, KEY_2);
        String expected = String.format("?fields=id,title,series,%s,%s", VALUE_1, VALUE_2);

        Assertions.assertEquals(expected, converter.fromQuery(input));
    }

}
