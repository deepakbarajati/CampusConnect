package com.campusConnect.classroomService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authService", path = "/campusConnect")
public interface UserClient {

    @GetMapping("/users/{userId}")
    Boolean userExistWithId(@PathVariable("userId") Long userId);

}
