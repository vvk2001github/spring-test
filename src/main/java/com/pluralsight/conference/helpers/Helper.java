package com.pluralsight.conference.helpers;

import java.util.Comparator;

import com.pluralsight.conference.model.Exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.MessageSource;

@Component
public class Helper {

    @Value("${pagination.page.size:5}")
    private Long pageSize;

    @Value("${pagination.relativeLinks.Count:4}")
    private Long relativeLinksCount;
    
    @Autowired
    public MessageSource messageSource;

    // Sort and Compare by Exercise.descr
    public Comparator<Exercise> compareExByDescr = Comparator.comparing(Exercise::getDescr);

    public String exTypeToString(Integer t) {
        switch(t) {
            case 0:
                return messageSource.getMessage("exmessages.Type0", null, LocaleContextHolder.getLocale());
            case 1:
                return messageSource.getMessage("exmessages.Type1", null, LocaleContextHolder.getLocale());
            case 2:
                return messageSource.getMessage("exmessages.Type2", null, LocaleContextHolder.getLocale());
            case 3:
                return messageSource.getMessage("exmessages.Type3", null, LocaleContextHolder.getLocale());
            default:
                return messageSource.getMessage("exmessages.TypeUndefined", null, LocaleContextHolder.getLocale());
        }
    }

    public String getLocalizedMsg(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

    public Long paginationPageSize() {
        return this.pageSize;
    }

    public Long paginationRelativeLinksCount() {
        return this.relativeLinksCount;
    }

}
