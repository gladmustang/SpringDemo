package com.example.gateway.Filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;



@Component
public class DemoFilter implements WebFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        MultiValueMap<String, HttpCookie> map = serverWebExchange.getRequest().getCookies();
        System.out.println("before request call in filter 1: "+map);

        return webFilterChain.filter(serverWebExchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
