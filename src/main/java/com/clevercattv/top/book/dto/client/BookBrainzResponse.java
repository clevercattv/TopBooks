package com.clevercattv.top.book.dto.client;

import lombok.Data;

@Data
public class BookBrainzResponse {
// https://bookbrainz.org/develop

    private String bbid;

    private String authorType;
    private String beginArea;
    private String beginDate;
    private DefaultAlias defaultAlias;
    private String disambiguation;
    private String endArea;
    private String endDate;
    private String ended;
    private String gender;

    @Data
    class DefaultAlias {
        private String language;
        private String name;
        private String primary;
        private String sortName;
    }

}
