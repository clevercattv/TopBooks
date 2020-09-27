package com.clevercattv.top.book.client.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public abstract class AbstractQueryConverter implements QueryConverter {

    private final Map<String, String> keywords;

    @Override
    public String fromQuery(String query) {
        StringBuilder builder = new StringBuilder(query);
        for (Map.Entry<String, String> entry : keywords.entrySet()) {
            String key = entry.getKey();
            int index = builder.indexOf(key);
            builder.replace(index, index + key.length(), entry.getValue());
        }
        return builder.toString();
    }

}
