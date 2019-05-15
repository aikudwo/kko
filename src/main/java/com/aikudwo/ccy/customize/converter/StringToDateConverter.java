package com.aikudwo.ccy.customize.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String SHORT_MONTH_FORMAT = "yyyy-MM";
    private static final String DOT_DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
    private static final String SHORT_DOT_DATE_FORMAT = "yyyy.MM.dd";
    private static final String SHORT_DOT_MONTH_FORMAT = "yyyy.MM";

    @Override
    public Date convert(@NonNull String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();
        try {
            if (source.contains("-")) {
                SimpleDateFormat formatter;
                if (source.contains(":")) {
                    formatter = new SimpleDateFormat(DATE_FORMAT);
                } else if (source.lastIndexOf("-") == source.indexOf("-")) {
                    formatter = new SimpleDateFormat(SHORT_MONTH_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                return formatter.parse(source);
            } else if (source.contains(".")) {
                SimpleDateFormat formatter;
                if (source.contains(":")) {
                    formatter = new SimpleDateFormat(DOT_DATE_FORMAT);
                } else if (source.lastIndexOf(".") == source.indexOf(".")) {
                    formatter = new SimpleDateFormat(SHORT_DOT_MONTH_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DOT_DATE_FORMAT);
                }
                return formatter.parse(source);
            } else if (source.matches("^\\d+$")) {
                Long lDate = new Long(source);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", source));
    }
}
