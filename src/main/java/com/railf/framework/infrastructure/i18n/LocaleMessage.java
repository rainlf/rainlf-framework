package com.railf.framework.infrastructure.i18n;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author : rain
 * @date : 2021/5/19 16:43
 * <p>
 * HTTP Header:
 * Accept-Language: en-US
 * Accept-Language: zh-CN
 */
@Component
public class LocaleMessage implements InitializingBean {

    private static LocaleMessage instance;

    public static String local(String code) {
        return instance.getMessage(code);
    }

    public static String local(String code, Object... args) {
        return instance.getMessage(code, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LocaleMessage.instance = this;
    }

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code) {
        return this.getMessage(code, new Object[]{});
    }

    public String getMessage(String code, String defaultMessage) {
        return this.getMessage(code, null, defaultMessage);
    }

    public String getMessage(String code, String defaultMessage, Locale locale) {
        return this.getMessage(code, null, defaultMessage, locale);
    }

    public String getMessage(String code, Locale locale) {
        return this.getMessage(code, null, "", locale);
    }

    public String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, "");
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return this.getMessage(code, args, "", locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(code, args, defaultMessage, locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}