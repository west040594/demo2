package com.example.demo;

import com.example.demo.dto.Test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/metadata")
public class TestController {

    private final Consumer<String> consumer = System.out::println;

    @PostMapping(value = "/init", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Test> init(@RequestBody Object object) {
        Mono<Test> publisher = Mono.deferContextual(contextView -> Mono.just(new Test("HelloWorld!")))
                .subscribeOn(Schedulers.boundedElastic());

        publisher.subscribe(test -> consumer.accept(test.getValue()));
        return publisher;
    }


}
