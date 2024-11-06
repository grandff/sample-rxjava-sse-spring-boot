package com.kjm.sample.rxjava.rxjavarestapi.common.utility;

import java.util.Optional;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class IPUtils {
    
    /**
     * 클라이언트 IP 주소를 안전하게 추출하는 메소드
     * - X-Forwarded-For 헤더 확인
     * - Proxy-Client-IP 헤더 확인
     * - WL-Proxy-Client-IP 헤더 확인
     * - HTTP_CLIENT_IP 헤더 확인
     * - HTTP_X_FORWARDED_FOR 헤더 확인
     * - RemoteAddr 값 반환
     * 
     * @return 클라이언트 IP 주소, 추출 실패시 "Unknown"
     */
    public static String getClientIP() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .filter(ServletRequestAttributes.class::isInstance)
            .map(ServletRequestAttributes.class::cast)
            .map(ServletRequestAttributes::getRequest)
            .map(request -> {
                String ip = null;
                
                // X-Forwarded-For 체크
                ip = request.getHeader("X-Forwarded-For");
                if (isValidIP(ip)) {
                    return extractFirstIP(ip);
                }
                
                // Proxy-Client-IP 체크
                ip = request.getHeader("Proxy-Client-IP");
                if (isValidIP(ip)) {
                    return ip;
                }
                
                // WL-Proxy-Client-IP 체크
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (isValidIP(ip)) {
                    return ip;
                }
                
                // HTTP_CLIENT_IP 체크
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (isValidIP(ip)) {
                    return ip;
                }
                
                // HTTP_X_FORWARDED_FOR 체크
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (isValidIP(ip)) {
                    return ip;
                }
                
                // RemoteAddr 반환
                ip = request.getRemoteAddr();
                if (isValidIP(ip)) {
                    return ip;
                }
                
                return "Unknown";
            })
            .orElse("Unknown");
    }
    
    /**
     * IP 주소가 유효한지 확인
     * @param ip 검사할 IP 주소
     * @return 유효성 여부
     */
    private static boolean isValidIP(String ip) {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }
    
    /**
     * X-Forwarded-For 헤더에서 첫 번째 IP 추출
     * @param ip X-Forwarded-For 헤더 값
     * @return 첫 번째 IP 주소
     */
    private static String extractFirstIP(String ip) {
        if (ip.contains(",")) {
            return ip.split(",")[0].trim();
        }
        return ip;
    }
}
