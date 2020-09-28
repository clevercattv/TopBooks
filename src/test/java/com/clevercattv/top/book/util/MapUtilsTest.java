package com.clevercattv.top.book.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class MapUtilsTest {

    @Test
    void asMap_ShouldThrowIllegalArgumentException_WhenListEmpty() {
        List<Object> emptyList = Collections.emptyList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> MapUtils.asMap(emptyList));
    }

    @Test
    void asMap_ShouldThrowIllegalArgumentException_WhenArgumentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> MapUtils.asMap(null));
    }

    @ParameterizedTest
    @MethodSource("getNotPairedArgumentCount")
    void asMap_ShouldThrowIllegalArgumentException_WhenArgumentCountNotPaired(String[] args) {
        List<String> keyValueList = Arrays.asList(args);

        Assertions.assertThrows(IllegalArgumentException.class, () -> MapUtils.asMap(keyValueList));
    }

    @ParameterizedTest
    @MethodSource("getPairedArgumentCount")
    void asMap_ShouldReturnHashMap_WhenArgumentCountPaired(String[] args, Map<String, String> expected) {
        List<String> keyValueList = Arrays.asList(args);

        Assertions.assertEquals(MapUtils.asMap(keyValueList), expected);
    }

    // Didn't work without cast to Object, with 2+ arguments works fine
    private static Stream<Arguments> getNotPairedArgumentCount() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Test1"}),
                Arguments.of((Object) new String[]{"Test2", "Test2", "Test3"})
        );
    }

    private static Stream<Arguments> getPairedArgumentCount() {
        Map<String, String> firstMap = new HashMap<>();
        Map<String, String> secondMap = new HashMap<>();
        firstMap.put("Test1", "Test2");

        secondMap.put("Test3", "Test4");
        secondMap.put("Test5", "Test6");

        return Stream.of(
                Arguments.of(new String[]{"Test1", "Test2"}, firstMap),
                Arguments.of(new String[]{"Test3", "Test4", "Test5", "Test6"}, secondMap)
        );
    }

}
