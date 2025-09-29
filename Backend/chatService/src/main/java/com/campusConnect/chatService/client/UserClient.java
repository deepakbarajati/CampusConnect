package com.campusConnect.chatService.client;

import com.campusConnect.chatService.advice.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class UserClient {
    private final RestClient restClient;
    public Boolean userExist(Long userId){
        String uri=""+userId;
        ApiResponse<Boolean> exist= restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return exist.getData();
    }
}
