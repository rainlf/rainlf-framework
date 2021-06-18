package com.railf.framework.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : rain
 * @date : 2020/6/1 16:02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    private static final String standardFormat = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(standardFormat);
    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat(standardFormat));

    public static long getTimestamp() {
        return new Date().getTime() / 1000;
    }

    public static String format(Date date) {
        return date != null ? sdf.get().format(date) : null;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String format(LocalDateTime localDateTime) {
        return dtf.format(localDateTime);
    }

    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(localDateTime);
    }

    public static Date parse(String date) {
        try {
            return sdf.get().parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Parse date failed: " + date, e);
        }
    }

    public static Date parse(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Parse date failed: " + date, e);
        }
    }

    public static LocalDateTime parseDateTime(String date) {
        return LocalDateTime.parse(date, dtf);
    }

    public static LocalDateTime parseDateTime(String date, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dtf);
    }
}
