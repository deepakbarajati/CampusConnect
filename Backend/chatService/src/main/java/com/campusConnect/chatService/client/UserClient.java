package com.campusConnect.chatService.client;

import com.campusConnect.chatService.advice.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authService", url = "http://localhost:8080", path = "api/v1/campusConnect/auth")
//@FeignClient(name = "authService", url = "http://localhost:8080", path = "api/v1/campusConnect/auth")
public interface UserClient {

    @GetMapping("/user/{userId}")
    ResponseEntity<ApiResponse<Boolean>> userExistWithId(@PathVariable("userId") Long userId);
}


