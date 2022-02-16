package com.pluralsight.conference.ctx1.helpers;

import java.util.Comparator;

import com.pluralsight.conference.core.model.Exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.MessageSource;

@Component
public class Helper {
    
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
}
