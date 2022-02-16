package com.pluralsight.conference.core.helpers;

import org.springframework.stereotype.Component;

@Component
public class CoreHelper {

    public Long paginationPageSize() {
        return 3l;
    }

    public Long paginationRelativeLinksCount() {
        return 2l;
    }

}
