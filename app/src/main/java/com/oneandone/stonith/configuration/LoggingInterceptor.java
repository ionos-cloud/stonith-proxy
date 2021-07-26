package com.oneandone.stonith.configuration;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        MDC.put("request-id", UUID.randomUUID().toString());
        MDC.put("request-ip", request.getRemoteAddr());
        MDC.put("x-forwarded-for", request.getHeader("X-Forwarded-For"));
        return true;
    }
}