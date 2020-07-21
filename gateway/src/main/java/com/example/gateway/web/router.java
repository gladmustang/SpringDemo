package com.example.gateway.web;


import com.alibaba.fastjson.JSONObject;
import com.example.gateway.object.Quote;
import com.example.gateway.service.restTemplate.DemoService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/demoService2/{sleepTime}")
    @HystrixCommand(fallbackMethod = "fallbackCall", commandKey = "demoService2")
    public JSONObject demoCtl2(@PathVariable int sleepTime){

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = demoService.getQuote2();

        JSONObject jsonObject = JSONObject.parseObject(result);
        jsonObject.getJSONObject("value");

        return jsonObject;

    }

    public JSONObject fallbackCall(@PathVariable int sleepTime) {
        return JSONObject.parseObject("{'result': 'this is a fallback of demoService2'}");
    }
}
