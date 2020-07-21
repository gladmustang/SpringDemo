package com.example.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties (prefix="httpbin")
public class HttpBinConfigurer {
    private String host = "localhost";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
