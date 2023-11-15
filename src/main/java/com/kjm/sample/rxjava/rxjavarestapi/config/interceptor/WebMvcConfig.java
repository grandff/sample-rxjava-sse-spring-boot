package com.kjm.sample.rxjava.rxjavarestapi.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	 //@Autowired
	 //private TestInterceptor testIntercptor;
	 
	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 System.out.println("호호호출WebMvcConfigurer come hsere");
		 registry.addInterceptor(new TestInterceptor())
         .addPathPatterns("/api/book/**");
	 }
}
