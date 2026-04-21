package com.campusConnect.opportunityService.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this.error = error;
    }
}
