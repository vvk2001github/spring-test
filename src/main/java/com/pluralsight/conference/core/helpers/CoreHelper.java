package com.pluralsight.conference.core.helpers;

import org.springframework.stereotype.Component;

@Component
public class CoreHelper {

    public Integer paginationPageSize() {
        return 3;
    }

    public Integer paginationRelativeLinksCount() {
        return 2;
    }

}
