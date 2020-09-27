package com.clevercattv.top.book.client.util;

import com.clevercattv.top.book.util.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LibGenQueryConverter extends AbstractQueryConverter {

    public LibGenQueryConverter() {
        super(MapUtils.asMap(Arrays.asList(
                "image", "coverurl",
                "authors", "author"
        )));
    }

}
