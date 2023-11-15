package com.kjm.sample.rxjava.rxjavarestapi.config.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@WebFilter
@Component
@Order(1)
public class WebFilterConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 로직
        System.out.println("Initiating filter...");
    }


    @Override
    public void destroy() {
        // 필터 종료 로직
        System.out.println("Destroying filter...");
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		// TODO Auto-generated method stub
		 System.out.println("Filtering request...");
        chain.doFilter(request, response); // 다음 필터로 요청 전달
        System.out.println("Filtering response...");
		
	}
}
