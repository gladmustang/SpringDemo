package com.example.gateway.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlMappingConfigurer {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, HttpBinConfigurer httpbin) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpbin.getHost()))
//                .route(p -> p //fallback for failing
//                        .path("/delay/3")
//                        .filters(f -> f.hystrix(config -> config
//                                .setName("mycmd")
//                                .setFallbackUri("forward:/fallback")))
//                        .uri(httpbin.getHost()))
                .route(p -> p
                        .path("/delay/4")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpbin.getHost()))
//                .route(p -> p //match a scope of url
//                        .path("/notes/*")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://localhost:8081"))
                .build();
    }


//    @Bean
//    public HystrixCircuitBreakerFactory defaultConfig() {
//        HystrixCircuitBreakerFactory circuitBreakerFactory = new HystrixCircuitBreakerFactory();
//        circuitBreakerFactory.configureDefault(id -> HystrixCommand.Setter
//                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id)).andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000)));
//
//        return circuitBreakerFactory;
//    }

}
