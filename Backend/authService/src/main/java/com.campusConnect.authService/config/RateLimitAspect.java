package com.campusConnect.authService.config;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitAspect {

    private final HttpServletRequest request;

    // Simple in-memory rate limiter: IP -> request count
    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final long RATE_LIMIT_WINDOW_MS = 60000; // 1 minute
    private final int MAX_REQUESTS_PER_WINDOW = 10;

    @Around("@annotation(com.campusConnect.authService.config.RateLimited)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        String clientIp = getClientIp();

        AtomicInteger count = requestCounts.computeIfAbsent(clientIp, k -> new AtomicInteger(0));

        if (count.incrementAndGet() > MAX_REQUESTS_PER_WINDOW) {
            log.warn("Rate limit exceeded for IP: {}", clientIp);
            throw new RuntimeException("Rate limit exceeded");
        }

        try {
            return joinPoint.proceed();
        } finally {
            // Reset counter after time window
            new Thread(() -> {
                try {
                    Thread.sleep(RATE_LIMIT_WINDOW_MS);
                    requestCounts.remove(clientIp);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    private String getClientIp() {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
