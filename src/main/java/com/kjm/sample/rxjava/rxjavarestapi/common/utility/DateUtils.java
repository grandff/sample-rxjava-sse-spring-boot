package com.kjm.sample.rxjava.rxjavarestapi.common.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    private static final DateTimeFormatter DEFAULT_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatNow() {
        return LocalDateTime.now().format(DEFAULT_FORMATTER);
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (StringUtils.isEmpty(dateTimeStr)) return null;
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }

    public static String getRelativeTimeString(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        
        if (minutes < 1) return "방금 전";
        if (minutes < 60) return minutes + "분 전";
        
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        if (hours < 24) return hours + "시간 전";
        
        long days = ChronoUnit.DAYS.between(dateTime, now);
        if (days < 30) return days + "일 전";
        
        return formatDate(dateTime);
    }

}
