package com.active.apigateway;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@Slf4j
public class AuthenticationGatewayFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFactory.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthenticationGatewayFactory(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst("Authorization");

            return webClientBuilder.build().get()
                    .uri("lb://auth-service/api/auth/token")
                    .header("Authorization", token)
                    .retrieve().bodyToMono(String.class)
                    .map(response -> {
                        exchange.getRequest().mutate().header("uid", response);
                        return exchange;
                    }).flatMap(chain::filter)
                    .onErrorResume(error -> {
                        HttpStatusCode errorCode = null;
                        String errorMsg = "";
                        if (error instanceof WebClientResponseException webCLientException) {
                            errorCode = webCLientException.getStatusCode();
                            errorMsg = webCLientException.getStatusText();

                        } else {
                            errorCode = HttpStatus.BAD_GATEWAY;
                            errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                        }


                        ServerHttpResponse response = exchange.getResponse();
                        byte[] bodyBytes = "Token validation error".getBytes();
                        DataBuffer buffer = response.bufferFactory().wrap(bodyBytes);
                        response.setStatusCode(errorCode);
                        response.getHeaders().add("Content-Type", "application/json");

                        return response.writeWith(Mono.just(buffer));
                    });
        };
    }

    @NoArgsConstructor
    public static class Config {
    }

}
