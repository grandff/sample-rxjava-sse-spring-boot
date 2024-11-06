package com.kjm.sample.rxjava.rxjavarestapi.common.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String maskEmail(String email) {
        if (isEmpty(email)) return "";
        String[] parts = email.split("@");
        if (parts.length != 2) return email;
        
        String name = parts[0];
        String domain = parts[1];
        
        if (name.length() <= 2) {
            return name.charAt(0) + "*@" + domain;
        }
        
        return name.charAt(0) + "*".repeat(name.length() - 2) + 
               name.charAt(name.length() - 1) + "@" + domain;
    }

    public static String maskPhoneNumber(String phone) {
        if (isEmpty(phone)) return "";
        return phone.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
    }
}
