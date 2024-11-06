package com.kjm.sample.rxjava.rxjavarestapi.config.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        
        // FIXME 로그인 처리는 여기서 해야함 
        // FIXME 만약 세션을 사용하지 않는다면 추가하지 않아도 됨
        if(session == null || session.getAttribute("user") == null) {            
            return true;
        }

        return true;
    }
}
