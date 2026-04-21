package com.campusConnect.classroomService.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<Boolean> userExistsWithId(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri("lb://authService/campusConnect/users/{userId}", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .doOnError(error -> log.error("Error calling authService for user {}: {}", userId, error.getMessage()))
                .onErrorResume(error -> Mono.just(false)); // Fallback to false
    }
}
