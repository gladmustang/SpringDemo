package com.example.gateway.service.restTemplate;

import com.example.gateway.GatewayApplication;
import com.example.gateway.object.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DemoService {
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(DemoService.class);

    public Quote getQuote(){
        Quote quote = restTemplate.getForObject(
                "https://gturnquist-quoters.cfapps.io/api/random", Quote.class);

        log.info(quote.toString());
        return quote;
    }

}
