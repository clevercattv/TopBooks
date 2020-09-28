package com.clevercattv.top.book.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapUtils {

    /**
     * Generate hashmap from list.
     * @param list (key1, value1, key2, value2 and so on..)
     * @return HashMap with inserted key/value set
     */
    public static <T> Map<T, T> asMap(List<T> list) {
        if (Objects.isNull(list)) {
            throw new IllegalArgumentException("List can't be null!");
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List can't be empty!");
        }
        if (list.size() % 2 == 1) {
            throw new IllegalArgumentException("List count must be paired!");
        }
        Map<T, T> map = new HashMap<>();
        for (int i = 0; i < list.size() - 1; i = i + 2) {
            map.put(list.get(i), list.get(i + 1));
        }
        return map;
    }

}
