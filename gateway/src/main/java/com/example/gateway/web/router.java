package com.example.gateway.web;


import com.example.gateway.object.Quote;
import com.example.gateway.service.restTemplate.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class router {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

    @RequestMapping("/demoService")
    public Quote demoCtl(){
        return demoService.getQuote();

    }
}
