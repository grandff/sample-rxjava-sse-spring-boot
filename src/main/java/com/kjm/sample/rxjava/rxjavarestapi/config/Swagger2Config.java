package com.kjm.sample.rxjava.rxjavarestapi.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger2Config {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition") // 그룹이름 설정
                .pathsToMatch("/api/**") // API 명세서에 적을 주소
                .build();

    }

    @Bean
    public OpenAPI openAPiInfo() {
        return new OpenAPI()
                .info(new Info().title("Sample REST API with RxJava and SSE")
                        .description("RxJava와 SSE를 추가한 REST API 서비스")
                        .version("v1.0.0"));
    }

}
