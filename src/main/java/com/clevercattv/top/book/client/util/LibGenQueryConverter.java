package com.clevercattv.top.book.client.util;

import com.clevercattv.top.book.util.MapUtils;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@Getter
public class LibGenQueryConverter extends AbstractQueryConverter {

    private final Map<String, String> keywords = MapUtils.asMap(
            Arrays.asList(
                    "image", "coverurl",
                    "authors", "author"
            )
    );

}
