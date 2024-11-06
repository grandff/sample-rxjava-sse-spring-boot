package com.kjm.sample.rxjava.rxjavarestapi.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private long accessTokenValidityInMinutes = 30;
    private long refreshTokenValidityInDays = 7;
}
