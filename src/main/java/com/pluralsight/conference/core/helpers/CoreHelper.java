package com.pluralsight.conference.core.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoreHelper {

    @Value("${pagination.page.size:5}")
    private Long pageSize;

    @Value("${pagination.relativeLinks.Count:4}")
    private Long relativeLinksCount;

    public Long paginationPageSize() {
        return this.pageSize;
    }

    public Long paginationRelativeLinksCount() {
        return this.relativeLinksCount;
    }

}
