package com.kjm.sample.rxjava.rxjavarestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kjm.sample.rxjava.rxjavarestapi")
public class RxjavarestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RxjavarestapiApplication.class, args);
	}

}
