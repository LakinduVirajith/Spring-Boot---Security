package com.spring_boot.security_example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final WebClient webClient;

    @GetMapping("/api/hello")
    public String hello(Principal principal){
        return "Hello" + principal.getName()+ ", Welcome to Spring Security";
    }

    @GetMapping("/api/users")
    public String[] users(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client) {
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/users")
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }
}
