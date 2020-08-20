package com.example.reactivedemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Main implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Mono<String> data = Mono.just("foo");
        System.out.println(data.block());

        Flux<String> fluxData = Flux.just("str1", "str2");
        fluxData.subscribe(item-> {
            System.out.println(item);
        });

        Flux<Integer> ints = Flux.range(1, 9).
                map(i -> {
                    if(i != 3) {
                        return i;
                    } else {
                        throw new RuntimeException("i is 3");
                    }
                });
        ints.subscribe(i-> {
            System.out.println(i);
        }, error->{
            System.err.println(error);
        });

    }
}
