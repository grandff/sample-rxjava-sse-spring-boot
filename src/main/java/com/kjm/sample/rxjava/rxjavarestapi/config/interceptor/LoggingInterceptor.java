package com.kjm.sample.rxjava.rxjavarestapi.config.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kjm.sample.rxjava.rxjavarestapi.common.utility.IPUtils;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        String clientIP = IPUtils.getClientIP();

        log.debug("Request [{}]: {}", clientIP, requestURI);
        request.setAttribute("requestStartTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("requestStartTime");
        long endTime = System.currentTimeMillis();

        log.debug("Response : {} ({}ms)", request.getRequestURI(), endTime - startTime);
    }
}
