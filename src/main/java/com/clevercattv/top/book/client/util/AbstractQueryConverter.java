package com.clevercattv.top.book.client.util;

import java.util.Map;

public abstract class AbstractQueryConverter implements QueryConverter {

    @Override
    public String fromQuery(String query) {
        if (query.length() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(query);
        for (Map.Entry<String, String> entry : getKeywords().entrySet()) {
            String key = entry.getKey();
            int index = builder.indexOf(key);
            if (index != -1) {
                builder.replace(index, index + key.length(), entry.getValue());
            }
        }
        return builder.toString();
    }

}
