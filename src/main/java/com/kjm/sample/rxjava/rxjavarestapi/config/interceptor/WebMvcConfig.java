package com.kjm.sample.rxjava.rxjavarestapi.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configurable
public class WebMvcConfig implements WebMvcConfigurer{
	
	 @Autowired
	 private TestInterceptor testIntercptor;
	 
	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(testIntercptor)
         .addPathPatterns("/api/book");
	 }
}
